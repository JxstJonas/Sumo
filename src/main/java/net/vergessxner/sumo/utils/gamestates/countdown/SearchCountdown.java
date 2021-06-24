package net.vergessxner.sumo.utils.gamestates.countdown;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class SearchCountdown implements ICountdown {

    private BukkitTask bukkitTask;

    @Override
    public void start() {
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    int missingPlayers = (Settings.MIN_PLAYERS - Bukkit.getOnlinePlayers().size());

                    if(missingPlayers == 1) {
                        player.sendMessage(Sumo.PREFIX + "§7Es wird noch §cein §7Spieler benötigt um das Spiel zu starten");
                    }else {
                        player.sendMessage(Sumo.PREFIX + "§7Es werden noch §c" +
                                missingPlayers +
                                " §7Spieler benötigt um das Spiel zu starten");
                    }
                });
            }
        }.runTaskTimerAsynchronously(Sumo.getInstance(), 0, 20*30);
    }

    @Override
    public void stop() {
        if(bukkitTask != null) bukkitTask.cancel();
        bukkitTask = null;
    }

    //Useless Part
    @Override
    public int getTime() { return 0; }

    @Override
    public void setTime(int seconds) {}

    @Override
    public boolean isRunning() {
        if(bukkitTask == null) return false;
        return !bukkitTask.isCancelled();
    }
}
