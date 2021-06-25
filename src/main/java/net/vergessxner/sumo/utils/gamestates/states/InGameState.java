package net.vergessxner.sumo.utils.gamestates.states;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.gamestates.IGameStates;
import net.vergessxner.sumo.utils.gamestates.countdown.InGameCountdown;
import net.vergessxner.sumo.utils.helpers.MinecraftLocation;
import net.vergessxner.sumo.utils.helpers.PlayerInvs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class InGameState implements IGameStates {

    private InGameCountdown inGameCountdown;
    private boolean running;

    @Override
    public void start() {
        inGameCountdown = new InGameCountdown();

        Sumo.getInstance().getConfigLoader().load();
        MinecraftLocation location = Sumo.getInstance().getConfigLoader().getConfig().getInGameSpawn();

        //Teleport and set Items
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(location != null)
                player.teleport(location.toLocation());

            PlayerInvs.setInGameInv(player);
        });


        inGameCountdown.start();

        running = true;
    }

    @Override
    public void stop() {
        running = false;

        inGameCountdown.stop();
    }

    @Override
    public boolean isFullyRunning() {
        return running;
    }
}
