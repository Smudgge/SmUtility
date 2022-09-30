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

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import me.smudge.smutility.configuration.ConfigManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class MessageManager {

    /**
     * Used to convert a message
     * Updating the prefix and deserializing
     *
     * @param message The message to convert
     * @return Converted message
     */
    public static Component convert(String message) {
        String prefix = ConfigManager.getMessages().getPrefix();

        if (message == null) {
            SmUtility.getLogger().warn("Message is null! Please check messages.yml config for missing values.");
            SmUtility.getLogger().warn(
                    "If updating to 1.0.4 it may had been because this is missing :\n" +
                            "  # Command messages\n" +
                            "  reloaded: '{prefix} Reloaded all configs! <3'\n" +
                            "  playerIsOffline: '{prefix} Player is offline'\n" +
                            "  requiresArguments: '&7&l> &7This command requires arguments. /<commands> <args>'\n" +
                            "  ^^^^^^^"
            );
            message = "null";
        }

        try {
            return Component.text()
                    .append(LegacyComponentSerializer.legacyAmpersand().deserialize(message
                            .replace("{prefix}", prefix)))
                    .build();
        } catch (Exception exception) {
            SmUtility.getLogger().warn("Could not find prefix value in messages.yml!");
            return Component.text()
                    .append(LegacyComponentSerializer.legacyAmpersand().deserialize(message))
                    .build();
        }
    }

    /**
     * Used to convert a message
     * Updating the prefix
     *
     * @param message The message to convert
     * @return Converted message
     */
    public static String convertString(String message) {
        String prefix = ConfigManager.getMessages().getPrefix();

        return message.replace("{prefix}", prefix);
    }

    /**
     * Used to send a player a message
     *
     * @param player  Player to send message
     * @param message Message to send
     */
    public static void player(Player player, String message) {
        player.sendMessage(MessageManager.convert(message));
    }

    /**
     * Used to send a message to all players online
     *
     * @param message The message to send
     */
    public static void all(String message) {
        for (Player player : SmUtility.getProxyServer().getAllPlayers()) {
            player.sendMessage(MessageManager.convert(message));
        }
    }

    /**
     * Used to send a message to players with permission
     *
     * @param message    Message to send
     * @param permission Permission to check for
     */
    public static void all(String message, String permission) {
        for (Player player : SmUtility.getProxyServer().getAllPlayers()) {
            if (!player.hasPermission(permission)) continue;
            player.sendMessage(MessageManager.convert(message));
        }
    }

    /**
     * Used to log a message in the console
     *
     * @param message Message to log
     */
    public static void log(String message) {
        for (String string : message.split("\n")) {
            SmUtility.getProxyServer().getConsoleCommandSource().sendMessage(convert(string));
        }
    }

    /**
     * Used to log the header in console
     * When the plugin has loaded
     */
    public static void logHeader() {
        String message =
                        "&a   _____           _    _ _   _ _ _ _         %" +
                        "&a  / ____|         | |  | | | (_) (_) |       %" +
                        "&a | (___  _ __ ___ | |  | | |_ _| |_| |_ _   _ %" +
                        "&a  \\___ \\| '_ ` _ \\| |  | | __| | | | __| | | |%" +
                        "&a  ____) | | | | | | |__| | |_| | | | |_| |_| |%" +
                        "&a |_____/|_| |_| |_|\\____/ \\__|_|_|_|\\__|\\__, |%" +
                        "&a                                         __/ |%" +
                        "&a                                        |___/%" +
                        "%" +
                        "     &7By Smudge         &7Version " + SmUtility.class.getAnnotation(Plugin.class).version();

        for (String string : message.split("%")) {
            MessageManager.log(string);
        }
    }
}
