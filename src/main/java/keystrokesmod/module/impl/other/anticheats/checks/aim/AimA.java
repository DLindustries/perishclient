package keystrokesmod.module.impl.other.anticheats.checks.aim;

import keystrokesmod.module.impl.other.Anticheat;
import keystrokesmod.module.impl.other.anticheats.Check;
import keystrokesmod.module.impl.other.anticheats.TRPlayer;
import keystrokesmod.module.impl.other.anticheats.config.AdvancedConfig;
import keystrokesmod.module.impl.other.anticheats.utils.world.EntityUtils;
import keystrokesmod.module.impl.other.anticheats.utils.world.LevelUtils;
import keystrokesmod.module.impl.other.anticheats.utils.world.PlayerMove;
import keystrokesmod.module.impl.other.anticheats.utils.world.PlayerRotation;
import keystrokesmod.script.classes.Vec3;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class AimA extends Check {
    public AimA(@NotNull TRPlayer player) {
        super("AimA", player);
    }

    @Override
    public void _onTick() {
        if (player.currentRot.equals(player.lastRot)) return;
        if (AdvancedConfig.aimAOnlyOnSwing && !(!player.lastSwing && player.currentSwing)) return;

        float deltaYaw = player.currentRot.y - player.lastRot.y;
        float deltaPitch = player.currentRot.x - player.lastRot.x;

        if (deltaYaw < AdvancedConfig.aimAMinDeltaYaw || deltaPitch < AdvancedConfig.aimAMinDeltaPitch) return;

        List<EntityLivingBase> possibleTargets = LevelUtils.getEntities().stream()
                .filter(entity -> !AdvancedConfig.aimAOnlyPlayer || entity instanceof EntityPlayer)
                .filter(entity -> !(entity == player.fabricPlayer))
                .filter(entity -> !AdvancedConfig.aimAOnlyIfTargetIsMoving || !PlayerMove.isNoMove(new Vec3(entity.motionX, entity.motionY, entity.motionZ)))
                .filter(entity -> new Vec3(entity).distanceTo(player.fabricPlayer) <= AdvancedConfig.aimAMaxDistance)
                .collect(Collectors.toList());

        double diffYaw = 0;
        double diffPitch = 0;
        boolean flagYaw = false;
        boolean flagPitch = false;
        EntityLivingBase flagTarget = null;
        for (EntityLivingBase entity : possibleTargets) {
            diffYaw = Math.abs(PlayerRotation.getYaw(player.fabricPlayer, EntityUtils.getEyePosition(entity)) - entity.rotationYaw);
            diffPitch = Math.abs(PlayerRotation.getPitch(player.fabricPlayer, EntityUtils.getEyePosition(entity)) - entity.rotationPitch);

            flagYaw = diffYaw < AdvancedConfig.aimAMinDiffYaw;
            flagPitch = diffPitch < AdvancedConfig.aimAMinDiffPitch;

            if (flagPitch || flagYaw) {
                flagTarget = entity;
                break;
            }
        }

        if (flagYaw && flagPitch) {
            flag(String.format("Too small deviation. target: %s  diff: %.2f,%.2f  delta: %.2f,%.2f",
                    flagTarget.getName(), diffYaw, diffPitch, deltaYaw, deltaPitch)
            );
        } else if (flagYaw) {
            flag(String.format("Too small yaw deviation. target: %s  diff: %.2f  delta: %.2f",
                    flagTarget.getName(), diffYaw, deltaYaw)
            );
        } else if (flagPitch) {
            flag(String.format("Too small pitch deviation. target: %s  diff: %.2f  delta: %.2f",
                    flagTarget.getName(), diffPitch, deltaPitch)
            );
        }
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.aimAAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getAimCheck().isToggled() || !Anticheat.getExperimentalMode().isToggled();
    }
}
