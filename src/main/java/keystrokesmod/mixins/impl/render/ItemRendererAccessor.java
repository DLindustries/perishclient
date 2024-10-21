package keystrokesmod.mixins.impl.render;

import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {

    @Invoker("transformFirstPersonItem")
    void transformFirstPersonItem(float animationProgression, float swingProgress);

    @Invoker("func_178103_d")
    void blockTransformation();
}
