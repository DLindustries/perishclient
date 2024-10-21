package keystrokesmod.mixins.impl.network;


import net.minecraft.network.play.client.C02PacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(C02PacketUseEntity.class)
public interface C02PacketUseEntityAccessor {

    @Accessor("entityId")
    int getEntityId();
}
