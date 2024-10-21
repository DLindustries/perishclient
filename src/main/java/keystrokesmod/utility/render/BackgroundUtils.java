package keystrokesmod.utility.render;

import keystrokesmod.Raven;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class BackgroundUtils {
    private static final int BLOOM_COLOR = new Color(255, 255, 255, 50).getRGB();
    private static int mountainx = 0; // Initialize mountainx to 0

    public static void renderBackground(@NotNull GuiScreen gui) {
        final int width = gui.width;
        final int height = gui.height;

        // Calculate the original width of the mountain image
        int mountainWidth = width * 2; // Assuming the mountain image is twice the screen width

        // Render the mountain image at its original size
        RenderUtils.drawImage(new ResourceLocation("keystrokesmod:textures/backgrounds/mountain.png"), mountainx, 0, mountainWidth, height);

        // Update mountainx to create the scrolling effect
        mountainx = (mountainx - 1) % mountainWidth; // Decrement mountainx and wrap around to 0 when it reaches the end of the image


    }

    @Getter
    private static final ResourceLocation logoPng = new ResourceLocation("keystrokesmod:textures/backgrounds/loadingscreen.png");
}
