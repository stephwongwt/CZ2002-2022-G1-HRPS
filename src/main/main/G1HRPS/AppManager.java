package main.G1HRPS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Manager that handles running the app
 */
public class AppManager {
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Scanner sc_;
    private ReservationManager reservation_manager_;
    private GuestManager guest_manager_;
    private PaymentManager payment_manager_;
    private RoomServiceManager room_service_manager_;
    private RoomManager room_manager_;
    private MenuItemManager menu_item_manager_;
    private final List<AppMenuItem> APP_MENU_LIST;
    private final int DEFAULT_CHECK_IN_HOUR = 15;

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
                    System.out.println("|---|Quit|---|");
                    Quit();
                    System.out.println("Exiting, Goodbye.");
                    running = false;
                    break;
                case AddGuest:
                    System.out.println("|---|Add Guest|---|");
                    CreateNewGuest();
                    break;
                case AddRoom:
                    System.out.println("|---|Add Room|---|");
                    CreateNewRoom();
                    break;
                case SearchGuest:
                    System.out.println("|---|Search Guest|---|");
                    Guest search_guest = SearchManagerList(guest_manager_);
                    search_guest.toString();
                    final String guest_id = search_guest.GetIdentity();
                    final int guest_room_number = search_guest.GetRoomNum();

                    System.out.println("What would you like to do with this guest?\n" +
                                    "[0] Go back\n" +
                                    "[1] Check In\n" +
                                    "[2] Check Out\n" +
                                    "[3] Order Room Service\n" +
                                    "[4] Edit Details");
                    int sub_guest_option = GetIntFromInput(0, 4);

