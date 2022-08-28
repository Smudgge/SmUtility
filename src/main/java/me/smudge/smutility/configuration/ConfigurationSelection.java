package me.smudge.smutility.configuration;/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

import java.util.ArrayList;

public interface ConfigurationSelection {

    /**
     * Used to set a value to the config
     * @param path The location of the key
     * @param value The value to set
     */
    void set(String path, Object value);

    /**
     * Used to get any object
     * @param path The location of the key
     * @return Object
     */
    Object get(String path);

    /**
     * Used to get a config section
     * @param path The location of the key
     * @return Config section
     */
    ConfigurationSection getSection(String path);

    /**
     * Used to get all config sections
     * @param path The location of the key
     * @return Config sections
     */
    ArrayList<ConfigurationSection> getAllSections(String path);

    /**
     * Used to get a string from the config
     * @param path The location of the key
     * @return String value
     */
    String getString(String path);

    /**
     * Used to get an integer from the config
     * @param path The location of the key
     * @return Int value
     */
    int getInt(String path);

    /**
     * Used to get an array of integers from the config
     * @param path The location of the key
     * @return Array of integers
     */
    ArrayList<Integer> getIntArray(String path);

    /**
     * Used to get a boolean from the config
     * @param path The location of the key
     * @return Boolean value
     */
    boolean getBoolean(String path);
}
