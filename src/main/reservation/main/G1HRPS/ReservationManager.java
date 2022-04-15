package main.G1HRPS;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.ArrayList;

public class ReservationManager extends DatabaseHandler implements Supermanager<Reservation> {
    private List<Reservation> reservation_list_;
    private final String DB_FILENAME = "reservation_db.txt";

    public ReservationManager() {
        reservation_list_ = new ArrayList<Reservation>();
    }

    public Reservation CreateNewReservation(String guest_id, String check_in_date, String check_out_date, int adult_num, int children_num, ReservationStatus status, int room_num) {
        UUID reservation_code = UniqueIdGenerator.Generate();
        Reservation rsvp = new Reservation(reservation_code, guest_id, check_in_date, check_out_date, adult_num, children_num, status, room_num);
        reservation_list_.add(rsvp);
        return rsvp;
    }

    public void InitializeDB() {
        ArrayList<String> dbArray = (ArrayList) read(DB_FILENAME);
        ArrayList<Reservation> dataList = new ArrayList<Reservation>();
        for(String st : dbArray){
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            UUID reservation_code = UUID.fromString(star.nextToken().trim());
            String guest_id = star.nextToken().trim();
            String check_in_date = star.nextToken().trim();
            String check_out_date = star.nextToken().trim();
            int adult_num = Integer.parseInt(star.nextToken().trim());
            int children_num = Integer.parseInt(star.nextToken().trim());
            ReservationStatus status = ReservationStatus.valueOf(star.nextToken().trim());
            int room_num = Integer.parseInt(star.nextToken().trim());
            Reservation obj = new Reservation(reservation_code, guest_id, check_in_date, check_out_date, adult_num, children_num, status, room_num);
            dataList.add(obj);
        }
        this.reservation_list_ = dataList;
    }

    public void SaveDB() {
        List<String> reservationData = new ArrayList<>();
        for (Reservation rsvp : reservation_list_) {
            StringBuilder st = new StringBuilder();
            st.append(rsvp.GetReservationCode().toString());
            st.append(SEPARATOR);
            st.append(rsvp.GetGuestId());
            st.append(SEPARATOR);
            st.append(rsvp.GetCheckInDate());
            st.append(SEPARATOR);
            st.append(rsvp.GetCheckOutDate());
            st.append(SEPARATOR);
            st.append(rsvp.GetAdultNum());
            st.append(SEPARATOR);
            st.append(rsvp.GetChildrenNum());
            st.append(SEPARATOR);
            st.append(rsvp.GetStatus().toString());
            st.append(SEPARATOR);
            st.append(rsvp.GetRoomNum());
            reservationData.add(st.toString());
        }
        try {
            write(DB_FILENAME, reservationData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check in reservation by changing status.
     * @param reservation obj to be updated
     * @return true if status updated, else false if not found
     */
    public boolean CheckIn(Reservation reservation) {
        Reservation rsvp = SearchList(reservation.GetReservationCode().toString());
        int rsvp_index = reservation_list_.indexOf(rsvp);
        if (rsvp != null) {
            rsvp.SetStatus(ReservationStatus.CheckedIn);
            reservation_list_.set(rsvp_index, rsvp);
            return true;
        }
        return false;
    }

    /**
     * Check out reservation by changing status.
     * @param reservation obj to be updated
     * @return true if status updated, else false if not found
     */
    public boolean CheckOut(Reservation reservation) {
        Reservation rsvp = SearchList(reservation.GetReservationCode().toString());
        int rsvp_index = reservation_list_.indexOf(rsvp);
        if (rsvp != null) {
            rsvp.SetStatus(ReservationStatus.CheckedOut);
            reservation_list_.set(rsvp_index, rsvp);
            return true;
        }
        return false;
    }

    /**
     * Takes in an class object and list to add the object into.
     * 
     * @param reservation The object to be added
     */
    @Override
    public boolean AddToList(Reservation reservation) {
        boolean success = false;
        Reservation found = SearchList(reservation.GetReservationCode().toString());
        if (found == null) {
            try {
                success = this.reservation_list_.add(reservation);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Takes in an class object and list to remove the object from the given list.
     * 
     * @param reservation The object to be removed
     */
    @Override
    public boolean RemoveFromList(Reservation reservation) {
        boolean success = false;
        try {
            success = this.reservation_list_.remove(reservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Returns an ArrayList List<Reservation>, room_list
     * 
     * @return reservation_list_ ArrayList that is returned.
     */
    @Override
    public List<Reservation> GetList() {
        return this.reservation_list_;
    }

    /**
     * Search internal list of reservations for one with id matching the search_text.
     * 
     * @param search_text UUID reservation code to be searched
     * @return Reservation if found, else null
     */
    @Override
    public Reservation SearchList(String search_text) {
        for (Reservation reservation : reservation_list_) {
            if (reservation.GetReservationCode().equals(UUID.fromString(search_text))) {
                return reservation;
            }
        }
        return null;
    }

    /**
     * Search internal list of reservations for one with id matching the search_text.
     * 
     * @param guest obj to be found
     * @return Reservation if found, else null
     */
    public Reservation SearchList(Guest guest) {
        for (Reservation reservation : reservation_list_) {
            if (reservation.GetGuestId().equals(guest.GetIdentity())) {
                return reservation;
            }
        }
        return null;
    }
}