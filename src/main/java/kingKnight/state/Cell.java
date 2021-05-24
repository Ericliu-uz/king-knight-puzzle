package kingKnight.state;

/**
 * Class representing the cell state on the chessboard.
 */
public enum Cell {
    /**
     * Enum type value represent the cell is empty.
     */
    EMPTY, //ordinal: 0
    /**
     * Enum type value represent the chess king is on this cell.
     */
    KING, //ordinal: 1
    /**
     * Enum type value represent the chess knight is on this cell.
     */
    KNIGHT; //ordinal: 2

    /**
     * Returns the instance represented by the value specified.
     *
     * @param value the value representing an instance
     * @return the instance represented by the value specified
     * @throws IllegalArgumentException if the value specified does not
     * represent an instance
     */
    public static Cell of(int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException();
        }
        return values()[value];
    }

    /**
     * Returns the integer value that represents this instance.
     *
     * @return the integer value that represents this instance
     */
    public int getValue() {
        return ordinal();
    }

    public String toString() {
        return Integer.toString(ordinal());
    }

}
