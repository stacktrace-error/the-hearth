package hearth.content;

import arc.graphics.*;
import hearth.types.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.distribution.StackRouter;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class HearthBlocks {

    public static Block

    //storage
    corePedestal,

    //distribution
    poweredRail, poweredRouter,

    //production   large boiler gated behind resource after quartz
    hearth, siliconFurnace, heatedBoiler, crystallizationChamber, steamPress, heatVoid,

    //drills todo actually fucking get these to work
    basicBore, thermalDrill,

    //power
    steamTurbine, powerChannel,

    //units
    shipPad,

    //turrets
    oscillate, vibrationMine, //throws piezo mines that slow enemies, midgame

    //defense
    magneticAccelerator, magneticRedirector, barrierProjector,

    //walls
    smallNickelWall, nickelWall, largeNickelWall,
    smallInvarWall, invarWall, largeInvarWall,
    smallQuartzWall, quartzWall, largeQuartzWall, //todo shockwaves when powered, produces energy on hit
    smallHeavyThoriumWall, heavyThoriumWall, largeHeavyThoriumWall,
    smallArmatineWall, armatineWall, largeArmatineWall;

    public static void load(){

        //storage
        corePedestal = new CoreBlock("core-pedestal"){{
            requirements(Category.effect, with(HearthResources.ceramics, 4500, HearthResources.nickel, 2000, Items.silicon, 1500, Items.graphite, 2000));
            size = 6;
            unitType = HearthUnits.none;
            isFirstTier = true;
            itemCapacity = 5000;
        }};

        //distribution
        poweredRail = new StackConveyor("powered-rail"){{
            requirements(Category.distribution, with(HearthResources.nickel, 1, Items.silicon, 1));
            health = 80;
            speed = 3f / 60f;
            itemCapacity = 4;
            glowColor = HearthExtras.power;

            outputRouter = false;
            hasPower = true;
            consumesPower = true;
            conductivePower = true;

            underBullets = true;
            baseEfficiency = 0.3f;
            consumePower(5f / 60f);
        }};

        poweredRouter = new StackRouter("powered-router"){{
            requirements(Category.distribution, with(HearthResources.nickel, 1, Items.silicon, 1));
            health = 80;
            speed = 4f;
            glowColor = HearthExtras.power;

            hasPower = true;
            consumesPower = true;
            conductivePower = true;

            underBullets = true;
            baseEfficiency = 0.2f;
            solid = false;
            consumePower(7f / 60f);
        }};

        //production
        hearth = new HeatProducer("hearth"){{
            requirements(Category.crafting, with(HearthResources.nickel, 50));

            size = 6;
            rotate = false;
            itemCapacity = 60;
            heatOutput = 30f;
            ambientSound = Sounds.fire;
            ambientSoundVolume = 0.4f;
            hasLiquids = true;
            squareSprite = false;

            craftTime = 60f;
            consumeItem(Items.coal, 5);
            consumeLiquid(Liquids.water, 1);

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(), new DrawDefault(), new DrawHeatOutput());
        }};

        siliconFurnace = new HeatCrafter("silicon-furnace"){{
            requirements(Category.crafting, with(HearthResources.nickel, 50));

            size = 2;
            itemCapacity = 12;
            heatRequirement = 5f;
            maxEfficiency = 1.5f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.04f;
            craftTime = 75f;
            squareSprite = false;

            outputItem = new ItemStack(Items.silicon, 2);
            consumeItems(with(Items.sand, 4));

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawHeatRegion("-heat-ins"){{ color = Color.valueOf("ff6060ff"); }}, new DrawDefault(), new DrawHeatInput()
            );
        }};

        heatedBoiler = new HeatCrafter("heated-boiler"){{
            requirements(Category.crafting, with(HearthResources.nickel, 40, Items.silicon, 30));

            size = 2;
            heatRequirement = 10f;
            maxEfficiency = 2f;
            ambientSound = Sounds.spray;
            ambientSoundVolume = 0.05f;
            hasLiquids = true;
            craftTime = 60f;
            squareSprite = false;

            consumeLiquid(Liquids.water, 1);
            outputLiquid = new LiquidStack(HearthResources.steam, 80f / 60f);

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(), new DrawLiquidTile(HearthResources.steam, 4f), new DrawDefault(), new DrawHeatInput());
        }};

        crystallizationChamber = new GenericCrafter("crystallization-chamber"){{
            requirements(Category.crafting, with(HearthResources.nickel, 60, Items.silicon, 60));

            size = 4;
            ambientSound = Sounds.getSound(31);
            ambientSoundVolume = 0.2f;
            craftTime = 190f;
            hasLiquids = true;
            liquidCapacity = 250f;
            squareSprite = false;

            consumePower(90f / 60f);
            consumeLiquid(Liquids.water, 200f / 60f);
            consumeItem(Items.sand, 7);
            outputItem = new ItemStack(HearthResources.quartz, 3);

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawBubbles(Liquids.water.color), new DrawRegion("-spinner"){{rotateSpeed = 0.5f; spinSprite = false;}}, new DrawDefault());
        }};

        steamPress = new GenericCrafter("steam-press"){{
            requirements(Category.crafting, with(HearthResources.nickel, 60, Items.silicon, 60));

            size = 3;
            craftEffect = Fx.pulverizeMedium;
            craftTime = 60f;
            hasLiquids = true;
            liquidCapacity = 250f;
            squareSprite = false;

            consumeLiquid(HearthResources.steam, 200f / 60f);
            consumeItem(Items.coal, 5);
            outputItem = new ItemStack(Items.graphite, 3);

            drawer = new DrawDefault();
        }};

        heatVoid = new HeatProducer("heat-void"){{
            requirements(Category.crafting, BuildVisibility.sandboxOnly, with());
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
            rotateDraw = false;
            size = 1;
            heatOutput = -1000f;
            ambientSound = Sounds.none;
        }};



        //drills
        thermalDrill = new HeatDrill("thermal-drill"){{
            requirements(Category.production, with(HearthResources.nickel, 50));

            size = 3;
            drillTime = 69f * 6.9f;
            tier = 3;
            drawSpinSprite = false;
            squareSprite = false;
        }};



        //power
        powerChannel = new AutoTileBlock("power-channel"){{
            requirements(Category.power, with(HearthResources.nickel, 50));

            size = 1;
            solid = false;
            conductivePower = true;
            hasPower = true;
        }};

        steamTurbine = new ConsumeGenerator("steam-turbine"){{
            requirements(Category.power, with(HearthResources.nickel, 50));

            size = 3;
            outputsPower = hasLiquids = true;
            liquidCapacity = 120f;
            ambientSound = Sounds.steam;
            ambientSoundVolume = 0.06f;
            squareSprite = false;

            consumeLiquid(HearthResources.steam, 1);
            powerProduction = 120f / 60f;

            generateEffect = Fx.steam; //who needs good effects anyway

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-spinner2", -15f), new DrawBlurSpin("-spinner", 4f){{blurThresh=1.1f;}}, new DrawLiquidTile(HearthResources.steam, 8f), new DrawRegion("-top"));
        }};



        //units - pads
        shipPad = new MechPad("ship-pad"){{
            requirements(Category.effect, with(HearthResources.nickel, 6));
            size = 5;
            unitType = HearthUnits.shieldShip;
            hasPower = true;

            mechReqs = with(HearthResources.nickel, 50);
            consumePower(1f);
        }};



        //turrets
        vibrationMine = new ShockMine("vibration-mine"){{
            requirements(Category.turret, with(HearthResources.nickel, 6));
            size = 1;
        }};

        /*oscillate = new PayloadAmmoTurret("oscillate"){{
            requirements(Category.turret, with(HearthResources.nickel, 6));
            size = 3;
            targetAir = false;
            shoot.shots = 1;
            reload = 220f;
            shake = 2f;

            ammo(
                vibrationMine, new ArtilleryBulletType(3f, 0, "vibration-mine"){{
                    width = height = 8f;
                    lifetime = 60f;
                    collidesTiles = false;
                    fragSpread = 0f;
                    fragBullets = 1;
                    fragBullet = new BulletType(0f, 0){{
                        spawnUnit = new UnitType("thumper"){{
                            constructor = SentryUnit::create; //todo make this render the sprite like a block
                            speed = 0;
                            rotateSpeed = 0;
                            abilities.add(new EnergyFieldAbility(0, 30f, 5f * 8f){{
                                status = StatusEffects.tarred;
                                statusDuration = 2.5f * 60f;
                            }});
                        }};
                    }};
                }}
            );
        }};*/



        //defense
        magneticAccelerator = new MagneticBlock("magnetic-accelerator"){{
            requirements(Category.effect/*, BuildVisibility.editorOnly*/, with());
            size = 3;
            rotate = true;
            directional = true;
            range = 3.5f * 4f;

            heatRequirement = -10f;
            consumePower(10f);
        }};

        magneticRedirector = new MagneticBlock("magnetic-redirector"){{
            requirements(Category.effect/*, BuildVisibility.editorOnly*/, with());
            size = 2;
            rotate = true;
            baseStrength = 6f;

            heatRequirement = -10f;
            consumePower(10f);
        }};

        barrierProjector = new DirectionalForceProjector("barrier-projector"){{
            requirements(Category.effect, with(Items.surgeAlloy, 100, Items.silicon, 125));
            size = 3;
            width = 50f;
            length = 36;
            shieldHealth = 2000f;
            cooldownNormal = 3f;
            cooldownBrokenBase = 0.35f;

            consumePower(4f);
        }};



        //walls
        smallNickelWall = new Wall("small-nickel-wall"){{
            requirements(Category.defense, with(HearthResources.nickel, 6));
            size = 1;
            health = 200;
        }};
        nickelWall = new Wall("nickel-wall"){{
            requirements(Category.defense, with(HearthResources.nickel, 6 * 4));
            size = 2;
            health = smallNickelWall.health * 2;
        }};
        largeNickelWall = new Wall("large-nickel-wall"){{
            requirements(Category.defense, with(HearthResources.nickel, 6 * 9));
            size = 3;
            health = smallNickelWall.health * 3;
        }};

        smallInvarWall = new Wall("small-invar-wall"){{
            requirements(Category.defense, with(HearthResources.invar, 6));
            size = 1;
            health = 200;
        }};
        invarWall = new Wall("invar-wall"){{
            requirements(Category.defense, with(HearthResources.invar, 6 * 4));
            size = 2;
            health = smallInvarWall.health * 2;
        }};
        largeInvarWall = new Wall("large-invar-wall"){{
            requirements(Category.defense, with(HearthResources.invar, 6 * 9));
            size = 3;
            health = smallInvarWall.health * 3;
        }};

        smallQuartzWall = new PiezoWall("small-quartz-wall"){{
            requirements(Category.defense, with(HearthResources.quartz, 6));
            size = 1;
            health = 200;
        }};
        quartzWall = new PiezoWall("quartz-wall"){{
            requirements(Category.defense, with(HearthResources.quartz, 6 * 4));
            size = 2;
            health = smallQuartzWall.health * 2;
        }};
        largeQuartzWall = new PiezoWall("large-quartz-wall"){{
            requirements(Category.defense, with(HearthResources.quartz, 6 * 9));
            size = 3;
            health = smallQuartzWall.health * 3;
        }};

        smallHeavyThoriumWall = new Wall("small-heavy-thorium-wall"){{
            requirements(Category.defense, with(Items.thorium, 6));
            size = 1;
            health = 1;
        }};
        heavyThoriumWall = new Wall("heavy-thorium-wall"){{
            requirements(Category.defense, with(Items.thorium, 6 * 4));
            size = 2;
            health = smallHeavyThoriumWall.health * 2;
        }};
        largeHeavyThoriumWall = new Wall("large-heavy-thorium-wall"){{
            requirements(Category.defense, with(Items.thorium, 6 * 9));
            size = 3;
            health = smallHeavyThoriumWall.health * 3;
        }};

        smallArmatineWall = new Wall("small-armatine-wall"){{
            requirements(Category.defense, with(HearthResources.armatine, 6));
            size = 1;
            health = 200;
        }};
        armatineWall = new Wall("armatine-wall"){{
            requirements(Category.defense, with(HearthResources.armatine, 6 * 4));
            size = 2;
            health = smallArmatineWall.health * 2;
        }};
        largeArmatineWall = new Wall("large-armatine-wall"){{
            requirements(Category.defense, with(HearthResources.armatine, 6 * 9));
            size = 3;
            health = smallArmatineWall.health * 3;
        }};
    }
}
