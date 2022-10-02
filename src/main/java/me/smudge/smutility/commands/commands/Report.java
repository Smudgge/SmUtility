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

        String message = ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getMessage();
        String parsedMessage = message
                .replace("{player}", "@console")
                .replace("{message}", arguments)
                .replace("{server}", "@console");
        String permission = "smutility." + ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getSection().getString("permissionToSee");

        MessageManager.log(parsedMessage);
        MessageManager.all(parsedMessage, permission);
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            player.sendMessage(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        String parsedMessage = message
                .replace("{player}", player.getName())
                .replace("{message}", arguments)
                .replace("{server}", player.getServerFormatted());
        String permission = "smutility." + ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getSection().getString("permissionToSee");

        // If they are unable to see their own report, send it to them
        if (!player.getPlayer().hasPermission(permission)) {
            MessageManager.player(player.getPlayer(), parsedMessage);
        }

        MessageManager.all(parsedMessage, permission);
    }
}
