package net.vergessxner.sumo.listener;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.TeamManager;
import net.vergessxner.sumo.utils.gamestates.GameStateManager;
import net.vergessxner.sumo.utils.gamestates.states.EndingState;
import net.vergessxner.sumo.utils.gamestates.states.InGameState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        //Check if Player is under Death Height
        if(Sumo.getInstance().getGameStateManager().getCurrentGameState() instanceof InGameState && Sumo.getInstance().getGameStateManager().getCurrentGameState().isFullyRunning()) {
            if(player.getLocation().getY() <= Sumo.getInstance().getConfigLoader().getConfig().getDeathHeight()
            && !TeamManager.spectators.contains(player)) {

                TeamManager.switchToSpectator(player);
                Bukkit.broadcastMessage(Sumo.PREFIX + "§7" + player.getName() + "§c ist ausgeschieden");


                //Winning Check
                if(Bukkit.getOnlinePlayers().size() - TeamManager.spectators.size() == 1) {
                    //Get Winner
                    Player winner = null;
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if(!TeamManager.spectators.contains(onlinePlayer)){
                            winner = onlinePlayer;

                            onlinePlayer.sendTitle("§6Gewonnen", "", 5, 20, 10);
                        } else onlinePlayer.sendTitle("§cVerloren", "", 5, 20, 10);

                        onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 8);
                    }

                    Bukkit.broadcastMessage(Sumo.PREFIX + "§2" + winner.getName() + " §7hat das Spiel gewonnen§7!");
                    Sumo.getInstance().getGameStateManager().startGameState(EndingState.class);
                }
            }
        }
    }

}
