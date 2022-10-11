package hearth.content;

import arc.graphics.Color;
import mindustry.type.*;

public class HearthResources {

    public static Liquid steam;

    public static Item nickel, ceramics, quartz, ferrite, invar, vanadium, armatine;

    public static void load(){
        //liquids
        steam = new Liquid("steam", Color.grays(0.9f)){{
            gas = true;
            heatCapacity = 0f;
            temperature = 0.7f;
        }};

        //items
        nickel = new Item("nickel", Color.valueOf("786f72")){{ //todo description
            cost = 0.5f;
        }};

        ceramics = new Item("ceramics", Color.valueOf("56433b")){{
            cost = 0.8f;
        }};

        quartz = new Item("quartz"){{ //todo sprite
            cost = 1.3f;
        }};

        ferrite = new Item("ferrite", Color.valueOf("a28981")){{
            buildable = false;
        }};

        invar = new Item("invar"){{ //todo sprite
            cost = 1f;
        }};

        vanadium = new Item("vanadium", Color.valueOf("a7514f")){{
            cost = 1.3f;
        }};

        armatine = new Item("armatine"){{ //todo sprite
            cost = 1.5f;
        }};
    }
}
