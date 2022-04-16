package main.G1HRPS;

/**
 * Enumset for the room service order statuses.
 * 
 * @author Yeo Hong Wei
 *
 */
public enum RoomServiceOrderStatus {
	Confirmed(0), Preparing(1), Delivered(2);

	private final int value;

	private RoomServiceOrderStatus(int value) {
		this.value = value;
	}

	public int GetValue() {
		return value;
	}
}