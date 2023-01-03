package hearth.content;

import mindustry.content.TechTree;

import static hearth.content.HBlocks.*;
import static hearth.content.HResources.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;

public class AhkarTechTree extends TechTree {
    public static void load(){
        HSpace.ahkar.techTree = nodeRoot("ahkar", lens ,false, () -> {
            //items
            node(nickel, () ->
                node(lensBase, () ->
                    node(lens, () ->
                        node(ferrite, () -> {
                            node(invar);
                            node(vanadium, () ->
                                node(armatine)
            );}))));

            node(sand, () ->
                node(silicon, () -> {
                    node(water, () -> {
                    node(ozone, () ->
                        node(hydrogen, () ->
                            node(helium)
            ));});}));
        });
    }
}
