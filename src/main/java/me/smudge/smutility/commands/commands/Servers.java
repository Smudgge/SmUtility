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
import me.smudge.smutility.MessageManager;
import me.smudge.smutility.ServerManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.configuration.ConfigManager;

import java.util.ArrayList;

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
    public void onConsoleRun(String arguments) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            MessageManager.log(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        String message = this.getMessage();

        if (message == null) return;

        MessageManager.log(message);
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            player.sendMessage(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        message = this.getMessage();

        if (message == null) {
            player.sendMessage(ConfigManager.getMessages().getMessages().getSomethingWentWrong());
            return;
        }

        player.sendMessage(message);
    }

    /**
     * Used to get the servers message
     * @return String version of the list of servers
     */
    private String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();

        // Append the header
        stringBuilder.append(ConfigManager.getCommands().getCommandInfo("servers").getSection().getString("header"));
        stringBuilder.append("&r\n\n");

        // Get the order of servers
        ArrayList<String> order = (ArrayList<String>) ConfigManager.getCommands().getCommandInfo("servers").getSection().get("order");

        if (order == null) {
            MessageManager.log("&7Cannot show the servers list because &forder &7is not defined in the &6commands.yml");
            return null;
        }

        // Loop though all the servers in the order list
        for (String serverName : order) {

            RegisteredServer server = ServerManager.getServer(serverName);

            // If the server exists
            if (server == null) continue;

            this.appendServer(stringBuilder, server);
        }

        stringBuilder.append("\n");
        stringBuilder.append(ConfigManager.getCommands().getCommandInfo("servers").getSection().getString("footer"));

        return stringBuilder.toString();
    }

    /**
     * Used to append a server to the string builder
     *
     * @param builder String builder
     * @param server  The registered server
     */
    private void appendServer(StringBuilder builder, RegisteredServer server) {
        String name = ServerManager.format(server.getServerInfo().getName());
        int amountOfPlayers = ServerManager.getPlayersNotVanishable(server).size();
        String format = ConfigManager.getCommands().getCommandInfo("servers").getSection().getString("server");

        builder.append(format
                .replace("{server}", name)
                .replace("{online}", Integer.toString(amountOfPlayers))
        );

        builder.append("\n");
    }
}
