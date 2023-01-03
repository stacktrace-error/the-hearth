package hearth.content;

import hearth.world.blocks.payload.ComponentReceiver;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
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

    //production

    //payload

    //power

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

        //production

        //payload

        //power

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
