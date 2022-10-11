package hearth;

import arc.struct.Seq;
import hearth.content.HearthResources;
import mindustry.Vars;
import mindustry.mod.*;
import hearth.content.HearthBlocks;
import rhino.ImporterTopLevel;
import rhino.NativeJavaPackage;

public class HearthMain extends Mod{
    public HearthMain(){}

    @Override
    public void loadContent() {
        new HearthResources().load();
        new HearthBlocks().load();
    }

    //"donated" by sh1p
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