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

import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.smudge.smutility.commands.CommandHandler;
import me.smudge.smutility.commands.commands.Chat;
import me.smudge.smutility.database.DatabaseManager;
import me.smudge.smutility.database.PlayerHistoryEvent;

public class EventManager {

    /**
     * Used to send chat messages when toggled a chat command
     */
    public static void onPlayerChat(PlayerChatEvent event) {
        for (Chat command : CommandHandler.getChatCommands()) {
            if (!command.playerNames.contains(event.getPlayer().getGameProfile().getName())) continue;

            // Cancel event
            event.setResult(PlayerChatEvent.ChatResult.denied());

            // Send message
            command.onCommandRun(new UtilityPlayer(event.getPlayer()), event.getMessage());

            return;
        }
    }

    /**
     * When a player connects to the server
     */
    public static void onPlayerJoin(ServerPostConnectEvent event) {
        String playerName = event.getPlayer().getGameProfile().getName();
        RegisteredServer server  = event.getPlayer().getCurrentServer().get().getServer();

        DatabaseManager.getPlayerHistoryDatabase().addHistory(playerName, server.getServerInfo().getName(), PlayerHistoryEvent.JOIN);
    }

    /**
     * When a player leaves a server
     */
    public static void onPlayerLeave(KickedFromServerEvent event) {
        String playerName = event.getPlayer().getGameProfile().getName();
        RegisteredServer server = event.getServer();

        DatabaseManager.getPlayerHistoryDatabase().addHistory(playerName, server.getServerInfo().getName(), PlayerHistoryEvent.LEAVE);
    }
}
