/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.smudge.smutility.configuration.ConfigManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Used to get server info
 */
public class ServerManager {

    /**
     * Get the formatted server name
     * @param serverName Servers name to convert
     * @return Formatted name
     */
    public static String format(String serverName) {
        for (Map.Entry<String, String> server : ConfigManager.getMessages().getServers().entrySet()) {
            if (!Objects.equals(server.getKey(), serverName)) continue;
            return server.getValue();
        }
        return ConfigManager.getMessages().getServers().get("Default")
                .replace("{server}", serverName);
    }

    /**
     * Get players that are unable to vanish
     * @param server Registered server to get players from
     * @return List of players
     */
    public static ArrayList<Player> getPlayersNotVanishable(RegisteredServer server) {
        ArrayList<Player> players = new ArrayList<>();
        for (Player player : server.getPlayersConnected()) {

            UtilityPlayer utilityPlayer = new UtilityPlayer(player);
            if (!utilityPlayer.isVanishable()) players.add(player);

        }
        return players;
    }

    /**
     * Used to get the list of players with permission
     * Players that are vanished will not be in the list
     * @param permission Permission to check
     * @return List of players not vanished
     */
    public static ArrayList<UtilityPlayer> getPlayerList(String permission) {
        ArrayList<UtilityPlayer> players = new ArrayList<>();
        ArrayList<String> permissions = ServerManager.getRankPermissions();

        for (Player player : SmUtility.getProxyServer().getAllPlayers()) {

            if (!player.hasPermission(permission)) continue;

            UtilityPlayer utilityPlayer = new UtilityPlayer(player);

            if (!Objects.equals(utilityPlayer.highestPermission(permissions), permission)) continue;

            if (utilityPlayer.isVanished()) continue;

            players.add(utilityPlayer);
        }
        return players;
    }

    /**
     * Used to get the rank permissions as a list
     */
    private static ArrayList<String> getRankPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        for (String permission : ConfigManager.getCommands().getSection("list").getSection("list").getKeys()) {
            permissions.add(SmUtility.permissionPrefix + ".rank." + permission);
        }
        return permissions;
    }

    /**
     * Used to get the raw list of players with permission
     * @param permission Permission to check for
     * @return List of players ignoring if they are vanished or not
     */
    public static ArrayList<UtilityPlayer> getPlayerListRaw(String permission) {
        ArrayList<UtilityPlayer> players = new ArrayList<>();
        ArrayList<String> permissions = ServerManager.getRankPermissions();

        for (Player player : SmUtility.getProxyServer().getAllPlayers()) {

            if (!player.hasPermission(permission)) continue;

            UtilityPlayer utilityPlayer = new UtilityPlayer(player);

            if (!Objects.equals(utilityPlayer.highestPermission(permissions), permission)) continue;

            players.add(utilityPlayer);
        }
        return players;
    }
}
