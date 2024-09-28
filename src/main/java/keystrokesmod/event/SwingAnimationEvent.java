package keystrokesmod.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public final class SwingAnimationEvent extends Event {
    private int animationEnd;

    public SwingAnimationEvent(int animationEnd) {
        this.animationEnd = animationEnd;
    }

    public int getAnimationEnd() {
        return animationEnd;
    }

    public void setAnimationEnd(int animationEnd) {
        this.animationEnd = animationEnd;
    }
}