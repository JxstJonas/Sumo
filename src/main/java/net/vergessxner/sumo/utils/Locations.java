package net.vergessxner.sumo.utils;

import net.vergessxner.sumo.utils.helpers.MinecraftLocation;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class Locations {

    private MinecraftLocation lobbySpawn;
    private MinecraftLocation inGameSpawn;
    private MinecraftLocation specSpawn;

    public MinecraftLocation getLobbySpawn() {
        return lobbySpawn;
    }

    public void setLobbySpawn(MinecraftLocation lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
    }

    public MinecraftLocation getInGameSpawn() {
        return inGameSpawn;
    }

    public void setInGameSpawn(MinecraftLocation inGameSpawn) {
        this.inGameSpawn = inGameSpawn;
    }

    public MinecraftLocation getSpecSpawn() {
        return specSpawn;
    }

    public void setSpecSpawn(MinecraftLocation specSpawn) {
        this.specSpawn = specSpawn;
    }
}
