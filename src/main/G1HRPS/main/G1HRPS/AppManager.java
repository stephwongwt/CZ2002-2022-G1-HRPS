package main.G1HRPS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Manager that handles running the main application.
 * 
 * @author Steph Wong
 *
 */
public class AppManager {
	/**
	 * Global date time formatter to be used when comparing dates.
	 */
	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * Handles input by user.
	 */
	public Scanner sc_;

	/**
	 * Manages list of reservations.
	 */
	private ReservationManager reservation_manager_;

	/**
	 * Manages list of guests.
	 */
	private GuestManager guest_manager_;

	/**
	 * Manages list of payments.
	 */
	private PaymentManager payment_manager_;

	/**
	 * Manages list of room services.
	 */
	private RoomServiceManager room_service_manager_;

	/**
	 * Manages list of rooms.
	 */
	private RoomManager room_manager_;

	/**
	 * Manages list of menu items for room service orders.
	 */
	private MenuItemManager menu_item_manager_;

	/**
	 * List of application's main menu navigation.
	 */
	private final List<AppMenuItem> APP_MENU_LIST;

	/**
	 * Default check in time for all guests at our hotel.
	 */
	private final int DEFAULT_CHECK_IN_HOUR = 15;

	/**
	 * Constructor that initializes each of the manager objects and scanner. Also
	 * initializes APP_MENU_LIST using the AppMenuItem enumset.
	 */
	public AppManager() {
		System.out.println("Application Start");
		sc_ = new Scanner(System.in);
		reservation_manager_ = new ReservationManager();
		guest_manager_ = new GuestManager();
		payment_manager_ = new PaymentManager();
		room_service_manager_ = new RoomServiceManager();
		room_manager_ = new RoomManager();
		menu_item_manager_ = new MenuItemManager();
		APP_MENU_LIST = java.util.Arrays.asList(AppMenuItem.values());
	}

	/**
	 * Call InitializeDB for all managers.
	 * Checks in guests with existing room numbers into respective rooms.
	 * Checks in reservations of said guests as well.
	 */
	public void Initialize() {
		System.out.println("Initializing Databases...");
		reservation_manager_.InitializeDB();
		guest_manager_.InitializeDB();
		payment_manager_.InitializeDB();
		room_service_manager_.InitializeDB();
		room_manager_.InitializeDB();
		menu_item_manager_.InitializeDB();

		List<Guest> guest_list = guest_manager_.GetList();
		for (Guest guest : guest_list) {
			int room_number = guest.GetRoomNum();
			if (room_number != 0) {
				room_manager_.CheckInGuest(guest, room_number);
				Reservation rsvp = reservation_manager_.SearchList(guest);
				if (rsvp != null) {
					if (rsvp.GetRoomNum() == room_number) {
						reservation_manager_.CheckIn(rsvp);
					}
				}
			}
		}
	}

