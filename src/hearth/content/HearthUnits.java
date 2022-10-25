package hearth.content;

import mindustry.ai.types.*;
import mindustry.entities.units.AIController;
import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.Vars.tilesize;

public class HearthUnits {
    public static UnitType

    //ships/mechs
    shieldShip;

    public static void load(){
        shieldShip = new UnitType("shield-ship"){{
            constructor = PayloadUnit::create;
            outlineColor = HearthGraphics.outline;
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
