package hearth.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.type.*;

public class HResources {

    public static Liquid steam, armatineL;

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

        armatine = new Item("armatine", Color.valueOf("a48656")){{
            cost = 1.5f;
        }};
        armatineL = new Liquid("armatine-l", Color.valueOf("eaa46e")){{
            hidden = true;
            temperature = 1f;
            viscosity = 0.7f;
            effect = StatusEffects.melting;
            lightColor = Color.valueOf("f0511d").a(0.4f);
        }};
    }
}
