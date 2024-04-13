package dev.nilkoush.worldguardregionevents;

/**
 * Enum for movement ways
 */
public enum MovementWay {
    MOVE("MOVE", 0),
    TELEPORT("TELEPORT", 1),
    SPAWN("SPAWN", 2),
    DISCONNECT("DISCONNECT", 3);

    private final String name;
    private final int number;

    MovementWay(String name, int number) {
        this.name = name;
        this.number = number;
    }

    /**
     * Get the name of the movement way
     * @return The name of the movement way
     */
    public String getName() {
        return name;
    }

    /**
     * Get the number of the movement way
     * @return The number of the movement way
     */
    public int getNumber() {
        return number;
    }
}
