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
import me.smudge.smutility.ServerManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.configuration.ConfigManager;
import me.smudge.smutility.configuration.ConfigurationSection;

import java.util.ArrayList;

public class List extends CustomCommand {

    @Override
    protected String getConfigName() {
        return "list";
    }

    @Override
    protected boolean requiresArguments() {
        return false;
    }

    @Override
    public String getArgumentName() {
        return null;
    }

    @Override
    public void onConsoleRun(String arguments) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            MessageManager.log(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        ArrayList<String> sendersRankPermissions = new ArrayList<>();

        for (ConfigurationSection section : this.getInfo().getAllSections("list")) {
            String formattedPermission = SmUtility.permissionPrefix + ".rank." + section.getKey();
            sendersRankPermissions.add(formattedPermission);
        }

        MessageManager.log(this.getMessage(sendersRankPermissions));
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            player.sendMessage(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        ArrayList<String> sendersRankPermissions = new ArrayList<>();

        for (ConfigurationSection section : this.getInfo().getAllSections("list")) {
            String formattedPermission = SmUtility.permissionPrefix + ".rank." + section.getKey();

            if (player.getPlayer().hasPermission(formattedPermission)) sendersRankPermissions.add(formattedPermission);
        }

        player.sendMessage(this.getMessage(sendersRankPermissions));
    }

    private String getMessage(ArrayList<String> sendersRankPermissions) {
        // Building the list of players
        StringBuilder stringBuilder = new StringBuilder();

        // Appending the list header
        stringBuilder.append(this.getInfo().getString("header"));
        stringBuilder.append("\n");

        // Append each player rank and players
        for (ConfigurationSection section : this.getInfo().getAllSections("list")) {

            // Permission to get
            String formattedPermission = SmUtility.permissionPrefix + ".rank." + section.getKey();

            // Get the players with the permission that are not vanished
            ArrayList<UtilityPlayer> players = ServerManager.getPlayerListFiltered(formattedPermission);

            // If the player has the permission they can see the vanished players
            if (sendersRankPermissions.contains(formattedPermission))
                players = ServerManager.getPlayerListRaw(formattedPermission);

            // If there are no players
            if (players.size() == 0) continue;

            // Append the rank header
            stringBuilder.append("\n");
            stringBuilder.append(
                    section.getString("header")
                            .replace("{amount}", Integer.toString(players.size()))
            );

            // Append the players
            for (UtilityPlayer utilityPlayer : players) {
                stringBuilder.append("\n");
                stringBuilder.append(
                        section.getString("section")
                                .replace("{player}", utilityPlayer.getChatColour() + utilityPlayer.getName())
                                .replace("{server}", utilityPlayer.getServerFormatted())
                );
            }

            stringBuilder.append("\n");
        }

        // Append the footer
        stringBuilder.append("\n");
        stringBuilder.append(this.getInfo().getString("footer"));

        return stringBuilder.toString();
    }
}
