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

import java.util.ArrayList;

/**
 * Represents the command's info
 * Used for getting from config
 */
public class CommandOptions {

    /**
     * Data loaded from the yaml
     */
    private final Commands commands;

    /**
     * The command name
     */
    private final String name;

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
     * @return If the command is enabled
     */
    public boolean getEnabled() {
        return this.commands.getSection(this.name).getBoolean("enable");
    }

    /**
     * Used to get the command option as a section
     */
    public ConfigurationSection getSection() {
        return this.commands.getSection(this.name);
    }

    /**
     * Used to get command aliases
     *
     * @return Null if there are non
     */
    public ArrayList<String> getAliases() {
        if (this.commands.getSection(this.name).get("aliases") == null) return null;
        if (!(this.commands.getSection(this.name).get("aliases") instanceof ArrayList)) return null;
        return (ArrayList<String>) this.commands.getSection(this.name).get("aliases");
    }
}
