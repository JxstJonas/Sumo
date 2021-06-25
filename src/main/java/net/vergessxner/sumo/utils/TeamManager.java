package net.vergessxner.sumo.utils;

import net.vergessxner.sumo.Sumo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class TeamManager {

    public static final ArrayList<Player> spectators = new ArrayList<>();

    public static void switchToSpectator(Player player) {
        spectators.add(player);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(!spectators.contains(onlinePlayer)) onlinePlayer.hidePlayer(Sumo.getInstance(), player);
        }

        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(Sumo.getInstance().getConfigLoader().getConfig().getSpecSpawn().toLocation());
    }

}
