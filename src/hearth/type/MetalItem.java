package hearth.type;

import arc.Core;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.meta.Stat;

public class MetalItem extends Item {
    public Color moltenColor;
    public float moltenTemp;
    public Liquid molten = Liquids.slag;

    public MetalItem(String name, Color color){
        super(name, color);
        this.moltenColor = Liquids.slag.color.cpy();
        moltenTemp = Liquids.slag.temperature;
    }

    public MetalItem(String name, Color color, Color moltenColor){
        super(name, color);
        this.moltenColor = moltenColor;
        moltenTemp = Liquids.slag.temperature;
    }

    public MetalItem(String name, Color color, Color moltenColor, float moltenTemp){
        super(name, color);
        this.moltenColor = moltenColor;
        this.moltenTemp = moltenTemp;
    }

    @Override
    public void setStats(){
        stats.add(new Stat("can-melt"), true);
    }

    @Override
    public void init(){
        super.init();
        String localized = localizedName;

        molten = new Liquid(name + "-molten", moltenColor){{
            viscosity = 0.7f;
            temperature = 1f;
            effect = StatusEffects.melting;
            lightColor = moltenColor.a(0.4f);
            hidden = true;
            localizedName = "Molten " + localized;
            description = "[red]Stop! You have violated the Law! Pay the court a fine or serve your sentence. Your stolen goods are now forfeit.[]";
        }};
    }

    @Override
    public void load(){
        super.load();
        molten.fullIcon = molten.uiIcon = Core.atlas.find(molten.name, uiIcon);
    }
}
