package hearth.entities.bases;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import hearth.vfx.HGraphics;
import hearth.content.HResources;
import hearth.content.HUnits;
import mindustry.content.Fx;
import mindustry.entities.Leg;
import mindustry.gen.Unit;
import mindustry.graphics.*;
import mindustry.type.*;

import static hearth.HearthMain.parallax;

public class AhkarUnitType extends UnitType {
    public int podSize = 1;
    public float podTime = 180f;
    public float podFallFract = 0.1f;
    public TextureRegion podRegion;

    public int minArmorTier = 0;
    public float idlePowerCons = 0f, movePowerCons = 1f / 60f;
    public PayloadStack capacities = new PayloadStack(HResources.lens, 2);

    public float
    fortingTime = 2f * 60f,
    fortArmorMult = 1f,
    fortSpeedMult = 0.5f,
    forShadowElevMult = 0.2f;
    public boolean canFort = false;

    public float tractorBeamRange = 8f * 8f;

    private static final Vec2 legOffset = new Vec2();

    public AhkarUnitType(String name){
        super(name);
        outlineColor = HGraphics.outline;
        //controller = u -> !playerControllable || (u.team.isAI() && !u.team.rules().rtsAi) ? aiController.get() : new svfgdzdfdgsafgzAI();
    }

    @Override
    public void init(){
        super.init();

        podRegion = Core.atlas.find("hearth-unit-pod" + podSize, Core.atlas.find("launchpod"));
    }

    @Override
    public void draw(Unit unit){
        if(!unit.hasEffect(HUnits.incoming)) {
            super.draw(unit);
            if (unit instanceof HLegsUnit) drawLegs((HLegsUnit) unit);
        } else {
            Draw.z(110f); Lines.stroke(2f); Draw.color(unit.team.color, Interp.bounceIn.apply(1 - getDropAlpha(unit, podTime)));
                parallax.drawSkyLines(unit.x, unit.y, 6, hitSize + 12f, 999999999, 0);
                Lines.poly(unit.x, unit.y, 6, hitSize + 12f / 3f, 0f);
                Lines.stroke(4f);
                Lines.poly(unit.x, unit.y, 6, hitSize + 12f, 0);

            float alpha = getDropAlpha(unit, podTime * podFallFract);
            Draw.reset(); Draw.z(111f);
                Vec2 pod = parallax.parallax(unit.x, unit.y, alpha * (200 / podFallFract), true);
                Draw.alpha(1 - alpha);
                Draw.rect(podRegion, pod, podRegion.width * (0.5f + alpha), podRegion.height * (0.5f + alpha), 0);

            Draw.reset();

            if(unit.getDuration(HUnits.incoming) < 2) {
                Fx.spawnShockwave.at(unit, podSize * 10f);
                Fx.dynamicExplosion.at(unit, podSize);
            }
        }
    }

    public float getDropAlpha(Unit unit, float time){
        return Mathf.clamp( unit.getDuration(HUnits.incoming) / time, 0f, 1f);
    }

    public void drawLegs(HLegsUnit unit) {
        applyColor(unit);
        Tmp.c3.set(Draw.getMixColor());

        Leg[] legs = unit.legs;

        float ssize = footRegion.width * footRegion.scl() * 1.5f;
        float rotation = unit.baseRotation;
        float invDrown = 1f - unit.drownTime;

        if (footRegion.found()) {
            for (Leg leg : legs) {
                Drawf.shadow(leg.base.x, leg.base.y, ssize, invDrown);
            }
        }

        //legs are drawn front first
        for (int j = legs.length - 1; j >= 0; j--) {
            int i = (j % 2 == 0 ? j / 2 : legs.length - 1 - j / 2);
            Leg leg = legs[i];
            boolean flip = i >= legs.length / 2f;
            int flips = Mathf.sign(flip);

            Vec2 position = unit.legOffset(legOffset, i).add(unit);

            Tmp.v1.set(leg.base).sub(leg.joint).inv().setLength(legExtension);

            if (footRegion.found() && leg.moving && shadowElevation > 0) {
                float scl = shadowElevation * invDrown;
                float elev = Mathf.slope(1f - leg.stage) * scl;
                Draw.color(Pal.shadow);
                Draw.rect(footRegion, leg.base.x + shadowTX * elev, leg.base.y + shadowTY * elev, position.angleTo(leg.base));
                Draw.color();
            }

            Draw.mixcol(Tmp.c3, Tmp.c3.a);

            if (footRegion.found()) {
                Draw.rect(footRegion, leg.base.x, leg.base.y, position.angleTo(leg.base));
            }

            Lines.stroke(legRegion.height * legRegion.scl() * flips);
            Lines.line(legRegion, position.x, position.y, leg.joint.x, leg.joint.y, false);

            Lines.stroke(legBaseRegion.height * legRegion.scl() * flips);
            Lines.line(legBaseRegion, leg.joint.x + Tmp.v1.x, leg.joint.y + Tmp.v1.y, leg.base.x, leg.base.y, false);

            if (jointRegion.found()) {
                Draw.rect(jointRegion, leg.joint.x, leg.joint.y);
            }

            //base joints are drawn after everything else
            if(baseJointRegion.found()){
                for(int n = legs.length - 1; n >= 0; n--){
                    Vec2 pos = unit.legOffset(legOffset, (n % 2 == 0 ? n/2 : legs.length - 1 - n/2)).add(unit);
                    Draw.rect(baseJointRegion, pos.x, pos.y, rotation);
                }
            }

            if(baseRegion.found()){
                Draw.rect(baseRegion, unit.x, unit.y, rotation - 90);
            }
        }
    }
}
