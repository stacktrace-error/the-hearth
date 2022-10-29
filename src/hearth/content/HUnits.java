package hearth.content;

import mindustry.entities.abilities.RegenAbility;
import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.Vars.tilesize;

public class HUnits {
    public static UnitType

    //ships/mechs
    none, shieldShip;

    //twilight, dusk, sundown, nightfall

    //dawn, daybreak

    //sunbeam,

    public static void load(){
        none = new UnitType("none"){{
            constructor = PayloadUnit::create;
            flying = true;
            drawBody = drawCell = drawBuildBeam = drawMinimap = drawShields = false;
            payloadCapacity = 0f;
            hidden = true;
            hittable = false;
            targetable = false;
            health = 5112007;
            armor = 12062020;
            hitSize = 0f;
            canDrown = false;
            canAttack = false;
            coreUnitDock = true;

            speed = 15f;
            rotateSpeed = 69420;
            buildSpeed = 0.0000001f;


            abilities.add(new RegenAbility(){{ percentAmount = 100f; }});
        }};

        shieldShip = new UnitType("shield-ship"){{
            constructor = PayloadUnit::create;
            outlineColor = HExtras.outline;
            coreUnitDock = true;
            isEnemy = false;
            envDisabled = 0;
            lowAltitude = false;
            flying = true;
            drag = 0.08f;
            speed = 7.5f;
            rotateSpeed = 8f;
            accel = 0.08f;
            itemCapacity = 110;
            health = 700f;
            armor = 3f;
            hitSize = 15f;
            buildBeamOffset = 8f;
            payloadCapacity = 2f * 2f * tilesize * tilesize;

            engineOffset = 10f;
            engineSize = 3f;
        }};
    }
}
