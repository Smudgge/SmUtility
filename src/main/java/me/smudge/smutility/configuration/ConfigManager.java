/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.configuration;

import me.smudge.smutility.configuration.configs.Commands;
import me.smudge.smutility.configuration.configs.Messages;

import java.io.File;

/**
 * Used to get the configs
 */
public class ConfigManager {

    private static Commands commands;
    private static Messages messages;

    /**
     * Used to set up the configs
     */
    public ConfigManager(File folder) {

        // Setup configs
        ConfigManager.commands = new Commands(folder);
        ConfigManager.messages = new Messages(folder);

    }

    /**
     * Used to reload all the configs
     */
    public static void reloadAll() {
        ConfigManager.commands.load();
        ConfigManager.messages.load();
    }

    /**
     * @return Main plugin config
     */
    public static Commands getCommands() {
        return ConfigManager.commands;
    }

    /**
     * @return Messages config
     */
    public static Messages getMessages() {
        return ConfigManager.messages;
    }
}
