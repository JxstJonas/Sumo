package net.vergessxner.sumo;

import net.vergessxner.sumo.command.StartCommand;
import net.vergessxner.sumo.command.SumoCommand;
import net.vergessxner.sumo.listener.*;
import net.vergessxner.sumo.utils.Locations;
import net.vergessxner.sumo.utils.TeamManager;
import net.vergessxner.sumo.utils.gamestates.GameStateManager;
import net.vergessxner.sumo.utils.gamestates.states.LobbyState;
import net.vergessxner.sumo.utils.helpers.ConfigLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Sumo extends JavaPlugin {

    public static String PREFIX = "§2Sumo §8» §7";
    private static Sumo instance;

    private GameStateManager gameStateManager;
    private TeamManager teamManager;
    private ConfigLoader<Locations> configLoader;


    @Override
    public void onEnable() {
        gameStateManager.startGameState(LobbyState.class);

        //Listener
        getServer().getPluginManager().registerEvents(new ConnectListener(), this);
        getServer().getPluginManager().registerEvents(new MoveListener(), this);
        getServer().getPluginManager().registerEvents(new TrowSlimeBall(), this);
        getServer().getPluginManager().registerEvents(new DefaultListener(), this);
        getServer().getPluginManager().registerEvents(new ClickListener(), this);

        new TrowSlimeBall().start();

        //Commands
        getCommand("start").setExecutor(new StartCommand());
        getCommand("sumo").setExecutor(new SumoCommand());

        //Tab Complete
        getCommand("sumo").setTabCompleter(new SumoCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onLoad() {
        instance = this;

        gameStateManager = new GameStateManager();
        File file = new File("./plugins/Sumo/locations.json");
        if(!file.exists()) {
            System.out.println(file.getPath());
            new File("./plugins/Sumo/").mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configLoader = new ConfigLoader<>(file, Locations.class);
        Sumo.getInstance().getConfigLoader().load();

        teamManager = new TeamManager();
    }

    public static Sumo getInstance() {
        return instance;
    }

    public ConfigLoader<Locations> getConfigLoader() {
        return configLoader;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }
}
