package keystrokesmod.script.classes;

import keystrokesmod.event.PreMotionEvent;

public class PlayerState {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public boolean isSprinting;
    public boolean isSneaking;

    public PlayerState(PreMotionEvent e) {
        this.x = e.getPosX();
        this.y = e.getPosY();
        this.z = e.getPosZ();
        this.yaw = e.getYaw();
        this.pitch = e.getPitch();
        this.onGround = e.isOnGround();
        this.isSprinting = e.isSprinting();
        this.isSneaking = e.isSneaking();
    }
}
