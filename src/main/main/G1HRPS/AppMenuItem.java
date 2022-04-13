package main.G1HRPS;

public enum AppMenuItem {
	Quit(0),
	AddGuest(1),
	AddRoom(2),
	SearchGuest(3),
	SearchRoom(4),
	SearchReservations(5),
	Display(6);

	private final int value;

	private AppMenuItem(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}