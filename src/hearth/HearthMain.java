package hearth;

import arc.Core;
import arc.Events;
import arc.struct.Seq;
import hearth.content.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.mod.*;
import rhino.*;

public class HearthMain extends Mod{
    public HearthMain(){}

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
    }

    //donated by sh1p
    public static NativeJavaPackage p = null;

    @Override
    public void init() {
        super.init();
        ImporterTopLevel scope = (ImporterTopLevel) Vars.mods.getScripts().scope;

        Seq<String> packages = Seq.with(
                "hearth",
                "hearth.types",
                "hearth.content"
        );

        packages.each(name -> {
            p = new NativeJavaPackage(name, Vars.mods.mainLoader());
            p.setParentScope(scope);
            scope.importPackage(p);
        });
    }
}