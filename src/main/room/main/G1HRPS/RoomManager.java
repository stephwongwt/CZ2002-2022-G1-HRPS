package main.G1HRPS;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * A manager class that stores the Room Object as elements in a list. Search
 * methods
 * parses through the list of rooms, comparing instance variables of each room
 * object with the arguments passed in.
 * 
 * @author LiangHee
 *
 */
public class RoomManager extends DatabaseHandler implements Supermanager<Room> {
    private List<Room> room_list_;
    private final String db_filename = "room_db.txt";

    public RoomManager() {
        this.room_list_ = new ArrayList<Room>();
    }

    /**
     * Takes in user input to create a new Room and add to room_list_.
     * 
     * @param room_number
     * @param room_type
     * @param room_price
     * @param bed_size
     * @param wifi_enabled
     * @param w_view
     * @param w_smoking
     * @param status
     */
    public Room CreateNewRoom(int room_number, RoomType room_type, float room_price, BedSize bed_size,
            boolean wifi_enabled, boolean w_view, boolean w_smoking, RoomStatus status) {
        Room new_room = new Room(room_number, room_type, room_price, bed_size, wifi_enabled, w_view, w_smoking, status);
        AddToList(new_room);
        return new_room;
    }

    /**
     * Assign a guest to a room, guest is stored as an element in a guest list
     * 
     * @param guest       This is the guest to be added
     * @param room_number This is the room number of the room that the guest will be
     *                    added to
     */
    public void CheckInGuest(Guest guest, int room_number) {
        Room room = SearchList(room_number);
        room.AddGuestToRoom(guest);
    }

    /**
     * removes all guests elements from the guest list of a room
     * 
     * @param room_number This is the room number to remove guest elements from.
     */
    public void CheckOutGuests(int room_number) {
        for (Room room : room_list_) {
            if (room.GetRoomNumber() == room_number) {
                room.ClearGuestList();
                room.SetStatus(RoomStatus.Vacant);
            }
        }
    }

    /**
     * Sets a room status to vacant, occupied, reserved, maintenance
     * This method is primarily to set room status to reserved and maintenance,
     * as rooms are auto set to vacant and occupied in check in.
     * 
     * @param room_number   The room to update status
     * @param status        The status to be changed into
     */
    public void SetRoomStatus(int room_number, RoomStatus status) {
        for (Room room : room_list_) {
            if (room.GetRoomNumber() == room_number) {
                room.SetStatus(status);
            }
        }
    }

