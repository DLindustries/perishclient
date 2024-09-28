package keystrokesmod.module.impl.other.anticheats.checks.combat;

import keystrokesmod.module.impl.other.Anticheat;
import keystrokesmod.module.impl.other.anticheats.Check;
import keystrokesmod.module.impl.other.anticheats.TRPlayer;
import keystrokesmod.module.impl.other.anticheats.config.AdvancedConfig;
import keystrokesmod.module.impl.other.anticheats.utils.world.BlockUtils;
import keystrokesmod.module.impl.other.anticheats.utils.world.LevelUtils;
import keystrokesmod.script.classes.Vec3;
import keystrokesmod.utility.RotationUtils;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class HitBoxA extends Check {
    public HitBoxA(@NotNull TRPlayer player) {
        super("*HitBoxA*", player);
    }

    @Override
    public void _onTick() {
        if (player.currentSwing && !player.lastSwing) {  // 第1个挥手tick
            onSwing();
        }
    }

    private void onSwing() {
        if (isDisabled()) return;

        player.timeTask.schedule(() -> {
            try {
                WorldClient level = LevelUtils.getClientLevel();
                EntityLivingBase target = LevelUtils.getEntities(level).stream()
                        .filter(entity -> !(entity == player.fabricPlayer))
                        .filter(entity -> entity.hurtTime >= 10 - AdvancedConfig.hitBoxACheckDelay)
                        .filter(entity -> new Vec3(entity).distanceTo(player.fabricPlayer) <= 6)
                        .min((e1, e2) -> (int) ((new Vec3(e1).distanceTo(player.fabricPlayer) - new Vec3(e2).distanceTo(player.fabricPlayer)) * 100))
                        .orElseThrow(NoSuchElementException::new);

                // 此时至少有任何一个可能的目标被命中，那么检查是否击中墙壁
                // bro I just found out there's a RotationUtils include raycast 💀
                MovingObjectPosition hitResult = RotationUtils.rayCast(new Vec3(target).distanceTo(player.fabricPlayer), player.currentRot.y, player.currentRot.x);

                if (hitResult.typeOfHit != MovingObjectPosition.MovingObjectType.MISS && BlockUtils.isFullBlock(LevelUtils.getClientLevel().getBlockState(hitResult.getBlockPos()))) {
                    flag("Impossible hit.");
                }
            } catch (NoSuchElementException ignored) {}
        }, AdvancedConfig.hitBoxACheckDelay * 50L, TimeUnit.MILLISECONDS);

    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.hitBoxAAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getCombatCheck().isToggled() || !Anticheat.getExperimentalMode().isToggled();
    }
}
