/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.commands.commands;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.smudge.smutility.MessageManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.Command;
import me.smudge.smutility.configuration.ConfigManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Send extends Command {

    @Override
    public String getName() {
        return "send";
    }

    @Override
    public ArrayList<String> getAliases() {
        return null;
    }

    @Override
    public String getPermission() {
        return "send";
    }

    @Override
    public String getArgumentName() {
        return "@a #server";
    }

    @Override
    public void onCommandRun(UtilityPlayer player, String argument) {
        String[] arguments = argument.split(" ");

        if (arguments.length < 2) {
            player.sendMessage("{prefix} Usage: /%s <from> <to>");
            return;
        }

        final String fromArg = arguments[0];
        final String toArg = arguments[1];

        Optional<RegisteredServer> toServer = parseToLocation(toArg);

        if (toServer.isEmpty()) {
            player.sendMessage("{prefix} Not a valid location - /send #server #server - /send @a #server");
            return;
        }

        List<Player> selectedPlayer = this.parseFromLocation(fromArg);

        player.sendMessage("{prefix} Attempting to send players/player");

        for (Player p : selectedPlayer) {
            if (inServer(p, toServer.get())) continue;
            p.createConnectionRequest(toServer.get()).fireAndForget();
        }
    }

    @Override
    public void onConsoleRun(String argument) {
        String[] arguments = argument.split(" ");

        if (arguments.length < 2) {
            MessageManager.log("{prefix} Usage: /%s <from> <to>");
            return;
        }

        final String fromArg = arguments[0];
        final String toArg = arguments[1];

        Optional<RegisteredServer> toServer = parseToLocation(toArg);

        if (toServer.isEmpty()) {
            MessageManager.log("{prefix} Not a valid location - /send #server #server - /send @a #server");
            return;
        }

        List<Player> selectedPlayer = this.parseFromLocation(fromArg);

        MessageManager.log("{prefix} Attempting to send players/player");

        for (Player p : selectedPlayer) {
            if (inServer(p, toServer.get())) continue;
            p.createConnectionRequest(toServer.get()).fireAndForget();
        }
    }

    private List<Player> parseFromLocation(String location) {
        ProxyServer proxyServer = SmUtility.getProxyServer();

        if (location.equals("@a")) {
            return new ArrayList<>(proxyServer.getAllPlayers());
        } else if (location.startsWith("#")) {
            Optional<RegisteredServer> server = proxyServer.getServer(location.substring(1));

            if (server.isPresent()) return new ArrayList<>(server.get().getPlayersConnected());
        } else {
            Optional<Player> player = proxyServer.getPlayer(location);

            if (player.isPresent()) return Collections.singletonList(player.get());
        }

        return Collections.emptyList();
    }

    private Optional<RegisteredServer> parseToLocation(String location) {
        ProxyServer proxyServer = SmUtility.getProxyServer();

        if (location.startsWith("#")) {
            return proxyServer.getServer(location.substring(1));
        } else {
            Optional<Player> player = proxyServer.getPlayer(location);

            if (player.isPresent()) {
                Optional<ServerConnection> server = player.get().getCurrentServer();

                if (server.isPresent()) return Optional.of(server.get().getServer());
            }
        }

        return Optional.empty();
    }

    private static boolean inServer(Player player, RegisteredServer server) {
        Optional<ServerConnection> playerServer = player.getCurrentServer();
        return playerServer.filter(serverConnection -> serverConnection.getServer() == server).isPresent();
    }
}
