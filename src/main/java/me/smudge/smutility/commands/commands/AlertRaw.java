package me.smudge.smutility.commands.commands;

import com.google.gson.Gson;
import com.velocitypowered.api.proxy.Player;
import me.smudge.smutility.ServerManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;
import me.smudge.smutility.commands.Command;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;

public class AlertRaw extends Command {

    @Override
    public String getName() {
        return "alertraw";
    }

    @Override
    public ArrayList<String> getAliases() {
        return null;
    }

    @Override
    public String getPermission() {
        return "alertraw";
    }

    @Override
    public String getArgumentName() {
        return "message-json";
    }

    @Override
    public void onCommandRun(UtilityPlayer player, String arguments) {

        GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.gson();
        Component component = gsonComponentSerializer.deserialize(arguments);

        for (Player temp : SmUtility.getProxyServer().getAllPlayers()) {
            temp.sendMessage(component);
        }
    }

    @Override
    public void onConsoleRun(String arguments) {

        GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.gson();
        Component component = gsonComponentSerializer.deserialize(arguments);

        for (Player temp : SmUtility.getProxyServer().getAllPlayers()) {
            temp.sendMessage(component);
        }
    }
}
