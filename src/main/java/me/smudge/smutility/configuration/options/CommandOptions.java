/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.configuration.options;

import me.smudge.smutility.configuration.ConfigurationSection;
import me.smudge.smutility.configuration.configs.Commands;

/**
 * Represents the command's info
 * Used for getting from config
 */
public class CommandOptions {

    /**
     * Data loaded from the yaml
     */
    private Commands commands;

    /**
     * The command name
     */
    private String name;

    public CommandOptions(Commands commands, String name) {
        this.commands = commands;
        this.name = name;
    }

    /**
     * @return Command name
     */
    public String getName() {
        return this.commands.getSection(this.name).getString("name");
    }

    /**
     * @return Command permission
     */
    public String getPermission() {
        return this.commands.getSection(this.name).getString("permission");
    }

    /**
     * @return Command message
     */
    public String getMessage() {
        return this.commands.getSection(this.name).getString("message");
    }

    /**
     * Used to get the command option as a section
     */
    public ConfigurationSection getSection() {
        return this.commands.getSection(this.name);
    }
}
