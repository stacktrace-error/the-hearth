package hearth.types;

import mindustry.entities.TargetPriority;
import mindustry.world.blocks.defense.*;
import mindustry.world.meta.BlockGroup;

public class PiezoWall extends Wall {
    public PiezoWall(String name){
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        buildCostMultiplier = 6f;
        canOverdrive = false;
        drawDisabled = false;
        crushDamageMultiplier = 5f;
        priority = TargetPriority.wall;
    }
}
