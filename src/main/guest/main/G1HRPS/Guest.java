package main.G1HRPS;

import java.util.UUID;

public class Guest {

	private static final int MIN_CC_NUMLEN = 8;
	private static final int MAX_CC_NUMLEN = 19;

	private String identity_;
	private UUID payment_id_;
	private int room_num_;
	private String name_;
	private String cc_number_;
	private String billing_address_;
	private String contact_;
	private String country_;
	private Gender gender_;
	private String nationality_;

	/**
	 * 
	 * @param identity
	 * @param payment_id
	 * @param room_num
	 * @param name
	 * @param cc_number
	 * @param address
	 * @param contact
	 * @param country
	 * @param gender
	 * @param nationality
	 */

	public Guest(String identity, UUID payment_id, int room_num, String name, String cc_number, String address, String contact, String country, Gender gender, String nationality) {

		this.identity_ = identity;
		this.payment_id_ = payment_id;
		this.room_num_ = room_num;
		this.name_ = name;
		this.cc_number_ = cc_number;
		this.billing_address_ = address;
		this.contact_ = contact;
		this.country_ = country;
		this.gender_ = gender;
		this.nationality_ = nationality;

	}

	/**
	 * 
	 * @return	Guest identity
	 */

	public String GetIdentity() {

		return this.identity_;

	}

	/**
	 * Set new identity to this guest
	 * 
	 * @param identity
	 */
	public void SetIdentity(String identity) {

		this.identity_ = identity;

	}

	/**
	 * 
	 * @return	Name of this guest
	 */

	public String GetName() {

		return this.name_;

	}

	/**
	 * Set new name to this guest
	 * 
	 * @param name
	 */
	public void SetName(String name) {

		this.name_ = name;

	}

	/**
	 * 
	 * @return	Credit card number of this guest
	 */

	public String GetCcNumber() {

		return this.cc_number_;

	}

	/**
	 * Set new credit card number to this guest
	 * 
	 * @param cc_number
	 */
	public void SetCcNumber(String cc_number) {
		String noSpace_ccNum = cc_number.replaceAll("\\s+", "");

		if(noSpace_ccNum.matches("[0-9]+") && noSpace_ccNum.length() >= MIN_CC_NUMLEN && noSpace_ccNum.length() <= MAX_CC_NUMLEN)
			this.cc_number_ = cc_number;
		else
			System.out.println("Invalid credit card number");
	}

	/**
	 * 
	 * @return	Billing address of this guest
	 */

	public String GetBillingAddress() {

		return this.billing_address_;

	}

	/**
	 * Set new billing address of this guest
	 * 
	 * @param billing_address
	 */
	public void SetBillingAddress(String billing_address) {

		this.billing_address_ = billing_address;

	}

	/**
	 * 
	 * @return	Contact information of this guest
	 */

	public String GetContact() {

		return this.contact_;

	}

	/**
	 * Set new contact information of this guest
	 * 
	 * @param contact
	 */
	public void SetContact(String contact) {

		this.contact_ = contact;

	}

	/**
	 * 
	 * @return	Country of residence of this guest
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
	 * 
	 * @return	Gender of this guest
	 */

	public Gender GetGender() {

		return gender_;

	}

	/**
	 * Set new gender information of this guest
	 * 
	 * @param gender
	 */
	public void SetGender(Gender gender) {

		this.gender_ = gender;

	}

	/**
	 * 
	 * @return	Nationality of this guest
	 */

	public String GetNationality() {

		return nationality_;

	}

	/**
	 * Set new nationality of this guest
	 * 
	 * @param nationality
	 */
	public void SetNationality(String nationality) {

		this.nationality_ = nationality;

	}

	/**
	 * 
	 * @return	Room number this guest is staying at
	 */

	public int getRoomNum() {

		return room_num_;

	}

	/**
	 * Set a new room number this guest is staying at
	 * 
	 * @param room_num
	 */
	public void SetRoomNum(int room_num) {

		this.room_num_ = room_num;

	}

	/**
	 * 
	 * @return	Payment ID of this guest
	 */

	public UUID getPaymentId() {

		return this.payment_id_;

	}

	/**
	 * Set new payment ID of this guest
	 * 
	 * @param payment_id
	 */
	public void setPaymentId(UUID payment_id) {

		this.payment_id_ = payment_id;
	
	}

	/**
	 * Prints guest information, which are all the attributes of the
	 * corresponding guest object
	 * 
	 */
	
	@Override
	public String toString() {
		
		String output = String.format(
			"|ID: %s|Name: %s|Room #: %d|Payment ID: %s|\n" +
			"|Credit Card #: %s|\n" +
			"|Billing Address: %s|\n" +
			"|Contact: %s|Country: %s|Gender: %s|Nationality: %s|\n",
			this.identity_, this.name_, this.room_num_, this.payment_id_.toString(), this.cc_number_,
			this.billing_address_, this.contact_, this.country_, this.gender_.toString(), this.nationality_
		);

		return output;
	}
}