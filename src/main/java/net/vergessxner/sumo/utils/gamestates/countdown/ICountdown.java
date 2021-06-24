package net.vergessxner.sumo.utils.gamestates.countdown;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public interface ICountdown {

    void start();
    void stop();

    int getTime();
    void setTime(int seconds);

    boolean isRunning();

}
