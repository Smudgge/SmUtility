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

import me.smudge.smutility.MessageManager;
import me.smudge.smutility.ServerManager;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.configuration.ConfigManager;

public class Find extends CustomCommand {

    @Override
    protected String getConfigName() {
        return "find";
    }

    @Override
    protected boolean requiresArguments() {
        return true;
    }

    @Override
    public String getArgumentName() {
        return "player";
    }

    @Override
    public void onConsoleRun(String arguments) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            MessageManager.log(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        UtilityPlayer playerToFind = new UtilityPlayer(arguments);

        if (playerToFind.getPlayer() == null) {
            MessageManager.log(ConfigManager.getMessages().getMessages().getPlayerIsOffline());
            return;
        }

        String serverName = playerToFind.getServer().getServerInfo().getName();

        MessageManager.log(
                this.getInfo().getString("message")
                    .replace("{player}", arguments)
                    .replace("{server}", ServerManager.format(serverName))
        );
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            player.sendMessage(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        UtilityPlayer playerToFind = new UtilityPlayer(arguments);

        if (playerToFind.getPlayer() == null) {
            player.sendMessage(ConfigManager.getMessages().getMessages().getPlayerIsOffline());
            return;
        }

        String serverName = playerToFind.getServer().getServerInfo().getName();

        player.sendMessage(message
                .replace("{player}", arguments)
                .replace("{server}", ServerManager.format(serverName))
        );
    }
}
