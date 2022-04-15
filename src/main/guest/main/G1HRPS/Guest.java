package main.G1HRPS;

import java.time.LocalDateTime;

public class Guest {
    public static final int MIN_CC_NUMLEN = 8;
    public static final int MAX_CC_NUMLEN = 19;
    public static final String EMPTY = "EMPTY";
    private static final int EMPTY_ROOM = 0;
    private String identity_;
    private String payment_id_;
    private int room_num_;
    private String name_;
    private String credit_card_number_;
    private String billing_address_;
    private String contact_;
    private String country_;
    private Gender gender_;
    private String nationality_;
    private String check_in_date_;

    /**
     * Create guest obj, used when initializing from DB.
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
     * @param check_in_date
     */
    public Guest(String identity, String payment_id, int room_num, String name, String credit_card_number, String billing_address, String contact, String country, Gender gender, String nationality, String check_in_date) {
        identity_ = identity;
        payment_id_ = payment_id;
        room_num_ = room_num;
        name_ = name;
        credit_card_number_ = credit_card_number;
        billing_address_ = billing_address;
        contact_ = contact;
        country_ = country;
        gender_ = gender;
        nationality_ = nationality;
        check_in_date_ = check_in_date;
    }

    /**
     * Create guest obj, used when creating new guests.
     * 
     * @param identity
     * @param name
     * @param credit_card_number
     * @param billing_address
     * @param contact
     * @param country
     * @param gender
     * @param nationality
     */
    public Guest(String identity, String name, String credit_card_number, String billing_address, String contact, String country, Gender gender, String nationality) {
        this.identity_ = identity;
        this.payment_id_ = "";
        this.room_num_ = 0;
        this.name_ = name;
        this.SetCreditCardNumber(credit_card_number);
        this.billing_address_ = billing_address;
        this.contact_ = contact;
        this.country_ = country;
        this.gender_ = gender;
        this.nationality_ = nationality;
        LocalDateTime dateTime = LocalDateTime.now();
        this.check_in_date_ = AppManager.DATETIME_FORMATTER.format(dateTime);
    }

    /**
     * Gets the check in date of this guest.
     * 
     * @return Check in date timestamp in string
     */
    public String GetCheckInDate() {
        return this.check_in_date_;
    }

    /**
     * Sets the check in date of this guest.
     * 
     * @return Check in date timestamp in string
     */
    public void SetCheckInDate(String check_in_date) {
        this.check_in_date_ = check_in_date;
    }

    /**
     * Gets the identification of this guest.
     *
     * @return String containing identity of this guest.
     */
    public String GetIdentity() {
        return this.identity_;
    }

    /**
     * Sets new identity information of this guest.
     *
     * @param identity String containing guest's identification.
     */
    public void SetIdentity(String identity) {
        this.identity_ = identity;
    }

    /**
     * Gets the name of this guest.
     *
     * @return String with name of this guest.
     */
    public String GetName() {
        return this.name_;
    }

    /**
     * Sets new name to this guest.
     *
     * @param name
     */
    public void SetName(String name) {
        this.name_ = name;
    }

    /**
     * Gets a credit card number of this guest.
     *
     * @return String containing credit card number.
     */
    public String GetCcNumber() {
        return this.credit_card_number_;
    }

    /**
     * Sets new credit card number to this guest.
     *
     * @param credit_card_number
     * @return true if the entered credit card number is valid.
     *         false if the entered credit card number is invalid.
     */
    public boolean SetCreditCardNumber(String credit_card_number) {
        String noSpace_CreditCardNum = credit_card_number.replaceAll("\\s+", "");
        if (noSpace_CreditCardNum.matches("[0-9]+") &&
        (noSpace_CreditCardNum.length() >= MIN_CC_NUMLEN) &&
        (noSpace_CreditCardNum.length() <= MAX_CC_NUMLEN)) {
            this.credit_card_number_ = credit_card_number;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the billing address of this guest.
     *
     * @return String containing billing address of this guest.
     */
    public String GetBillingAddress() {
        return this.billing_address_;
    }

    /**
     * Sets new billing address of this guest.
     *
     * @param billing_address
     */
    public void SetBillingAddress(String billing_address) {
        this.billing_address_ = billing_address;
    }

    /**
     * Gets the contact information of this guest.
     *
     * @return String with contact information of this guest
     */
    public String GetContact() {
        return this.contact_;
    }

    /**
     * Sets new contact information of this guest.
     *
     * @param contact
     */
    public void SetContact(String contact) {
        this.contact_ = contact;
    }

    /**
     * Gets country of residence of this guest.
     *
     * @return String containing country of residence of this guest.
     */
    public String GetCountry() {
        return this.country_;
    }

    /**
     * Set new country of residence of this guest
     *
     * @param country
     */
    public void SetCountry(String country) {
        this.country_ = country;
    }

    /**
     * Gets the gender of this guest.
     *
     * @return Gender enum of this guest (Female/Male/Other)
     */
    public Gender GetGender() {
        return this.gender_;
    }

    /**
     * Sets new gender information of this guest.
     *
     * @param gender Female/Male/Other
     */
    public void SetGender(Gender gender) {
        this.gender_ = gender;
    }

    /**
     * Gets the nationality of this guest.
     *
     * @return String containing nationality of this guest.
     */
    public String GetNationality() {
        return this.nationality_;
    }

    /**
     * Sets new nationality of this guest.
     *
     * @param nationality
     */
    public void SetNationality(String nationality) {
        this.nationality_ = nationality;
    }

    /**
     * Gets room number of room this guest is staying at.
     *
     * @return An int containing room number of this guest.
     */
    public int GetRoomNum() {
        return this.room_num_;
    }

    /**
     * Sets a new room number to this guest.
     *
     * @param room_num
     */
    public void SetRoomNum(int room_num) {
        this.room_num_ = room_num;
    }

    /**
     * Gets the payment ID of this guest.
     *
     * @return UUID object with payment ID of this guest.
     */
    public String GetPaymentId() {
        return this.payment_id_;
    }

    /**
     * Sets new payment ID to this guest.
     *
     * @param payment_id a generated UUID in string format
     */
    public void SetPaymentId(String payment_id) {
        this.payment_id_ = payment_id;
    }

    /**
     * Used to print guest information.
     */
    @Override
    public String toString() {
        String room_num = (this.room_num_ == EMPTY_ROOM) ? EMPTY : String.valueOf(this.room_num_);
        String payment_id = (this.payment_id_.isEmpty()) ? EMPTY : this.payment_id_.toString();
        String check_in_date = (this.check_in_date_.isEmpty()) ? EMPTY : this.check_in_date_.toString();
        String output = String.format(
                "|ID: %s|Name: %s|Room #: %s|Payment ID: %s|\n" +
                "|Credit Card #: %s|\n" +
                "|Billing Address: %s|\n" +
                "|Contact: %s|Country: %s|Gender: %s|Nationality: %s|\n" +
                "|Check In Date: %s|",
                identity_, name_, room_num, payment_id,
                credit_card_number_,
                billing_address_,
                contact_, country_, gender_.toString(), nationality_,
                check_in_date);
        return output;
    }
}
