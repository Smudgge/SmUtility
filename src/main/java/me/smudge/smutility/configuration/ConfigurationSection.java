/*
       _____           _    _ _   _ _     
      / ____|         | |  | | | (_) |    
     | (___  _ __ ___ | |  | | |_ _| |___ 
      \___ \| '_ ` _ \| |  | | __| | / __|
      ____) | | | | | | |__| | |_| | \__ \
     |_____/|_| |_| |_|\____/ \__|_|_|___/
     
     Author : Smudge
*/

package me.smudge.smutility.configuration;

import java.util.ArrayList;
import java.util.Map;

public class ConfigurationSection implements ConfigurationSelection {

    private Map<String, Object> data;

    private String key;

    public ConfigurationSection(Map<String, Object> data) {
        this.data = data;
    }

    public ConfigurationSection(Map<String, Object> data, String key) {
        this.data = data;
        this.key = key;
    }

    @Override
    public void set(String path, Object value) {
        this.data.put(path, value);
    }

    @Override
    public Object get(String path) {
        return this.data.get(path);
    }

    @Override
    public ConfigurationSection getSection(String path) {
        if (this.data.get(path) instanceof Map) return new ConfigurationSection((Map<String, Object>) this.data.get(path));
        return null;
    }

    @Override
    public ArrayList<ConfigurationSection> getAllSections(String path) {
        Map<String, Object> temp = (Map<String, Object>) this.get(path);
        ArrayList<ConfigurationSection> sections = new ArrayList<>();
        for (Map.Entry<String, Object> section : temp.entrySet()) {
            sections.add(new ConfigurationSection((Map<String, Object>) section.getValue(), section.getKey()));
        }
        return sections;
    }

    @Override
    public String getString(String path) {
        if (this.data.get(path) instanceof String) return (String) this.data.get(path);
        return null;
    }

    @Override
    public int getInt(String path) {
        if (this.data.get(path) instanceof Integer) return (int) this.data.get(path);
        return 0;
    }

    @Override
    public ArrayList<Integer> getIntArray(String path) {
        if (this.data.get(path) instanceof ArrayList) return (ArrayList<Integer>) this.data.get(path);
        return null;
    }

    public String getKey() {
        return this.key;
    }

    public ArrayList<String> getKeys() {
        return new ArrayList<>(this.data.keySet());
    }
}
