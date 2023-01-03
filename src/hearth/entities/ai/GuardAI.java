package hearth.entities.ai;

import arc.math.Mathf;
import mindustry.entities.Units;
import mindustry.entities.units.AIController;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;

public class GuardAI extends AIController {
    @Override
    public void updateMovement(){
        unloadPayloads();

        if(target != null){
            //moveTo(target, (target instanceof Sized s ? s.hitSize()/2f * 1.1f : 0f) + unit.hitSize/2f + 15f, 50f);
            unit.lookAt(target);
        }
    }

    @Override
    public void updateTargeting(){
        if(retarget()) target = findTarget(unit.x, unit.y, unit.range(), true, true);
    }

    @Override
    public Teamc findTarget(float x, float y, float range, boolean air, boolean ground){

        //Sort by max health and closer target.
        Unit result = Units.closest(unit.team, x, y, Math.max(range, 400f), u -> !u.dead() && u.type != unit.type && u.targetable(unit.team) && u.type.playerControllable,
                (u, tx, ty) -> -u.maxHealth + Mathf.dst2(u.x, u.y, tx, ty) / 6400f);
        if(result != null) return result;

        return null;
    }
}