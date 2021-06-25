package net.vergessxner.sumo.listener;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.gamestates.states.InGameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Jonas H.
 * Created: 25 Juni 2021
 */

public class ClickListener implements Listener {

    /**
     * If you are looking for a TrowListener where the ball is thrown, look in {@link TrowSlimeBall#start()}.
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!(Sumo.getInstance().getGameStateManager().getCurrentGameState() instanceof InGameState)) {
            Player player = event.getPlayer();

            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(event.getItem() != null && event.getItem().getItemMeta().getDisplayName().equals("§8» §cVerlassen §8(§7§oRechtsklick§8)")) {
                    player.kickPlayer(null);
                    event.setCancelled(true);
                }
            }

        }
    }

}
