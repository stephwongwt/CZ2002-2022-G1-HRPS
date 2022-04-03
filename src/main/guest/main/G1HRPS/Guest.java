package main.G1HRPS;

public class Guest {

	private final int MIN_CC_NUMLEN = 8;
	private final int MAX_CC_NUMLEN = 19;

	private String identity_;
	private String payment_id_;
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
	 * @param name
	 * @param cc_number
	 * @param address
	 * @param contact
	 * @param country
	 * @param gender
	 * @param nationality
	 */

	public Guest() {}

	public Guest(String identity, String name, String cc_number, String address, String contact, String country, Gender gender, String nationality) {

		this.identity_ = identity;
		this.payment_id_ = null;
		this.room_num_ = 0;
		this.name_ = name;
		this.cc_number_ = cc_number;
		this.billing_address_ = address;
		this.contact_ = contact;
		this.country_ = country;
		this.gender_ = gender;
		this.nationality_ = nationality;

	}

	public String GetIdentity() {

		return this.identity_;

	}

	/**
	 * 
	 * @param identity
	 */
	public void SetIdentity(String identity) {

		this.identity_ = identity;

	}

	public String GetName() {

		return this.name_;

	}

	/**
	 * 
	 * @param name
	 */
	public void SetName(String name) {

		this.name_ = name;

	}

	public String GetCcNumber() {

		return this.cc_number_;

	}

	/**
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

	public String GetBillingAddress() {

		return this.billing_address_;

	}

	/**
	 * 
	 * @param billing_address
	 */
	public void SetBillingAddress(String billing_address) {

		this.billing_address_ = billing_address;

	}

	public String GetContact() {

		return this.contact_;

	}

	/**
	 * 
	 * @param contact
	 */
	public void SetContact(String contact) {

		this.contact_ = contact;

	}

	public String GetCountry() {

		return this.country_;

	}

	/**
	 * 
	 * @param country
	 */
	public void SetCountry(String country) {

		this.country_ = country;

	}

	public Gender GetGender() {

		return gender_;

	}

	/**
	 * 
	 * @param gender
	 */
	public void SetGender(Gender gender) {

		this.gender_ = gender;

	}

	public String GetNationality() {

		return nationality_;

	}

	/**
	 * 
	 * @param nationality
	 */
	public void SetNationality(String nationality) {

		this.nationality_ = nationality;

	}

	public int getRoomNum() {

		return room_num_;

	}

	/**
	 * 
	 * @param room_num
	 */
	public void SetRoomNum(int room_num) {

		this.room_num_ = room_num;

	}

	public String getPaymentId() {

		return this.payment_id_;

	}

	/**
	 * 
	 * @param payment_id
	 */
	public void setPaymentId(String payment_id) {

		this.payment_id_ = payment_id;
	
	}


	public void printGuestInfo() {
		if(this.payment_id_ != null)
			System.out.printf(
				"|ID: %s|Name: %s|Room #: %d|Payment ID: %s|Credit Card #: %d|%n" +
				"|Billing Address: %s|Contact: %s|Country: %s|Gender: %s|Nationality: %s|%n",
				this.identity_, this.name_, this.room_num_, this.payment_id_, this.cc_number_,
				this.billing_address_, this.contact_, this.country_, this.gender_.toString(), this.nationality_
			);
		else
			System.out.printf(
				"|ID: %s|Name: %s|Contact: %s|Country: %s|Gender: %s|Nationality: %s|",
				this.identity_, this.name_, this.contact_, this.country_, this.gender_.toString(), this.nationality_
			);
	}
}