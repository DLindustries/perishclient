package keystrokesmod.module.impl.other.anticheats.checks.movement;

import keystrokesmod.module.impl.other.Anticheat;
import keystrokesmod.module.impl.other.anticheats.Check;
import keystrokesmod.module.impl.other.anticheats.TRPlayer;
import keystrokesmod.module.impl.other.anticheats.config.AdvancedConfig;
import keystrokesmod.module.impl.other.anticheats.utils.world.PlayerMove;
import keystrokesmod.script.classes.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class StrafeA extends Check {
    private static final Set<Integer> movement = new HashSet<>();
    private Set<Vec3> futureMotion = null;

    public StrafeA(@NotNull TRPlayer player) {
        super("StrafeA", player);
        movement.add(-1);
        movement.add(0);
        movement.add(1);
    }

    @Override
    public void _onTick() {
        if (player.currentOnGround || PlayerMove.isNoMove(player.currentMotion) || Math.abs(player.lastRot.y - player.currentRot.y) < 5) {
            futureMotion = null;
            return;
        }

        if (PlayerMove.isInvalidMotion(player.currentMotion)) return;
        if (player.sprintHistory.subList(0, 10).stream().anyMatch(b -> b != player.currentSprint)) return;

        if (futureMotion != null) {
            for (Vec3 motion : futureMotion) {
                double diff = PlayerMove.getMaxXZDiff(player.currentMotion, motion);
                if (diff <= AdvancedConfig.strafeAMaxDiffToFlag) {
                    flag(String.format("Strafe in air. diff:%.4f", diff));
                }
            }
        }

        futureMotion = new HashSet<>();

        for (int forward : movement) {
            for (int strafe : movement) {
                Vec3 strafeMotion = PlayerMove.getStrafeMotion(
                        PlayerMove.speed(player.fabricPlayer),
                        PlayerMove.direction(forward, strafe, player.fabricPlayer.prevRotationYaw + (player.fabricPlayer.rotationYaw - player.fabricPlayer.prevRotationYaw) * 50),
                        player.currentMotion.y()
                );
                futureMotion.add(strafeMotion);
            }
        }
    }



    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.strafeAAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getMovementCheck().isToggled();
    }
}
