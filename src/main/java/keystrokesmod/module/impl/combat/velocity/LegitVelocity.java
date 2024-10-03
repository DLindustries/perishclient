package keystrokesmod.module.impl.combat.velocity;

import keystrokesmod.Raven;
import keystrokesmod.event.PostVelocityEvent;
import keystrokesmod.module.impl.combat.Velocity;
import keystrokesmod.module.setting.impl.ButtonSetting;
import keystrokesmod.module.setting.impl.SliderSetting;
import keystrokesmod.module.setting.impl.SubMode;
import keystrokesmod.module.setting.utils.ModeOnly;
import keystrokesmod.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

public class LegitVelocity extends SubMode<Velocity> {
    private final ButtonSetting jumpInInv;
    private final ButtonSetting jumpResetEnabled;
    private final SliderSetting minDelay;
    private final SliderSetting maxDelay;
    private final SliderSetting chance;
    private final ButtonSetting targetNearbyCheck;
    private final ButtonSetting ignoreLiquid;

    public LegitVelocity(String name, Velocity parent) {
        super(name, parent);
        this.registerSetting(jumpInInv = new ButtonSetting("Jump in inv", false));
        this.registerSetting(jumpResetEnabled = new ButtonSetting("Jump Reset Enabled", true));
        this.registerSetting(minDelay = new SliderSetting("Min delay", 0, 0, 150, 1, "ms", jumpResetEnabled::isToggled));
        this.registerSetting(maxDelay = new SliderSetting("Max delay", 0, 0, 150, 1, "ms", jumpResetEnabled::isToggled));
        this.registerSetting(chance = new SliderSetting("Chance", 80, 0, 100, 1, "%", jumpResetEnabled::isToggled));
        this.registerSetting(targetNearbyCheck = new ButtonSetting("Target nearby check", false));
        this.registerSetting(ignoreLiquid = new ButtonSetting("Ignore liquid", true));
    }

    @Override
    public void guiUpdate() {
        Utils.correctValue(minDelay, maxDelay);
    }

    @SubscribeEvent
    public void onPostVelocity(PostVelocityEvent event) {
        if (Utils.nullCheck()) {
            if (mc.thePlayer.maxHurtTime <= 0)
                return;
            if (ignoreLiquid.isToggled() && Utils.inLiquid())
                return;
            if (targetNearbyCheck.isToggled() && !Utils.isTargetNearby())
                return;

            // Always try to jump when taking knockback
            if (canJump()) {
                if (Math.random() * 100 < chance.getInput()) {
                    // Successful jump reset, jump immediately
                    mc.thePlayer.jump();
                    Utils.sendMessage("jump reset sucessfully");
                } else {
                    // Failed jump reset, jump with delay
                    long delay = (long) (Math.random() * (maxDelay.getInput() - minDelay.getInput()) + minDelay.getInput());
                    Raven.getExecutor().schedule(this::performDelayedJump, delay, TimeUnit.MILLISECONDS);
                    Utils.sendMessage("jump reset failed");
                }
            }
        }
    }

    private boolean canJump() {
        return mc.thePlayer.onGround && (jumpInInv.isToggled() || mc.currentScreen == null);
    }

    private void performDelayedJump() {
        if (canJump()) {
            mc.thePlayer.jump();
        }
    }
}