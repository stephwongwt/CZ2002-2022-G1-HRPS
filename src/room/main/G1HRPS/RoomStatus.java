package main.G1HRPS;

/**
 * Enumset for the different types of room statuses.
 * 
 * @author LiangHee
 *
 */
public enum RoomStatus {
	Vacant(0), Occupied(1), Reserved(2), Maintenance(3);

	private final int value;

	private RoomStatus(int value) {
		this.value = value;
	}

	public int GetValue() {
		return value;
	}
}