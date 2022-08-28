package me.smudge.smutility.commands.commands;

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
    protected void onCommandRun(UtilityPlayer player, String arguments, String message) {
        CommandOptions options = ConfigManager.getCommands().getCommandInfo("history");

        StringBuilder builder = new StringBuilder();

        builder.append(options.getSection().getString("header").replace("{player}", arguments)).append("\n\n");

        int limit = options.getSection().getInt("limit");

        for (Map<String, Object> map : DatabaseManager.getPlayerHistoryDatabase().getCollections(new HistoryCollection(), "playerName", arguments)) {
            if (limit == 0) continue;

            builder.append(
                    options.getSection().getString("format")
                            .replace("{event}", PlayerHistoryEvent.valueOf((String) map.get("event")).toSymbol())
                            .replace("{server}", ServerManager.format((String) map.get("serverName")))
                            .replace("{date}", (String) map.get("date"))
            );
            builder.append("\n");

            limit -= 1;
        }

        builder.append("\n").append(options.getSection().getString("footer"));

        player.sendMessage(builder.toString());
    }
}
