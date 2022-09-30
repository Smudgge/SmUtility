/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.UtilityPlayer;

import java.util.ArrayList;

/**
 * Represents a command
 */
public abstract class Command {

    public abstract String getName();

    public abstract ArrayList<String> getAliases();

    public abstract String getPermission();

    public abstract String getArgumentName();

    public abstract void onCommandRun(UtilityPlayer player, String arguments);

    public abstract void onConsoleRun(String arguments);

    /**
     * When the command is executed
     *
     * @param commandContext The context of the command
     */
    public int execute(CommandContext<CommandSource> commandContext) {
        CommandSource source = commandContext.getSource();

        // Get the player that executed the command
        Player player = null;
        if (source instanceof Player) player = (Player) source;

        // Get the arguments
        String arguments = null;
        if (this.getArgumentName() != null)
            arguments = commandContext.getArgument(this.getArgumentName(), String.class);

        // If a player executed the command
        if (source instanceof Player) {
            this.onCommandRun(new UtilityPlayer(player), arguments);
            return 1;
        }

        this.onConsoleRun(arguments);
        return 1;
    }

    /**
     * @return The full permission
     */
    public String getParsedPermission() {
        return SmUtility.permissionPrefix + "." + this.getPermission();
    }

    /**
     * Used to get the command literal {@link LiteralCommandNode}
     */
    public LiteralCommandNode<CommandSource> getCommandNode(String commandName) {
        if (this.getArgumentName() == null) {
            return LiteralArgumentBuilder.<CommandSource>literal(commandName)
                    .requires(ctx -> ctx.hasPermission(this.getParsedPermission()))
                    .executes(this::execute)
                    .build();
        }

        return LiteralArgumentBuilder.<CommandSource>literal(commandName)
                .requires(ctx -> ctx.hasPermission(this.getParsedPermission()))
                .then(RequiredArgumentBuilder.<CommandSource, String>argument(this.getArgumentName(), StringArgumentType.greedyString())
                        .executes((this::execute))
                        .build())
                .executes(this::execute)
                .build();
    }
}