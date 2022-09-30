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
    private static ArrayList<Command> commands;

    /**
     * Create a new instance of the command handler
     * and setup all the commands
     */
    public CommandHandler() {

        CommandHandler.commands = new ArrayList<>();

        CommandHandler.commands.add(new Reload());
        CommandHandler.commands.add(new Info());

        CommandHandler.commands.add(new Alert());
        CommandHandler.commands.add(new Find());
        CommandHandler.commands.add(new Servers());
        CommandHandler.commands.add(new Report());
        CommandHandler.commands.add(new Send());
        CommandHandler.commands.add(new List());
        CommandHandler.commands.add(new History());

        // Staff chat
        for (ConfigurationSection section : ConfigManager.getCommands().getAllSections("chats")) {
            CommandHandler.commands.add(new Chat(section, section.getKey()));
        }
    }

    /**
     * Used to set up and register the commands
     */
    public void setup() {
        CommandManager manager = SmUtility.getProxyServer().getCommandManager();

        for (Command command : CommandHandler.commands) {
            manager.register(
                    manager.metaBuilder(command.getName()).build(),
                    new BrigadierCommand(command.getCommandNode(command.getName()))
            );

            if (command.getAliases() == null) continue;
            for (String alias : command.getAliases()) {
                manager.register(
                        manager.metaBuilder(alias).build(),
                        new BrigadierCommand(command.getCommandNode(alias))
                );
            }
        }
    }

    /**
     * Used to delete and unregister the commands
     */
    public void unregister() {
        CommandManager manager = SmUtility.getProxyServer().getCommandManager();

        for (Command command : CommandHandler.commands) {
            manager.unregister(command.getName());

            if (command.getAliases() == null) continue;
            for (String alias : command.getAliases()) manager.unregister(alias);
        }
    }

    /**
     * Used to get all the chat commands
     */
    public static ArrayList<Chat> getChatCommands() {
        ArrayList<Chat> temp = new ArrayList<>();
        for (Command command : CommandHandler.commands) {
            if (command instanceof Chat) temp.add((Chat) command);
        }
        return temp;
    }
}
