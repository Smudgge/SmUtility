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

import me.smudge.smutility.SmUtility;
import me.smudge.smutility.commands.Command;
import me.smudge.smutility.configuration.ConfigurationSection;
import me.smudge.smutility.utility.Send;
import me.smudge.smutility.utility.UtilityPlayer;

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
    private ArrayList<String> playerNames = new ArrayList<>();

    /**
     * New instance of this command
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
            if (this.playerNames.contains(player.getName())) this.playerNames.remove(player.getName());
            else this.playerNames.add(player.getName());

            player.sendMessage("{prefix} Toggled &f" + this.name);
            return;
        }

        String message = this.section.getString("format")
                .replace("{server}", player.getServerFormated())
                .replace("{rank}", player.getRank())
                .replace("{name}", player.getName())
                .replace("{chat}", player.getChatColour())
                .replace("{message}", arguments);

        Send.all(message, SmUtility.permissionPrefix + "." + this.section.getString("permission"));
    }
}
