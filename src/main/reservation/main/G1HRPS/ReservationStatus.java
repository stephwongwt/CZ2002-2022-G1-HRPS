package main.G1HRPS;

public enum ReservationStatus {
	Confirmed(0),
	Waitlist(1),
	CheckedIn(2),
	CheckedOut(3),
	Expired(4);

	private final int value;
    private ReservationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}