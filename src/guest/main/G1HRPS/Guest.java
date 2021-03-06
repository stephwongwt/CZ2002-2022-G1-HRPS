package main.G1HRPS;

/**
 * Guest class for storing the info of each of the guests of our hotel.
 * 
 * @author Kim Sang Hyeon
 *
 */
public class Guest {
	/**
	 * Global minimum credit card number length.
	 */
	public static final int MIN_CC_NUMLEN = 8;

	/**
	 * Global maximum credit card number length.
	 */
	public static final int MAX_CC_NUMLEN = 19;

	/**
	 * Global string to be used for filling database when object is empty.
	 */
	public static final String EMPTY = "EMPTY";

	/**
	 * Global definition of an unassigned room number.
	 */
	public static final int EMPTY_ROOM = 0;

	/**
	 * Stores identification number of guest, either national identity or driving
	 * license information.
	 */
	private String identity_;

	/**
	 * Generated after guest wants to check out, linked to a payment object.
	 */
	private String payment_id_;

	/**
	 * Room the guest has checked into
	 */
	private int room_num_;

	/**
	 * Name of guest.
	 */
	private String name_;

	/**
	 * Credit card number of guest.
	 */
	private String credit_card_number_;

	/**
	 * Billing address of guest.
	 */
	private String billing_address_;

	/**
	 * Contact number of guest.
	 */
	private String contact_;

	/**
	 * Origin country of guest.
	 */
	private String country_;

	/**
	 * Gender of guest.
	 */
	private Gender gender_;

	/**
	 * Nationality of guest.
	 */
	private String nationality_;

	/**
	 * Generated when guest has checked in, used for calculating days of stay
	 * automatically.
	 */
	private String check_in_date_;

	/**
	 * Create guest object, used when initializing from DB.
	 * 
	 * @param identity           Either national identity or driving license
	 *                           information
	 * @param payment_id         Filled when bill has been generated
	 * @param room_num           Filled when guest has checked in
	 * @param name               Name of Guest
	 * @param credit_card_number Payment details
	 * @param billing_address    Address to send bill to
	 * @param contact            Mobile contact number
	 * @param country            Guest's country of origin
	 * @param gender             Gender of guest
	 * @param nationality        Nationality of guest
	 * @param check_in_date      Filled when guest has checked in, used for
	 *                           calculating days of stay automatically.
	 */
	public Guest(String identity, String payment_id, int room_num, String name, String credit_card_number,
			String billing_address, String contact, String country, Gender gender, String nationality,
			String check_in_date) {
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
	 * Create guest object, used when creating new guests. Check in date set to empty.
	 * 
	 * @param identity           Either national identity or driving license
	 *                           information
	 * @param name               Name of Guest
	 * @param credit_card_number Payment details
	 * @param billing_address    Address to send bill to
	 * @param contact            Mobile contact number
	 * @param country            Guest's country of origin
	 * @param gender             Gender of guest
	 * @param nationality        Nationality of guest
	 */
	public Guest(String identity, String name, String credit_card_number, String billing_address, String contact,
			String country, Gender gender, String nationality) {
		this.identity_ = identity;
		this.payment_id_ = EMPTY;
		this.room_num_ = EMPTY_ROOM;
		this.name_ = name;
		this.SetCreditCardNumber(credit_card_number);
		this.billing_address_ = billing_address;
		this.contact_ = contact;
		this.country_ = country;
		this.gender_ = gender;
		this.nationality_ = nationality;
		this.check_in_date_ = EMPTY;
	}

	/**
	 * Gets the check in date of this guest.
	 * 
	 * @return String containing check in date timestamp
	 */
	public String GetCheckInDate() {
		return this.check_in_date_;
	}

	/**
	 * Sets the check in date of this guest.
	 * 
	 * @param check_in_date String of check in date time
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
	 * @param name String to be set at guest's name.
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
	 * Sets new credit card number to this guest. Also cleans up the credit card
	 * number, removing spaces and verifying length.
	 *
	 * @param credit_card_number String to be set at guest's card number.
	 * @return true if the entered credit card number is valid. false if the entered
	 *         credit card number is invalid.
	 */
	public boolean SetCreditCardNumber(String credit_card_number) {
		String noSpace_CreditCardNum = credit_card_number.replaceAll("\\s+", "");
		if (noSpace_CreditCardNum.matches("[0-9]+") && (noSpace_CreditCardNum.length() >= MIN_CC_NUMLEN)
				&& (noSpace_CreditCardNum.length() <= MAX_CC_NUMLEN)) {
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
	 * @param billing_address New value to be set.
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
	 * @param contact New value to be set.
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
	 * @param country New value to be set.
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
	 * @param gender New value (Female/Male/Other) to be set.
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
	 * @param nationality New value to be set.
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
	 * @param room_num New value to be set.
	 */
	public void SetRoomNum(int room_num) {
		this.room_num_ = room_num;
	}

	/**
	 * Gets the payment ID of this guest.
	 *
	 * @return String with payment ID or EMPTY if unassigned for this guest.
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
				"| ID: %s | Name: %s | Room #: %s | Payment ID: %s |\n" + "| Credit Card #: %s |\n" + "| Billing Address: %s |\n"
						+ "| Contact: %s | Country: %s | Gender: %s | Nationality: %s |\n" + "| Check In Date: %s |\n",
				identity_, name_, room_num, payment_id, credit_card_number_, billing_address_, contact_, country_,
				gender_.toString(), nationality_, check_in_date);
		return output;
	}
}
