package net.vergessxner.sumo.utils.gamestates.countdown;

import net.vergessxner.sumo.Sumo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class EndingCountdown implements ICountdown {

    private static final int FINAL_SECONDS = 10;

    private BukkitTask bukkitTask;
    private int seconds = 0;

    @Override
    public void start() {
        seconds = FINAL_SECONDS;

        bukkitTask = new BukkitRunnable(){
            @Override
            public void run() {
                switch (seconds) {
                    case 20: case 10: case 5: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(Sumo.PREFIX + "§7Der Server wird in §c" + seconds + " §7gestoppt!");
                        break;
                    case 1:
                        Bukkit.broadcastMessage(Sumo.PREFIX + "§7Der Server wird §cjetzt §7gestoppt!");
                        Bukkit.shutdown();
                        break;
                }

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.setLevel(seconds);
                }

                seconds--;
            }
        }.runTaskTimerAsynchronously(Sumo.getInstance(), 0, 20);
    }

    @Override
    public void stop() {
        if(bukkitTask != null && !bukkitTask.isCancelled())
            bukkitTask.cancel();
        bukkitTask = null;
    }

    @Override
    public int getTime() {
        return seconds;
    }

    @Override
    public void setTime(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public boolean isRunning() {
        if(bukkitTask == null) return false;
        return !bukkitTask.isCancelled();
    }

}
