package hearth;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.gl.FrameBuffer;
import arc.struct.Seq;
import hearth.content.*;
import hearth.vfx.Parallax;
import hearth.vfx.HGraphics;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.graphics.Layer;
import mindustry.mod.*;
import rhino.*;

public class HearthMain extends Mod{
    public static Parallax parallax = new Parallax();
    public FrameBuffer buffer = Vars.renderer.effectBuffer;

    @Override
    public void loadContent() {
        HResources.load();
        HUnits.load();
        HBlocks.load();
        HSpace.load();
        AhkarTechTree.load();

        Events.on(EventType.ClientLoadEvent.class, (e) -> {
            if(!Vars.mobile) {
                Vars.control.setInput(new HInput());
            }
        });

        Vars.renderer.addEnvRenderer(1024, () ->
                Draw.drawRange(Layer.legUnit, 1f, () -> buffer.begin(Color.clear), () -> {
                buffer.end();
                buffer.blit(new HGraphics.ChromaticAberrationShader());
        }));
    }

    public HearthMain(){}

    //donated by sh1p
    public static NativeJavaPackage p = null;

    @Override
    public void init() {
        super.init();
        ImporterTopLevel scope = (ImporterTopLevel) Vars.mods.getScripts().scope;

        Seq<String> packages = Seq.with(
            "hearth",
            "hearth.content",
            "hearth.types"
        );

        packages.each(name -> {
            p = new NativeJavaPackage(name, Vars.mods.mainLoader());
            p.setParentScope(scope);
            scope.importPackage(p);
        });
    }
}