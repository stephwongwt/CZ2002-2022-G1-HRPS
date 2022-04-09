package main.G1HRPS;

import java.util.List;
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
	private final List<AppMenuItem> app_menu_list;

	public AppManager() {
		System.out.println("Application Start");
		sc_ = new Scanner(System.in);
		reservation_manager_ = new ReservationManager();
		guest_manager_ = new GuestManager();
		payment_manager_ = new PaymentManager();
		room_service_manager_ = new RoomServiceManager();
		room_manager_ = new RoomManager();
		menu_item_manager_ = new MenuItemManager();
		app_menu_list = java.util.Arrays.asList(AppMenuItem.values());
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
		while (running) {
			AppMenuItem selection = PrintMenu();
			switch (selection) {
				case Quit:
					Quit();
					System.out.println("Exiting, Goodbye.");
					running = false;
					break;
				case AddGuest:
					CreateNewGuest();
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
	 * @return selected menu option
	 */
	private AppMenuItem PrintMenu() {
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
		return AppMenuItem.values()[option];
	}

	private void CreateNewGuest() {
		String identity = null;
		String name = null;
		String credit_card_number = null;
		String address = null;
		String contact = null;
		String country = null;
		Gender gender = null;
		String nationality = null;
		
		sc_.nextLine();
		System.out.println("Enter Identification/Driving License Number (e.g. S1234567A):");
		identity = sc_.nextLine().toUpperCase();
		System.out.println("Enter Name (e.g. John Smith):");
		name = sc_.nextLine().toUpperCase();
		System.out.println("Enter Credit Card Number (e.g. 4605100120021234):");
		credit_card_number = sc_.nextLine().toUpperCase();
		System.out.println("Enter Address (e.g. 50 Nanyang Ave, S639798):");
		address = sc_.nextLine().toUpperCase();
		System.out.println("Enter Contact (e.g. +6590001000):");
		contact = sc_.nextLine().toUpperCase();
		System.out.println("Enter Name (e.g. Singapore):");
		country = sc_.nextLine().toUpperCase();
		System.out.println("Enter Gender (e.g. Female/Male/Other):");
		while (true) {
			try {
				gender = Gender.valueOf(sc_.next().toUpperCase());
				if (gender != null)
				{
					sc_.nextLine();
					break;
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again.");
			}
		}
		
		System.out.println("Enter Name (e.g. Singaporean):");
		nationality = sc_.nextLine().toUpperCase();

		// TODO: Add guest to guest manager
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