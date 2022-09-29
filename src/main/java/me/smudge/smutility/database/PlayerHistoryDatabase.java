package me.smudge.smutility.database;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerHistoryDatabase extends Database {

    /**
     * Used to create a database object
     * Used to create a {@link PlayerHistoryDatabase}
     *
     * @param folder The folder the database will be in
     */
    public PlayerHistoryDatabase(File folder) {
        super(folder, "playerhistory");

        this.createCollection(new HistoryCollection());
    }

    /**
     * Used to add new history
     *
     * @param playerName Players Name
     * @param serverName Server involved
     * @param event      Event that has occurred
     */
    public void addHistory(String playerName, String serverName, PlayerHistoryEvent event) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH;mm;ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = dateTimeFormatter.format(localDateTime);

        HistoryCollection collection = new HistoryCollection();

        collection.playerName = playerName;
        collection.serverName = serverName;
        collection.date = date;
        collection.event = event.toString();

        this.addCollection(collection);
    }
}
