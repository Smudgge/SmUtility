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

import me.smudge.smutility.SmUtility;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents a config file
 */
public class Configuration implements ConfigurationSelection {

    /**
     * The configuration file
     */
    private final File file;

    /**
     * Data loaded from the yaml
     */
    private Map<String, Object> data;

    /**
     * Creates a new {@link Configuration}
     */
    public Configuration(File folder, String fileName) {

        // Get config file
        this.file = new File(folder, fileName);

        // Create file if it doesn't exist
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    /**
     * Loads the configuration file to the yaml instance
     * @return True if successful
     */
    public boolean load() {
        if (!file.exists()) {
            try (InputStream input = SmUtility.class.getResourceAsStream("/" + file.getName())) {

                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }

            } catch (IOException exception) {
                exception.printStackTrace();
                return false;
            }
        }

        try (InputStream inputStream = new FileInputStream(this.file)) {

            Yaml yaml = new Yaml();
            this.data = (Map<String, Object>) yaml.load(inputStream);

        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
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

    @Override
    public boolean getBoolean(String path) {
        if (this.data.get(path) instanceof Boolean) return (boolean) this.data.get(path);
        return false;
    }
}
