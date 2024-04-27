package it.uniroma3.tagd;

public enum Anomaly {
    LOST_UPDATE("Lost Update"),
    DIRTY_READ("Dirty Read"),
    GHOST_UPDATE("Ghost Update"),
    INCONSISTENT_READ("Inconsistent Read"),
    PHANTOM_READ("Phantom Read");

    private final String name;

    Anomaly(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
