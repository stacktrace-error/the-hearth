package hearth.type;

import arc.util.Nullable;
import mindustry.ctype.UnlockableContent;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.type.PayloadStack;

public class Recipe{
    public UnlockableContent displayContent; //lets me use buildtables
    public float time;
    public float payloadSpeed = 0.7f;
    public @Nullable ItemStack[] inputItems;
    public @Nullable ItemStack[] outputItems;
    public @Nullable LiquidStack[] inputLiquids;
    public @Nullable LiquidStack   outputLiquid;
    public @Nullable PayloadStack[] inputPayload;
    public @Nullable PayloadStack[] outputPayload;
    public float inputPower = 0f;

    public Recipe(){}
}