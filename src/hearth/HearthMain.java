package hearth;

import arc.struct.Seq;
import hearth.content.*;
import mindustry.Vars;
import mindustry.mod.*;
import rhino.*;

public class HearthMain extends Mod{
    public HearthMain(){}

    @Override
    public void loadContent() {
        HearthResources.load();
        HearthUnits.load();
        HearthBlocks.load();
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