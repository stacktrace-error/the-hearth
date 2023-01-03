package hearth;

import arc.ApplicationListener;
import arc.Core;
import arc.graphics.*;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import mindustry.ai.UnitCommand;
import mindustry.game.Team;
import mindustry.graphics.Shaders;
import mindustry.world.meta.Attribute;

public class HGraphics {

    //Pal
    public static Color

    prosper = Color.valueOf("db773e"),

    outline = Color.valueOf("2c2929"),
    power = Color.valueOf("c3f1e5"),

    tractor = Color.valueOf("8ca9e8");


    //shaders
    public static class ChromaticAberrationShader extends Shaders.LoadShader {
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
            setUniformf("u_invsize", 1f/Core.camera.width, 1f/Core.camera.height);
        }
    }
}

//if Vars.state.waveSpacing - dropship prewave time <= Vars.state.wavetime, drawDropship()