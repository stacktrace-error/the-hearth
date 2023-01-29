package hearth.content;

import arc.graphics.Color;
import arc.struct.Seq;
import hearth.type.*;
import mindustry.type.*;

import static mindustry.content.Items.*;

public class HResources {

    public static Liquid helium;

    public static Item nickel, ice, ferrite, invar, vanadium, armatine;

    public static PayloadItem machineFrame, lensBase, lens, aberrationLens;

    public static final Seq<Item> ahkarItems = new Seq<>();
    public static final Seq<Item> ahkarOnlyItems = new Seq<>();
    static Seq<PayloadItem> generateItems = new Seq<>();


    public static void load(){
        nickel = new MetalItem("nickel", Color.valueOf("786f72")){{
            cost = 0.5f;
        }};


        //silicon


        ice = new Item("ice", Color.valueOf("9fe5ff")){{
           buildable = false;
        }};


        machineFrame = new PayloadItem("machine-frame", 1.5f);
        lensBase = new PayloadItem("lens-base", 1.5f);
        lens = new PayloadItem("lens", 1.5f); //lens frame, sand


        ferrite = new MetalItem("ferrite", Color.valueOf("a28981")){{
            buildable = false;
        }};
        invar = new MetalItem("invar", Color.valueOf("9d958b")){{
            cost = 1f;
        }};


        //thorium


        vanadium = new Item("vanadium", Color.valueOf("a7514f")){{
            cost = 1.3f;
        }};


        aberrationLens = new PayloadItem("aberration-lens", 2f); //lens frame, vanadium, thorium


        armatine = new MetalItem("armatine", Color.valueOf("a48656")){{
            cost = 1.5f;
        }};


        //liquids
        helium = new Liquid("helium", Color.grays(0.9f)){{
            gas = true;
        }};


        ahkarItems.addAll(nickel, sand, silicon, ice, ferrite, invar, thorium, vanadium, armatine);
        ahkarOnlyItems.addAll(ahkarItems).removeAll(serpuloItems);

        generateItems.addAll(lens, aberrationLens);
        for (PayloadItem a : generateItems) if(a.name != null) new Item(a.name);
    }
}
