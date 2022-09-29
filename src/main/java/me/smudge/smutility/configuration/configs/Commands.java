/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.configuration.configs;

import me.smudge.smutility.configuration.Configuration;
import me.smudge.smutility.configuration.options.CommandOptions;

import java.io.File;

/**
 * Represents the main config
 */
public class Commands extends Configuration {

    /**
     * Creates a new {@link Configuration}
     * Creates a new instance of the main config
     *
     * @param folder Folder of the config
     */
    public Commands(File folder) {
        super(folder, "commands.yml");
        this.load();
    }

    /**
     * Used to get command info
     *
     * @param path Config path
     * @return The command info
     */
    public CommandOptions getCommandInfo(String path) {
        return new CommandOptions(this, path);
    }
}
