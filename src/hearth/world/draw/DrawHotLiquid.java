package hearth.world.draw;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.draw.*;

public class DrawHotLiquid extends DrawLiquidTile{
    public String suffix = "-heat";
    public TextureRegion glow;

    public DrawHotLiquid(){}

    @Override
    public void draw(Building build) {
        super.draw(build);
        if (build.liquids.currentAmount() > 0.001f){
            Liquid liq = build.liquids.current();

            Draw.color(liq.color, build.liquids.currentAmount() / build.block().liquidCapacity  * ((liq.temperature - 0.5f) * 2f));
            Draw.blend(Blending.additive);
            Draw.rect(glow, build.x, build.y);
            Draw.reset(); Draw.blend();
        }
    }

    @Override
    public void load(Block block) {
        glow = Core.atlas.find(block.name + suffix);
    }
}
