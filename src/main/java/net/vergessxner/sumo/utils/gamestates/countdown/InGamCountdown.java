package net.vergessxner.sumo.utils.gamestates.countdown;

import net.vergessxner.sumo.Sumo;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class InGamCountdown implements ICountdown {

    private static final int FINAL_SECONDS = 60*15;

    private BukkitTask bukkitTask;
    private int seconds;

    @Override
    public void start() {
        bukkitTask = new BukkitRunnable(){
            @Override
            public void run() {

            }
        }.runTaskTimerAsynchronously(Sumo.getInstance(), 0, 20);
    }

    @Override
    public void stop() {

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
