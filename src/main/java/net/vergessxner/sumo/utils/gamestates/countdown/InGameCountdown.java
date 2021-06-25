package net.vergessxner.sumo.utils.gamestates.countdown;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.gamestates.states.EndingState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class InGameCountdown implements ICountdown {

    private static final int FINAL_SECONDS = 60*2;

    private BukkitTask bukkitTask;
    private int seconds;

    @Override
    public void start() {
        seconds = FINAL_SECONDS;

        bukkitTask = new BukkitRunnable(){
            @Override
            public void run() {
                switch (seconds) {
                    case 60*10: case 60*5: case 60*3: case 60*2: case 60: case 30: case 15: case 10: case 5: case 4: case 3: case 2:
                        //Broadcast
                        Bukkit.broadcastMessage(Sumo.PREFIX + "§7Das Spiel endet in §2" + seconds + " §7Sekunden.");
                        break;
                    case 1:
                        //Ending State
                        Sumo.getInstance().getGameStateManager().startGameState(EndingState.class);
                        break;
                }

                seconds--;
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
