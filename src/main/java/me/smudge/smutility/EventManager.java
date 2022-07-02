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

import com.velocitypowered.api.event.player.PlayerChatEvent;
import me.smudge.smutility.commands.CommandHandler;
import me.smudge.smutility.commands.commands.Chat;

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
}
