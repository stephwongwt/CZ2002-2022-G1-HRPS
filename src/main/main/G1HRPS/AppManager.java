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
	 * Call InitializeDB for all managers.
	 */
	public void Initialize() {
		reservation_manager_.InitializeDB();
		guest_manager_.InitializeDB();
		payment_manager_.InitializeDB();
		room_service_manager_.InitializeDB();
		room_manager_.InitializeDB();
		menu_item_manager_.InitializeDB();
	}

	/**
	 * Handles input and output of console messages. Calls respective managers to perform functions.
	 */
	public void Run() {
		// TODO - implement AppManager.Run
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @return
	 */
	private AppMenuItem PrintMenu() {
		// TODO - implement AppManager.PrintMenu
		throw new UnsupportedOperationException();
	}

	/**
	 * Calls SaveDB for all managers.
	 */
	private void Quit() {
		reservation_manager_.SaveDB();
		guest_manager_.SaveDB();
		payment_manager_.SaveDB();
		room_service_manager_.SaveDB();
		room_manager_.SaveDB();
		menu_item_manager_.SaveDB();
	}

}