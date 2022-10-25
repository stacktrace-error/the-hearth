package hearth.types;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.struct.*;
import mindustry.content.Liquids;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.Bar;
import mindustry.world.blocks.heat.HeatConsumer;
import mindustry.world.meta.*;
import mindustry.world.*;
import static mindustry.Vars.tilesize;

public class MagneticBlock extends Block{
    public float range = 80f;
    public float baseStrength = 1f;
    public boolean directional = false;
    public float polMultiplier = 0.3f;
    public boolean ignoreType = false;
    public float dstMult = 0.5f;

    public float heatRequirement = -10f;

    /*public @Load("@-0") TextureRegion rot0; public @Load("@-1") TextureRegion rot1;
    public @Load("@-arrow") TextureRegion arrow;*/

    public MagneticBlock(String name) {
        super(name);
        update = true;
        solid = true;
    }

    @Override
    public void setStats () {
        super.setStats();
        stats.add(Stat.input, heatRequirement, StatUnit.heatUnits);
        if(!directional) {stats.add(Stat.range, range / tilesize, StatUnit.blocks);}
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("cooling", (MagneticBlock.MagneticBlockBuild e) -> new Bar("bar.cooling", Liquids.cryofluid.color, e::heatPercent)
        );
    }

    @Override
    public void drawPlace ( int x, int y, int rotation, boolean valid){
    super.drawPlace(x, y, rotation, valid);
        if(!directional) {Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);}
    }

    public class MagneticBlockBuild extends Building implements HeatConsumer{
        public Seq<Bullet> targets = new Seq<>();
        public float dirX = 0f, dirY = 0f, push = 0f, strength;
        Vec2 pushDir, bPos;
        public float[] sideHeat = new float[4];

        public float heatPercent(){ return Mathf.clamp(calculateHeat(sideHeat) / heatRequirement, 0, 1); }

        @Override
        public void updateTile() {
            super.updateTile();

            strength = baseStrength * efficiency * heatPercent() * Time.delta;

            //get targets
//            if (potentialEfficiency > 0) {
                targets.clear();
                targets = Groups.bullet.intersect(x - range, y - range, range * 2f, range * 2f);
                if(!ignoreType) {targets.filter(b -> b.type().hittable);}

                if (efficiency > 0 && targets.size >= 0) { //todo this is not getting target correctly
                    for (Bullet target : targets) {
                        if (target.type() != null && ignoreType || target.type().hittable) {
                            if (directional) {
                                //if railgun apply force directionally
                                dirX = Geometry.d4x[rotation] * strength; dirY = Geometry.d4y[rotation] * strength;
                                target.vel(new Vec2(target.vel.x += dirX, target.vel.y += dirY));
                            } else {
                                //if not apply in a radius
                                bPos = new Vec2(target.x, target.y);
                                pushDir = new Vec2(x, y).sub(bPos);
                                pushDir.setLength(Mathf.clamp(strength - Mathf.pow(bPos.dst(x, y) * dstMult, 2), 0, strength));
                                if (rotation == 1 || rotation == 3) {
                                    pushDir.rotate(180);
                                    pushDir.setLength(pushDir.len() * polMultiplier);
                                }
                                target.vel.x += pushDir.x;
                                target.vel.y += pushDir.y;
                            }
                        }
                    }
                }
            }
//        }

        @Override
        public boolean shouldConsume() { return targets.size > 0; }

        @Override
        public float[] sideHeat(){
            return sideHeat;
        }

        @Override
        public float heatRequirement(){
            return heatRequirement;
        }

        /*@Override
        public void draw() {
            Draw.rect(rotation == 1 || rotation == 3 ? rot0 : rot1, x, y, 0);
            if(directional){
                Draw.rect(arrow, x, y, rotdeg());
            }
        }*/
    }
}