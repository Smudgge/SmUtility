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
import me.smudge.smutility.configuration.ConfigurationSection;

import java.util.ArrayList;

public class Chat extends Command {

    /**
     * The chat section
     */
    private final ConfigurationSection section;

    /**
     * Name of the chat
     */
    private final String name;

    /**
     * Players that have it toggled
     */
    public ArrayList<String> playerNames = new ArrayList<>();

    /**
     * New instance of this command
     *
     * @param section Chat section
     */
    public Chat(ConfigurationSection section, String name) {
        this.section = section;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.section.getString("name");
    }

    @Override
    public ArrayList<String> getAliases() {
        return (ArrayList<String>) this.section.get("aliases");
    }

    @Override
    public String getPermission() {
        return this.section.getString("permission");
    }

    @Override
    public String getArgumentName() {
        return "message";
    }

    @Override
    public void onCommandRun(UtilityPlayer player, String arguments) {

        if (arguments == null) {
            if (ConfigManager.getCommands().getBoolean("1.19 chat")) {
                player.sendMessage("&7&l> &7Canceling velocity chat events on 1.19 is not allowed therefor clients cannot toggle chats.");
                return;
            }

            if (this.playerNames.contains(player.getName())) this.playerNames.remove(player.getName());
            else this.playerNames.add(player.getName());

            player.sendMessage("{prefix} Toggled &f" + this.name + " &e&l" + this.playerNames.contains(player.getName()));
            return;
        }

        String message = this.section.getString("format")
                .replace("{server}", player.getServerFormatted())
                .replace("{rank}", player.getRank())
                .replace("{name}", player.getName())
                .replace("{chat}", player.getChatColour())
                .replace("{message}", arguments);

        MessageManager.all(message, SmUtility.permissionPrefix + "." + this.section.getString("permission"));
    }

    @Override
    public void onConsoleRun(String arguments) {

        if (arguments == null) {
            MessageManager.log("You cannot toggle the console");
        }

        String message = this.section.getString("format")
                .replace("{server}", "Console")
                .replace("{rank}", "&7")
                .replace("{name}", "Console")
                .replace("{chat}", "&f")
                .replace("{message}", arguments);

        MessageManager.all(message, SmUtility.permissionPrefix + "." + this.section.getString("permission"));
    }
}
