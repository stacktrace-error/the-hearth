package hearth.world.blocks.payload;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.geom.Geometry;
import arc.struct.*;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class PayloadCrafter extends PayloadBlock{
    public @Nullable ItemStack[] outputItems;
    public @Nullable PayloadStack outputPayload; //todo if output payload, outputsPayload = true
    public @Nullable LiquidStack[] outputLiquids;
    public @Nullable PayloadStack inputPayload;

    public int[] liquidOutputDirections = {-1};
    public boolean dumpExtraLiquid = false;
    public boolean ignoreLiquidFullness = false;

    public float craftTime = 80;
    public Effect craftEffect = Fx.none;
    public Effect updateEffect = Fx.none;
    public float updateEffectChance = 0.04f;
    public float warmupSpeed = 0.019f;

    public DrawBlock drawer = new DrawDefault();

    public PayloadCrafter(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        ambientSound = Sounds.machine;
        sync = true;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.factory);
        rotate = true;
    }

    @Override
    public void setStats(){
        stats.timePeriod = craftTime;
        super.setStats();
        stats.add(Stat.productionTime, craftTime / 60f, StatUnit.seconds);

        if(outputItems != null){
            stats.add(Stat.output, StatValues.items(craftTime, outputItems));
        }

        if(outputLiquids != null){
            stats.add(Stat.output, StatValues.liquids(1f, outputLiquids));
        }

        if(outputPayload != null){
            stats.add(Stat.output, payload(outputPayload));
        }
    }

    public static StatValue payload(PayloadStack pay) {
        return (table) -> {
            table.add(new ItemImage(pay.item.uiIcon, 1));
            table.add(pay.item.localizedName).padLeft(2.0F).padRight(5.0F).color(Color.lightGray).style(Styles.outlineLabel);
        };
    }

    @Override
    public void setBars(){
        super.setBars();

        //set up liquid bars for liquid outputs
        if(outputLiquids != null && outputLiquids.length > 0){
            //no need for dynamic liquid bar
            removeBar("liquid");

            //then display output buffer
            for(LiquidStack stack : outputLiquids){
                addLiquidBar(stack.liquid);
            }
        }

        addBar("progress", (BlockProducer.BlockProducerBuild entity) -> new Bar("bar.progress", Pal.ammo, () -> entity.progress));
    }

    @Override
    public boolean rotatedOutput(int x, int y){
        return outputPayload != null;
    }

    @Override
    public void load(){
        super.load();

        drawer.load(this);
    }

    @Override
    public void init(){
        outputsLiquid = outputLiquids != null;
        outputsPayload = outputPayload != null;

        if(outputItems != null) hasItems = true;
        if(outputLiquids != null) hasLiquids = true;

        super.init();
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.finalIcons(this);
    }

    @Override
    public boolean outputsItems(){
        return outputItems != null;
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out){
        drawer.getRegionsToOutline(this, out);
    }

    @Override
    public void drawOverlay(float x, float y, int rotation){
        if(outputLiquids != null){
            for(int i = 0; i < outputLiquids.length; i++){
                int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

                if(dir != -1){
                    Draw.rect(
                        outputLiquids[i].liquid.fullIcon,
                        x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
                        y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
                        8f, 8f
                    );
        }}}
    }

    public class PayloadCrafterBuild extends PayloadBlockBuild<Payload>{

        @Override
        public boolean shouldActiveSound(){
            return shouldConsume();
        }

        @Override
        public boolean acceptPayload(Building source,  Payload payload){
            return inputPayload != null && payload == inputPayload.item;
        }
    }
}
