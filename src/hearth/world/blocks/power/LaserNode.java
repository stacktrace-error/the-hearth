package hearth.world.blocks.power;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.Log;
import mindustry.Vars;
import mindustry.core.Renderer;
import mindustry.entities.TargetPriority;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.blocks.power.BeamNode;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.meta.BlockStatus;
import mindustry.world.meta.Env;

import java.util.Arrays;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class LaserNode extends BeamNode{
    public int range = 5;

    public float pulseScl = 7, pulseMag = 0.05f;
    public float laserWidth = 0.8f;

    public LaserNode(String name){
        super(name);
        consumesPower = outputsPower = false;
        drawDisabled = false;
        envEnabled |= Env.space;
        allowDiagonal = false;
        underBullets = true;
        priority = TargetPriority.transport;
        laserColor1 = Color.white;
        laserColor2 = Color.white.cpy().a(0.5f);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        for(int i = 0; i < 8; i++){
            int maxLen = range + size/2;
            Building dest = null;
            Point2 dir = Geometry.d8[i];
            int dx = dir.x, dy = dir.y;
            int offset = size/2;
            for(int j = 1 + offset; j <= range + offset; j++){
                Building other = world.build(x + j * dir.x, y + j * dir.y);

                //hit insulated wall
                if(other != null && other.isInsulated()){
                    break;
                }

                if(other != null && other.block.hasPower && other.team == Vars.player.team() && !(other.block instanceof PowerNode)){
                    maxLen = j;
                    dest = other;
                    break;
                }
            }

            Drawf.dashLine(Pal.placing,
                    x * tilesize + dx * (tilesize * size / 2f + 2),
                    y * tilesize + dy * (tilesize * size / 2f + 2),
                    x * tilesize + dx * (maxLen) * tilesize,
                    y * tilesize + dy * (maxLen) * tilesize
            );

            if(dest != null){
                Drawf.square(dest.x, dest.y, dest.block.size * tilesize/2f + 2.5f, 0f);
            }
        }
    }

    public class LaserNodeBuild extends BeamNodeBuild {
        public Building[] links = new Building[8];
        public Tile[] dests = new Tile[8];
        public int lastChange = -2;

        @Override
        public void updateTile(){
            if(lastChange != world.tileChanges){
                lastChange = world.tileChanges;
                updateDirections();
            }
        }

        @Override
        public BlockStatus status(){
            if(Mathf.equal(power.status, 0f, 0.001f)) return BlockStatus.noInput;
            if(Mathf.equal(power.status, 1f, 0.001f)) return BlockStatus.active;
            return BlockStatus.noOutput;
        }

        @Override
        public void draw(){
            super.draw();

            if(Mathf.zero(Renderer.laserOpacity)) return;

            Draw.z(Layer.power);
            Draw.color(laserColor1, laserColor2, (1f - power.graph.getSatisfaction()) * 0.86f + Mathf.absin(3f, 0.1f));
            Draw.alpha(Renderer.laserOpacity);
            float w = laserWidth + Mathf.absin(pulseScl, pulseMag);

            for(int i = 0; i < 8; i ++){
                if(links[i] == null || links[i].block == null) return;
                LaserNode node = null;
                if(links[i].block instanceof LaserNode) node = (LaserNode)links[i].block;

                if(     dests[i] != null && (!(links[i].block instanceof LaserNode) ||
                        (links[i].tileX() != tileX() && links[i].tileY() != tileY()) ||
                        node != null && (links[i].id > id && range >= node.range) ||
                        node != null && range > node.range)){

                    int dst = Math.max(Math.abs(dests[i].x - tile.x),  Math.abs(dests[i].y - tile.y));
                    //don't draw lasers for adjacent blocks
                    if(dst > 1 + size/2){
                        Point2 point = Geometry.d8[i];
                        float poff = tilesize/2f;
                        Drawf.laser(laser, laserEnd, x + poff*size*point.x, y + poff*size*point.y, dests[i].worldx() - poff*point.x, dests[i].worldy() - poff*point.y, w);
                    }
                }
            }

            Draw.reset();
        }

        @Override
        public void pickedUp(){
            Arrays.fill(links, null);
            Arrays.fill(dests, null);
        }

        public void updateDirections() {
            for (int i = 0; i < 8; i++) {
                Building prev = links[i];
                Point2 dir = Geometry.d8[i];
                links[i] = null;
                dests[i] = null;
                int offset = size / 2;
                //find first block with power in range
                for (int j = 1 + offset; j <= range + offset; j++) {
                    Building other = world.build(tile.x + j * dir.x, tile.y + j * dir.y);

                    //hit insulated wall
                    if (other != null && other.isInsulated()) {
                        break;
                    }

                    //power nodes do NOT play nice with beam nodes, do not touch them as that forcefully modifies their links
                    if (other != null && other.block.hasPower && other.team == team && !(other.block instanceof PowerNode)) {
                        links[i] = other;
                        dests[i] = world.tile(tile.x + j * dir.x, tile.y + j * dir.y);
                        break;
                    }
                }

                Building next = links[i];

                if (next != prev) {
                    //unlinked, disconnect and reflow
                    if (prev != null) {
                        prev.power.links.removeValue(pos());
                        power.links.removeValue(prev.pos());

                        PowerGraph newgraph = new PowerGraph();
                        //reflow from this point, covering all tiles on this side
                        newgraph.reflow(this);

                        if (prev.power.graph != newgraph) {
                            //reflow power for other end
                            PowerGraph og = new PowerGraph();
                            og.reflow(prev);
                        }
                    }

                    //linked to a new one, connect graphs
                    if (next != null) {
                        power.links.addUnique(next.pos());
                        next.power.links.addUnique(pos());

                        power.graph.addGraph(next.power.graph);
                    }
                }
            }
        }
    }
}