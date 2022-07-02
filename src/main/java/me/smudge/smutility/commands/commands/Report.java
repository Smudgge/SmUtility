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
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.configuration.ConfigManager;

public class Report extends CustomCommand {
    @Override
    protected String getConfigName() {
        return "report";
    }

    @Override
    public String getArgumentName() {
        return "message";
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {

        MessageManager.all(
                message.replace("{player}", player.getName()).replace("{message}", arguments),
                ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getSection().getString("permissionToSee")
        );

    }
}
