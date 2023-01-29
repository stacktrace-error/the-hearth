package hearth.world.blocks.units;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Nullable;
import hearth.type.Recipe;
import mindustry.entities.Units;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.PayloadBlock;
import mindustry.world.blocks.units.UnitAssembler;
import mindustry.world.consumers.ConsumePayloadDynamic;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BlockGroup;

public class UnitManufacturer extends PayloadBlock{

    public Seq<Recipe> plans = new Seq<>();
    protected @Nullable ConsumePayloadDynamic consPayload;
    public TextureRegion[] topRegions;

    public UnitManufacturer(String name) {
        super(name);
        update = true;
        rotate = true;
        rotateDraw = false;
        acceptsPayload = true;
        flags = EnumSet.of(BlockFlag.unitAssembler);
        group = BlockGroup.units;
        commandable = true;
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("progress", (UnitAssembler.UnitAssemblerBuild e) -> new Bar("bar.progress", Pal.ammo, () -> e.progress));

        addBar("units", (UnitAssembler.UnitAssemblerBuild e) ->
            new Bar(() ->
                Core.bundle.format("bar.unitcap",
                        Fonts.getUnicodeStr(e.unit().name),
                        e.team.data().countType(e.unit()),
                        Units.getStringCap(e.team)
                ),
                () -> Pal.power,
                () -> (float)e.team.data().countType(e.unit()) / Units.getCap(e.team)
            )
        );
    }

    @Override
    public void load() {
        super.load();
        Seq<TextureRegion> reg = new Seq<>();

        for (int i = 1; i < 4; i++) {
            reg.add(Core.atlas.find(name + "-top-" + i));
        }
        topRegions = reg.toArray(TextureRegion.class);
    }

    public class UnitManufacturerBuild extends PayloadBlockBuild<Payload>{

    }
}
