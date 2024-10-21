package keystrokesmod.module.impl.render;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.setting.impl.ButtonSetting;
import keystrokesmod.module.setting.impl.DescriptionSetting;
import keystrokesmod.utility.ContainerUtils;

public class NoBackground extends Module {
    private static ButtonSetting onlyChest = null;

    public NoBackground() {
        super("NoBackground", category.render);
        this.registerSetting(new DescriptionSetting("Remove default background."));
        this.registerSetting(onlyChest = new ButtonSetting("Only chest", false));
    }

    public static boolean noRender() {
        return ModuleManager.noBackground != null && ModuleManager.noBackground.isEnabled()
                && onlyChest != null && (!onlyChest.isToggled() || ContainerUtils.isChest(true));
    }
}
