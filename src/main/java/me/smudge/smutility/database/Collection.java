package me.smudge.smutility.database;

import java.util.UUID;

/**
 * Represents a database collection or table
 */
public abstract class Collection {

    /**
     * The primary key
     */
    public String primaryKey = UUID.randomUUID().toString();
}
