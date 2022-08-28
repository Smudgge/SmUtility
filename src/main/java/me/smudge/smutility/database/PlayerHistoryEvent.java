package me.smudge.smutility.database;

public enum PlayerHistoryEvent {
    JOIN, LEAVE;

    public String toSymbol() {
        if (this == PlayerHistoryEvent.JOIN) {
            return "&a+";
        }
        return "&c-";
    }
}
