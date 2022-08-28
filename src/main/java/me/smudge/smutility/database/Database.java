package me.smudge.smutility.database;

import com.google.gson.Gson;
import me.smudge.smutility.SmUtility;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
     * @param folder The folder the database will be in
     * @param fileName The file name of the database
     */
    public Database(File folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
        this.setup();
    }

    /**
     * Used to set up the connection to the database
     */
    private void setup() {
        // Create database directory if it doesn't exist
        File file = new File(folder, File.separator);
        file.mkdir();

        String url = "jdbc:sqlite:" + folder.getAbsolutePath() + File.separator + fileName + ".sqlite3";

        try {

            this.connection = DriverManager.getConnection(url);

            if (this.connection == null) {
                SmUtility.getLogger().warn("Unable to connect to the database!");
                this.usingDatabase = false;
            }
        }
        catch (SQLException exception) {
            SmUtility.getLogger().warn("Unable to connect to the database!");
            exception.printStackTrace();
            this.usingDatabase = false;
        }
    }

    /**
     * Used to create a collection in the database
     * @param collection Collection to create
     */
    public void createCollection(Object collection) {
        Gson gson = new Gson();
        String json = gson.toJson(collection);
        System.out.println(json);
    }
}
