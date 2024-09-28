package keystrokesmod.module.impl.other.anticheats.utils.world;

import keystrokesmod.Raven;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LevelUtils {
    public static WorldClient getClientLevel() {
        return Objects.requireNonNull(Raven.mc.theWorld);
    }

    public static @NotNull List<EntityLivingBase> getEntities(@NotNull WorldClient level) {
        List<EntityLivingBase> result = new ArrayList<>();
        level.loadedEntityList.forEach(entity -> {
            if (entity instanceof EntityLivingBase) result.add((EntityLivingBase) entity);
        });
        return result;
    }

    public static @NotNull List<AbstractClientPlayer> getPlayers(@NotNull WorldClient level) {
        List<AbstractClientPlayer> result = new ArrayList<>();
        level.loadedEntityList.forEach(entity -> {
            if (entity instanceof AbstractClientPlayer) result.add((AbstractClientPlayer) entity);
        });
        return result;
    }

    public static @NotNull List<EntityLivingBase> getEntities() {
        return getEntities(getClientLevel());
    }

    public static @NotNull List<AbstractClientPlayer> getPlayers() {
        return getPlayers(getClientLevel());
    }
}
