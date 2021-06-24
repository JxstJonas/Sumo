package net.vergessxner.sumo.listener;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.Settings;
import net.vergessxner.sumo.utils.gamestates.IGameStates;
import net.vergessxner.sumo.utils.gamestates.states.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class ConnectListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        IGameStates gamestate = Sumo.getInstance().getGameStateManager().getCurrentGameState();

        if(gamestate instanceof LobbyState) {
            event.setJoinMessage(Sumo.PREFIX + "§a" + player.getName() + " §7hat das Spiel betreten");

            LobbyState lobbyState = (LobbyState) gamestate;

            //Start LobbyCountdown and stop Searching Countdown
            if(lobbyState.getSearchCountdown().isRunning() && Bukkit.getOnlinePlayers().size() == Settings.MIN_PLAYERS) {
                lobbyState.getSearchCountdown().stop();
                lobbyState.getLobbyCountdown().start();
            }
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        IGameStates gamestate = Sumo.getInstance().getGameStateManager().getCurrentGameState();

        if(gamestate instanceof LobbyState) {
            event.setQuitMessage(Sumo.PREFIX + "§c" + player.getName() + " §7hat das Spiel verlassen");

            LobbyState lobbyState = (LobbyState) gamestate;

            //Stop LobbyCountDown and Start Searching Countdown
            if(lobbyState.getLobbyCountdown().isRunning() && Bukkit.getOnlinePlayers().size() == Settings.MIN_PLAYERS) {
                lobbyState.getLobbyCountdown().stop();
                lobbyState.getSearchCountdown().start();
            }
        }
    }

}
