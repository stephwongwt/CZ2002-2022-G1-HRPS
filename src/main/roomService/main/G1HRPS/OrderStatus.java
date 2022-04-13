package main.G1HRPS;

public enum OrderStatus {
    Confirmed(0),
    Preparing(1),
    Delivered(2);

    private final int value;

    private OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}