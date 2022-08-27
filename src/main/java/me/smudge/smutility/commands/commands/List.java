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

import me.smudge.smutility.ServerManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.configuration.ConfigurationSection;

import java.util.ArrayList;

public class List extends CustomCommand {

    @Override
    protected String getConfigName() {
        return "list";
    }

    @Override
    protected boolean getRequireArguments() {
        return false;
    }

    @Override
    public String getArgumentName() {
        return null;
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.getInfo().getString("header"));
        stringBuilder.append("\n");

        for (ConfigurationSection section : this.getInfo().getAllSections("list")) {

            String formattedPermission = SmUtility.permissionPrefix + ".rank." + section.getKey();

            ArrayList<UtilityPlayer> players = ServerManager.getPlayerList(formattedPermission);
            if (player.getPlayer().hasPermission(formattedPermission))
                players = ServerManager.getPlayerListRaw(formattedPermission);

            if (players.size() == 0) continue;

            stringBuilder.append("\n");
            stringBuilder.append(section.getString("header")
                    .replace("{amount}", Integer.toString(players.size())));

            for (UtilityPlayer utilityPlayer : players) {
                stringBuilder.append("\n");
                stringBuilder.append(section.getString("section")
                        .replace("{player}", utilityPlayer.getChatColour() + utilityPlayer.getName())
                        .replace("{server}", utilityPlayer.getServerFormated()));
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append(this.getInfo().getString("footer"));

        player.sendMessage(stringBuilder.toString());
    }
}
