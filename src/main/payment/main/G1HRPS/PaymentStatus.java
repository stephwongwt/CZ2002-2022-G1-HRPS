package main.G1HRPS;

public enum PaymentStatus {
    Pending(0),
    Complete(1),
    Cancelled(2);

    private final int value;

    private PaymentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}