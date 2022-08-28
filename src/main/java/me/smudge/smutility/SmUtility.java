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

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.smudge.smutility.commands.CommandHandler;
import me.smudge.smutility.configuration.ConfigManager;
import me.smudge.smutility.database.DatabaseManager;
import me.smudge.smutility.database.PlayerHistoryDatabase;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "smutility",
        name = "SmUtility",
        version = "1.1.0",
        description = "A Velocity Utility Plugin",
        url = "https://smudgg.netlify.app",
        authors = {"Smudge"}
)
public class SmUtility {

    private static ProxyServer server;
    private static Logger logger;

    private static CommandHandler commandHandler;

    public static String permissionPrefix = "smutility";

    @Inject
    public SmUtility(ProxyServer server, Logger logger, @DataDirectory final Path folder) {
        SmUtility.server = server;
        SmUtility.logger = logger;

        // Setup configs
        new ConfigManager(folder.toFile());

        // Setup database
        new DatabaseManager(folder.toFile());

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

        MessageManager.logHeader();

        // Setup commands
        SmUtility.commandHandler = new CommandHandler();
        SmUtility.commandHandler.setup();

    }

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        EventManager.onPlayerChat(event);
    }

    @Subscribe
    public void onPlayerJoin(ServerPostConnectEvent event) {
        EventManager.onPlayerJoin(event);
    }

    @Subscribe
    public void onPlayerLeave(DisconnectEvent event) {
        EventManager.onPlayerLeave(event);
    }

    /**
     * Used to get the proxy server
     */
    public static ProxyServer getProxyServer() {
        return SmUtility.server;
    }

    /**
     * Used to get the logger
     */
    public static Logger getLogger() {
        return SmUtility.logger;
    }

    /**
     * Used to reload all the commands
     */
    public static void reloadCommands() {
        SmUtility.commandHandler.unregister();

        SmUtility.commandHandler = new CommandHandler();
        SmUtility.commandHandler.setup();
    }
}
