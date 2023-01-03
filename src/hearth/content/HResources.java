package hearth.content;

import arc.graphics.Color;
import arc.struct.Seq;
import hearth.type.MetalItem;
import hearth.type.PayloadItem;
import mindustry.content.StatusEffects;
import mindustry.type.*;

import static mindustry.content.Items.*;

public class HResources {

    public static Liquid helium, armatineL;

    public static Item nickel, ferrite, invar, vanadium, armatine;

    public static PayloadItem machineFrame, lensBase, lens, abberationLens;

    public static final Seq<Item> ahkarItems = new Seq<>();
    public static final Seq<Item> ahkarOnlyItems = new Seq<>();
    static Seq<PayloadItem> generateItems = new Seq<>();


    public static void load(){
        nickel = new MetalItem("nickel", Color.valueOf("786f72")){{ //todo description
            cost = 0.5f;
        }};


        //silicon


        machineFrame = new PayloadItem("machine-frame", 1.25f);
        lensBase = new PayloadItem("lens-base", 1.25f);
        lens = new PayloadItem("lens", 1.25f); //lens frame, sand


        ferrite = new MetalItem("ferrite", Color.valueOf("a28981")){{
            buildable = false;
        }};
        invar = new MetalItem("invar", Color.valueOf("9d958b")){{ //todo sprite
            cost = 1f;
        }};


        //thorium


        vanadium = new Item("vanadium", Color.valueOf("a7514f")){{
            cost = 1.3f;
        }};


        abberationLens = new PayloadItem("aberration-lens", 1.5f); //lens frame, vanadium, thorium


        armatine = new MetalItem("armatine", Color.valueOf("a48656")){{
            cost = 1.5f;
        }};
        armatineL = new Liquid("armatine-l", Color.valueOf("eaa46e")){{
            temperature = 1f;
            viscosity = 0.7f;
            effect = StatusEffects.melting;
            lightColor = Color.valueOf("f0511d").a(0.4f);
        }};


        //liquids
        helium = new Liquid("helium", Color.grays(0.9f)){{ //todo color
            gas = true;
        }};


        ahkarItems.addAll(nickel, sand, silicon, ferrite, invar, thorium, vanadium, armatine);
        ahkarOnlyItems.addAll(ahkarItems).removeAll(serpuloItems);

        generateItems.addAll(lens, abberationLens);
        for (PayloadItem a : generateItems) if(a.name != null) new Item(a.name);
    }
}
