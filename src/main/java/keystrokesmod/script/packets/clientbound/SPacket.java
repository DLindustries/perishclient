package keystrokesmod.script.packets.clientbound;

public class SPacket {
    public String name;
    protected net.minecraft.network.Packet packet;

    public SPacket(net.minecraft.network.Packet b) {
        this.packet = b;
        this.name = b.getClass().getSimpleName();
    }
}
