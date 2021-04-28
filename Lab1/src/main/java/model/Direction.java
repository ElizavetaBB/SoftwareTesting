package model;

public enum Direction {
    SOUTH(0),
    NORTH(1),
    WEST(3),
    EAST(4);

    public final int count;

    Direction(int count) {
        this.count = count;
    }
}
