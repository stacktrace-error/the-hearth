package hearth.content;

import arc.func.Prov;
import arc.graphics.Color;
import arc.struct.*;
import arc.struct.ObjectMap.*;
import hearth.entities.bases.HLegsUnit;
import hearth.entities.bases.HUnitEntity;
import hearth.entities.bases.AhkarUnitType;
import hearth.entities.TractorPayloadUnit;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.part.RegionPart;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.*;

@SuppressWarnings("unchecked")
public class HUnits {

    //Steal from Progressed Materials which stole from Endless Rusting which stole from Progressed Materials in the past which stole from BetaMindy
    private static final Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
        prov(TractorPayloadUnit.class, TractorPayloadUnit::new),
        prov(HLegsUnit.class, HLegsUnit::new),
        prov(HUnitEntity.class, HUnitEntity::new)
    };

    private static final ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

        /** Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
         @author GlennFolker */
        private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov){
            Entry<Class<T>, Prov<T>> entry = new Entry<>();
            entry.key = type;
            entry.value = prov;
            return entry;
        }

        /** Setups all entity IDs and maps them into {@link EntityMapping}.
         @author GlennFolker */
        private static void setupID(){
            for(int i = 0, j = 0, len = EntityMapping.idMap.length; i < len; i++){
                if(EntityMapping.idMap[i] == null){
                    idMap.put(types[j].key, i);
                    EntityMapping.idMap[i] = types[j].value;

                    if(++j >= types.length) break;
                }
            }
        }

        /** Retrieves the class ID for a certain entity type.
         @author GlennFolker */
        public static <T extends Entityc> int classID(Class<T> type){
            return idMap.get(type, -1);
        }

    public static AhkarUnitType

    dropship,

    //fort ground
    dusk,

    daybreak;

    //    dawn, dusk
    //daybreak, nightfall
    // sunbeam, twilight    related concepts?
    //        , sundown     no partner

    public static void load(){
        setupID();

        dusk = new AhkarUnitType("dusk"){{
            constructor = LegsUnit::create;
            health = 1300f;
            armor = 6f;
            hitSize = 28f;
            speed = 0.62f;
            rotateSpeed = 2.7f;
            drag = 0.1f;
            strafePenalty = 0.9f;
            drownTimeMultiplier = 2f;
            hovering = true;
            allowLegStep = true;
            shadowElevation = 0.65f;
            groundLayer = Layer.legUnit;

            legCount = 4;
            legBaseOffset = 10f;
            lockLegBase = true;
            legLength = 16.8f;
            legExtension = -5;

            legGroupSize = 1;
            legPairOffset = 1.5f;
            legLengthScl = 0.85f;
            legStraightness = 0.2f;
            legMoveSpace = 1.3f;
            legSpeed = 0.2f;
            legContinuousMove = true;

            legSplashDamage = 32;
            legSplashRange = 30;
            stepShake = 1f;
            rippleScale = 2f;

            weapons.add(new Weapon("hearth-dusk-turret"){{
                shootSound = Sounds.mediumCannon;
                rotationLimit = 30f; //todo fort limit, normal should be 0
                layerOffset = 0.1f;
                reload = 90f;
                shootY = 22f;
                shake = 5f;
                recoil = 2f;
                rotate = true;
                rotateSpeed = 0.6f;
                mirror = false;
                x = 0f;
                y = 0f;
                shadow = 50f;
                shootWarmupSpeed = 0.06f;
                cooldownTime = 110f;
                heatColor = Color.valueOf("ff1732");
                minWarmup = 0.9f;

                bullet = new BasicBulletType(7f, 50){{
                    recoil = 5f;
                    width = 11f;
                    height = 20f;
                    lifetime = 25f;
                    shootEffect = Fx.shootBig;
                }};
            }});
        }};

        daybreak = new AhkarUnitType("daybreak"){{ //todo is much larger than expected, move concept onto locus size
            constructor = HLegsUnit::create;
            health = 8000;
            armor = 6f;
            hitSize = 28f;
            speed = 0.62f;
            rotateSpeed = 2.7f;
            drag = 0.1f;
            strafePenalty = 0.9f;
            drownTimeMultiplier = 2f;
            hovering = true;
            allowLegStep = true;
            shadowElevation = 0.65f;
            groundLayer = Layer.legUnit;

            legCount = 4;
            legBaseOffset = 10f;
            lockLegBase = true;
            legLength = 19.2f;
            legExtension = -5;

            legGroupSize = 1;
            legPairOffset = 1.5f;
            legLengthScl = 0.85f;
            legStraightness = 0.2f;
            legMoveSpace = 1.3f;
            legSpeed = 0.2f;
            legContinuousMove = true;

            legSplashDamage = 32;
            legSplashRange = 30;
            stepShake = 1f;
            rippleScale = 2f;

            weapons.add(new Weapon("hearth-daybreak-turret"){{
                shootSound = Sounds.mediumCannon;
                rotationLimit = 30f; //todo fort limit, normal should be 0
                layerOffset = 0.1f;
                reload = 90f;
                shootY = 22f;
                shake = 5f;
                recoil = 2f;
                rotate = true;
                rotateSpeed = 0.6f;
                mirror = false;
                x = 0f;
                y = -2f;
                shadow = 50f;
                shootWarmupSpeed = 0.06f;
                cooldownTime = 110f;
                heatColor = Color.valueOf("ff1732");
                minWarmup = 0.9f;

                bullet = new BasicBulletType(7f, 50){{
                    recoil = 5f;
                    width = 11f;
                    height = 20f;
                    lifetime = 25f;
                    shootEffect = Fx.shootBig;
                }};
            }});
        }};

        dropship = new AhkarUnitType("dropship"){{
            constructor = TractorPayloadUnit::create;
            lowAltitude = false;
            flying = true;
            drag = 0.08f;
            speed = 4f;
            rotateSpeed = 2f;
            rotateMoveFirst = true;
            accel = 0.08f;
            itemCapacity = 110;
            health = 700f;
            armor = 3f;
            hitSize = 50f;
            buildBeamOffset = 20f;
            payloadCapacity = 16f * 2f * Vars.tilePayload;
            shadowElevationScl = 3f;

            engineSize = 0f;
            setEnginesMirror(
                    new UnitEngine(95 / 4f, -56 / 4f, 5f, 330f),
                    new UnitEngine(89 / 4f, -95 / 4f, 4f, 315f)
            );
        }};
        UnitTypes.emanate.constructor = TractorPayloadUnit::create;
        UnitTypes.emanate.payloadCapacity = 524288f;
        UnitTypes.emanate.pickupUnits = true;
    }
}
