/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.utility;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.configuration.ConfigManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a player
 * Makes it easyer to do simple tasks
 */
public class UtilityPlayer {

    /**
     * The velocity player
     */
    private final String playerName;
    private final Player player;

    /**
     * Used to create a new utility player
     * @param player Player to cast to
     */
    public UtilityPlayer(Player player) {
        this.playerName = player.getGameProfile().getName();
        this.player = player;
    }

    /**
     * Used to create a new utility player
     * @param playerName The players name
     */
    public UtilityPlayer(String playerName) {
        this.playerName = playerName;
        this.player = this.getPlayer();
    }

    /**
     * Used to get the player instance
     * @return Null if they are offline
     */
    public Player getPlayer() {
        if (this.player != null) return player;
        for (Player player : SmUtility.getProxyServer().getAllPlayers()) {
            if (Objects.equals(player.getGameProfile().getName(), this.playerName)) return player;
        }
        return null;
    }

    /**
     * Send the player a message
     * @param message Message to send
     */
    public void sendMessage(String message) {
        Send.player(this.player, message);
    }

    /**
     * Used to get the server they are connected to
     * @return Null if they are not connected to a server
     */
    public RegisteredServer getServer() {
        if (this.player == null) return null;

        for (RegisteredServer server : SmUtility.getProxyServer().getAllServers()) {
            for (Player temp : server.getPlayersConnected()) {
                if (temp == this.player) return server;
            }
        }

        return null;
    }

    /**
     * Get the server formatted as a string
     */
    public String getServerFormated() {
        return ServerManager.format(this.getServer().getServerInfo().getName());
    }

    /**
     * Check if the player is able to vanish
     * @return If they are able to vanish
     */
    public boolean isVanishable() {
        return this.player.hasPermission(SmUtility.permissionPrefix + ".vanishable");
    }

    /**
     * Used to check if this player is vanished
     * @return True if an error occurred
     * This makes sure there is never an error
     * when the vanished player will be spotted
     */
    public boolean isVanished() {
        if (this.player == null) return true;

        if (ServerManager.getPlayersNotVanishable(this.getServer()).size() == 0) return true;

        Player nonVanishablePlayer = ServerManager.getPlayersNotVanishable(this.getServer()).get(0);

        // If vanished, the player would not be able to see them on the tab list
        return !nonVanishablePlayer.getTabList().containsEntry(this.player.getUniqueId());
    }

    /**
     * Used to get the players name
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * Get the players rank colour
     */
    public String getRank() {
        if (this.player == null) return null;

        for (Map.Entry<String, String> entry : ConfigManager.getMessages().getRanks().entrySet()) {
            if (!this.player.hasPermission(entry.getKey())) continue;
            return entry.getValue();
        }

        return ConfigManager.getMessages().getRanks().get("Default");
    }

    /**
     * @return Chat colour
     */
    public CharSequence getChatColour() {
        if (this.isVanished()) return ConfigManager.getMessages().getString("vanished");
        return ConfigManager.getMessages().getString("default");
    }

    /**
     * Used to get the highest permission from the player
     * @param permissions List of permissions in order
     */
    public String highestPermission(ArrayList<String> permissions) {
        for (String permission : permissions) {
            if (this.player.hasPermission(permission)) return permission;
        }
        return null;
    }
}
