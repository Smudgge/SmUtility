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
     *
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
     *
     * @param server Registered server to get players from
     * @return List of players
     */
    public static ArrayList<Player> getPlayersNotVanishable(RegisteredServer server) {
        ArrayList<Player> players = new ArrayList<>();

        // For each player on the server
        for (Player player : server.getPlayersConnected()) {

            UtilityPlayer utilityPlayer = new UtilityPlayer(player);

            // If they are not vanishable
            if (utilityPlayer.isVanishable()) continue;

            players.add(player);
        }
        return players;
    }

    /**
     * Used to get the list of players with a permission
     * Players that are vanished will not be in the list
     *
     * @param permission Permission to check
     * @return List of players not vanished
     */
    public static ArrayList<UtilityPlayer> getPlayerListFiltered(String permission) {
        ArrayList<UtilityPlayer> players = new ArrayList<>();
        ArrayList<String> permissions = ServerManager.getRankPermissions();

        // For each player online
        for (Player player : SmUtility.getProxyServer().getAllPlayers()) {

            // If they have the permission
            if (!player.hasPermission(permission)) continue;

            UtilityPlayer utilityPlayer = new UtilityPlayer(player);

            // If the permission is the highest priority
            if (!Objects.equals(utilityPlayer.highestPermission(permissions), permission)) continue;

            // If the player is not vanished
            if (utilityPlayer.isVanished()) continue;

            players.add(utilityPlayer);
        }
        return players;
    }

    /**
     * Used to get the rank permissions as a list
     *
     * @return All possible rank permissions
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
     *
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

    /**
     * Used to get a server from its name
     *
     * @param serverName The name of the server to check for
     * @return Null if the server doesnt exist
     */
    public static RegisteredServer getServer(String serverName) {
        for (RegisteredServer server : SmUtility.getProxyServer().getAllServers()) {
            if (Objects.equals(server.getServerInfo().getName(), serverName)) return server;
        }

        return null;
    }
}
