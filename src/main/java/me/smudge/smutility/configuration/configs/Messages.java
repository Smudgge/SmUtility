/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.configuration.configs;

import me.smudge.smutility.configuration.Configuration;
import me.smudge.smutility.configuration.options.MessageOptions;

import java.io.File;
import java.util.Map;

/**
 * Represents the messages config
 */
public class Messages extends Configuration {

    /**
     * Creates a new {@link Configuration}
     * Creates a new instance of the messages
     *
     * @param folder Config folder
     */
    public Messages(File folder) {
        super(folder, "messages.yml");
        this.load();
    }

    /**
     * @return The plugins prefix
     */
    public String getPrefix() {
        return this.getString("prefix");
    }

    public MessageOptions getMessages() {
        return new MessageOptions(this);
    }

    public Map<String, String> getServers() {
        return (Map<String, String>) this.get("servers");
    }

    public Map<String, String> getRanks() {
        return (Map<String, String>) this.get("ranks");
    }

    public String getError() {
        return this.getString("error");
    }
}
