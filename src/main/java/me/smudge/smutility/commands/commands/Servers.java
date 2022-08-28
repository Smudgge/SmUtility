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

import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.smudge.smutility.ServerManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.configuration.ConfigManager;

public class Servers extends CustomCommand {

    @Override
    protected String getConfigName() {
        return "servers";
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
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ConfigManager.getCommands().getCommandInfo("servers").getSection().getString("header"));
        stringBuilder.append("&r\n\n");

        for (RegisteredServer server : SmUtility.getProxyServer().getAllServers()) {

            String name = ServerManager.format(server.getServerInfo().getName());
            int amountOfPlayers = ServerManager.getPlayersNotVanishable(server).size();
            String row = ConfigManager.getCommands().getCommandInfo("servers").getSection().getString("server");

            stringBuilder.append(row
                    .replace("{server}", name)
                    .replace("{online}", Integer.toString(amountOfPlayers))
            );
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append(ConfigManager.getCommands().getCommandInfo("servers").getSection().getString("footer"));

        player.sendMessage(stringBuilder.toString());
    }
}
