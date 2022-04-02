package main.G1HRPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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

	public AppManager() {
		System.out.println("Application Start");
		sc_ = new Scanner(System.in);
		reservation_manager_ = new ReservationManager();
		guest_manager_ = new GuestManager();
		payment_manager_ = new PaymentManager();
		room_service_manager_ = new RoomServiceManager();
		room_manager_ = new RoomManager();
		menu_item_manager_ = new MenuItemManager();
	}

	/**
	 * Call InitializeDB for all managers.
	 */
	public void Initialize() {
		System.out.println("Initializing Databases...");
		reservation_manager_.InitializeDB();
		guest_manager_.InitializeDB();
		payment_manager_.InitializeDB();
		room_service_manager_.InitializeDB();
		room_manager_.InitializeDB();
		menu_item_manager_.InitializeDB();
	}

	/**
	 * Handles input and output of console messages.
	 * Calls respective managers to perform functions.
	 */
	public void Run() {
		boolean running = true;
		List<AppMenuItem> app_menu_list = java.util.Arrays.asList(AppMenuItem.values());
		while (running) {
			System.out.println("\r\n|--------|Choose an option|--------|");
			for (AppMenuItem app_menu_item : app_menu_list) {
				System.out.printf("[%d] %s\r\n", app_menu_item.getValue(), app_menu_item.toString());
			}
			int option = 0;
			
			while (true) {
				try {
					option = sc_.nextInt();
					if ((option >= 0) && (option < app_menu_list.size())) {
						break;
					}
					else
					{
						System.out.println("Unavailable, please select another option.");
					}
				} catch (Exception e) {
					sc_.nextLine();
					System.out.println("Unavailable, please select another option.");
				}
			}
			AppMenuItem selection = AppMenuItem.values()[option];
			switch (selection) {
				case Quit:
					Quit();
					System.out.println("Exiting, Goodbye.");
					running = false;
					break;
				case AddGuest:
					System.out.println("AddGuest");
					break;
				case AddRoom:
					System.out.println("AddRoom");
					break;
				case SearchGuest:
					System.out.println("SearchGuest");
					break;
				case SearchRoom:
					System.out.println("SearchRoom");
					break;
				case SearchReservations:
					System.out.println("SearchReservations");
					break;
				case Display:
					System.out.println("Display");
					break;
				default:
					System.out.println("Option does not exist");
					break;
			}
		}
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
		System.out.print("Saving databases...");
		reservation_manager_.SaveDB();
		guest_manager_.SaveDB();
		payment_manager_.SaveDB();
		room_service_manager_.SaveDB();
		room_manager_.SaveDB();
		menu_item_manager_.SaveDB();
		System.out.println("Completed.");
	}

}