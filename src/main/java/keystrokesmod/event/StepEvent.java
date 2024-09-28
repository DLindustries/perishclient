package keystrokesmod.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class StepEvent extends Event {
    private final double height;

    public StepEvent(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }
}
