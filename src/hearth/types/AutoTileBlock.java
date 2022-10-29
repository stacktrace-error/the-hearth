package hearth.types;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import hearth.content.HExtras;
import mindustry.world.*;

public class AutoTileBlock extends Block /*implements Autotiler*/ {

    /*public @Load(value = "@-#", length = 5) TextureRegion[] regions;
    public @Load(value = "@-glow-#", length = 5) TextureRegion[] glowRegions;*/

    public TextureRegion[][][] rotateRegions;

    public static boolean glow = false;
    public static Color glowColor = HExtras.power;
    public static boolean channel = true;

    public AutoTileBlock(String name){
        super(name);
        update = true;
    }

    /*@Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return (otherblock.hasPower && (otherblock.outputsLiquid || (lookingAt(tile, rotation, otherx, othery, otherblock))) && lookingAtEither(tile, rotation, otherx, othery, otherrot, otherblock);
    }*/
}
//if there's 2 connections and total rotations are even, draw straight variant, if they are odd, draw the curve
//otherwise, draw 4 way and put a side on places that don't have a connection
