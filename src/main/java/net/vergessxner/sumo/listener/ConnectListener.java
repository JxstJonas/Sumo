package net.vergessxner.sumo.listener;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.Settings;
import net.vergessxner.sumo.utils.TeamManager;
import net.vergessxner.sumo.utils.gamestates.IGameStates;
import net.vergessxner.sumo.utils.gamestates.states.EndingState;
import net.vergessxner.sumo.utils.gamestates.states.InGameState;
import net.vergessxner.sumo.utils.gamestates.states.LobbyState;
import net.vergessxner.sumo.utils.helpers.MinecraftLocation;
import net.vergessxner.sumo.utils.helpers.PlayerInvs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class ConnectListener implements Listener {

    private ArrayList<Player> noQuitMessageShow = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        IGameStates gamestate = Sumo.getInstance().getGameStateManager().getCurrentGameState();

        if(gamestate instanceof LobbyState) {

            //Server Full
            if(Bukkit.getOnlinePlayers().size() > Settings.MAX_PLAYERS) {
                if(player.hasPermission("sumo.join")) {
                    Player kickPlayer = null;

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if(!onlinePlayer.hasPermission("sumo.join")) {
                            kickPlayer = onlinePlayer;
                        }
                    }

                    if(kickPlayer == null) {
                        noQuitMessageShow.add(player);
                        player.kickPlayer(Sumo.PREFIX + "§cDer Server ist voll!");
                        return;
                    } else {
                        kickPlayer.kickPlayer(Sumo.PREFIX + "§cDu wurdest von einem Premium Spieler oder höher gekickt");
                    }
                }else {
                    noQuitMessageShow.add(player);
                    player.kickPlayer(Sumo.PREFIX + "§cDer Server ist voll!");
                }
            }

            //Start with normal Joining Process
            event.setJoinMessage(Sumo.PREFIX + "§2" + player.getName() + " §7hat das Spiel betreten");

            LobbyState lobbyState = (LobbyState) gamestate;

            //Start LobbyCountdown and stop Searching Countdown
            if(lobbyState.getSearchCountdown().isRunning() && Bukkit.getOnlinePlayers().size() == Settings.MIN_PLAYERS) {
                lobbyState.getSearchCountdown().stop();
                lobbyState.getLobbyCountdown().start();
            }

            join(player);
        } else if(gamestate instanceof InGameState) {
            TeamManager.switchToSpectator(player);
            player.sendMessage(Sumo.PREFIX + "§7Du kannst nur das Spielgeschehen beobachten.");
        }else if(gamestate instanceof EndingState) {
            join(player);
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        IGameStates gamestate = Sumo.getInstance().getGameStateManager().getCurrentGameState();

        if(gamestate instanceof LobbyState) {
            if(!noQuitMessageShow.contains(player))
                event.setQuitMessage(Sumo.PREFIX + "§c" + player.getName() + " §7hat das Spiel verlassen");

            LobbyState lobbyState = (LobbyState) gamestate;

            //Stop LobbyCountDown and Start Searching Countdown
            if(lobbyState.getLobbyCountdown().isRunning() && Bukkit.getOnlinePlayers().size()  == Settings.MIN_PLAYERS) {
                lobbyState.getLobbyCountdown().stop();
                lobbyState.getSearchCountdown().start();

                player.sendMessage(Sumo.PREFIX + "§7Der Countdown wurde §cabgebrochen§7!");
            }
        } else if(gamestate instanceof InGameState) {
            if(TeamManager.spectators.contains(player)) return;

            //Checking whether the match has been won
            if(Bukkit.getOnlinePlayers().size() - (TeamManager.spectators.size() + 1) == 1) {
                //Get Winner
                Player winner = null;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!TeamManager.spectators.contains(onlinePlayer)){
                        if(player.equals(onlinePlayer)) continue;
                        winner = onlinePlayer;

                        onlinePlayer.sendTitle("§6Gewonnen", "", 5, 20, 10);
                    } else onlinePlayer.sendTitle("§cVerloren", "", 5, 20, 10);

                    onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 8);
                }

                Bukkit.broadcastMessage(Sumo.PREFIX + "§2" + winner.getName() + " §7hat das Spiel gewonnen§7!");
                Sumo.getInstance().getGameStateManager().startGameState(EndingState.class);
            } else if(Sumo.getInstance().getGameStateManager().getCurrentGameState() instanceof InGameState) {
                event.setQuitMessage(Sumo.PREFIX + "§c" + player.getName() + " §7hat das Spiel verlassen");
            }

        }
    }



    private void join(Player player) {
        //Teleport
        MinecraftLocation location = Sumo.getInstance().getConfigLoader().getConfig().getLobbySpawn();

        if(location != null) {
            player.teleport(location.toLocation());
        } else {
            player.sendMessage(Sumo.PREFIX + "§cDer Server wurde noch nicht aufgesetzt. Verwende /sumo");
            player.sendMessage(Sumo.PREFIX + "§cNachdem sie den Server fertig eingerichtet haben startet sie ihn neu.");
        }

        player.setFoodLevel(20);
        player.setHealth(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.setLevel(0);
        player.setExp(0);

        PlayerInvs.setLobbyInv(player);
    }

}
