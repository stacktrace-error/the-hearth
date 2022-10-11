package hearth.types;


import mindustry.gen.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;
import arc.*;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.heat.*;

public class HeatDrill extends Drill{

    public float heatRequirement, overheatScale, maxEfficiency;

    @Override
    public void setBars(){
        super.setBars();

        addBar("heat", (HeatDrill.HeatDrillBuild entity) ->
            new Bar(() ->
                Core.bundle.format("bar.heatpercent", (int)entity.heat, (int)(entity.efficiencyScale() * 100)),
                () -> Pal.lightOrange,
                () -> entity.heat / heatRequirement
            )
        );
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.input, heatRequirement, StatUnit.heatUnits);
        stats.add(Stat.maxEfficiency, (int)(maxEfficiency * 100f), StatUnit.percent);
    }

    public HeatDrill(String name){
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.drills;
        hasLiquids = true;
        liquidCapacity = 5f;
        hasItems = true;
        ambientSound = Sounds.drill;
        ambientSoundVolume = 0.018f;
        //drills work in space I guess
        envEnabled |= Env.space;
        heatRequirement = 10f;
        overheatScale = 1f;
        maxEfficiency = 1f;
    }

    public class HeatDrillBuild extends DrillBuild implements HeatConsumer{
        public float[] sideHeat = new float[4];
        public float heat = 0f;

        @Override
        public void updateTile(){
            heat = calculateHeat(sideHeat);
            super.updateTile();
        }

        public float efficiencyScale(){
            float over = Math.max(heat - heatRequirement, 0f);
            return Math.min(Mathf.clamp(heat / heatRequirement) + over / heatRequirement * overheatScale, maxEfficiency);
        }

        @Override
        public float heatRequirement(){
            return heatRequirement;
        }

        @Override
        public float[] sideHeat(){
            return sideHeat;
        }

        @Override
        public float efficiency() {
            if(heat < (heatRequirement -0.01)) return 0;
            else return Mathf.clamp(heat / heatRequirement,0f , maxEfficiency);
        }
    }
}
