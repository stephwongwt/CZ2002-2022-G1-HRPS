package main.G1HRPS;

public enum BedSize {
    Single(0),
    Supersingle(1),
    Double(2),
    Queen(3),
    King(4);

    private final int value;

    private BedSize(int value) {
        this.value = value;
    }

    public int GetValue() {
        return value;
    }
}