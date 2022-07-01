/*
       _____           _    _ _   _ _
      / ____|         | |  | | | (_) |
     | (___  _ __ ___ | |  | | |_ _| |___
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/

     Author : Smudge
*/

package me.smudge.smutility.commands;

import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.commands.commands.*;
import me.smudge.smutility.configuration.ConfigManager;
import me.smudge.smutility.configuration.ConfigurationSection;

import java.util.ArrayList;

/**
 * Handles all utility commands
 */
public class CommandHandler {

    /**
     * List of the commands
     */
    private ArrayList<Command> commands = new ArrayList<>();

    /**
     * Create a new instance of the command handler
     * and setup all the commands
     */
    public CommandHandler() {

        this.commands.add(new Reload());

        this.commands.add(new Alert());
        this.commands.add(new Find());
        this.commands.add(new Servers());
        this.commands.add(new Report());
        this.commands.add(new Send());
        this.commands.add(new List());

        // Staff chat
        for (ConfigurationSection section : ConfigManager.getCommands().getAllSections("chats")) {
            this.commands.add(new Chat(section, section.getKey()));
        }
    }

    /**
     * Used to set up and register the commands
     */
    public void setup() {
        CommandManager manager = SmUtility.getProxyServer().getCommandManager();

        for (Command command : this.commands) {
            manager.register(
                    manager.metaBuilder(command.getName()).build(),
                    new BrigadierCommand(command.getCommandNode())
            );
        }
    }

    /**
     * Used to delete and unregister the commands
     */
    public void unregister() {
        CommandManager manager = SmUtility.getProxyServer().getCommandManager();

        for (Command command : this.commands) {
            manager.unregister(manager.metaBuilder(command.getName()).build());
            try {manager.unregister(command.getName());} catch (Exception ignored) {}
        }
    }
}
