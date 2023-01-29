package hearth.entities.bases;

import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.*;
import hearth.content.HUnits;
import mindustry.Vars;
import mindustry.ai.Pathfinder;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.graphics.InverseKinematics;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.*;

public class HLegsUnit extends HUnitEntity {
    //todo stop foot from rotating while placed
    //todo custom weapon class, can move, fortify multipliers

    private static final Vec2 straightVec = new Vec2();

    transient Leg[] legs = {};
    transient float totalLength;
    transient float moveSpace;
    transient float baseRotation;
    transient Floor lastDeepFloor;
    transient Vec2 curMoveOffset = new Vec2();

    @Override
    public EntityCollisions.SolidPred solidity(){
        return type.allowLegStep ? EntityCollisions::legsSolid : EntityCollisions::solid;
    }

    @Override
    public int pathType(){
        return type.allowLegStep ? Pathfinder.costLegs : Pathfinder.costGround;
    }

    @Override
    public Floor drownFloor(){
        return lastDeepFloor;
    }

    @Override
    public void add(){
        super.add();
        resetLegs();
    }

    @Override
    public void unloaded(){
        super.unloaded();
        resetLegs(1f);
    }

    @Override
    public void destroy(){
        super.destroy();

        if(!isAdded() || Vars.headless) return;

        float legExplodeRad = type.legRegion.height  / 4f / 1.45f;

        //create effects for legs being destroyed
        for(int i = 0; i < legs.length; i++){
            Leg l = legs[i];

            Vec2 base = legOffset(Tmp.v1, i).add(x, y);

            Tmp.v2.set(l.base).sub(l.joint).inv().setLength(type.legExtension);

            for(Vec2 vec : new Vec2[]{base, l.joint, l.base}){
                Damage.dynamicExplosion(vec.x, vec.y, 0f, 0f, 0f, legExplodeRad, state.rules.damageExplosions, false, team, type.deathExplosionEffect);
            }

            Fx.legDestroy.at(base.x, base.y, 0f, new LegDestroyData(base.cpy(), l.joint, type.legRegion));
            Fx.legDestroy.at(l.joint.x, l.joint.y, 0f, new LegDestroyData(l.joint.cpy().add(Tmp.v2), l.base, type.legBaseRegion));

        }
    }

    public void resetLegs(){
        resetLegs(type.legLength);
    }

    public void resetLegs(float legLength){
        int count = type.legCount;

        this.legs = new Leg[count];

        if(type.lockLegBase){
            baseRotation = rotation;
        }

        for(int i = 0; i < legs.length; i++){
            Leg l = new Leg();

            float dstRot = legAngle(i);
            Vec2 baseOffset = legOffset(Tmp.v5, i).add(x, y);

            l.joint.trns(dstRot, legLength/2f).add(baseOffset);
            l.base.trns(dstRot, legLength).add(baseOffset);

            legs[i] = l;
        }
    }

