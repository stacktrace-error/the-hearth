package hearth.world.blocks.payload;

import arc.Core;
import arc.graphics.g2d.Draw;
import hearth.type.ItemPayload;
import hearth.type.PayloadItem.PayloadItemBuild;
import mindustry.gen.Building;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.storage.CoreBlock;

@SuppressWarnings("unused")
public class ComponentReceiver extends PayloadBlock{
    public float maxSize = 2;

    public ComponentReceiver(String name) {
        super(name);
        acceptsPayload = true;
        outputsPayload = false;
        rotate = true;
    }

    public class ComponentReceiverBuild extends PayloadBlockBuild<BuildPayload>{
        public CoreBlock.CoreBuild linkedCore = null;

        @Override
        public boolean acceptPayload(Building source, Payload payload){ //todo lock input to backside
            return super.acceptPayload(source, payload) && linkedCore != null &&
                payload.fits(maxSize) &&
                payload instanceof ItemPayload &&
                ((ItemPayload)payload).item() != null &&
                linkedCore.acceptItem(source, ((ItemPayload)payload).item());
        }

        @Override
        public void updateTile() {
            super.updateTile();

            if(linkedCore != null && moveInPayload()){
                linkedCore.handleItem(this, ((ItemPayload)payload).item());
                payload.build.remove();
                payload = null;
            }
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            if(linkedCore == null) {
                proximity.each(t -> {
                    if (t instanceof CoreBlock.CoreBuild) {
                        linkedCore = (CoreBlock.CoreBuild) t;
                    }
                });
            } else if(linkedCore.tile.build == null) linkedCore = null;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.rect(inRegion, x, y, rotdeg());

            drawPayload();

            Draw.rect(topRegion, x, y);
            if (block.teamRegions[team.id] == block.teamRegion) Draw.color(team.color);
            Draw.rect(block.teamRegions[team.id], x, y);
            Draw.rect(rotation >= 2 ?
                    Core.atlas.find(name+"-out2-"+team.name, Core.atlas.find(name+"-out2")) :
                    Core.atlas.find(name+"-out1-"+team.name, Core.atlas.find(name+"-out1")),
                    x, y, size * (rotation % 2 != 0 ? -16f : 16f), size * (rotation % 2 != 0 ? -16f : 16f), rotdeg());
            Draw.color();
        }
    }
}
