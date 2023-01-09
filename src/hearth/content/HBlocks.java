package hearth.content;

import hearth.world.blocks.payload.*;
import hearth.world.blocks.power.LaserNode;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.payloads.PayloadConveyor;
import mindustry.world.blocks.payloads.PayloadSource;
import mindustry.world.meta.Attribute;

import static hearth.content.HResources.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.with;

public class HBlocks {

    public static final Attribute quarry = Attribute.add("quarryness");

    public static Block

    //storage
    componentReceiver,

    //distribution
    transporter,

    //production
    foundry, smallPaySource,

    //power
    laserNode,

    //units

    //turrets

    //defense
    barrierProjector,

    //env
    grayRegolith, grayRegolithWall;

    public static void load(){

        //storage
        componentReceiver = new ComponentReceiver("component-receiver"){{
            requirements(Category.effect, with(nickel, 20));
            size = 2;
        }};

        //distribution
        transporter = new PayloadConveyor("transporter"){{
            requirements(Category.effect, with(nickel, 20));
            size = 2;
        }};

        //production
        foundry = new PayloadCrafter("foundry"){{
            requirements(Category.effect, with(nickel, 100, silicon, 125));
            size = 4;
        }};

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
    }
}
