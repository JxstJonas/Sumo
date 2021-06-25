package net.vergessxner.sumo.utils.gamestates;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public interface IGameStates {

    void start();
    void stop();

    //Wird nur benötigt um ein ausscheiden beim Teleport zu vermeiden
    boolean isFullyRunning();

}
