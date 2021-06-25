package net.vergessxner.sumo.utils.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Jonas H.
 * Created: 25 Juni 2021
 */

public class PlayerInvs {

    public static void setLobbyInv(Player player) {
        player.getInventory().clear();

        player.getInventory().setItem(8, ItemBuilder.of(Material.FIRE_CHARGE).setDisplayName("§8» §cVerlassen §8(§7§oRechtsklick§8)").build());
    }

    public static void setInGameInv(Player player) {
        player.getInventory().clear();

        player.getInventory().setItem(0, ItemBuilder.of(Material.SLIME_BALL).setDisplayName("§8» §2Slimeball").build());
    }

}
