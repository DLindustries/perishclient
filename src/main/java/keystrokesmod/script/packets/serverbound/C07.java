package keystrokesmod.script.packets.serverbound;

import keystrokesmod.script.classes.Vec3;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class C07 extends CPacket {
    public Vec3 pos;
    public String status;
    public String facing;

    public C07(Vec3 pos, String status, String facing) {
        super(null);
        this.pos = pos;
        this.status = status;
        this.facing = facing;
    }

    protected C07(C07PacketPlayerDigging packet) {
        super(packet);
        this.pos = Vec3.convert(packet.getPosition());
        this.status = packet.getStatus().name();
        this.facing = packet.getFacing().name();
    }

    @Override
    public C07PacketPlayerDigging convert() {
        return new C07PacketPlayerDigging(getAction(), new BlockPos(this.pos.x, this.pos.y, this.pos.z), getEnumFacing());
    }

    private C07PacketPlayerDigging.Action getAction() {
        for (C07PacketPlayerDigging.Action action : C07PacketPlayerDigging.Action.values()) {
            if (action.name().equals(this.status)) {
                return action;
            }
        }
        return null;
    }

    private EnumFacing getEnumFacing() {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            if (enumFacing.name().equals(this.facing)) {
                return enumFacing;
            }
        }
        return null;
    }
}