    /**
     * Get occupancy of rooms based on room types
     * 
     * Sample:
     * EnumMap<RoomType.Single, HashMap<15, int[221, 222]>>
     * Standard: Number: 2 out of 15 vacant
     * Available Rooms: 221, 222
     * 
     * @return an enum map of room types, with an internal hashmap of total_rooms and int array of vacant room numbers.
     */
    public EnumMap<RoomType, HashMap<String, Vector<Integer>>> GetRoomStatisticsByTypeOccupancyRate() {
        int single_total = 0;
        int single_vacant = 0;
        Vector<Integer> single_vacant_list = new Vector<>();
        int standard_total = 0;
        int standard_vacant = 0;
        Vector<Integer> standard_vacant_list = new Vector<>();
        int deluxe_total = 0;
        int deluxe_vacant = 0;
        Vector<Integer> deluxe_vacant_list = new Vector<>();
        int suite_total = 0;
        int suite_vacant = 0;
        Vector<Integer> suite_vacant_list = new Vector<>();
        int vip_total = 0;
        int vip_vacant = 0;
        Vector<Integer> vip_vacant_list = new Vector<>();

        EnumMap<RoomType, HashMap<String, Vector<Integer>>> room_stats = new EnumMap<>(RoomType.class);

        for (Room room : room_list_) {
            RoomType type = room.GetRoomType();
            switch (type) {
                case Single:
                    single_total++;
                    if (room.GetStatus() == RoomStatus.Vacant) {
                        single_vacant_list.add(room.GetRoomNumber());
                        single_vacant++;
                    }
                    break;
                case Standard:
                    standard_total++;
                    if (room.GetStatus() == RoomStatus.Vacant) {
                        standard_vacant_list.add(room.GetRoomNumber());
                        standard_vacant++;
                    }
                    break;
                case Vip:
                    vip_total++;
                    if (room.GetStatus() == RoomStatus.Vacant) {
                        vip_vacant_list.add(room.GetRoomNumber());
                        vip_vacant++;
                    }
                    break;
                case Suite:
                    suite_total++;
                    if (room.GetStatus() == RoomStatus.Vacant) {
                        suite_vacant_list.add(room.GetRoomNumber());
                        suite_vacant++;
                    }
                    break;
                case Deluxe:
                    deluxe_total++;
                    if (room.GetStatus() == RoomStatus.Vacant) {
                        deluxe_vacant_list.add(room.GetRoomNumber());
                        deluxe_vacant++;
                    }
                    break;
                default:
                    break;
            }
        }
        HashMap<String, Vector<Integer>> single_stats = new HashMap<>();
        single_stats.put(String.valueOf(single_total), single_vacant_list);
        room_stats.put(RoomType.Single, single_stats);
        HashMap<String, Vector<Integer>> standard_stats = new HashMap<>();
        standard_stats.put(String.valueOf(standard_total), standard_vacant_list);
        room_stats.put(RoomType.Single, standard_stats);
        HashMap<String, Vector<Integer>> vip_stats = new HashMap<>();
        vip_stats.put(String.valueOf(vip_total), vip_vacant_list);
        room_stats.put(RoomType.Single, vip_stats);
        HashMap<String, Vector<Integer>> suite_stats = new HashMap<>();
        suite_stats.put(String.valueOf(suite_total), suite_vacant_list);
        room_stats.put(RoomType.Single, suite_stats);
        HashMap<String, Vector<Integer>> deluxe_stats = new HashMap<>();
        deluxe_stats.put(String.valueOf(deluxe_total), deluxe_vacant_list);
        room_stats.put(RoomType.Single, deluxe_stats);

        return room_stats;
    }

    /**
     * All room numbers, organized by status.
     * e.g. Vacant : 02-35,02-36,02-38
     * Occupied:02-39,02-61
     */
    public void GetRoomStatisticsByStatus() {
        String vacantRoomNumbers;
        String occupiedRoomNumbers;
        String reservedRoomNumbers;
        String maintenanceRoomNumbers;
        for (var i = 0; i < room_list_.size(); i++) {
            RoomStatus status = room_list_.get(i).GetStatus();
            switch (status) {
                case Vacant:
                    vacantRoomNumbers = vacantRoomNumbers + Integer.toString(room_list_.get(i).GetRoomNumber()) + ",";
                    break;
                case Occupied:
                    occupiedRoomNumbers = occupiedRoomNumbers + Integer.toString(room_list_.get(i).GetRoomNumber())
                            + ",";
                    break;
                case Reserved:
                    reservedRoomNumbers = reservedRoomNumbers + Integer.toString(room_list_.get(i).GetRoomNumber())
                            + ",";
                    break;
                case Maintenance:
                    maintenanceRoomNumbers = maintenanceRoomNumbers
                            + Integer.toString(room_list_.get(i).GetRoomNumber()) + ",";
                    break;
                default:
                    break;
            }
        }
        System.out.println("Vacant: ");
        System.out.println("       Rooms:" + vacantRoomNumbers.substring(1, vacantRoomNumbers.length() - 1));
        System.out.println("Occupied:");
        System.out.println("       Rooms:" + occupiedRoomNumbers.substring(1, vacantRoomNumbers.length() - 1));
        System.out.println("Reserved: ");
        System.out.println("       Rooms:" + reservedRoomNumbers.substring(1, reservedRoomNumbers.length() - 1));
        System.out.println("Maintenance: ");
        System.out.println("       Rooms:" + maintenanceRoomNumbers.substring(1, maintenanceRoomNumbers.length() - 1));
    }

