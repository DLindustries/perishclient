package keystrokesmod.mixins.impl.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.NetworkManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiConnecting.class)
public interface GuiConnectingAccessor {
    @Accessor("networkManager")
    NetworkManager getNetworkManager();

    @Accessor("networkManager")
    void setNetworkManager(NetworkManager networkManager);

    @Accessor("cancel")
    boolean isCancel();

    @Accessor("logger")
    Logger getLogger();

    @Accessor("previousGuiScreen")
    GuiScreen getPreviousGuiScreen();
}
