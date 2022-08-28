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

public class Alert extends CustomCommand {

    @Override
    protected String getConfigName() {
        return "alert";
    }

    @Override
    protected boolean requiresArguments() {
        return true;
    }

    @Override
    public String getArgumentName() {
        return "message";
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {

        MessageManager.all(message.replace("{message}", arguments));
        MessageManager.log(message.replace("{message}", arguments));

    }
}
