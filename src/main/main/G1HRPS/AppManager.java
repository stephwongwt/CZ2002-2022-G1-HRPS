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
					CreateNewRoom();
					break;
				case SearchGuest:
					Guest search_guest = SearchList(guest_manager_);
					// search_guest.toString();

					/// check in guest & room & reservation
					// check if guest has reservation
					// reservation_manager_.SearchListByGuestId(search_guest.GetIdentity());
					// reservation exists, check in
					// guest_manager_.CheckInGuest(search_guest, room_num);

					// guest has no reservation
					// room_manager_.GetRoomStatisticsByTypeOccupancyRate();
					// ask for input to pick room
					// guest_manager_.CheckInGuest(search_guest, room_num);


					/// check out guest & room & reservation
					// Payment checkout = payment_manager_.GenerateAndPrintBill();
					// ask if making payment
					// yes
					// guest_manager_.CheckOutGuest(search_guest, checkout.GetPaymentId());
					// room_manager_.CheckOutAllGuest(search_guest.GetRoomNum());
					// no
					// exit submenu

					/// edit guest menu

					/// Add room service order
					// boolean success = room_service_manager_.CreateNewRoomServiceOrder();
					// check success

					break;
				case SearchRoom:
					Room search_room = SearchList(room_manager_);
					/// complete room service order
					// List<RoomServiceOrder> rso_list = room_service_manager_.GetOrderedItemsByRoom(search_room.GetRoomNum());
					// for (RoomServiceOrder rso : rso_list) {
					// 	rso.toString();
					// }
					// pick which rso to complete

					/// edit room details
					break;
				case SearchReservations:
					Reservation search_rsvp = SearchList(reservation_manager_);
					/// check in guest & room & reservation
					/// delete reservation

					break;
				case Display:
					sc_.nextLine();
					System.out.println("Which to display?\n" +
							"[0] Go back\n" +
							"[1] Room Availabilities\n" +
							"[2] All Guests\n" +
							"[3] All Reservations");
					int option = sc_.nextInt();
					switch (option) {
						case 0:
							break;
						case 1:
							DisplayList(room_manager_);
							break;
						case 2:
							DisplayList(guest_manager_);
							break;
						case 3:
							DisplayList(reservation_manager_);
							break;
						default:
							System.out.println("Option does not exist");
							continue;
					}
					break;
				default:
					System.out.println("Option does not exist");
					break;
			}
		}
		sc_.close();
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
				} else {
					System.out.println("Unavailable, please select another option.");
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please select another option.");
			}
		}
		return AppMenuItem.values()[option];
	}

	/**
	 * Handles the input and output for creating new guest
	 */
	private void CreateNewGuest() {
		String identity;
		String name;
		String credit_card_number;
		String address;
		String contact;
		String country;
		Gender gender;
		String nationality;
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
		System.out.println("Enter Country (e.g. Singapore):");
		country = sc_.nextLine().toUpperCase();
		System.out.println("Enter Gender (e.g. Female[0]/Male[1]/Other[1]):");
		gender = GetEnumFromInput(Gender.values());
		System.out.println("Enter Nationality (e.g. Singaporean):");
		nationality = sc_.nextLine().toUpperCase();
		boolean success = guest_manager_.CreateNewGuest(identity, name, credit_card_number, address, contact, country,
				gender, nationality);
		if (success) {
			System.out.printf("Guest %s successfully created", name);
		} else {
			System.out.println("Guest was not created. Some error happened.");
		}
		System.out.printf("%s, %s, %s, %s, %s, %s, %s, %s", identity, name, credit_card_number, address, contact,
				country, gender.toString(), nationality);
	}

	/**
	 * Handles the input and output for creating new room
	 */
	private void CreateNewRoom() {
		int room_num;
		RoomType room_type;
		float room_price;
		BedSize bed_size;
		boolean wifi_enabled;
		boolean with_view;
		boolean with_smoking;
		RoomStatus status;
		sc_.nextLine();
		System.out.println("Enter Room Number:");
		while (true) {
			try {
				room_num = sc_.nextInt();
				if (room_num != 0) {
					sc_.nextLine();
					break;
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again.");
			}
		}
		System.out.println("Enter Room Type (Single[0]/Standard[1]/VIP[2]/Suite[3]/Deluxe[4]):");
		room_type = GetEnumFromInput(RoomType.values());
		System.out.println("Enter price of room per night (e.g 100.00):");
		room_price = sc_.nextFloat();
		System.out.println("Enter Bed Size (Single[0]/SuperSingle[1]/Double[2]/Queen[3]/King[4]):");
		bed_size = GetEnumFromInput(BedSize.values());
		System.out.println("Enter WiFi (False[0]/True[1]):");
		wifi_enabled = GetBooleanFromInput();
		System.out.println("Enter View (False[0]/True[1]):");
		with_view = GetBooleanFromInput();
		System.out.println("Enter Smoking (False[0]/True[1]):");
		with_smoking = GetBooleanFromInput();
		System.out.println("Enter Room Status (Vacant[0]/Occupied[1]/Reserved[2]/Maintenance[3]):");
		status = GetEnumFromInput(RoomStatus.values());
		// TODO: Add room to room manager
		System.out.printf("%d, %s, %s, %s, %s, %s, %s, %s", room_num, room_type.toString(), room_price,
				bed_size.toString(), Boolean.valueOf(wifi_enabled), Boolean.valueOf(with_view),
				Boolean.valueOf(with_smoking), status.toString());
	}

	/**
	 * Present user with a list of choices from an enumset.
	 * 
	 * @param <T>      the enumset
	 * @param opt_list an array of the enum options
	 * @return
	 */
	private <T> T GetEnumFromInput(T[] opt_list) {
		T t = null;
		while (true) {
			int option;
			try {
				option = sc_.nextInt();
				if ((option >= 0) && (option < opt_list.length)) {
					t = opt_list[option];
					sc_.nextLine();
					break;
				} else {
					System.out.println("Unavailable, please select another option.");
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please select another option.");
			}
		}
		return t;
	}

	/**
	 * Present user with a choice of true or false options.
	 * 
	 * @return return user's input in Boolean.
	 */
	private Boolean GetBooleanFromInput() {
		while (true) {
			int option;
			try {
				option = sc_.nextInt();
				if (option == 1) {
					return true;
				} else if (option == 0) {
					return false;
				} else {
					sc_.nextLine();
					System.out.println("Unavailable, please try again.");
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again.");
			}
		}
	}

	/**
	 * Gets input of search text from user and calls the SearchList method of
	 * Supermanager.
	 * Example usage:
	 * GuestManager guest_manager = new GuestManager();
	 * SearchList(guest_manager);
	 * 
	 * @param <T> type which the manager is managing
	 * @param sm  a generic type manager which has implemented Supermanager
	 * @return the object matching the search text
	 */
	private <T> T SearchList(Supermanager<T> sm) {
		String search_text = "";
		System.out.println("Enter search text:");
		sc_.nextLine();
		while (true) {
			try {
				search_text += sc_.nextLine();
				return sm.SearchList(search_text);
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Error Input, please try again.");
			}
		}
	}

	/**
	 * Calls the GetList method of Supermanager and then access each object and
	 * calls toString.
	 * Example usage:
	 * GuestManager guest_manager = new GuestManager();
	 * DisplayList(guest_manager);
	 * 
	 * @param <T> type which the manager is managing
	 * @param sm  a generic type manager which has implemented Supermanager
	 */
	private <T> void DisplayList(Supermanager<T> sm) {
		List<T> sm_list = sm.GetList();
		for (T t : sm_list) {
			t.toString();
		}
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