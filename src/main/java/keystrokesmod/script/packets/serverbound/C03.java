package keystrokesmod.script.packets.serverbound;

import keystrokesmod.script.classes.Vec3;
import net.minecraft.network.play.client.C03PacketPlayer;

public class C03 extends CPacket {
    public Vec3 pos;
    public float yaw;
    public float pitch;
    public boolean ground;

    public C03(boolean ground) {
        super(new C03PacketPlayer(ground));
        this.ground = ground;
    }

    public C03(Vec3 pos, boolean ground) {
        super(new C03PacketPlayer.C04PacketPlayerPosition(pos.x, pos.y, pos.z, ground));
        this.pos = pos;
        this.ground = ground;
    }

    public C03(float yaw, float pitch, boolean ground) {
        super(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, ground));
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
    }

    public C03(Vec3 pos, float yaw, float pitch, boolean ground) {
        super(new C03PacketPlayer.C06PacketPlayerPosLook(pos.x, pos.y, pos.z, yaw, pitch, ground));
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
    }

    protected C03(C03PacketPlayer packet, String filler, String filler2, String filler3, String filler4, String filler5) {
        super(packet);
        if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            this.pos = new Vec3(packet.getPositionX(), packet.getPositionY(), packet.getPositionZ());
        }
        if (packet instanceof C03PacketPlayer.C05PacketPlayerLook || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            this.yaw = packet.getYaw();
            this.pitch = packet.getPitch();
        }
        this.ground = packet.isOnGround();
    }
}
