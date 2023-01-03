package hearth.type;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.meta.Stat;

public class MetalItem extends Item {
    public Color moltenColor;
    public float moltenTemp;
    public Liquid molten = Liquids.slag;

    public MetalItem(String name, Color color){
        super(name, color);
        this.moltenColor = Liquids.slag.color;
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
        stats.add(new Stat("canMelt"), true);
    }

    @Override
    public void load(){
        super.load();
        String localized = localizedName;
        TextureRegion region = uiIcon;

        molten = new Liquid(name + "-l", moltenColor){{ //todo crash
            viscosity = 0.7f;
            effect = StatusEffects.melting;
            lightColor = moltenColor.a(0.4f);
            //hidden = true;
            fullIcon = uiIcon = region;
            localizedName = "Molten " + localized;
            description = "[red]Stop! You have violated the Law! Pay the court a fine or serve your sentence. Your stolen goods are now forfeit.[]";
        }};
    }
}
