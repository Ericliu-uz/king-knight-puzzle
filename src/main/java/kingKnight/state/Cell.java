package kingKnight.state;

public enum Cell {

    EMPTY, //ordinal: 0
    KING, //1
    KNIGHT; //2

    public static Cell of(int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException();
        }
        return values()[value];
    }

    public int getValue() {
        return ordinal();
    }

    public String toString() {
        return Integer.toString(ordinal());
    }

}
