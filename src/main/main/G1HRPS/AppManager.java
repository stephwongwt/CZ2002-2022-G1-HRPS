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
    public static final DateTimeFormatter datetime_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

        ///TODO: rooms need to be populated with guests again on init
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
                    Guest guest = SearchList(guest_manager_);
                    guest.toString();
                    final String guest_id = guest.GetIdentity();
                    final int guest_room_number = guest.GetRoomNum();

                    System.out.println("Which to display?\n" +
                            "[0] Go back\n" +
                            "[1] Check In\n" +
                            "[2] Check Out\n" +
                            "[3] Order Room Service\n" +
                            "[4] Edit Details");
                    System.out.println("What would you like to do with this Guest?");
                    int opt = 0;
                    while (true) {
                        try {
                            opt = sc_.nextInt();
                            if (opt != 0) {
                                sc_.nextLine();
                                break;
                            }
                        } catch (Exception e) {
                            sc_.nextLine();
                            System.out.println("Unavailable, please try again.");
                        }
                    }

                    switch (opt) {
                        case 0:
                            break;
                        case 1:
                        /// check in guest & room & 
                            System.out.println("|-----|Check In|-----|");
                            Reservation check_in_reservation = reservation_manager_.SearchList(guest);
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
                            guest_manager_.CheckIntoRoom(guest, room_to_check_in);
                            room_manager_.CheckInGuest(guest, room_to_check_in);
                            System.out.println("Guest checked in.");
                            break;
                        case 2:
                            System.out.println("|-----|Check Out|-----|");
                            System.out.println("Enter discount percentage (e.g. 10 -> 10%): ");
                            int discounts = GetIntFromInput();
                            System.out.println("Enter tax percentage for Guest (e.g. 10 -> 10%): ");
                            int tax = GetIntFromInput();

                            Room guest_room = room_manager_.SearchList(guest);
                            List<RoomServiceOrder> room_service_orders = room_service_manager_.GetOrderedItemsByRoom(guest_room_number);
                            Payment bill = payment_manager_.GenerateAndPrintBill(guest_id, guest_room_number, LocalDateTime.parse(guest.GetCheckInDate(), datetime_formatter), guest_room.GetRoomPrice(), discounts, tax, room_service_orders);
                            bill.toString();

                            System.out.println("Making payment for this bill? (False[0]/True[1])");
                            boolean make_payment = GetBooleanFromInput();
                            if (make_payment) {
                                guest_manager_.CheckOutOfRoom(guest, bill.GetPaymentID());
                                room_manager_.CheckOutGuests(bill.GetRoomNum());
                                Reservation check_out_reservation = reservation_manager_.SearchList(guest);
                                reservation_manager_.CheckOut(check_out_reservation);
                            }
                            break;
                        case 3:
                            System.out.println("|-----|Order Room Service|-----|");
                            List<MenuItem> ordered_item_list = PrintRoomServiceMenu();
                            System.out.println("Any remarks to add to order?");
                            String remarks = sc_.nextLine().toUpperCase();
                            RoomServiceOrder room_service_order = room_service_manager_.CreateNewRoomServiceOrder(guest_id, guest_room_number, ordered_item_list, remarks);
                            if (room_service_order != null) {
                                System.out.println("Room Service Order successfully created!");
                            }
                            break;
                        case 4:
                            System.out.println("|-----|Edit Guest Details|-----|");
                            boolean continue_editing = true;
                            while (continue_editing) {
                                System.out.println("[0] ID | [1] PaymentID | [2] RoomNum | [3] Name | [4] Credit Card No. | [5] Billing Address");
                                System.out.println("[0] ID | [1] PaymentID | [2] RoomNum | [3] Name | [4] Credit Card No. | [5] Billing Address");

                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case SearchRoom:
                    Room search_room = SearchList(room_manager_);
                    /// complete room service order
                    // List<RoomServiceOrder> rso_list = room_service_manager_.GetOrderedItemsByRoom(search_room.GetRoomNum());
                    // for (RoomServiceOrder rso : rso_list) {
                    //     rso.toString();
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
            System.out.printf("[%d] %s\r\n", app_menu_item.GetValue(), app_menu_item.toString());
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
                        System.out.println("Unavailable, please select another option.");
                    }
                } catch (Exception e) {
                    sc_.nextLine();
                    System.out.println("Unavailable, please select another option.");
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
        credit_card_number = sc_.nextLine().toUpperCase();
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
                System.out.println("Unavailable, please try again.");
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
                System.out.println("Unavailable, please try again.");
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
                System.out.println("Unavailable, please try again.");
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
                System.out.println("Unavailable, please try again.");
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
                System.out.println("Unavailable, please try again.");
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