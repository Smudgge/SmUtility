package me.smudge.smutility.commands.commands;

import com.velocitypowered.api.plugin.Plugin;
import me.smudge.smutility.MessageManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.Command;
import me.smudge.smutility.configuration.ConfigManager;

import java.util.ArrayList;

public class Info extends Command {

    @Override
    public String getName() {
        return "smutility";
    }

    @Override
    public ArrayList<String> getAliases() {
        return null;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getArgumentName() {
        return null;
    }

    @Override
    public void onCommandRun(UtilityPlayer player, String arguments) {

        player.sendMessage(this.getMessage());

    }

    @Override
    public void onConsoleRun(String arguments) {

        MessageManager.log(this.getMessage());

    }

    private String getMessage() {

        return "&8&m&l------&r &6&lSmUtility &8&m&l------\n\n" +
                "&7Velocity proxy utility plugin\n" +
                "&7Version &f" + SmUtility.class.getAnnotation(Plugin.class).version() + "\n\n" +
                "'&8&m&l---------------------'";
    }
}
