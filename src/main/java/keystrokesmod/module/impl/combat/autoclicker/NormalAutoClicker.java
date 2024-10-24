package keystrokesmod.module.impl.combat.autoclicker;

import keystrokesmod.module.setting.impl.ButtonSetting;
import keystrokesmod.module.setting.impl.SliderSetting;
import keystrokesmod.module.setting.impl.SubMode;
import keystrokesmod.utility.CoolDown;
import keystrokesmod.utility.Utils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

public class NormalAutoClicker extends SubMode<IAutoClicker> {
    private final SliderSetting minCPS;
    private final SliderSetting maxCPS;
    private final ButtonSetting butterFly;
    private final boolean leftClick;
    private final boolean rightClick;
    private final boolean always;

    private final CoolDown clickStopWatch = new CoolDown(0);
    private int ticksDown;
    private long nextSwing;
    private long clickStartTime; // added for 3-second timer

    public NormalAutoClicker(String name, @NotNull IAutoClicker parent, boolean left, boolean always) {
        super(name, parent);
        this.leftClick = left;
        this.rightClick = !left;
        this.always = always;

        minCPS = new SliderSetting("Min CPS", 8, 1, 40, 0.1);
        maxCPS = new SliderSetting("Max CPS", 14, 1, 40, 0.1);
        butterFly = new ButtonSetting("Butterfly", true);
        this.registerSetting(minCPS, maxCPS, butterFly);
    }

    @Override
    public void guiUpdate() {
        Utils.correctValue(minCPS, maxCPS);
    }

    @Override
    public void onUpdate() {
        clickStopWatch.setCooldown(nextSwing);
        if (clickStopWatch.hasFinished()) {
            final long clicks = (long) (Utils.randomizeDouble(minCPS.getInput(), maxCPS.getInput()));

            if (Mouse.isButtonDown(0) || always) {
                ticksDown++;
                if (clickStartTime == 0) {
                    clickStartTime = System.currentTimeMillis(); // start timer when left click is held
                } else if (System.currentTimeMillis() - clickStartTime > 3000) {
                    Utils.sendMessage("You are clicking for too long!"); // send message if held for more than 3 seconds
                }
            } else {
                ticksDown = 0;
                clickStartTime = 0; // reset timer when left click is released
            }

            if (this.nextSwing >= 50 * 2 && butterFly.isToggled()) {
                this.nextSwing = (long) (Math.random() * 100);
            } else {
                this.nextSwing = 1000 / clicks;
            }

            if (rightClick && ((Mouse.isButtonDown(1) && !Mouse.isButtonDown(0)) || always)) {
                parent.click();

                if (Math.random() > 0.9) {
                    parent.click();
                }
            }

            if (leftClick && ticksDown > 1 && (!Mouse.isButtonDown(1) || always)) {
                parent.click();
            }

            this.clickStopWatch.start();
        }
    }
}