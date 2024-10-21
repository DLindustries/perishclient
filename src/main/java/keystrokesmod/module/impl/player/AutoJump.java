package keystrokesmod.module.impl.player;

import keystrokesmod.module.Module;
import keystrokesmod.module.setting.impl.ButtonSetting;
import keystrokesmod.utility.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class AutoJump extends Module {
    public static ButtonSetting b;
    private boolean c = false;

    public AutoJump() {
        super("AutoJump", Module.category.player, 0);
        this.registerSetting(b = new ButtonSetting("Cancel when shifting", true));
    }

    public void onDisable() {
        this.ju(this.c = false);
    }

    @SubscribeEvent
    public void p(PlayerTickEvent e) {
        if (Utils.nullCheck()) {
            if (mc.thePlayer.onGround && (!b.isToggled() || !mc.thePlayer.isSneaking())) {
                if (Utils.onEdge()) {
                    this.ju(this.c = true);
                } else if (this.c) {
                    this.ju(this.c = false);
                }
            } else if (this.c) {
                this.ju(this.c = false);
            }

        }
    }

    private void ju(boolean ju) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), ju);
    }
}
