package main.G1HRPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

public class GuestManager extends DatabaseHandler implements Supermanager<Guest> {
    private List<Guest> guest_list_;
    private final String DB_FILENAME = "guest_db.txt";

    /**
     * Creates a Guest Manager.
     */
    public GuestManager() {
        guest_list_ = new ArrayList<Guest>();
    }

    /**
     * Creates a new guest and adds it to guest list.
     * 
     * @param identity
     * @param payment_id
     * @param room_num
     * @param name
     * @param credit_card_number
     * @param billing_address
     * @param contact
     * @param country
     * @param gender
     * @param nationality
     */
    public Guest CreateNewGuest(String identity, String name, String credit_card_number, String billing_address, String contact, String country, Gender gender, String nationality) {
        Guest new_guest = new Guest(identity, name, credit_card_number, billing_address, contact, country, gender, nationality);
        Guest found_exisiting_guest = SearchList(identity);
        if (found_exisiting_guest == null) {
            AddToList(new_guest);
            return new_guest;
        } else { // cannot create new guest due to existing guest
            return null;
        }
    }
    
    public boolean VerifyCreditCardNumber(String credit_card_number) {
        String noSpace_CreditCardNum = credit_card_number.replaceAll("\\s+", "");
        if (noSpace_CreditCardNum.matches("[0-9]+") &&
        (noSpace_CreditCardNum.length() >= Guest.MIN_CC_NUMLEN) &&
        (noSpace_CreditCardNum.length() <= Guest.MAX_CC_NUMLEN)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a new guest object into guest list.
     * 
     * @param guest Guest object to be added
     * @return true if success / false if failed
     */
    public boolean AddToList(Guest guest) {
        boolean success = false;
        try {
            success = guest_list_.add(guest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Removes a given guest from the guest list.
     * 
     * @param guest Guest object to be removed
     * @return true if success / false if failed
     */
    @Override
    public boolean RemoveFromList(Guest guest) {
        boolean success = false;
        try {
            success = this.guest_list_.remove(guest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Uses guest ID as a key to search for corresponding guest
     * in the list.
     * 
     * @param search_text
     * @return Guest object with matching guest ID.
     */
    @Override
    public Guest SearchList(String guest_id) {
        for (Guest guest : guest_list_) {
            if (guest_id.equals(guest.GetIdentity())) {
                return guest;
            }
        }
        return null;
    }

    /**
     * Gets a list of current guests.
     * 
     * @return List of guests objects.
     */
    public List<Guest> GetList() {
        return guest_list_;
    }

    /**
     * Checks-in a guest by setting room number.
     * 
     * @param guest
     * @param room_num
     */
    public void CheckIntoRoom(Guest guest, int room_num) {
        guest.SetRoomNum(room_num);
    }

    /**
     * Checks-out a guest by setting
     * <ol>
     * <li>Payment ID
     * <li>Billing address
     * <li>Credit card number
     * </ol>
     * 
     * @param guest
     * @param room_num
     * @param billing_address
     * @param credit_card_number
     */
    public void CheckOutOfRoom(Guest guest, UUID payment_id) {
        guest.SetPaymentId(payment_id);
    }

    /**
     * Read guest data as list of String objects and create
     * a list of Guest class objects.
     * Each String object is parsed to create each of the guests.
     */
    public void InitializeDB() {
        ArrayList<String> dbArray = (ArrayList) read(DB_FILENAME);
        ArrayList<Guest> dataList = new ArrayList<>();
        for (String st : dbArray) {
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            String identity = star.nextToken().trim();
            UUID payment_id = UUID.fromString(star.nextToken().trim());
            int room_num = Integer.parseInt(star.nextToken().trim());
            String name = star.nextToken().trim();
            String cc_number = star.nextToken().trim();
            String address = star.nextToken().trim();
            String contact = star.nextToken().trim();
            String country = star.nextToken().trim();
            Gender gender = Gender.valueOf(star.nextToken().trim());
            String nationality = star.nextToken().trim();
            Guest obj = new Guest(identity, name, cc_number, address, contact, country, gender, nationality);
            obj.SetPaymentId(payment_id);
            obj.SetRoomNum(room_num);
            dataList.add(obj);
        }
        this.guest_list_ = dataList;
    }

    /**
     * Save each guest object from guest list by converting the
     * attributes to a String object and saving to each line of the
     * save file.
     * 
     */
    public void SaveDB() {
        List<String> guestData = new ArrayList<>();
        for (Guest guest : guest_list_) {
            StringBuilder st = new StringBuilder();
            st.append(guest.GetIdentity());
            st.append(SEPARATOR);
            st.append(guest.GetPaymentId());
            st.append(SEPARATOR);
            st.append(guest.GetRoomNum());
            st.append(SEPARATOR);
            st.append(guest.GetName());
            st.append(SEPARATOR);
            st.append(guest.GetCcNumber());
            st.append(SEPARATOR);
            st.append(guest.GetBillingAddress());
            st.append(SEPARATOR);
            st.append(guest.GetContact());
            st.append(SEPARATOR);
            st.append(guest.GetCountry());
            st.append(SEPARATOR);
            st.append(guest.GetGender().toString());
            st.append(SEPARATOR);
            st.append(guest.GetNationality());
            guestData.add(st.toString());
        }
        try {
            write(DB_FILENAME, guestData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
