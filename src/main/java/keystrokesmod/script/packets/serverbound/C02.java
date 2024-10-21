package keystrokesmod.script.packets.serverbound;

import keystrokesmod.script.classes.Entity;
import keystrokesmod.script.classes.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class C02 extends CPacket {
    public Entity entity;
    public String action;
    public Vec3 hitVec;

    public C02(Entity entity, String action, Vec3 hitVec) {
        super(null);
        this.entity = entity;
        this.action = action;
        this.hitVec = hitVec;
    }

    public C02(C02PacketUseEntity packet) {
        super(packet);
        if (packet.getEntityFromWorld(Minecraft.getMinecraft().theWorld) == null) {
            this.entity = null;
        }
        else {
            this.entity = new Entity(packet.getEntityFromWorld(Minecraft.getMinecraft().theWorld));
        }
        this.action = packet.getAction().name();
        if (packet.getHitVec() != null) {
            this.hitVec = new Vec3(packet.getHitVec().xCoord, packet.getHitVec().yCoord, packet.getHitVec().zCoord);
        }
    }

    @Override
    public C02PacketUseEntity convert() {
        if (this.hitVec != null && getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
            return new C02PacketUseEntity(this.entity.entity, new net.minecraft.util.Vec3(this.hitVec.x, this.hitVec.y, this.hitVec.z));
        }
        return new C02PacketUseEntity(this.entity.entity, getAction());
    }

    private C02PacketUseEntity.Action getAction() {
        for (C02PacketUseEntity.Action action : C02PacketUseEntity.Action.values()) {
            if (action.name().equals(this.action)) {
                return action;
            }
        }
        return null;
    }
}
