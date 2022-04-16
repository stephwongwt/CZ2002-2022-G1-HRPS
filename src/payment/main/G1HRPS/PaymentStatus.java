package main.G1HRPS;

/**
 * Enumset for the payment statuses.
 * 
 * @author Kim Sang Hyeon
 *
 */
public enum PaymentStatus {
	Pending(0), Complete(1), Cancelled(2);

	private final int value;

	private PaymentStatus(int value) {
		this.value = value;
	}

	public int GetValue() {
		return value;
	}
}