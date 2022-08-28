package me.smudge.smutility.database;

import java.io.File;

/**
 * Represents the database manager
 */
public class DatabaseManager {

    private static PlayerHistoryDatabase playerHistoryDatabase;

    /**
     * Used to initialise databases
     * @param folder Parent folder
     */
    public DatabaseManager(File folder) {
        DatabaseManager.playerHistoryDatabase = new PlayerHistoryDatabase(folder);
    }


    public static PlayerHistoryDatabase getPlayerHistoryDatabase() {
        return DatabaseManager.playerHistoryDatabase;
    }
}
