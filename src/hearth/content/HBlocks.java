package hearth.content;

import hearth.world.blocks.distribution.PayDuctRouter;
import hearth.world.blocks.distribution.PayloadDuct;
import hearth.world.blocks.misc.DrawTest;
import hearth.world.blocks.payload.ComponentReceiver;
import hearth.world.blocks.power.LaserNode;
import hearth.world.blocks.units.UnitManufacturer;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.DirectionalForceProjector;
import mindustry.world.blocks.distribution.DuctBridge;
import mindustry.world.blocks.payloads.PayloadSource;
import mindustry.world.meta.Attribute;

import static hearth.content.HResources.nickel;
import static mindustry.content.Items.silicon;
import static mindustry.content.Items.surgeAlloy;
import static mindustry.type.ItemStack.with;

public class HBlocks{

    public static final Attribute quarry = Attribute.add("quarryness");

    public static Block
    //storage
    componentReceiver,

    //distribution
    transporter, splitter, transporterBridge,

    //production
    foundry, smallPaySource,

    //power
    laserNode,

    //units
    manufacturer,

    //turrets

    //defense
    barrierProjector,

    //env
    grayRegolith, grayRegolithWall,

    //misc
    fogClearer, drawTest;

    public static void load(){

        //storage
        componentReceiver = new ComponentReceiver("component-receiver"){{
            requirements(Category.effect, with(nickel, 20));
            size = 2;
        }};

        //distribution
        transporter = new PayloadDuct("transporter"){{
            requirements(Category.units, with(nickel, 20));
            size = 2;
        }};

        splitter = new PayDuctRouter("splitter"){{
            requirements(Category.units, with(nickel, 20));
            size = 2;
        }};

        transporterBridge = new DuctBridge("transporter-bridge"){{
            requirements(Category.units, with(nickel, 20));
            size = 2;
        }};


        //production
        /*foundry = new RecipeCrafter("foundry"){{ todo TFZTRCDVTDRVHQDTZDFDQEZTFQUZD
            requirements(Category.effect, with(nickel, 100, silicon, 125));
            size = 4;

            craftTime = 120;
            consumeLiquid(slag, 1);
            consumeItem(nickel, 1);
            inputPayload = Blocks.router;
            outputPayload = lensBase;
        }};*/

        smallPaySource = new PayloadSource("small-payload-source"){{
            requirements(Category.effect, with(nickel, 100, silicon, 125));
            size = 2;
        }};


        //power
        laserNode = new LaserNode("laser-node"){{
            requirements(Category.effect, with(nickel, 100, silicon, 125));
            size = 2;
        }};

        //units
        manufacturer = new UnitManufacturer("manufacturer"){{
            requirements(Category.units, with(nickel, 100, silicon, 125));
        }};

        //turrets

        //defense
        barrierProjector = new DirectionalForceProjector("barrier-projector"){{
            requirements(Category.effect, with(surgeAlloy, 100, silicon, 125));
            size = 3;
            width = 50f;
            length = 36;
            shieldHealth = 2000f;
            cooldownNormal = 3f;
            cooldownBrokenBase = 0.35f;

            consumePower(4f);
        }};

        //walls

        //misc
        fogClearer = new Block("fogClearer"){{
            requirements(Category.effect, with());
            update = true;
            fogRadius = 1000;
        }};

        drawTest = new DrawTest("draw-test"){{
            requirements(Category.effect, with(surgeAlloy, 100, silicon, 125));
        }};
    }
}