    @Override
    public void update(){
        super.update();

        if(Mathf.dst(deltaX(), deltaY()) > 0.001f){
            baseRotation = Angles.moveToward(baseRotation, Mathf.angle(deltaX(), deltaY()), type.rotateSpeed);
        }

        if(type.lockLegBase){
            baseRotation = rotation;
        }

        float legLength = type.legLength;

        //set up initial leg positions
        if(legs.length != type.legCount){
            resetLegs();
        }

        float moveSpeed = type.legSpeed;
        int div = Math.max(legs.length / type.legGroupSize, 2);
        moveSpace = legLength / 1.6f / (div / 2f) * type.legMoveSpace;
        totalLength += type.legContinuousMove ? type.speed * speedMultiplier * Time.delta : Mathf.dst(deltaX(), deltaY());

        float trns = moveSpace * 0.85f * type.legForwardScl;

        //rotation + offset vector
        boolean moving = moving();
        Vec2 moveOffset = !moving ? Tmp.v4.setZero() : Tmp.v4.trns(Angles.angle(deltaX(), deltaY()), trns);
        //make it smooth, not jumpy
        moveOffset = curMoveOffset.lerpDelta(moveOffset, 0.1f);

        lastDeepFloor = null;
        int deeps = 0;

        for(int i = 0; i < legs.length; i++){
            float dstRot = legAngle(i);
            Vec2 baseOffset = legOffset(Tmp.v5, i).add(x, y);
            Leg l = legs[i];

            l.joint.sub(baseOffset).clampLength(type.legMinLength * legLength/2f, type.legMaxLength * legLength/2f).add(baseOffset);
            l.base.sub(baseOffset).clampLength(type.legMinLength * legLength, type.legMaxLength * legLength).add(baseOffset);

            float stageF = (totalLength + i*type.legPairOffset) / moveSpace;
            int stage = (int)stageF;
            int group = stage % div;
            boolean move = i % div == group;
            boolean side = i < legs.length/2;
            //back legs have reversed directions
            boolean backLeg = Math.abs((i + 0.5f) - legs.length/2f) <= 0.501f;
            if(backLeg && type.flipBackLegs) side = !side;
            if(type.flipLegSide) side = !side;

            l.moving = move;
            l.stage = moving ? stageF % 1f : Mathf.lerpDelta(l.stage, 0f, 0.1f);

            Floor floor = Vars.world.floorWorld(l.base.x, l.base.y);
            if(floor.isDeep()){
                deeps ++;
                lastDeepFloor = floor;
            }

            if(l.group != group){

                //create effect when transitioning to a group it can't move in
                if(!move && (moving || !type.legContinuousMove) && i % div == l.group){
                    if(!headless && !inFogTo(player.team())){
                        if(floor.isLiquid){
                            floor.walkEffect.at(l.base.x, l.base.y, type.rippleScale, floor.mapColor);
                            floor.walkSound.at(x, y, 1f, floor.walkSoundVolume);
                        }else{
                            Fx.unitLandSmall.at(l.base.x, l.base.y, type.rippleScale, floor.mapColor);
                        }

                        //shake when legs contact ground
                        if(type.stepShake > 0){
                            Effect.shake(type.stepShake, type.stepShake, l.base);
                        }
                    }

                    if(type.legSplashDamage > 0){
                        Damage.damage(team, l.base.x, l.base.y, type.legSplashRange, type.legSplashDamage, false, true);
                    }
                }

                l.group = group;
            }

            //leg destination
            Vec2 legDest = Tmp.v1.trns(dstRot, legLength * type.legLengthScl).add(baseOffset).add(moveOffset);
            //join destination
            Vec2 jointDest = Tmp.v2;
            InverseKinematics.solve(legLength/2f, legLength/2f, Tmp.v6.set(l.base).sub(baseOffset), side, jointDest);
            jointDest.add(baseOffset);
            Tmp.v6.set(baseOffset).lerp(l.base, 0.5f);

            if(move){
                float moveFract = stageF % 1f;

                l.base.lerpDelta(legDest, moveFract);
                l.joint.lerpDelta(jointDest, moveFract / 2f);
            }

            l.joint.lerpDelta(jointDest, moveSpeed / 4f);

            //limit again after updating
            l.joint.sub(baseOffset).clampLength(type.legMinLength * legLength/2f, type.legMaxLength * legLength/2f).add(baseOffset);
            l.base.sub(baseOffset).clampLength(type.legMinLength * legLength, type.legMaxLength * legLength).add(baseOffset);
        }

        //when at least 1 leg is touching land, it can't drown
        if(deeps != legs.length || !floorOn().isDeep()){
            lastDeepFloor = null;
        }
    }

    Vec2 legOffset(Vec2 out, int index){
        out.trns(defaultLegAngle(index), type.legBaseOffset);

        if(type.legStraightness > 0){
            straightVec.trns(defaultLegAngle(index) - baseRotation, type.legBaseOffset);
            straightVec.y = Mathf.sign(straightVec.y) * type.legBaseOffset * type.legStraightLength;
            straightVec.rotate(baseRotation);
            out.lerp(straightVec, type.baseLegStraightness);
        }

        return out;
    }

    //@return outwards facing angle of leg at the specified index.
    float legAngle(int index){
        if(type.legStraightness > 0){
            return Mathf.slerp(defaultLegAngle(index), (index >= legs.length/2 ? -90 : 90f) + baseRotation, type.legStraightness);
        }
        return defaultLegAngle(index);
    }

    float defaultLegAngle(int index){
        return baseRotation + 360f / legs.length * index + (360f / legs.length / 2f);
    }

    //bs for existing
    public static HLegsUnit create(){
        return new HLegsUnit();
    }

    @Override
    public int classId(){
        return HUnits.classID(HLegsUnit.class);
    }
}
