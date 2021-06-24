package net.vergessxner.sumo.utils.gamestates.countdown;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.gamestates.states.InGameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class LobbyCountdown implements ICountdown {

    private static final int FINAL_SECONDS = 60;

    private BukkitTask bukkitTask;
    private int seconds = 0;

    @Override
    public void start() {
        seconds = FINAL_SECONDS;

        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                switch (seconds) {
                    case 60: case 30: case 15: case 10: case 5: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(Sumo.PREFIX + "§7Das Spiel startet in §2" + seconds + "§7 Sekunden.");
                        break;
                    case 1:
                        Bukkit.broadcastMessage(Sumo.PREFIX + "§7Das Spiel §2startet §7nun");
                        Sumo.getInstance().getGameStateManager().startGameState(InGameState.class);
                        break;
                }

                seconds--;
            }
        }.runTaskTimerAsynchronously(Sumo.getInstance(), 0, 20);
    }

    @Override
    public void stop() {
        if(bukkitTask != null) bukkitTask.cancel();
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
