package hearth;

import arc.graphics.Color;
import arc.struct.Seq;
import hearth.content.*;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.mod.*;
import rhino.*;

import static hearth.content.HearthExtras.prosperize;

public class HearthMain extends Mod{
    public HearthMain(){}

    @Override
    public void loadContent() {
        HearthResources.load();
        HearthUnits.load();
        HearthBlocks.load();
        prosperize();
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