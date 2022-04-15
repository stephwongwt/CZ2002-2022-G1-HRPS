package main.G1HRPS;

public enum RoomType {
    Single(0),
    Standard(1),
    Vip(2),
    Suite(3),
    Deluxe(4);

    private final int value;

    private RoomType(int value) {
        this.value = value;
    }

    public int GetValue() {
        return value;
    }
}