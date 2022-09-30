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

import com.velocitypowered.api.proxy.Player;
import me.smudge.smutility.MessageManager;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.configuration.ConfigManager;

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
    public void onConsoleRun(String arguments) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            MessageManager.log(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        String message = this.getInfo().getString("message");

        MessageManager.all(message.replace("{message}", arguments));
        MessageManager.log(message.replace("{message}", arguments));

    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            player.sendMessage(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        MessageManager.all(message.replace("{message}", arguments));
        MessageManager.log(message.replace("{message}", arguments));

    }
}