    /**
     * Tokenize each line in the database into an object.
     */
    public void InitializeDB() {
        // read String from text file
        ArrayList<String> dbArray = (ArrayList) read(db_filename);
        ArrayList<Room> dataList = new ArrayList<Room>();
        for (String st : dbArray) {
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer
            int room_number = Integer.parseInt(star.nextToken().trim());
            RoomType room_type = RoomType.valueOf(star.nextToken().trim());
            float room_price = Float.parseFloat(star.nextToken().trim());
            BedSize bed_size = BedSize.valueOf(star.nextToken().trim());
            boolean wifi_enabled = Boolean.parseBoolean(star.nextToken().trim());
            boolean w_view = Boolean.parseBoolean(star.nextToken().trim());
            boolean w_smoking = Boolean.parseBoolean(star.nextToken().trim());
            RoomStatus status = RoomStatus.valueOf(star.nextToken().trim());
            Room obj = new Room(room_number, room_type, room_price, bed_size, wifi_enabled, w_view, w_smoking, status);
            dataList.add(obj);
        }
        this.room_list_ = dataList;
    }

    /**
     * Data list is turned into formatted String and written the file named
     * db_filename.
     */
    public void SaveDB() {
        List<String> roomData = new ArrayList<String>();
        for (Room room : room_list_) {
            StringBuilder st = new StringBuilder();
            st.append(room.GetRoomNumber());
            st.append(SEPARATOR);
            st.append(room.GetRoomType());
            st.append(SEPARATOR);
            st.append(room.GetRoomPrice());
            st.append(SEPARATOR);
            st.append(room.GetBedSize());
            st.append(SEPARATOR);
            st.append(room.GetWifi());
            st.append(SEPARATOR);
            st.append(room.GetView());
            st.append(SEPARATOR);
            st.append(room.GetSmoking());
            st.append(SEPARATOR);
            st.append(room.GetStatus());
            st.append(SEPARATOR);
            roomData.add(st.toString());
        }
        try {
            write(db_filename, roomData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in a class object and list to add the object into.
     * Will reject adding a room with room number that already exists.
     * 
     * @param room This is the room object to be added
     * @return true if success / false if failed
     */
    @Override
    public boolean AddToList(Room room) {
        boolean success = false;
        Room found = SearchList(room.GetRoomNumber());
        if (found == null) {
            try {
                success = this.room_list_.add(room);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Removes a matching room from the room list.
     * 
     * @param room Payment object to be removed from the list of Payment objects.
     * @return true if success / false if failed
     */
    @Override
    public boolean RemoveFromList(Room room) {
        boolean success = false;
        try {
            success = room_list_.remove(room);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return success;
    }
    
    /**
     * Returns an ArrayList List<Room>, room_list
     * 
     * @return room_list_ ArrayList that is returned.
     */
    @Override
    public List<Room> GetList() {
        return this.room_list_;
    }

    /**
     * Search internal list of reservations for one with id matching the search_text.
     * 
     * @param search_text room number to be searched
     * @return Room if found, else null
     */
    @Override
    public Room SearchList(String search_text) {
        for (Room room : room_list_) {
            if (room.GetRoomNumber() == Integer.parseInt(search_text)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Looks for the room which a particular guest stays in.
     * 
     * @param search_guest Find room this guest stays in
     * @return Room if found, else null
     */
    public Room SearchList(Guest search_guest) {
        for (Room room : room_list_) {
            for (Guest guest_in_room : room.GetGuestList()) {
                if (guest_in_room.GetName().equals(search_guest.GetName())) {
                    return room;
                }
            }
        }
        return null;
    }

    /**
     * Looks for room with provided room number
     * 
     * @param search_room_num room number in integer
     * @return Room if found, else null
     */
    public Room SearchList(int search_room_num) {
        for (Room room : room_list_) {
            if (room.GetRoomNumber() == search_room_num) {
                return room;
            }
        }
        return null;
    }
}