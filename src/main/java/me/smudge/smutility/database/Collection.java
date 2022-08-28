package me.smudge.smutility.database;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a database collection or table
 */
public abstract class Collection {

    /**
     * The primary key
     */
    public String primaryKey = UUID.randomUUID().toString();

    /**
     * Used to get the name of the collection
     */
    public abstract String getName();

    /**
     * Used to get the collection as a map of key value pairs
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> map = new HashMap<>();

        Gson gson = new Gson();
        String json = gson.toJson(this);

        // For each json key value pair
        for (String pair : json.split(",")) {
            String parsed = pair
                    .replace("{", "")
                    .replace("}", "");
            String key = parsed.split(":")[0].replace("\"", "");
            String value = parsed.split(":")[1];

            // If the value is a string type
            if (value.contains("\"")) {
                map.put(key, value.replace("\"", "").trim());
            }
            // Otherwise its a integer
            else {
                map.put(key, Integer.parseInt(value.trim()));
            }
        }

        return map;
    }
}
