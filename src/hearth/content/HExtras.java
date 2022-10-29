package hearth.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.entities.*;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class HExtras {

    public static void prosperize() {
        Team teamProsper = Team.get(4);
        //why in satan's anus would these need to be final
        teamProsper.palette[0].set(Color.valueOf("f0a05f")); teamProsper.palette[1].set(Color.valueOf("c66653")); teamProsper.palette[2].set(Color.valueOf("aa2e3d"));
        teamProsper.hasPalette = true;
        //teamProsper.name = "prosper"; bad idea but might do it
    }


    //Pal
    public static Color

    prosper = Color.valueOf("db773e"),

    outline = Color.valueOf("2c2929"),
    power = Color.valueOf("c3f1e5");


    //Fx

    /*public static Effect

        steam = new Effect(35f, e -> {
            color(Color.lightGray);
            alpha(e.fslope() * 0.78f);
            randLenVectors(e.id, 2, 1.5f * 4f + (e.fin() * 7f), (x, y) -> Fill.circle(e.x + x, e.y + y, 0.2f));
        });*/
}
