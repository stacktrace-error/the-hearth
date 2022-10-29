package hearth;

import arc.struct.Seq;
import hearth.content.*;
import mindustry.Vars;
import mindustry.mod.*;
import rhino.*;

import static hearth.content.HExtras.prosperize;

public class HearthMain extends Mod{
    public HearthMain(){}

    @Override
    public void loadContent() {
        HResources.load();
        HUnits.load();
        HBlocks.load();
        HSpace.load();
        AhkarTechTree.load();
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