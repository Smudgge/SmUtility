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
    public String getArgumentName() {
        return "player";
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
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
