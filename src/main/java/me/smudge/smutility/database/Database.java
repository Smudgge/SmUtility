package me.smudge.smutility.database;

import me.smudge.smutility.MessageManager;
import me.smudge.smutility.SmUtility;
import me.smudge.smutility.configuration.ConfigManager;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a database
 */
public class Database {

    /**
     * Connection to the sqlite database
     */
    private Connection connection;

    /**
     * The path to the database
     */
    private final File folder;
    private final String fileName;

    /**
     * Can be set to false if an error occurs etc
     */
    private boolean usingDatabase = false;

    /**
     * Used to create a database object
     *
     * @param folder   The folder the database will be in
     * @param fileName The file name of the database
     */
    public Database(File folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;

        // If database is disabled
        if (!ConfigManager.getCommands().getCommandInfo("history").getEnabled()) return;

        this.setup();
    }

    /**
     * Used to set up the connection to the database
     */
    private void setup() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            this.usingDatabase = false;
            return;
        }

        // Create database directory if it doesn't exist
        File file = new File(folder, File.separator);
        file.mkdir();

        String url = "jdbc:sqlite:" + folder.getAbsolutePath() + File.separator + fileName + ".sqlite3";

        try {

            this.connection = DriverManager.getConnection(url);

            if (this.connection == null) {
                SmUtility.getLogger().warn("Unable to connect to the database!");
                this.usingDatabase = false;
                return;
            }
        } catch (SQLException exception) {
            SmUtility.getLogger().warn("Unable to connect to the database!");
            exception.printStackTrace();
            this.usingDatabase = false;
            return;
        }

        // Connected to the database
        this.usingDatabase = true;
        SmUtility.getProxyServer().getConsoleCommandSource().sendMessage(MessageManager.convert("&aDatabase Connected!"));
    }

    /**
     * Used to execute a statement
     *
     * @param sql Statement to execute
     */
    private void executeStatement(String sql) {
        if (!this.usingDatabase) {
            SmUtility.getLogger().warn("Tried to used the database when not connected!");
            return;
        }

        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException exception) {
            SmUtility.getLogger().warn("Unable to execute sql :");
            SmUtility.getLogger().warn(sql);
            exception.printStackTrace();
            this.usingDatabase = false;
        }
    }

    /**
     * Used to execute a query and return the results
     *
     * @param sql Statement to execute
     * @return Result set
     */
    private ResultSet executeQuery(String sql) {
        if (!this.usingDatabase) {
            SmUtility.getLogger().warn("Tried to used the database when not connected!");
            return null;
        }

        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException exception) {
            SmUtility.getLogger().warn("Unable to execute sql :");
            SmUtility.getLogger().warn(sql);
            exception.printStackTrace();
            this.usingDatabase = false;
        }

        return null;
    }

    /**
     * Used to create a new collection structure in the database
     *
     * @param collection Collection to create
     */
    public void createCollection(Collection collection) {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS `").append(collection.getName()).append("` (");
        builder.append("`id` TEXT PRIMARY KEY");

        // For each json key value pair
        for (Map.Entry<String, Object> record : collection.getAsMap().entrySet()) {
            if (Objects.equals(record.getKey(), "primaryKey")) continue;
            if (record.getValue() instanceof String) {
                builder.append(", `").append(record.getKey()).append("` TEXT");
            }
            if (record.getValue() instanceof Integer) {
                builder.append(", `").append(record.getKey()).append("` INTEGER");
            }
        }

        builder.append(");");

        this.executeStatement(builder.toString());
    }

    /**
     * Get all collections with key and value
     *
     * @param collection Collection structure to search
     * @param key        Key to look for
     * @param value      Value to look for
     * @return List of collections as maps
     */
    public ArrayList<Map<String, Object>> getCollections(Collection collection, String key, String value) {
        ArrayList<Map<String, Object>> collections = new ArrayList<>();

        ResultSet results = this.executeQuery("SELECT * FROM `" + collection.getName() + "` WHERE " + key + " = '" + value + "'");

        if (results == null) return null;

        try {
            while (results.next()) {
                Map<String, Object> temp = new HashMap<>();

                for (Map.Entry<String, Object> record : collection.getAsMap().entrySet()) {
                    if (Objects.equals(record.getKey(), "primaryKey")) continue;
                    if (record.getValue() instanceof String) {
                        temp.put(record.getKey(), results.getString(record.getKey()));
                    }
                    if (record.getValue() instanceof Integer) {
                        temp.put(record.getKey(), results.getInt(record.getKey()));
                    }
                }

                collections.add(temp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            this.usingDatabase = false;
            return null;
        }

        return collections;
    }

    /**
     * Used to add a collection
     *
     * @param collection Collection to add
     */
    public void addCollection(Collection collection) {
        StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO `").append(collection.getName()).append("` (id");

        for (Map.Entry<String, Object> record : collection.getAsMap().entrySet()) {
            if (Objects.equals(record.getKey(), "primaryKey")) continue;
            builder.append(", '").append(record.getKey()).append("'");
        }

        builder.append(") VALUES ('").append(collection.primaryKey).append("'");

        for (Map.Entry<String, Object> record : collection.getAsMap().entrySet()) {
            if (Objects.equals(record.getKey(), "primaryKey")) continue;
            if (record.getValue() instanceof String) {
                builder.append(", '").append(record.getValue()).append("'");
            }
            if (record.getValue() instanceof Integer) {
                builder.append(", ").append(record.getValue());
            }
        }

        builder.append(")");

        this.executeStatement(builder.toString());
    }
}
