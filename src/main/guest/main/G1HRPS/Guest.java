package main.G1HRPS;

import java.util.UUID;

public class Guest {

	private static final int MIN_CC_NUMLEN = 8;
	private static final int MAX_CC_NUMLEN = 19;
	private static final String EMPTY = "EMPTY";
	private static final UUID EMPTY_UUID = null;
	private static final int EMPTY_ROOM = 0;

	private String identity_;
	private UUID payment_id_;
	private int room_num_;
	private String name_;
	private String credit_card_number_;
	private String billing_address_;
	private String contact_;
	private String country_;
	private Gender gender_;
	private String nationality_;

	/**
	 * Creates a new guest object.
	 * 
	 * @param identity
	 * @param payment_id
	 * @param room_num
	 * @param name
	 * @param credit_card_number
	 * @param address Billing address
	 * @param contact
	 * @param country
	 * @param gender
	 * @param nationality
	 */

	public Guest(String identity, UUID payment_id, int room_num, String name, String credit_card_number, String address, String contact, String country, Gender gender, String nationality) {

		identity_ = identity;
		payment_id_ = payment_id;
		room_num_ = room_num;
		name_ = name;
		credit_card_number_ = credit_card_number;
		billing_address_ = address;
		contact_ = contact;
		country_ = country;
		gender_ = gender;
		nationality_ = nationality;

	}

	/**
	 * Gets the identification of this guest.
	 * 
	 * @return	String containing identity of this guest.
	 */

	public String GetIdentity() {

		return identity_;

	}

	/**
	 * Sets new identity information of this guest.
	 * 
	 * @param identity	String containing guest's identification.
	 */
	public void SetIdentity(String identity) {

		identity_ = identity;

	}

	/**
	 * Gets the name of this guest.
	 * 
	 * @return	String with name of this guest.
	 */

	public String GetName() {

		return name_;

	}

	/**
	 * Sets new name to this guest.
	 * 
	 * @param name
	 */
	public void SetName(String name) {

		name_ = name;

	}

	/**
	 * Gets a credit card number of this guest.
	 * 
	 * @return	String containing credit card number.
	 */

	public String GetCcNumber() {

		return credit_card_number_;

	}

	/**
	 * Sets new credit card number to this guest.
	 * 
	 * @param 	credit_card_number
	 * @return	<code>true</code>	if the entered credit card number is valid.
	 * 			</p>
	 * 			<code>false</code>	if the entered credit card number is invalid.
	 */
	public boolean SetCreditCardNumber(String credit_card_number) {
		String noSpace_CreditCardNum = credit_card_number.replaceAll("\\s+", "");

		if(noSpace_CreditCardNum.matches("[0-9]+") && (noSpace_CreditCardNum.length() >= MIN_CC_NUMLEN) && (noSpace_CreditCardNum.length() <= MAX_CC_NUMLEN)) {
			credit_card_number_ = credit_card_number;
			return true;
		}
		else{
			System.out.println("Invalid credit card number");
			return false;
		}
	}

	/**
	 * Gets the billing address of this guest.
	 * 
	 * @return	String containing billing address of this guest.
	 */

	public String GetBillingAddress() {

		return billing_address_;

	}

	/**
	 * Sets new billing address of this guest.
	 * 
	 * @param billing_address
	 */
	public void SetBillingAddress(String billing_address) {

		billing_address_ = billing_address;

	}

	/**
	 * Gets the contact information of this guest.
	 * 
	 * @return	String with contact information of this guest
	 */

	public String GetContact() {

		return contact_;

	}

	/**
	 * Sets new contact information of this guest.
	 * 
	 * @param contact
	 */
	public void SetContact(String contact) {

		contact_ = contact;

	}

	/**
	 * Gets country of residence of this guest.
	 * 
	 * @return	String containing country of residence of this guest.
	 */

	public String GetCountry() {

		return country_;

	}

	/**
	 * Set new country of residence of this guest
	 * 
	 * @param country
	 */
	public void SetCountry(String country) {

		country_ = country;

	}

	/**
	 * Gets the gender of this guest.
	 * 
	 * @return	Gender object of this guest.
	 */

	public Gender GetGender() {

		return gender_;

	}

	/**
	 * Sets new gender information of this guest.
	 * 
	 * @param gender
	 */
	public void SetGender(Gender gender) {

		gender_ = gender;

	}

	/**
	 * Gets the nationality of this guest.
	 * 
	 * @return	String containing nationality of this guest.
	 */

	public String GetNationality() {

		return nationality_;

	}

	/**
	 * Sets new nationality of this guest.
	 * 
	 * @param nationality
	 */
	public void SetNationality(String nationality) {

		nationality_ = nationality;

	}

	/**
	 * Gets room number of room this guest is staying at.
	 * 
	 * @return	An int containing room number of this guest.
	 */

	public int GetRoomNum() {

		return room_num_;

	}

	/**
	 * Sets a new room number to this guest.
	 * 
	 * @param room_num
	 */
	public void SetRoomNum(int room_num) {

		room_num_ = room_num;

	}

	/**
	 * Gets the payment ID of this guest.
	 * 
	 * @return	UUID object with payment ID of this guest.
	 */

	public UUID GetPaymentId() {

		return payment_id_;

	}

	/**
	 * Sets new payment ID to this guest.
	 * 
	 * @param payment_id
	 */
	public void SetPaymentId(UUID payment_id) {

		payment_id_ = payment_id;
	
	}

	/**
	 * Used to print guest information.
	 */
	@Override
	public String toString() {
		String room_num = (room_num_ == EMPTY_ROOM) ? EMPTY : String.valueOf(room_num_);
		String payment_id = (payment_id_ == EMPTY_UUID) ? EMPTY : payment_id_.toString();
		
		String output = String.format(
			"|ID: %s|Name: %s|Room #: %s|Payment ID: %s|\n" +
			"|Credit Card #: %s|\n" +
			"|Billing Address: %s|\n" +
			"|Contact: %s|Country: %s|Gender: %s|Nationality: %s|\n",
			identity_, name_, room_num, payment_id,
			credit_card_number_,
			billing_address_,
			contact_, country_, gender_.toString(), nationality_
		);

		return output;
	}
}