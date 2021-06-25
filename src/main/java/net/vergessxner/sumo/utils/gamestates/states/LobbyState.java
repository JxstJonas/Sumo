package net.vergessxner.sumo.utils.gamestates.states;

import net.vergessxner.sumo.utils.gamestates.IGameStates;
import net.vergessxner.sumo.utils.gamestates.countdown.ICountdown;
import net.vergessxner.sumo.utils.gamestates.countdown.LobbyCountdown;
import net.vergessxner.sumo.utils.gamestates.countdown.SearchCountdown;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class LobbyState implements IGameStates {

    private ICountdown searchCountdown;
    private ICountdown lobbyCountdown;

    private boolean running = false;

    @Override
    public void start() {
        searchCountdown = new SearchCountdown();
        lobbyCountdown = new LobbyCountdown();

        searchCountdown.start();

        running = true;
    }

    @Override
    public void stop() {
        running = false;

        searchCountdown.stop();
        lobbyCountdown.stop();

    }

    @Override
    public boolean isFullyRunning() {
        return running;
    }

    public ICountdown getSearchCountdown() {
        return searchCountdown;
    }

    public ICountdown getLobbyCountdown() {
        return lobbyCountdown;
    }
}