                    switch (sub_guest_option) {
                        case 0:
                            System.out.println("Back to previous menu...");
                            break;
                        case 1:
                            System.out.println("|------|Guest Check In|------|");
                            Reservation check_in_reservation = reservation_manager_.SearchList(search_guest);
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
                            guest_manager_.CheckIntoRoom(search_guest, room_to_check_in);
                            room_manager_.CheckInGuest(search_guest, room_to_check_in);
                            System.out.println("Guest checked in.");
                            break;
                        case 2:
                            System.out.println("|------|Guest Check Out|------|");
                            System.out.println("Enter discount percentage (e.g. 10 -> 10%): ");
                            int discounts = GetIntFromInput();
                            System.out.println("Enter tax percentage for Guest (e.g. 10 -> 10%): ");
                            int tax = GetIntFromInput();

                            Room guest_room = room_manager_.SearchList(search_guest);
                            List<RoomServiceOrder> room_service_orders = room_service_manager_.GetOrderedItemsByRoom(guest_room_number);
                            Pair<String, Payment> bill = payment_manager_.GenerateAndPrintBill(guest_id, guest_room_number, LocalDateTime.parse(search_guest.GetCheckInDate(), datetime_formatter), guest_room.GetRoomPrice(), discounts, tax, room_service_orders);
                            System.out.println(bill.a);

                            System.out.println("Making payment for this bill? (False[0]/True[1])");
                            boolean make_payment = GetBooleanFromInput();
                            if (make_payment) {
                                guest_manager_.CheckOutOfRoom(search_guest, bill.b.GetPaymentID());
                                room_manager_.CheckOutGuests(bill.b.GetRoomNum());
                                Reservation check_out_reservation = reservation_manager_.SearchList(search_guest);
                                reservation_manager_.CheckOut(check_out_reservation);
                            }
                            break;
                        case 3:
                            System.out.println("|------|Guest Order Room Service|------|");
                            List<MenuItem> ordered_item_list = PrintRoomServiceMenu();
                            System.out.println("Any remarks to add to order?");
                            String remarks = sc_.nextLine().toUpperCase();
                            RoomServiceOrder room_service_order = room_service_manager_.CreateNewRoomServiceOrder(guest_id, guest_room_number, ordered_item_list, remarks);
                            if (room_service_order != null) {
                                System.out.println("Room Service Order successfully created!");
                            }
                            break;
                        case 4:
                            System.out.println("|------|Guest Edit Details|------|");
                            boolean continue_editing_guest = true;
                            System.out.println("[0] Go back | [1] ID | [2] PaymentID | [3] RoomNum | [4] Name | [5] Credit Card No.");
                            System.out.println("[6] Billing Address | [7] Contact | [8] Country | [9] Gender | [10] Nationality | [11] Check In Date");
                            while (continue_editing_guest) {
                                int edit_opt = GetIntFromInput(0, 11);
                                switch (edit_opt) {
                                    case 0:
                                        System.out.println("Back to previous menu...");
                                        continue_editing_guest = false;
                                        break;
                                    case 1:
                                        System.out.println("Enter new identity:");
                                        String edit_identity = sc_.nextLine();
                                        search_guest.SetIdentity(edit_identity);
                                        break;
                                    case 2:
                                        System.out.println("Enter new payment id:");
                                        String edit_payment_id = sc_.nextLine();
                                        search_guest.SetIdentity(edit_payment_id);
                                        break;
                                    case 3:
                                        System.out.println("Enter new room number:");
                                        int edit_room_number = GetNonZeroIntFromInput();
                                        search_guest.SetRoomNum(edit_room_number);
                                        break;
                                    case 4:
                                        System.out.println("Enter new name:");
                                        String edit_name = sc_.nextLine();
                                        search_guest.SetName(edit_name);
                                        break;
                                    case 5:
                                        System.out.println("Enter new credit card number:");
                                        String edit_cc_number = sc_.nextLine();
                                        search_guest.SetCreditCardNumber(edit_cc_number);
                                        break;
                                    case 6:
                                        System.out.println("Enter new billing address:");
                                        String edit_billing = sc_.nextLine();
                                        search_guest.SetBillingAddress(edit_billing);
                                        break;
                                    case 7:
                                        System.out.println("Enter new contact number:");
                                        String edit_contact = sc_.nextLine();
                                        search_guest.SetContact(edit_contact);
                                        break;
                                    case 8:
                                        System.out.println("Enter new country:");
                                        String edit_country = sc_.nextLine();
                                        search_guest.SetCountry(edit_country);
                                        break;
                                        case 9:
                                        System.out.println("Enter new gender (e.g. Female[0]/Male[1]/Other[2]):");
                                        Gender edit_gender = GetEnumFromInput(Gender.values());
                                        search_guest.SetGender(edit_gender);
                                        break;
                                        case 10:
                                        System.out.println("Enter new Nationality:");
                                        String edit_nationality = sc_.nextLine();
                                        search_guest.SetNationality(edit_nationality);
                                        break;
                                        case 11:
                                        System.out.println("Enter new check in date (e.g. 2022-04-14 16:51:31):");
                                        String edit_check_in_date = sc_.nextLine();
                                        search_guest.SetCheckInDate(edit_check_in_date);
                                        break;
                                    default:
                                        System.out.println("Unavailable, please try again:");
                                        break;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case SearchRoom:
                    System.out.println("|---|Search Room|---|");
                    Room search_room = SearchManagerList(room_manager_);
                    search_room.toString();
                    System.out.println("What would you like to do with this room?\n" +
                                    "[0] Go back\n" +
                                    "[1] Complete Room Service Order\n" +
                                    "[2] Edit Details\n");
                    int sub_room_option = GetIntFromInput(0, 2);
                    switch (sub_room_option) {
                        case 0:
                            System.out.println("Back to previous menu...");
                            break;
                        case 1:
                            System.out.println("|------|Complete Room Service Order|------|");
                            List<RoomServiceOrder> order_list = room_service_manager_.GetOrderedItemsByRoom(search_room.GetRoomNumber());
                            if (!order_list.isEmpty()) {
                                System.out.println("Room services ordered:");
                                for (int i = 0; i < order_list.size(); i++) {
                                    RoomServiceOrder order = order_list.get(i);
                                    System.out.printf("[%d] Status (%s), Items: %\r\n", i, order.GetStatus().toString(), order.GetOrderedItemList().toString());
                                }
                                System.out.println("Select Order to complete:");
                                int order_index_to_complete = GetIntFromInput(0, order_list.size());
                                RoomServiceOrder order_to_complete = order_list.get(order_index_to_complete);
                                if (order_to_complete.GetStatus() != OrderStatus.Delivered) {
                                    order_to_complete.SetStatus(OrderStatus.Delivered);
                                } else {
                                    System.out.println("Order already completed.");
                                }
                            } else {
                                System.out.println("No orders was placed.");
                            }
                            break;
                        case 2:
                            System.out.println("|------|Edit Room Details|------|");
                            boolean continue_editing_room = true;
                            System.out.println("[0] Go back | [1] Room Number | [2] Room Type | [3] Room Price");
                            System.out.println("[4] Bed Size | [5] Wifi | [6] View | [7] Smoking | [8] Status");
                            while (continue_editing_room) {
                                int edit_opt = GetIntFromInput(0, 11);
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
                                        } else {
                                            System.out.println("Room number already exists.");
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Enter new Room Type (Single[0]/Standard[1]/VIP[2]/Suite[3]/Deluxe[4]):");
                                        RoomType room_type = GetEnumFromInput(RoomType.values());
                                        search_room.SetRoomType(room_type);
                                        break;
                                    case 3:
                                        System.out.println("Enter new price of room per night (e.g 100.00):");
                                        float room_price = GetNonZeroFloatFromInput();
                                        search_room.SetRoomPrice(room_price);
                                        break;
                                    case 4:
                                        System.out.println("Enter new Bed Size (Single[0]/SuperSingle[1]/Double[2]/Queen[3]/King[4]):");
                                        BedSize bed_size = GetEnumFromInput(BedSize.values());
                                        search_room.SetBedSize(bed_size);
                                        break;
                                    case 5:
                                        System.out.println("Enter new WiFi (False[0]/True[1]):");
                                        boolean wifi_enabled = GetBooleanFromInput();
                                        search_room.SetWifi(wifi_enabled);
                                        break;
                                    case 6:
                                        System.out.println("Enter new View (False[0]/True[1]):");
                                        boolean with_view = GetBooleanFromInput();
                                        search_room.SetView(with_view);
                                        break;
                                    case 7:
                                        System.out.println("Enter new Smoking (False[0]/True[1]):");
                                        boolean with_smoking = GetBooleanFromInput();
                                        search_room.SetSmoking(with_smoking);
                                        break;
                                    case 8:
                                        System.out.println("Enter new Room Status (Vacant[0]/Occupied[1]/Reserved[2]/Maintenance[3]):");
                                        RoomStatus status = GetEnumFromInput(RoomStatus.values());
                                        search_room.SetStatus(status);
                                        break;
                                    default:
                                        System.out.println("Unavailable, please try again:");
                                        break;
                                }
                            }
                            break;
                        default:
                            System.out.println("Unavailable, please try again:");
                            break;
                    }
                    break;
                case SearchReservations:
                    System.out.println("|---|Search Reservations|---|");
                    Reservation search_rsvp = SearchManagerList(reservation_manager_);
                    search_rsvp.toString();
                    System.out.println("What would you like to do with this reservation?\n" +
                                    "[0] Go back\n" +
                                    "[1] Check In\n" +
                                    "[2] Delete\n");
                    int sub_rsvp_option = GetIntFromInput(0, 2);
                    switch (sub_rsvp_option) {
                        case 0:
                            System.out.println("Back to previous menu...");
                            break;
                        case 1:
                            System.out.println("|------|Check In|------|");
                            reservation_manager_.CheckIn(search_rsvp);
                            Guest rsvp_guest = guest_manager_.SearchList(search_rsvp.GetGuestId());
                            int rsvp_room_num = search_rsvp.GetRoomNum();
                            guest_manager_.CheckIntoRoom(rsvp_guest, rsvp_room_num);
                            room_manager_.CheckInGuest(rsvp_guest, rsvp_room_num);
                            System.out.printf("Guest %s checked into room number %d. \r\n", rsvp_guest.GetName(), rsvp_room_num);
                            break;
                        default:
                            System.out.println("|------|Delete|------|");
                            boolean del_rsvp_success = reservation_manager_.RemoveFromList(search_rsvp);
                            if (del_rsvp_success) {
                                System.out.println("Successfully deleted reservation!");
                            } else {
                                System.out.println("Failed to delete reservation.");
                            }
                            break;
                    }
                    break;
                case Display:
                    System.out.println("|---|Display|---|");
                    sc_.nextLine();
                    System.out.println("Which to display?\n" +
                            "[0] Go back\n" +
                            "[1] All Rooms\n" +
                            "[2] All Guests\n" +
                            "[3] All Reservations");
                    int option = sc_.nextInt();
                    switch (option) {
                        case 0:
                            System.out.println("Back to previous menu...");
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
        System.out.println("\r\n|--------|Choose an option|--------|");
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
                            System.out.println(picked_items_list.toString());
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
        System.out.println(picked_items_list.toString());

        return picked_items_list;
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
        while (true) {
            credit_card_number = sc_.nextLine().toUpperCase();
            if (guest_manager_.VerifyCreditCardNumber(credit_card_number)) {
                break;
            } else {
                sc_.nextLine();
                System.out.println("Invalid credit card number, please try again:");
            }
        }

        System.out.println("Enter Address (e.g. 50 Nanyang Ave, S639798):");
        address = sc_.nextLine().toUpperCase();
        System.out.println("Enter Contact (e.g. +6590001000):");
        contact = sc_.nextLine().toUpperCase();
        System.out.println("Enter Country (e.g. Singapore):");
        country = sc_.nextLine().toUpperCase();
        System.out.println("Enter Gender (e.g. Female[0]/Male[1]/Other[2]):");
        gender = GetEnumFromInput(Gender.values());
        System.out.println("Enter Nationality (e.g. Singaporean):");
        nationality = sc_.nextLine().toUpperCase();
        Guest new_guest = guest_manager_.CreateNewGuest(identity, name, credit_card_number, address, contact, country, gender, nationality);
        if (new_guest == null) {
            System.out.println("Guest ID already exists, did not create.");
        } else {
            System.out.printf("Guest %s successfully created!", name);
        }
    }

    /**
     * Handles the input and output for creating new room
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
        Room new_room = room_manager_.CreateNewRoom(room_number, room_type, room_price, bed_size, wifi_enabled, with_view, with_smoking, status);
        if (new_room == null) {
            System.out.println("Room ID already exists, did not create.");
        } else {
            System.out.printf("Room %d successfully created!", room_number);
        }
    }

    private int PickRoom() {
        int picked_room_number;
        System.out.println("Pick a room number:");
        EnumMap<RoomType, HashMap<String, Vector<Integer>>> room_stats = room_manager_.GetRoomStatisticsByTypeOccupancyRate();
        Vector<Integer> all_available_rooms = new Vector<>();
        for (RoomType type : RoomType.values()) {
            HashMap<String, Vector<Integer>> stat = room_stats.get(type);
            String key_type_total_rooms = (String) stat.keySet().toArray()[0];
            Vector<Integer> available_rooms = stat.get(key_type_total_rooms);
            all_available_rooms.addAll(available_rooms);
            System.out.println("|--- " + type.toString() + " Rooms: " + available_rooms.size() + " out of " + key_type_total_rooms + " vacant");
            System.out.println("|--- Available Rooms: " + available_rooms);
        }
        while (true) {
            try {
                picked_room_number = sc_.nextInt();
                boolean valid_room = all_available_rooms.contains(picked_room_number);
                if (valid_room) {
                    sc_.nextLine();
                    break;
                }
            } catch (Exception e) {
                sc_.nextLine();
                System.out.println("Unavailable, please try again:");
            }
        }
        return picked_room_number;
    }

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

    private int GetIntFromInput(int inclusive_min, int inclusive_max) {
        int value = 0;
        while (true) {
            try {
                value = sc_.nextInt();
                if (value >= inclusive_min && value <= inclusive_max) {
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

    private int GetNonZeroIntFromInput() {
        int value = 0;
        while (true) {
            try {
                value = sc_.nextInt();
                if (value != 0) {
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

    private float GetNonZeroFloatFromInput() {
        float value = 0.0f;
        while (true) {
            try {
                value = sc_.nextFloat();
                if (value != 0.0f) {
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
     * Example usage:
     * GuestManager guest_manager = new GuestManager();
     * SearchList(guest_manager);
     *
     * @param <T> type which the manager is managing
     * @param sm  a generic type manager which has implemented Supermanager
     * @return the object matching the search text
     */
    private <T> T SearchManagerList(Supermanager<T> sm) {
        String search_text = "";
        System.out.println("Enter search text:");
        sc_.nextLine();
        while (true) {
            try {
                search_text += sc_.nextLine();
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