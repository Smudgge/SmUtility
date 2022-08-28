package me.smudge.smutility.database;

public class HistoryCollection extends Collection {

    /**
     * The name of the player
     */
    public String playerName = "placeholder";

    /**
     * The date and time
     */
    public String date = "placeholder";

    /**
     * The server that was joined
     */
    public String serverName = "placeholder";

    /**
     * Event that occurred {@link PlayerHistoryEvent}
     */
    public String event = "placeholder";

    @Override
    public String getName() {
        return "history";
    }
}
