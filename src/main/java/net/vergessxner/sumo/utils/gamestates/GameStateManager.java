package net.vergessxner.sumo.utils.gamestates;

import net.vergessxner.sumo.Sumo;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class GameStateManager {

    private IGameStates currentGameState;

    public void startGameState(Class<? extends IGameStates> clazz) {
        try {
            IGameStates gameStates = (IGameStates) clazz.getConstructors()[0].newInstance();

            stopCurrentGameState();

            currentGameState = gameStates;

            Bukkit.getScheduler().runTask(Sumo.getInstance(), currentGameState::start);
            System.out.println("GameState " + currentGameState.getClass().getSimpleName() + " started");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public void stopCurrentGameState() {
        if(currentGameState != null) {
            Bukkit.getScheduler().runTask(Sumo.getInstance(), currentGameState::stop);
            System.out.println("GameState " + currentGameState.getClass().getSimpleName() + " stopped");
        }
    }

    public IGameStates getCurrentGameState() {
        return currentGameState;
    }

}
