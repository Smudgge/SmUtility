package me.smudge.smutility.database;

import java.io.File;

public class PlayerHistoryDatabase extends Database {

    /**
     * Used to create a database object
     * Used to create a {@link PlayerHistoryDatabase}
     * @param folder   The folder the database will be in
     */
    public PlayerHistoryDatabase(File folder) {
        super(folder, "playerhistory");

        this.createCollection(new HistoryCollection());
    }
}
