package it.uniroma3.tagd;

import java.sql.Connection;

public enum IsolationLevel {
    READ_UNCOMMITTED("Read Uncommitted", Connection.TRANSACTION_READ_UNCOMMITTED),
    READ_COMMITTED("Read Committed", Connection.TRANSACTION_READ_COMMITTED),
    REPEATABLE_READ("Repeatable Read", Connection.TRANSACTION_REPEATABLE_READ),
    SERIALIZABLE("Serializable", Connection.TRANSACTION_SERIALIZABLE);

    private final String name;
    private final int level;

    IsolationLevel(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
