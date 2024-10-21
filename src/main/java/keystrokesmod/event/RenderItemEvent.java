package keystrokesmod.event;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public final class RenderItemEvent extends Event {
    private final EnumAction enumAction;
    private final boolean useItem;
    private final float animationProgression;
    private final float partialTicks;
    private final float swingProgress;
    private final ItemStack itemToRender;

    public RenderItemEvent(EnumAction enumAction, boolean useItem, float animationProgression, float partialTicks, float swingProgress, ItemStack itemToRender) {
        this.enumAction = enumAction;
        this.useItem = useItem;
        this.animationProgression = animationProgression;
        this.partialTicks = partialTicks;
        this.swingProgress = swingProgress;
        this.itemToRender = itemToRender;
    }

    public EnumAction getEnumAction() {
        return enumAction;
    }

    public boolean isUseItem() {
        return useItem;
    }

    public float getAnimationProgression() {
        return animationProgression;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public float getSwingProgress() {
        return swingProgress;
    }

    public ItemStack getItemToRender() {
        return itemToRender;
    }
}