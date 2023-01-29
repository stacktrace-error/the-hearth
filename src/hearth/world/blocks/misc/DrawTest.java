package hearth.world.blocks.misc;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Block;

import static hearth.HearthMain.parallax;

public class DrawTest extends Block{
    public float radius = 20f;
    public float height = 10000f;
    public float upScl = 6f;

    public DrawTest(String name) {
        super(name);
        update = true;
        size = 2;
        hasShadow = false;
    }

    public class DrawTestBuild extends Building{
        @Override
        public void drawTeam(){}

        @Override
        public void draw(){
            Draw.z(Layer.fogOfWar);
            Draw.color(Pal.surge);
            Draw.blend(Blending.additive);

            parallax.drawOmegaUltraGigaChadDeathRay(x, y, radius, height, upScl * (height * 0.1f));

            Draw.reset();
            Draw.blend();
        }
    }
}
