package keystrokesmod.script.packets.serverbound;

import net.minecraft.network.Packet;

public class CPacket {
    public String name;
    public net.minecraft.network.Packet packet;

    public CPacket(net.minecraft.network.Packet packet) {
        if (packet == null) {
            return;
        }
        this.packet = packet;
        this.name = packet.getClass().getSimpleName();
    }

    public Packet convert() {
        return packet;
    }
}
