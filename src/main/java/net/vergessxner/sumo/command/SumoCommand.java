package net.vergessxner.sumo.command;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.Locations;
import net.vergessxner.sumo.utils.helpers.MinecraftLocation;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class SumoCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(player.hasPermission("sumo.setup")) {

            //Saving Loc
            if(args.length == 2 && args[0].equalsIgnoreCase("setup")) {
                args[1] = args[1].toLowerCase();
                MinecraftLocation loc = new MinecraftLocation();
                loc.setLocation(player.getLocation());

                Sumo.getInstance().getConfigLoader().load();
                Locations storage = Sumo.getInstance().getConfigLoader().getConfig();
                if(storage == null) storage = new Locations();

                switch (args[1]) {
                    case "lobbyspawn":
                        storage.setLobbySpawn(loc);
                        Sumo.getInstance().getConfigLoader().set(storage);
                        player.sendMessage(Sumo.PREFIX + "§7Du hast den §2Lobby-Spawn §7gesetzt!");
                        break;
                    case "ingamespawn":
                        storage.setInGameSpawn(loc);
                        Sumo.getInstance().getConfigLoader().set(storage);
                        player.sendMessage(Sumo.PREFIX + "§7Du hast den §2InGame-Spawn §7gesetzt!");
                        break;
                    case "specspawn":
                        storage.setSpecSpawn(loc);
                        Sumo.getInstance().getConfigLoader().set(storage);
                        player.sendMessage(Sumo.PREFIX + "§7Du hast den §2Spec-Spawn §7gesetzt!");
                        break;
                    case "deathheight":
                        storage.setDeathHeight(player.getLocation().getY());
                        Sumo.getInstance().getConfigLoader().set(storage);
                        player.sendMessage(Sumo.PREFIX + "§7Du hast den §2DeathHeight-Spawn §7gesetzt!");
                        break;
                    default:
                        player.sendMessage("§7Vewende §2/sumo setup [lobbySpawn/InGameSpawn/SpecSpawn/DeathHeight]");
                        break;
                }

            }
        } else player.sendMessage(Sumo.PREFIX + "§cDu hast keine Berechtigung diesen Command auszuführen");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if(args.length == 1) list.add("setup");

        if(args.length == 2) {
            list.add("lobbySpawn");
            list.add("InGameSpawn");
            list.add("SpecSpawn");
            list.add("DeathHeight");
        }

        return list;
    }
}
