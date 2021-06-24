package net.vergessxner.sumo.command;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.gamestates.IGameStates;
import net.vergessxner.sumo.utils.gamestates.states.LobbyState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        if(player.hasPermission("sumo.start")) {

            if(args.length != 0) {
                player.sendMessage(Sumo.PREFIX + "§7Verwende: §2/start");
                return false;
            }

            IGameStates gameStates = Sumo.getInstance().getGameStateManager().getCurrentGameState();

            if(!(gameStates instanceof LobbyState)) {
                player.sendMessage(Sumo.PREFIX + "§cDu kannst diesen Command nur in der Lobby-Phase ausführen");
                return false;
            }

            LobbyState lobbyState = (LobbyState) gameStates;
            if(lobbyState.getSearchCountdown().isRunning()) {
                player.sendMessage(Sumo.PREFIX + "§cEs werden noch Spieler benötigt");
                return false;
            }

            if(lobbyState.getLobbyCountdown().getTime() <= 10) {
                player.sendMessage(Sumo.PREFIX + "§cDas Spiel startet bereits in unter 10 Sekunden!");
                return false;
            }

            lobbyState.getLobbyCountdown().setTime(10);
            player.sendMessage(Sumo.PREFIX + "§7Du hast erfolgreich das Spiel auf §210 Sekunden gesetzt!");

        } else player.sendMessage(Sumo.PREFIX + "§cDu hast keine Berechtigung diesen Command auszuführen");

        return false;
    }
}
