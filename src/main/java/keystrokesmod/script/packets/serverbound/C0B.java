package keystrokesmod.script.packets.serverbound;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class C0B extends CPacket {
    public String action;
    public int horsePower;

    public C0B(String action, int horsePower) {
        super(null);
        this.action = action;
        this.horsePower = horsePower;
    }
    public C0B(C0BPacketEntityAction packet) {
        super(packet);
        this.action = packet.getAction().name();
        this.horsePower = packet.getAuxData();
    }

    @Override
    public C0BPacketEntityAction convert() {
        return new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, getAction(), horsePower);
    }

    private C0BPacketEntityAction.Action getAction() {
        for (C0BPacketEntityAction.Action action : C0BPacketEntityAction.Action.values()) {
            if (action.name().equals(this.action)) {
                return action;
            }
        }
        return null;
    }
}
