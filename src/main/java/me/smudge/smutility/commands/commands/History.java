package me.smudge.smutility.commands.commands;

import me.smudge.smutility.MessageManager;
import me.smudge.smutility.ServerManager;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.CustomCommand;
import me.smudge.smutility.configuration.ConfigManager;
import me.smudge.smutility.configuration.options.CommandOptions;
import me.smudge.smutility.database.DatabaseManager;
import me.smudge.smutility.database.HistoryCollection;
import me.smudge.smutility.database.PlayerHistoryEvent;

import java.util.ArrayList;
import java.util.Map;

public class History extends CustomCommand {

    @Override
    protected String getConfigName() {
        return "history";
    }

    @Override
    protected boolean requiresArguments() {
        return true;
    }

    @Override
    public String getArgumentName() {
        return "player";
    }

    @Override
    public void onConsoleRun(String arguments) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            MessageManager.log(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        CommandOptions options = ConfigManager.getCommands().getCommandInfo("history");

        if (options.getSection().getBoolean("disable")) {
            MessageManager.log("&7&l> &7This feature is disabled, to enable set &fdisable &7to &ffalse");
            return;
        }

        MessageManager.log(this.getMessage(arguments));
    }

    @Override
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
        // If the command is disabled
        if (!ConfigManager.getCommands().getCommandInfo(this.getConfigName()).getEnabled()) {
            player.sendMessage(ConfigManager.getMessages().getMessages().getDisabledFeature());
            return;
        }

        CommandOptions options = ConfigManager.getCommands().getCommandInfo("history");

        if (options.getSection().getBoolean("disable")) {
            player.sendMessage("&7&l> &7This feature is disabled, to enable set &fdisable &7to &ffalse");
            return;
        }

        player.sendMessage(this.getMessage(arguments));
    }

    private String getMessage(String arguments) {
        CommandOptions options = ConfigManager.getCommands().getCommandInfo("history");

        StringBuilder builder = new StringBuilder();

        builder.append(options.getSection().getString("header").replace("{player}", arguments)).append("\n\n");

        ArrayList<Map<String, Object>> collections = DatabaseManager.getPlayerHistoryDatabase().getCollections(new HistoryCollection(), "playerName", arguments);
        ArrayList<Map<String, Object>> limited = new ArrayList<>();

        int ignoreAmount = collections.size() - options.getSection().getInt("limit");
        for (Map<String, Object> entry : collections) {
            if (ignoreAmount >= 0) {
                ignoreAmount -= 1;
                continue;
            }
            limited.add(entry);
        }

        for (Map<String, Object> map : limited) {
            builder.append(
                    options.getSection().getString("format")
                            .replace("{event}", PlayerHistoryEvent.valueOf((String) map.get("event")).toSymbol())
                            .replace("{server}", ServerManager.format((String) map.get("serverName")))
                            .replace("{date}", (String) map.get("date"))
            );
            builder.append("\n");
        }

        builder.append("\n").append(options.getSection().getString("footer"));

        return builder.toString();
    }
}
