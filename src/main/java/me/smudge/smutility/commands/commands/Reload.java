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
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.Command;
import me.smudge.smutility.configuration.ConfigManager;

import java.util.ArrayList;

public class Reload extends Command {

    @Override
    public String getName() {
        return "smutilityreload";
    }

    @Override
    public ArrayList<String> getAliases() {
        return null;
    }

    @Override
    public String getPermission() {
        return "reload";
    }

    @Override
    public String getArgumentName() {
        return null;
    }

    @Override
    public void onCommandRun(UtilityPlayer player, String arguments) {

        ConfigManager.reloadAll();
        player.sendMessage(ConfigManager.getMessages().getMessages().getReloaded());
        SmUtility.reloadCommands();

    }

    @Override
    public void onConsoleRun(String arguments) {

        ConfigManager.reloadAll();
        MessageManager.log(ConfigManager.getMessages().getMessages().getReloaded());
        SmUtility.reloadCommands();

    }
}