	/**
	 * Handles input and output of console messages. Calls respective managers to
	 * perform functions.
	 */
	public void Run() {
		boolean running = true;
		while (running) {
			AppMenuItem selection = PrintMenu();
			switch (selection) {
			case Quit:
				System.out.println("|---|Quit|---|");
				Quit();
				System.out.println("Exiting, Goodbye.");
				running = false;
				break;
			case AddGuest:
				System.out.println("|---|Add Guest|---|");
				Guest new_guest = CreateNewGuest();
				PrintGuestSubMenu(new_guest);
				break;
			case AddRoom:
				System.out.println("|---|Add Room|---|");
				CreateNewRoom();
				break;
			case AddMenuItem:
				System.out.println("|---|Add Menu Items|---|");
				sc_.nextLine();
				System.out.println("Enter name of menu item:");
				String menu_item_name = GetUppercaseStringFromInput();
				System.out.println("Enter price:");
				float menu_item_price = GetNonZeroFloatFromInput();
				System.out.println("Enter description:");
				String menu_item_description = GetStringFromInput();
				MenuItem new_menu_item = menu_item_manager_.CreateNewMenuItem(menu_item_name, menu_item_price,
						menu_item_description);
				if (new_menu_item != null) {
					System.out.println("Item created!");
					System.out.println(new_menu_item.toString());
				} else {
					System.out.println("Menu item already exists, did not create.");
				}
				break;
			case SearchGuest:
				System.out.println("|---|Search Guest|---|");
				System.out.println("Search by name (False[0]/True[1])?");
				boolean search_by_name = GetBooleanFromInput();
				Guest search_guest = null;
				if (search_by_name) {
					System.out.println("Enter name to search:");
					sc_.nextLine();
					String search_guest_name = GetUppercaseStringFromInput();
					List<Guest> search_guest_list = null;
					search_guest_list = guest_manager_.SearchListByName(search_guest_name);
					if (search_guest_list.size() == 1) {
						search_guest = search_guest_list.get(0);
					} else if (!search_guest_list.isEmpty()) {
						System.out.println("Pick a guest:");
						int guest_index;
						for (int i = 0; i < search_guest_list.size(); i++) {
							Guest found_guest = search_guest_list.get(i);
							System.out.printf("[%d] %s\n", i, found_guest.GetName());
						}
						guest_index = GetIntFromInput(0, search_guest_list.size());
						search_guest = search_guest_list.get(guest_index);
					}
				} else {
					search_guest = SearchManagerList(guest_manager_, "guest identity");
				}
				PrintGuestSubMenu(search_guest);
				break;
			case SearchRoom:
				System.out.println("|---|Search Room|---|");
				Room search_room = SearchManagerList(room_manager_, "room number");
				System.out.println(search_room.toString());
				System.out.println("What would you like to do with this room?\n" + "[0] Go back\n"
						+ "[1] Order Room Service\n" + "[2] Complete Room Service Order\n" + "[3] Edit Details\n");
				int sub_room_option = GetIntFromInput();
				switch (sub_room_option) {
				case 0:
					System.out.println("Back to previous menu...");
					break;
				case 1:
					System.out.println("|------|Order Room Service|------|");
					if (search_room.GetStatus() != RoomStatus.Occupied) {
						System.out.println("Unavailable, room is vacant");
						break;
					}
					List<MenuItem> ordered_item_list = PrintRoomServiceMenu();
					System.out.println("Any remarks to add to order?");
					sc_.nextLine();
					String remarks = GetUppercaseStringFromInput();
					RoomServiceOrder room_service_order = room_service_manager_.CreateNewRoomServiceOrder(
							search_room.GetGuestList().get(0).GetIdentity(), search_room.GetRoomNumber(),
							ordered_item_list, remarks);
					if (room_service_order != null) {
						System.out.println("Room Service Order successfully created!");
					}
					break;
				case 2:
					System.out.println("|------|Complete Room Service Order|------|");
					if (search_room.GetStatus() == RoomStatus.Vacant) {
						System.out.println("Unavailable, room is vacant");
						break;
					}
					List<RoomServiceOrder> order_list = room_service_manager_
							.GetOrderedItemsByRoom(search_room.GetRoomNumber());
					if (!order_list.isEmpty()) {
						System.out.println("Room services ordered:");
						for (int i = 0; i < order_list.size(); i++) {
							RoomServiceOrder order = order_list.get(i);
							System.out.printf("[%d] Status (%s), Items: %\r\n", i, order.GetStatus().toString(),
									order.GetOrderedItemList().toString());
						}
						System.out.println("Select Order to complete:");
						int order_index_to_complete = GetIntFromInput(0, order_list.size());
						RoomServiceOrder order_to_complete = order_list.get(order_index_to_complete);
						if (order_to_complete.GetStatus() != RoomServiceOrderStatus.Delivered) {
							order_to_complete.SetStatus(RoomServiceOrderStatus.Delivered);
						} else {
							System.out.println("Order already completed.");
						}
					} else {
						System.out.println("No orders was placed.");
					}
					break;
				case 3:
					System.out.println("|------|Edit Room Details|------|");
					boolean continue_editing_room = true;
					System.out.println("[0] Go back | [1] Room Number | [2] Room Type | [3] Room Price");
					System.out.println("[4] Bed Size | [5] Wifi | [6] View | [7] Smoking | [8] Status");
					while (continue_editing_room) {
						System.out.println("Pick detail to edit:");
						int edit_opt = GetIntFromInput();
						switch (edit_opt) {
						case 0:
							System.out.println("Back to previous menu...");
							continue_editing_room = false;
							break;
						case 1:
							System.out.println("Enter new Room Number:");
							int edit_room_num = GetNonZeroIntFromInput();
							Room found_room = room_manager_.SearchList(edit_room_num);
							if (found_room == null) {
								search_room.SetRoomNumber(edit_room_num);
								System.out.println("Edit successful.");
							} else {
								System.out.println("Room number already exists.");
							}
							break;
						case 2:
							System.out
									.println("Enter new Room Type (Single[0]/Standard[1]/VIP[2]/Suite[3]/Deluxe[4]):");
							RoomType room_type = GetEnumFromInput(RoomType.values());
							search_room.SetRoomType(room_type);
							break;
						case 3:
							System.out.println("Enter new price of room per night (e.g 100.00):");
							float room_price = GetNonZeroFloatFromInput();
							search_room.SetRoomPrice(room_price);
							System.out.println("Edit successful.");
							break;
						case 4:
							System.out.println(
									"Enter new Bed Size (Single[0]/SuperSingle[1]/Double[2]/Queen[3]/King[4]):");
							BedSize bed_size = GetEnumFromInput(BedSize.values());
							search_room.SetBedSize(bed_size);
							System.out.println("Edit successful.");
							break;
						case 5:
							System.out.println("Enter new WiFi (False[0]/True[1]):");
							boolean wifi_enabled = GetBooleanFromInput();
							search_room.SetWifi(wifi_enabled);
							System.out.println("Edit successful.");
							break;
						case 6:
							System.out.println("Enter new View (False[0]/True[1]):");
							boolean with_view = GetBooleanFromInput();
							search_room.SetView(with_view);
							System.out.println("Edit successful.");
							break;
						case 7:
							System.out.println("Enter new Smoking (False[0]/True[1]):");
							boolean with_smoking = GetBooleanFromInput();
							search_room.SetSmoking(with_smoking);
							System.out.println("Edit successful.");
							break;
						case 8:
							System.out.println(
									"Enter new Room Status (Vacant[0]/Occupied[1]/Reserved[2]/Maintenance[3]):");
							RoomStatus status = GetEnumFromInput(RoomStatus.values());
							search_room.SetStatus(status);
							System.out.println("Edit successful.");
							break;
						default:
							System.out.println("Unavailable, please try again:");
							break;
						}
					}
					System.out.println("Edited Room:\n" + search_room.toString());
					break;
				default:
					System.out.println("Unavailable, please try again:");
					break;
				}
				break;
			case SearchReservations:
				System.out.println("|---|Search Reservations|---|");
				Reservation search_rsvp = SearchManagerList(reservation_manager_, "reservation code");
				System.out.println(search_rsvp.toString());
				System.out.println("What would you like to do with this reservation?\n" + "[0] Go back\n"
						+ "[1] Check In\n" + "[2] Delete\n");
				int sub_rsvp_option = GetIntFromInput();
				switch (sub_rsvp_option) {
				case 0:
					System.out.println("Back to previous menu...");
					break;
				case 1:
					System.out.println("|------| Reservation Check In|------|");
					Guest rsvp_guest = guest_manager_.SearchList(search_rsvp.GetGuestId());
					int rsvp_room_num = search_rsvp.GetRoomNum();
					boolean success = true;
					success = (success && (room_manager_.CheckInGuest(rsvp_guest, rsvp_room_num)));
					success = (success && (guest_manager_.CheckIntoRoom(rsvp_guest, rsvp_room_num)));
					success = (success && (reservation_manager_.CheckIn(search_rsvp)));
					if (success) {
						System.out.printf("Guest %s checked into room number %d, with reservation code %s.\n", rsvp_guest.GetName(),
								rsvp_room_num, search_rsvp.GetReservationCode().toString());
					} else {
						System.err.println("Something went terribly wrong, did not check in guest.");
					}
					break;
				case 2:
					System.out.println("|------|Delete Reservation|------|");
					boolean del_rsvp_success = reservation_manager_.RemoveFromList(search_rsvp);
					if (del_rsvp_success) {
						System.out.println("Successfully deleted reservation!");
					} else {
						System.out.println("Failed to delete reservation.");
					}
					break;
				default:
					System.out.println("Unavailable, please try again:");
					break;
				}
				break;
			case SearchMenuItems:
				System.out.println("|---|Search Menu Item|---|");
				MenuItem search_menu_item = SearchManagerList(menu_item_manager_, "menu item name");
				System.out.println(search_menu_item.toString());
				System.out.println("What would you like to do with this menu item?\n" + "[0] Go back\n"
						+ "[1] Edit details\n" + "[2] Delete\n");
				int sub_menu_item_option = GetIntFromInput();
				switch (sub_menu_item_option) {
				case 0:
					System.out.println("Back to previous menu...");
					break;
				case 1:
					System.out.println("|------|Edit Menu Item Details|------|");
					boolean continue_editing_menu_item = true;
					System.out.println("[0] Go back | [1] Name | [2] Price | [3] Description");
					while (continue_editing_menu_item) {
						System.out.println("Pick detail to edit:");
						int edit_opt = GetIntFromInput();
						switch (edit_opt) {
						case 0:
							System.out.println("Back to previous menu...");
							continue_editing_menu_item = false;
							break;
						case 1:
							System.out.println("Enter new Name:");
							String edit_name = GetUppercaseStringFromInput();
							MenuItem found_menu_item = menu_item_manager_.SearchList(edit_name);
							if (found_menu_item == null) {
								search_menu_item.SetName(edit_name);
								System.out.println("Edit successful.");
							} else {
								System.out.println("Item name already exists.");
							}
							break;
						case 2:
							System.out.println("Enter new price:");
							float item_price = GetNonZeroFloatFromInput();
							search_menu_item.SetPrice(item_price);
							System.out.println("Edit successful.");
							break;
						case 3:
							System.out.println("Enter new description:");
							String item_description = GetStringFromInput();
							search_menu_item.SetDescription(item_description);
							System.out.println("Edit successful.");
							break;
						default:
							System.out.println("Unavailable, please try again:");
							break;
						}
					}
					System.out.println("Edited Menu Item:\n" + search_menu_item.toString());
					break;
				case 2:
					System.out.println("|------|Delete Menu Item|------|");
					boolean del_menu_item_success = menu_item_manager_.RemoveFromList(search_menu_item);
					if (del_menu_item_success) {
						System.out.println("Successfully deleted menu item!");
					} else {
						System.out.println("Failed to delete menu item.");
					}
					break;
				default:
					System.out.println("Unavailable, please try again:");
					break;
				}
				break;
			case Display:
				System.out.println("|---|Display|---|");
				sc_.nextLine();
				System.out.println("Which to display?\n" + "[0] Go back\n" + "[1] All Rooms\n" + "[2] All Guests\n"
						+ "[3] All Reservations\n" + "[4] All Menu Items\n" + "[5] All Payments\n" + "[6] All Room Service Orders\n" + "[7] Room Stats by Occupancy Rate\n"
						+ "[8] Room Stats by Status\n" + "[9] Room Services Ordered by Guest\n"
						+ "[10] Room Services Ordered by Room");
				int option = sc_.nextInt();
				switch (option) {
				case 0:
					System.out.println("Back to previous menu...");
					break;
				case 1:
					System.out.println("|------|All Rooms|------|");
					DisplayList(room_manager_);
					break;
				case 2:
					System.out.println("|------|All Guests|------|");
					DisplayList(guest_manager_);
					break;
				case 3:
					System.out.println("|------|All Reservations|------|");
					DisplayList(reservation_manager_);
					break;
				case 4:
					System.out.println("|------|All Menu Items|------|");
					DisplayList(menu_item_manager_);
					break;
				case 5:
					System.out.println("|------|All Payments|------|");
					DisplayList(payment_manager_);
					break;
				case 6:
					System.out.println("|------|All Room Service Orders|------|");
					DisplayList(room_service_manager_);
					break;
				case 7:
					System.out.println("|------|Room Stats by Occupancy Rate|------|");
					EnumMap<RoomType, Pair<Integer, Vector<Integer>>> room_stats_type = room_manager_
							.GetRoomStatisticsByTypeOccupancyRate();
					for (RoomType type : RoomType.values()) {
						Pair<Integer, Vector<Integer>> stat = room_stats_type.get(type);
						if (stat != null) {
							int type_total_rooms = stat.a;
							Vector<Integer> vacant_rooms = stat.b;
							System.out.println("." + type.toString() + " Rooms: " + vacant_rooms.size() + " out of "
									+ type_total_rooms + " vacant");
							Vector<String> room_numbers = new Vector<>();
							for (int room_number : vacant_rooms) {
								room_numbers.add(String.format("%04d", room_number));
							}
							System.out.printf("    .Vacancies: %s\n", room_numbers.toString());
						}
					}
					break;
				case 8:
					System.out.println("|------|Room Stats by Status|------|");
					EnumMap<RoomStatus, Vector<Integer>> room_stats_status = room_manager_.GetRoomStatisticsByStatus();
					for (RoomStatus status : RoomStatus.values()) {
						if (status != null) {
							Vector<Integer> stats = room_stats_status.get(status);
							if(stats != null)
							{
								Vector<String> room_numbers = new Vector<>();
								for (int room_number : stats) {
									room_numbers.add(String.format("%04d", room_number));
								}
								System.out.printf("%s: %s\n", status.toString(), room_numbers.toString());
							}
						}
					}
					break;
				case 9:
					System.out.println("|------|Room Services Ordered by Guest|------|");
					System.out.println("Enter Guest Id: ");
					sc_.nextLine();
					String guest_id = GetUppercaseStringFromInput();
					List<RoomServiceOrder> guest_ordered_list = room_service_manager_.GetOrderedItemsByGuest(guest_id);
					if (guest_ordered_list.isEmpty()) {
						System.out.println("Guest did not order room service.");
					} else {
						System.out.println(guest_ordered_list.toString());
					}
					break;
				case 10:
					System.out.println("|------|Room Services Ordered by Room|------|");
					System.out.println("Enter Room Number: ");
					sc_.nextLine();
					int room_number = GetNonZeroIntFromInput();
					List<RoomServiceOrder> room_ordered_list = room_service_manager_.GetOrderedItemsByRoom(room_number);
					if (room_ordered_list.isEmpty()) {
						System.out.println("Room did not order room service.");
					} else {
						System.out.println(room_ordered_list.toString());
					}
					break;
				default:
					System.out.println("Unavailable, please try again:");
					continue;
				}
				break;
			default:
				System.out.println("Unavailable, please try again:");
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
		System.out.println("\r\n|-|Choose an option|-|");
		for (AppMenuItem app_menu_item : APP_MENU_LIST) {
			System.out.printf("[%d] %s\r\n", app_menu_item.GetValue(), app_menu_item.toString());
		}
		int option = 0;
		while (true) {
			try {
				option = sc_.nextInt();
				if ((option >= 0) && (option < APP_MENU_LIST.size())) {
					break;
				} else {
					System.out.println("Unavailable, please try again:");
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		return AppMenuItem.values()[option];
	}

	/**
	 * This method handles the i/o sub menu for Guests. For use after
	 * adding/searching a guest.
	 * 
	 * @param sub_menu_guest the guest obj to operate on
	 */
	private void PrintGuestSubMenu(Guest sub_menu_guest) {
		if (sub_menu_guest == null) {
			System.out.println("Guest is null, unavailable");
			return;
		}
		System.out.println(sub_menu_guest.toString());
		final String guest_id = sub_menu_guest.GetIdentity();
		final int guest_room_number = sub_menu_guest.GetRoomNum();
		System.out.println("What would you like to do with this guest?\n" + "[0] Go back\n" + "[1] Create Reservation\n"
				+ "[2] Check In\n" + "[3] Check Out\n" + "[4] Order Room Service\n" + "[5] Edit Details");
		int sub_guest_option = GetIntFromInput();

		switch (sub_guest_option) {
		case 0:
			System.out.println("Back to previous menu...");
			break;
		case 1:
			System.out.println("|------|Guest Create Reservation|------|");
			CreateNewReservation(guest_id);
			break;
		case 2:
			System.out.println("|------|Guest Check In|------|");
			Room guest_in_room = room_manager_.SearchList(sub_menu_guest);
			if (guest_in_room != null) {
				System.out.println("Guest already checked into room " + guest_in_room.GetRoomNumber());
				break;
			}
			Reservation check_in_reservation = reservation_manager_.SearchList(sub_menu_guest);
			int room_to_check_in = 0;
			if (check_in_reservation != null) {
				if (reservation_manager_.CheckIn(check_in_reservation)) {
					room_to_check_in = check_in_reservation.GetRoomNum();
					System.out.println("Guest reservation found!");
				}
			} else {
				room_to_check_in = PickRoom();
			}
			System.out.println("Room Number: " + room_to_check_in);
			guest_manager_.CheckIntoRoom(sub_menu_guest, room_to_check_in);
			room_manager_.CheckInGuest(sub_menu_guest, room_to_check_in);
			System.out.println("Guest checked in.");
			break;
		case 3:
			System.out.println("|------|Guest Check Out|------|");
			Room guest_out_room = room_manager_.SearchList(sub_menu_guest);
			if (guest_out_room == null) {
				System.out.println("Guest has yet to check into any room.");
				break;
			}
			Payment guest_payment;
			if ((sub_menu_guest.GetPaymentId().isEmpty()) || (sub_menu_guest.GetPaymentId().equals(Guest.EMPTY))) {
				System.out.println("Enter discount percentage (e.g. 10 -> 10%): ");
				int discounts = GetIntFromInput();
				System.out.println("Enter tax percentage for Guest (e.g. 10 -> 10%): ");
				int tax = GetIntFromInput();
				System.out.println("Enter days of stay: ");
				int days_of_stay = GetNonZeroIntFromInput();

				Room guest_room = room_manager_.SearchList(sub_menu_guest);
				List<RoomServiceOrder> room_service_orders = room_service_manager_
						.GetOrderedItemsByRoom(guest_room_number);
				Pair<String, Payment> bill = payment_manager_.GenerateAndPrintBill(guest_id, guest_room_number,
						days_of_stay, guest_room.GetRoomPrice(), discounts, tax, room_service_orders);
				System.out.println("Generated Bill\n" + bill.a.toString());
				guest_payment = bill.b;
			} else {
				guest_payment = payment_manager_.SearchList(sub_menu_guest.GetPaymentId());
			}
			if (guest_payment == null) {
				System.out.println("System error Guest unable to check out");
				break;
			}
			System.out.println("Payment item:\n" + guest_payment.toString());
			System.out.println("Making payment for this bill? (False[0]/True[1])");
			boolean make_payment = GetBooleanFromInput();
			if (make_payment) {
				guest_manager_.CheckOutOfRoom(sub_menu_guest, guest_payment.GetPaymentID().toString());
				room_manager_.CheckOutGuests(guest_payment.GetRoomNum());
				Reservation check_out_reservation = reservation_manager_.SearchList(sub_menu_guest);
				if (check_out_reservation != null) {
					reservation_manager_.CheckOut(check_out_reservation);
				}
			}
			break;
		case 4:
			System.out.println("|------|Guest Order Room Service|------|");
			if (sub_menu_guest.GetRoomNum() == Guest.EMPTY_ROOM) {
				System.out.println("Guest has yet to check into any room.");
			}
			List<MenuItem> ordered_item_list = PrintRoomServiceMenu();
			System.out.println("Any remarks to add to order?");
			sc_.nextLine();
			String remarks = GetUppercaseStringFromInput();
			RoomServiceOrder room_service_order = room_service_manager_.CreateNewRoomServiceOrder(guest_id,
					guest_room_number, ordered_item_list, remarks);
			if (room_service_order != null) {
				System.out.println("Room Service Order successfully created!");
			}
			break;
		case 5:
			System.out.println("|------|Guest Edit Details|------|");
			boolean continue_editing_guest = true;
			System.out.println("[0] Go back | [1] ID | [2] PaymentID | [3] RoomNum | [4] Name | [5] Credit Card No.\n"
					+ "[6] Billing Address | [7] Contact | [8] Country | [9] Gender | [10] Nationality | [11] Check In Date");
			while (continue_editing_guest) {
				System.out.println("Pick detail to edit:");
				int edit_opt = GetIntFromInput(0, 11);
				switch (edit_opt) {
				case 0:
					System.out.println("Back to previous menu...");
					continue_editing_guest = false;
					break;
				case 1:
					System.out.println("Enter new identity:");
					String edit_identity = GetUppercaseStringFromInput();
					sub_menu_guest.SetIdentity(edit_identity);
					System.out.println("Edit successful.");
					break;
				case 2:
					System.out.println("Enter new payment id:");
					String edit_payment_id = GetUppercaseStringFromInput();
					sub_menu_guest.SetPaymentId(edit_payment_id);
					System.out.println("Edit successful.");
					break;
				case 3:
					System.out.println("Enter new room number:");
					int edit_room_number = GetNonZeroIntFromInput();
					sub_menu_guest.SetRoomNum(edit_room_number);
					System.out.println("Edit successful.");
					break;
				case 4:
					System.out.println("Enter new name:");
					String edit_name = GetUppercaseStringFromInput();
					sub_menu_guest.SetName(edit_name);
					System.out.println("Edit successful.");
					break;
				case 5:
					System.out.println("Enter new credit card number:");
					String edit_cc_number = GetUppercaseStringFromInput();
					if (sub_menu_guest.SetCreditCardNumber(edit_cc_number)) {
						System.out.println("Edit successful.");
					} else {
						System.out.println("Edit failed.");
					}
					break;
				case 6:
					System.out.println("Enter new billing address:");
					String edit_billing = GetUppercaseStringFromInput();
					sub_menu_guest.SetBillingAddress(edit_billing);
					System.out.println("Edit successful.");
					break;
				case 7:
					System.out.println("Enter new contact number:");
					String edit_contact = GetUppercaseStringFromInput();
					sub_menu_guest.SetContact(edit_contact);
					System.out.println("Edit successful.");
					break;
				case 8:
					System.out.println("Enter new country:");
					String edit_country = GetUppercaseStringFromInput();
					sub_menu_guest.SetCountry(edit_country);
					System.out.println("Edit successful.");
					break;
				case 9:
					System.out.println("Enter new gender (e.g. Female[0]/Male[1]/Other[2]):");
					Gender edit_gender = GetEnumFromInput(Gender.values());
					sub_menu_guest.SetGender(edit_gender);
					System.out.println("Edit successful.");
					break;
				case 10:
					System.out.println("Enter new Nationality:");
					String edit_nationality = sc_.nextLine();
					sub_menu_guest.SetNationality(edit_nationality);
					System.out.println("Edit successful.");
					break;
				case 11:
					System.out.println("Enter new check in date (e.g. 2022-04-14 16:51:31):");
					String edit_check_in_date = sc_.nextLine();
					sub_menu_guest.SetCheckInDate(edit_check_in_date);
					System.out.println("Edit successful.");
					break;
				default:
					System.out.println("Unavailable, please try again:");
					break;
				}
			}
			System.out.println("Edited Guest:\n" + sub_menu_guest.toString());
			break;
		default:
			System.out.println("Unavailable, please try again:");
			break;
		}
	}

	/**
	 * Prints room service menu, handling the i/o of selecting menu items. After
	 * completing order, returns a list of selected items to be processed.
	 * 
	 * @return a list of menu items selected
	 */
	private List<MenuItem> PrintRoomServiceMenu() {
		List<MenuItem> picked_items_list = new ArrayList<>();
		List<MenuItem> menu_list = menu_item_manager_.GetList();
		int menu_list_size = menu_list.size();

		System.out.println("\r\n|--------|Choose an option|--------|");
		for (int i = 0; i < menu_list_size; i++) {
			MenuItem menu_item = menu_list.get(i);
			System.out.printf("[%d] %s\r\n", i, menu_item.toString());
		}
		System.out.printf("[%d] Complete Order\r\n", menu_list_size);

		int option = -1;
		while (option != menu_list_size) {
			while (true) {
				try {
					option = sc_.nextInt();
					if ((option >= 0) && (option <= menu_list_size)) {
						if (option != menu_list_size) {
							picked_items_list.add(menu_list.get(option));
							System.out.println("Selected " + menu_list.get(option).toString());
						}
						break;
					} else {
						System.out.println("Unavailable, please try again:");
					}
				} catch (Exception e) {
					sc_.nextLine();
					System.out.println("Unavailable, please try again:");
				}
			}
		}
		System.out.println("List of ordered items:\n" + picked_items_list.toString());

		return picked_items_list;
	}

	/**
	 * Handles the input and output for creating new guest. Returned guest object
	 * can be applied to PrintGuestSubMenu(Guest sub_menu_guest).
	 * 
	 * @return Guest object if created successfully, else returns null.
	 */
	private Guest CreateNewGuest() {
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
		identity = GetUppercaseStringFromInput();
		System.out.println("Enter Name (e.g. John Smith):");
		name = GetUppercaseStringFromInput();
		System.out.println("Enter Credit Card Number (e.g. 4605100120021234):");
		while (true) {
			credit_card_number = GetUppercaseStringFromInput();
			if (guest_manager_.VerifyCreditCardNumber(credit_card_number)) {
				break;
			} else {
				System.out.println("Invalid credit card number, please try again:");
			}
		}
		System.out.println("Enter Address (e.g. 50 Nanyang Ave, S639798):");
		address = GetUppercaseStringFromInput();
		System.out.println("Enter Contact (e.g. +6590001000):");
		contact = GetUppercaseStringFromInput();
		System.out.println("Enter Country (e.g. Singapore):");
		country = GetUppercaseStringFromInput();
		System.out.println("Enter Gender (e.g. Female[0]/Male[1]/Other[2]):");
		gender = GetEnumFromInput(Gender.values());
		System.out.println("Enter Nationality (e.g. Singaporean):");
		nationality = GetUppercaseStringFromInput();
		Guest new_guest = guest_manager_.CreateNewGuest(identity, name, credit_card_number, address, contact, country,
				gender, nationality);
		if (new_guest == null) {
			System.out.println("Guest ID already exists, did not create.");
		} else {
			System.out.printf("Guest %s successfully created!\r\n", name);
		}
		return new_guest;
	}

	/**
	 * Handles the input and output for creating new room.
	 */
	private void CreateNewRoom() {
		int room_number;
		RoomType room_type;
		float room_price;
		BedSize bed_size;
		boolean wifi_enabled;
		boolean with_view;
		boolean with_smoking;
		RoomStatus status;
		sc_.nextLine();
		System.out.println("Enter Room Number:");
		room_number = GetNonZeroIntFromInput();
		System.out.println("Enter Room Type (Single[0]/Standard[1]/VIP[2]/Suite[3]/Deluxe[4]):");
		room_type = GetEnumFromInput(RoomType.values());
		System.out.println("Enter price of room per night (e.g 100.00):");
		room_price = GetNonZeroFloatFromInput();
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
		Room new_room = room_manager_.CreateNewRoom(room_number, room_type, room_price, bed_size, wifi_enabled,
				with_view, with_smoking, status);
		if (new_room == null) {
			System.out.println("Room ID already exists, did not create.");
		} else {
			System.out.printf("Room %d successfully created!\r\n", room_number);
		}
	}

	/**
	 * Handles i/o for picking a vacant room to check in.
	 * 
	 * @return room number to be further processed (e.g. check in)
	 */
	private int PickRoom() {
		int picked_room_number;
		System.out.println("Pick a room number:");
		EnumMap<RoomType, Pair<Integer, Vector<Integer>>> room_stats = room_manager_
				.GetRoomStatisticsByTypeOccupancyRate();
		Vector<Integer> all_vacant_rooms = new Vector<>();
		for (RoomType type : RoomType.values()) {
			Pair<Integer, Vector<Integer>> stat = room_stats.get(type);
			int key_type_total_rooms = stat.a;
			Vector<Integer> vacant_rooms = stat.b;
			all_vacant_rooms.addAll(vacant_rooms);
			System.out.println("." + type.toString() + " Rooms: " + vacant_rooms.size() + " out of "
					+ key_type_total_rooms + " vacant");
			System.out.println(".Vacant Rooms: " + vacant_rooms.toString());
		}
		if (all_vacant_rooms.isEmpty()) {
			System.out.println("No vacant rooms.");
			return 0;
		}
		System.out.println("Enter Room Num (0 to exit):");
		while (true) {
			try {
				picked_room_number = sc_.nextInt();
				if (picked_room_number == 0) {
					break;
				} else {
					boolean valid_room = all_vacant_rooms.contains(picked_room_number);
					if (valid_room) {
						sc_.nextLine();
						break;
					}
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		System.out.println("Selected room " + picked_room_number);
		return picked_room_number;
	}

	/**
	 * Handles the input and output for creating new reservation.
	 * 
	 * @param guest_id the guest making this reservation.
	 */
	private void CreateNewReservation(String guest_id) {
		LocalDateTime check_in_date;
		LocalDateTime check_out_date;
		int adult_num;
		int children_num;
		ReservationStatus status;
		int room_num;
		System.out.println("Enter Check In Date (e.g. 2022-04-15):");
		sc_.nextLine();
		check_in_date = GetDatetimeStringFromDateInput("yyyy-MM-dd");
		System.out.println("Enter Check Out Date (e.g. 2022-04-15):");
		while (true) {
			check_out_date = GetDatetimeStringFromDateInput("yyyy-MM-dd");
			if (ChronoUnit.DAYS.between(check_in_date, check_out_date) < 1) {
				System.out.println("Please key in a date more than a day difference from Check In Date: ");
			} else {
				break;
			}
		}
		System.out.println("Enter Number of Adults:");
		adult_num = GetIntFromInput(1, 5);
		System.out.println("Enter Number of Children:");
		children_num = GetIntFromInput(0, 5);
		room_num = PickRoom();
		if (room_num == 0) {
			status = ReservationStatus.Waitlist;
			System.out.println("Reservation status set to waitlist.");
		} else {
			System.out.println("Enter Reservation Status (Confirmed[0]/Waitlist[1]):");
			int status_int = GetIntFromInput(0, 1);
			if (status_int == 0) {
				status = ReservationStatus.Confirmed;
			} else {
				status = ReservationStatus.Waitlist;
			}
		}
		Room rsvp_room = room_manager_.SearchList(room_num);
		if (rsvp_room.GetStatus() == RoomStatus.Vacant) {
			Reservation new_rsvp = reservation_manager_.CreateNewReservation(guest_id, check_in_date.toString(),
				check_out_date.toString(), adult_num, children_num, status, room_num);
			if (new_rsvp == null) {
				System.out.println("Reservation code already exists, did not create.");
			} else {
				System.out.printf("Reservation successfully created! Details:\n%s\n", new_rsvp.toString());
			}
			rsvp_room.SetStatus(RoomStatus.Reserved);
			System.out.printf("Room Details:\n%s\n\n", rsvp_room.toString());
		} else {
			System.out.println("Room unavailable, did not create reservation.\n");
		}
	}

	/**
	 * Function to get a formatted local date time for a date only input.
	 * 
	 * @param date_format format used for the date input
	 * @return LocalDateTime a formatted local date time object
	 */
	private LocalDateTime GetDatetimeStringFromDateInput(String date_format) {
		DateTimeFormatter localdate_format = DateTimeFormatter.ofPattern(date_format);
		LocalDate input_date;
		LocalDateTime return_localdatetime;
		String value = "";
		while (true) {
			try {
				value = sc_.nextLine();
				input_date = LocalDate.parse(value, localdate_format);
				return_localdatetime = input_date.atTime(DEFAULT_CHECK_IN_HOUR, 0, 0);
				break;
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}

		return return_localdatetime;
	}

	/**
	 * Function to get string from user input and handles exceptions.
	 * 
	 * @return String user input
	 */
	private String GetStringFromInput() {
		String value = "";
		while (true) {
			try {
				value += sc_.nextLine();
				break;
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		return value;
	}

	/**
	 * Function to get UPPERCASE string from user input and handles exceptions. Used
	 * for case sensitive columns such as search fields.
	 * 
	 * @return String user input
	 */
	private String GetUppercaseStringFromInput() {
		String value = "";
		while (true) {
			try {
				value += sc_.nextLine().toUpperCase();
				break;
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		return value;
	}

	/**
	 * Function to get 0 inclusive integer from user input and handles exception.
	 * 
	 * @return int user input
	 */
	private int GetIntFromInput() {
		int value = 0;
		while (true) {
			try {
				value = sc_.nextInt();
				break;
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		return value;
	}

	/**
	 * Function to get ranged integer from user input and handles exception.
	 * 
	 * @param inclusive_min minimum value for input that is included
	 * @param inclusive_max maximum value for input that is included
	 * @return int user input
	 */
	private int GetIntFromInput(int inclusive_min, int inclusive_max) {
		int value = 0;
		while (true) {
			try {
				value = sc_.nextInt();
				if (value >= inclusive_min && value <= inclusive_max) {
					sc_.nextLine();
					break;
				} else {
					System.out.println("Min " + inclusive_min + ", Max " + inclusive_max);
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		return value;
	}

	/**
	 * Function to get non zero integer from user input and handles exception.
	 * 
	 * @return int user input
	 */
	private int GetNonZeroIntFromInput() {
		int value = 0;
		while (true) {
			try {
				value = sc_.nextInt();
				if (value == 0) {
					System.out.println("Unavailable, please try again:");
				} else {
					sc_.nextLine();
					break;
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		return value;
	}

	/**
	 * Function to get 0 inclusive float from user input and handles exception.
	 * 
	 * @return float user input
	 */
	private float GetFloatFromInput() {
		float value = 0.0f;
		while (true) {
			try {
				value = sc_.nextFloat();
				break;
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		return value;
	}

	/**
	 * Function to get non zero float from user input and handles exception.
	 * 
	 * @return float user input
	 */
	private float GetNonZeroFloatFromInput() {
		float value = 0.0f;
		while (true) {
			try {
				value = sc_.nextFloat();
				if (value == 0.0f) {
					System.out.println("Unavailable, please try again:");
				} else {
					sc_.nextLine();
					break;
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
		return value;
	}

	/**
	 * Present user with a list of choices from an enumset.
	 *
	 * @param <T>      the enumset
	 * @param opt_list an array of the enum options
	 * @return the enum selected by user input
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
					System.out.println("Unavailable, please try again:");
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
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
					System.out.println("Unavailable, please try again:");
				}
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
	}

	/**
	 * Gets input of search text from user and calls the SearchList method of
	 * Supermanager.
	 * <p>
	 * Example usage: GuestManager guest_manager = new GuestManager();
	 * SearchList(guest_manager);
	 *
	 * @param <T>				Type which the manager is managing
	 * @param sm  			A generic type manager which has implemented Supermanager
	 * @param search_term 	Term in which this function will search for, e.g. name
	 * @return the object matching the search text
	 */
	private <T> T SearchManagerList(Supermanager<T> sm, String search_term) {
		List<T> sm_list = sm.GetList();
		if (sm_list.isEmpty()) {
			System.out.println("List is empty.");
			return null;
		}
		String search_text = "";
		System.out.println("Enter "+ search_term +" to search:");
		sc_.nextLine();
		while (true) {
			try {
				search_text += sc_.nextLine().toUpperCase();
				return sm.SearchList(search_text);
			} catch (Exception e) {
				sc_.nextLine();
				System.out.println("Unavailable, please try again:");
			}
		}
	}

	/**
	 * Calls the GetList method of Supermanager and then access each object and
	 * calls toString.
	 * <p>
	 * Example usage: GuestManager guest_manager = new GuestManager();
	 * DisplayList(guest_manager);
	 *
	 * @param <T> Type which the manager is managing
	 * @param sm  A generic type manager which has implemented Supermanager
	 */
	private <T> void DisplayList(Supermanager<T> sm) {
		List<T> sm_list = sm.GetList();
		if (sm_list.isEmpty()) {
			System.out.println("List is empty.");
			return;
		}
		for (T t : sm_list) {
			System.out.println(t.toString() + "\n");
		}
	}

	/**
	 * Calls SaveDB for all managers.
	 */
	private void Quit() {
		System.out.print("Saving databases...");
		sc_.close();
		reservation_manager_.SaveDB();
		guest_manager_.SaveDB();
		payment_manager_.SaveDB();
		room_service_manager_.SaveDB();
		room_manager_.SaveDB();
		menu_item_manager_.SaveDB();
		System.out.println("Completed.");
	}
}