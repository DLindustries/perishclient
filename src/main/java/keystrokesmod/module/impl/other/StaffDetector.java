package keystrokesmod.module.impl.other;

import keystrokesmod.Raven;
import keystrokesmod.module.Module;
import keystrokesmod.module.impl.client.Settings;
import keystrokesmod.module.setting.impl.ButtonSetting;
import keystrokesmod.module.setting.impl.ModeSetting;
import keystrokesmod.utility.PacketUtils;
import keystrokesmod.utility.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.io.*;
import java.util.*;

public class StaffDetector extends Module {
    public static final String[] STAFFLISTS = new String[]{ "Hypixel", "BlocksMC", "Gamster", "GommeHD", "Pika", "Syuu", "Stardix", "MinemenClub", "MushMC", "Twerion", "BedwarsPractice", "QuickMacro", "Heypixel", "HylexMC", "Jartex", "Mineland" }; public static final List<Set<String>> STAFFS = new ArrayList<>(); public static final Set<String> hasFlagged = new HashSet<>();
    // ... (rest of the code remains the same)

    private final ModeSetting mode = new ModeSetting("Mode", STAFFLISTS, 0);
    private final ButtonSetting autoLobby = new ButtonSetting("Auto lobby", false);
    private final ButtonSetting alarm = new ButtonSetting("Alarm", false);
    private final ButtonSetting autoDisconnect = new ButtonSetting("Auto disconnect", false);

    public StaffDetector() {
        super("StaffDetector", category.other);
        this.registerSetting(mode, autoLobby, alarm, autoDisconnect);

        // ... (rest of the code remains the same)
    }

    @Override
    public void onUpdate() {
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            final String name = player.getName();
            if (hasFlagged.contains(name)) continue;

            if (STAFFS.get((int) mode.getInput()).contains(name)) {
                hasFlagged.add(name);

                Utils.sendMessage("§c§lStaff Detected: §r" + name +"it is highly recommended to turn down settings or play legit");
                if (autoLobby.isToggled()) {
                    PacketUtils.sendPacket(new C01PacketChatMessage("/lobby"));
                    Utils.sendMessage("Automatically removing you from this game ...");
                }
                if (alarm.isToggled()) {
                    mc.thePlayer.playSound("keystrokesmod:alarm", 1, 1);
                }
                if (autoDisconnect.isToggled()) {
                    mc.shutdown();
                    Utils.sendMessage("Automatically disconnecting from the server ...");
                }
            }
        }
    }
}