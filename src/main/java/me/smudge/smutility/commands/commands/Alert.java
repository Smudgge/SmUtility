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

import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.utility.Send;
import me.smudge.smutility.utility.UtilityPlayer;

public class Alert extends CustomCommand {

    @Override
    protected String getConfigName() {
        return "alert";
    }

    @Override
    public String getArgumentName() {
        return "message";
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {

        Send.all(message.replace("{message}", arguments));
        Send.log(message.replace("{message}", arguments));

    }
}
