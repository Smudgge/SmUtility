/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.configuration.options;

import me.smudge.smutility.configuration.configs.Messages;

/**
 * Represents the available messages
 */
public class MessageOptions {

    /**
     * Config instance
     */
    private final Messages messages;

    /**
     * Create a new instance of the options
     *
     * @param messages Message config instnace
     */
    public MessageOptions(Messages messages) {
        this.messages = messages;
    }

    /**
     * Message options
     */
    public String getReloaded() {
        return this.messages.getString("reloaded");
    }

    public String getPlayerIsOffline() {
        return this.messages.getString("playerIsOffline");
    }

    public String getRequiresArguments() {
        return this.messages.getString("requiresArguments");
    }

    public String getSomethingWentWrong() {
        return this.messages.getString("somethingWentWrong");
    }

    public String getDisabledFeature() {
        return this.messages.getString("disabledFeature");
    }

    public String getInvalidArgument() {
        return this.messages.getString("invalidArgument");
    }
}
