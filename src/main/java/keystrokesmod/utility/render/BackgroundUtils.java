package keystrokesmod.utility.render;

import keystrokesmod.utility.render.blur.GaussianBlur;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BackgroundUtils {
    private static final int BLOOM_COLOR = new Color(255, 255, 255, 50).getRGB();
    private static int mountainx = -99999;

    public static void renderBackground(@NotNull GuiScreen gui) {
        final int width = gui.width;
        final int height = gui.height;

        if (mountainx == -99999)
            mountainx = -width;


        RenderUtils.drawImage(new ResourceLocation("keystrokesmod:textures/backgrounds/mountain.png"), mountainx, height / 1F, width * 1F, height / 1F);
        RenderUtils.drawBloomShadow(0, 0, width, height, 12, 6, BLOOM_COLOR, true);
        RenderUtils.drawImage(new ResourceLocation("keystrokesmod:textures/backgrounds/ren.png"), 0, 0, width, height);
        if (mountainx >= 0) {
            mountainx = -width;
        }
        mountainx++;
    }
}
