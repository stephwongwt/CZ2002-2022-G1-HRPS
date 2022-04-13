package main.G1HRPS;

import java.util.ArrayList;
import Guest.Guest;

/**
 * This is a Room class object, containing details on a hotel room
 * 
 * @author LiangHee
 *
 */
public class Room {
    private int room_num_;
    private RoomType room_type_;
    private float room_price_;
    private BedSize bedsize_;
    private boolean wifi_enabled_;
    private boolean w_view_;
    private boolean w_smoking_;
    private RoomStatus status_;
    private ArrayList<Guest> guest_list_ = new ArrayList<Guest>();

    /**
     * Creates a Room object to store information about the room.
     * 
     * @param room_type   The room types are Single, Standard, VIP, Suite, Deluxe.
     * @param price       This is price of the room
     * @param room_number This is room number of the room
     * @param bedSize     Bed size of bed in room
     * @param wifiEnabled Whether a room has wifi or not
     * @param withView    Whether a room has view or not
     * @param Smoking     Whether a room allows smoking or not
     * @param status      Possible room statuses are vacant, occupied, reserved,
     *                    maintenance
     */
    public Room(RoomType room_type, float price, int room_number, BedSize bedSize, boolean wifiEnabled,
            boolean withView, boolean Smoking, RoomStatus status) {
        room_num_ = room_number;
        room_type_ = room_type;
        room_price_ = price;
        bedsize_ = bedSize;
        wifi_enabled_ = wifiEnabled;
        w_view_ = withView;
        w_smoking_ = Smoking;
        status_ = status;
    }

    /**
     * Prints out whether a room is available or not
     */
    public void CheckRoomAvailability() {
        if (status_ == RoomStatus.Vacant)
            System.out.println("Room is available");
        else
            System.out.println("Room is not available");
    }

    /**
     * The codes below are to allow outside classes to access/modify private data in
     * this class.
     * Get Room number
     * 
     * @return Returns room number of this room
     */
    public int GetRoomNumber() {
        return room_num_;
    }

    /**
     * Adds a guest object to the guest list of this room
     * 
     * @param g guest object to be added to this room
     */
    public void AddGuest(Guest g) {
        guest_list_.add(g);
    }

    /**
     * Removes all elements in guest list to become an empty list
     */
    public void CheckOutGuest(String guestName) {
        for(var j=0; j<getGuestList().size();j++) {
            if(guest_list_.get(j).GetName==guestName)){
                guest_list_.remove(j);
            }
        }
    }

    public void ClearGuests() {
        guest_list_.clear();
    }

    /**
     * Set a new room status
     * 
     * @param s Status to be changed into
     */
    public void SetStatus(RoomStatus s) {
        status_ = s;
    }

    /**
     * Get room status
     * 
     * @return Returns room status of this room
     */
    public RoomStatus GetStatus() {
        return status_;
    }

    /**
     * Get room type
     * 
     * @return Returns room type of this room
     */
    public RoomType GetRoomType() {
        return room_type_;
    }

    /**
     * Get guest list
     * 
     * @return Returns guest list of this room
     */
    public ArrayList<Guest> GetGuestList() {
        return guest_list_;
    }

    /**
     * Prints out general information of the room
     */
    @Override
    public String toString(){
        String output = "room number: "+room_num_ + 
                        "\nroom price: "+room_price_+
                        "\nbedsize: "+bedsize_+
                        "\nwifi enabled: "+wifi_enabled_+
                        "\nwith view: "+w_view_+
                        "\nwith smoking: "+w_smoking_+
                        "\nroom status: "+status_+
                        "\nguest staying: "+guest_list_
        return output;                    
    }
}