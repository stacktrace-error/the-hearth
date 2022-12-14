package hearth;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.Draw;
import arc.graphics.gl.*;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import mindustry.Vars;
import mindustry.graphics.Layer;
import mindustry.graphics.Shaders;

public class HGraphics {
    //Pal
    public static Color

    outline = Color.valueOf("2c2929"),

    tractor = Color.valueOf("8ca9e8");


    //shaders
    public static class ChromaticAberrationShader extends HLoadShader{
        public ChromaticAberrationShader(){
            super("aberration", "screenspace");
        }

        @Override
        public void apply(){
            setUniformf("u_dp", Scl.scl(1f));
            setUniformf("u_time", Time.time / Scl.scl(1f));
            setUniformf("u_offset",
                    Core.camera.position.x - Core.camera.width / 2,
                    Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_texsize", Core.camera.width, Core.camera.height);
        }
    }

    /*public static class ScanlineShader extends Shaders.LoadShader {
        public ScanlineShader(){
            super("old-tv", "screenspace");
        }

        @Override
        public void apply(){
            setUniformf("u_time", Time.time / Scl.scl(1f));
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
        }
    }*/

    public FrameBuffer buffer = Vars.renderer.effectBuffer;

    public void loadGraphics(){
        Vars.renderer.addEnvRenderer(1024, () -> {
            Draw.drawRange(Layer.legUnit, 1f, () -> buffer.begin(Color.clear), () -> {
                buffer.end();
                buffer.blit(new ChromaticAberrationShader());
            });
        });
    }

    public static class HLoadShader extends Shader{
        public HLoadShader(String frag, String vert){
            super(Shaders.getShaderFi(vert + ".vert"), Vars.tree.get("shaders/" + frag +".frag"));
        }
    }
}

//if Vars.state.waveSpacing - dropship prewave time <= Vars.state.wavetime, drawDropship()