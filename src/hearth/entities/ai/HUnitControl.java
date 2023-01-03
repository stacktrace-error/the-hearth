package hearth.entities.ai;

import mindustry.ai.UnitCommand;
import mindustry.ai.types.FlyingFollowAI;

public class HUnitControl {
    //todo
    // follow, guard commands
    // commit die, fort/unfort buttons
    // impl move into payload https://github.com/Anuken/Mindustry/pull/6951

    //guard AI: defenderAI until enemies in range, vector lerp group positions to stand in front of allies
    UnitCommand
    followCommand = new UnitCommand("follow", "", u -> new FlyingFollowAI()), //todo not valid AI
    guardCommand = new UnitCommand("guard", "mode-survival", u -> new GuardAI());
}


