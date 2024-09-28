package keystrokesmod.event;

import keystrokesmod.utility.GuiConnectingMsg;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PreConnectEvent extends Event {
    private final GuiConnecting screen;
    private final String ip;
    private final int port;
    private final GuiConnectingMsg extraMessage = new GuiConnectingMsg();

    public PreConnectEvent(GuiConnecting screen, String ip, int port) {
        this.screen = screen;
        this.ip = ip;
        this.port = port;
    }

    public GuiConnecting getScreen() {
        return screen;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public GuiConnectingMsg getExtraMessage() {
        return extraMessage;
    }
}
