package hearth.content;

import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.Vars.tilesize;

public class HearthUnits {
    public static UnitType

    //ships/mechs
    none, shieldShip;

    //twilight, dusk, sundown, nightfall

    //dawn, daybreak

    //sunbeam,

    public static void load(){
        none = new UnitType("none"){{
            constructor = PayloadUnit::create;
            payloadCapacity = 0f;
            hidden = true;
            hittable = false;
            targetable = false;
            health = 376517;
            hitSize = 0f;
            buildSpeed = 0.0000001f;

            speed = 15f;
            rotateSpeed = 69420;

            drawBody = drawCell = drawBuildBeam = drawMinimap = false;
        }};

        shieldShip = new UnitType("shield-ship"){{
            constructor = PayloadUnit::create;
            outlineColor = HearthExtras.outline;
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
