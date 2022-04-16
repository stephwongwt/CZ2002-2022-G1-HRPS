package main.G1HRPS;

/**
 * Enumset of application menu items for navigation.
 * 
 * @author Steph Wong
 *
 */
public enum AppMenuItem {
	Quit(0), AddGuest(1), AddRoom(2), AddMenuItem(3), SearchGuest(4), SearchRoom(5), SearchReservations(6),
	SearchMenuItems(7), Display(8);

	private final int value;

	private AppMenuItem(int value) {
		this.value = value;
	}

	public int GetValue() {
		return value;
	}
}