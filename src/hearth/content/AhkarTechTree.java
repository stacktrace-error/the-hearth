package hearth.content;

import mindustry.content.TechTree;

import static hearth.content.HBlocks.*;
import static hearth.content.HResources.*;
import static mindustry.content.Items.*;

public class AhkarTechTree extends TechTree {
    public static void load(){
        HSpace.ahkar.techTree = nodeRoot("ahkar", corePedestal ,false, () -> {
            //node(, () -> {});

            node(hearth, () -> {
                node(siliconFurnace, () ->
                    node(crystallizationChamber/*, () ->*/

                ));
                node(heatedBoiler, () ->
                    node(steamPress/*, () ->*/

                ));
            });

            node(poweredRail, () ->
                node(poweredRouter)

            );

            /*node(compressorPump, () ->
                node(channel*//*, () ->*//*

            ));*/

            node(steamTurbine, () ->
                node(powerChannel/*, () ->*/

            ));

            //items
            node(ceramics, () ->
                node(nickel, () ->
                    node(silicon, () ->
                        node(steam, () ->
                            node(graphite, () ->
                                node(quartz, () ->
                                    node(ferrite, () ->
                                        node(invar, () ->
                                            node(vanadium, () ->
                                                node(armatine/*, () ->*/
            ))))))))));
        });
    }
}
