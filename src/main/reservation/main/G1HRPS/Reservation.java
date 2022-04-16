package main.G1HRPS;

import java.util.UUID;

public class Reservation {

    private final UUID reservation_code_;
    private String guest_id_;
    public String check_in_date_;
    public String check_out_date_;
    public int adult_num_;
    public int children_num_;
    public ReservationStatus status_;
    private int room_num_;

    /**
     * 
     * @param reservation_code
     * @param guest_id
     * @param check_in_date
     * @param check_out_date
     * @param adult_num
     * @param children_num
     * @param status
     * @param room_num
     */
    public Reservation(UUID reservation_code, String guest_id, String check_in_date, String check_out_date, int adult_num, int children_num, ReservationStatus status, int room_num) {
        this.reservation_code_ = reservation_code;
        this.guest_id_ = guest_id;
        this.check_in_date_ = check_in_date;
        this.check_out_date_ = check_out_date;
        this.adult_num_ = adult_num;
        this.children_num_ = children_num;
        this.status_ = status;
        this.room_num_ = room_num;
    }

    public Reservation(String guest_id, String check_in_date, String check_out_date, int adult_num, int children_num, ReservationStatus status, int room_num) {
        this.reservation_code_ = UniqueIdGenerator.Generate();
        this.guest_id_ = guest_id;
        this.check_in_date_ = check_in_date;
        this.check_out_date_ = check_out_date;
        this.adult_num_ = adult_num;
        this.children_num_ = children_num;
        this.status_ = status;
        this.room_num_ = room_num;
    }

    public void SetGuestId(String guest_id) {
        this.guest_id_ = guest_id;
    }

    public void SetCheckInDate(String check_in_date) {
        this.check_in_date_ = check_in_date;
    }

    public void SetCheckOutDate(String check_out_date) {
        this.check_out_date_ = check_out_date;
    }

    public void SetAdultNum(int adult_num) {
        this.adult_num_ = adult_num;
    }

    public void SetChildrenNum(int children_num) {
        this.children_num_ = children_num;
    }

    public void SetStatus(ReservationStatus status) {
        this.status_ = status;
    }

    public void GetRoomNum(int room_num) {
        this.room_num_ = room_num;
    }

    public UUID GetReservationCode() {
        return reservation_code_;
    }

    public String GetGuestId() {
        return guest_id_;
    }

    public String GetCheckInDate() {
        return check_in_date_;
    }

    public String GetCheckOutDate() {
        return check_out_date_;
    }

    public int GetAdultNum() {
        return adult_num_;
    }

    public int GetChildrenNum() {
        return children_num_;
    }

    public ReservationStatus GetStatus() {
        return status_;
    }

    public int GetRoomNum() {
        return room_num_;
    }

    @Override
    public String toString() {
        String output = "|Code: " + this.reservation_code_ +
                        "|Guest Id: " + this.guest_id_ +
                        "|\n|Check In Date: " + this.check_in_date_ +
                        "|Check Out Date: " + this.check_out_date_ +
                        "|\n|Room Number: " + this.room_num_ +
                        "|\n|Number of Guests: " + (this.adult_num_ + this.children_num_) +
                        "|\n|Status:" + this.status_ + "|";
        return output;
    }
}