package main.G1HRPS;

/**
 * Enumset for the reservation statuses.
 * 
 * @author Chris Lim Qing Chuan
 *
 */
public enum ReservationStatus {
	Confirmed(0), Waitlist(1), CheckedIn(2), CheckedOut(3), Expired(4);

	private final int value;

	private ReservationStatus(int value) {
		this.value = value;
	}

	public int GetValue() {
		return value;
	}
}