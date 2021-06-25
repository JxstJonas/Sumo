package net.vergessxner.sumo.utils.gamestates.states;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.gamestates.IGameStates;
import net.vergessxner.sumo.utils.gamestates.countdown.EndingCountdown;
import net.vergessxner.sumo.utils.gamestates.countdown.ICountdown;
import net.vergessxner.sumo.utils.helpers.MinecraftLocation;
import net.vergessxner.sumo.utils.helpers.PlayerInvs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class EndingState implements IGameStates {

    private ICountdown countdown;
    private boolean running;

    @Override
    public void start() {
        countdown = new EndingCountdown();
        countdown.start();

        Sumo.getInstance().getConfigLoader().load();
        MinecraftLocation location = Sumo.getInstance().getConfigLoader().getConfig().getLobbySpawn();

        //Teleport and set Items
        if(location != null) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.teleport(location.toLocation());
                player.setGameMode(GameMode.ADVENTURE);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.showPlayer(Sumo.getInstance(), player);
                }

                PlayerInvs.setLobbyInv(player);
            });
        }

        running = true;
    }

    @Override
    public void stop() {
        running = false;
        countdown.stop();

    }

    @Override
    public boolean isFullyRunning() {
        return running;
    }
}
