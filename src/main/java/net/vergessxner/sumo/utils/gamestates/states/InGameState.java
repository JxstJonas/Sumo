package net.vergessxner.sumo.utils.gamestates.states;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.gamestates.IGameStates;
import net.vergessxner.sumo.utils.helpers.MinecraftLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class InGameState implements IGameStates {


    @Override
    public void start() {
        Sumo.getInstance().getConfigLoader().load();
        MinecraftLocation location = Sumo.getInstance().getConfigLoader().getConfig().getInGameSpawn();

        if(location != null) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.teleport(location.toLocation());
            });
        }
    }

    @Override
    public void stop() {

    }
}
