package main.G1HRPS;

import java.util.Scanner;

/**
 * Manager that handles running the app
 */
public class AppManager {

	public Scanner sc_;
	private ReservationManager reservation_manager_;
	private GuestManager guest_manager_;
	private PaymentManager payment_manager_;
	private RoomServiceManager room_service_manager_;
	private RoomManager room_manager_;
	private MenuItemManager menu_item_manager_;

	/**
	 * Loads from database
	 */
	public void Initialize() {
		// TODO - implement AppManager.Initialize
		throw new UnsupportedOperationException();
	}

	/**
	 * Handles input and output of console messages. Calls respective managers to perform functions.
	 */
	public void Run() {
		// TODO - implement AppManager.Run
		throw new UnsupportedOperationException();
	}

	private AppMenuItem PrintMenu() {
		// TODO - implement AppManager.PrintMenu
		throw new UnsupportedOperationException();
	}

	/**
	 * Will backup to database
	 */
	private void Quit() {
		// TODO - implement AppManager.Quit
		throw new UnsupportedOperationException();
	}

}