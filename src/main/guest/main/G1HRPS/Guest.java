package main.guest.main.G1HRPS;

public class Guest {

	private String identity_;
	private String payment_id_;
	private int room_num_;
	private String name_;
	private int cc_number_;
	private String billing_address_;
	private String contact_;
	private String country_;
	private Gender gender_;
	private String nationality_;

	/**
	 * 
	 * @param identity
	 * @param name
	 * @param cc_number_
	 * @param address
	 * @param contact
	 * @param country
	 * @param gender
	 * @param nationality
	 */
	public Guest(String identity, String name, int cc_number_, String address, String contact, String country, Gender gender, String nationality) {
		// TODO - implement Guest.Guest

		this.identity_ = identity;
		this.payment_id_ = null;
		this.room_num_ = -1;
		this.name_ = name;
		this.cc_number_ = cc_number_;
		this.billing_address_ = address;
		this.contact_ = contact;
		this.country_ = country;
		this.gender_ = gender;
		this.nationality_ = nationality;

		throw new UnsupportedOperationException();
	}

	public String GetIdentity() {

		return this.identity_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param identity
	 */
	public void SetIdentity(String identity) {
		// TODO - implement Guest.SetIdentity

		this.identity_ = identity;

		throw new UnsupportedOperationException();
	}

	public String GetName() {
		// TODO - implement Guest.GetName

		return this.name_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param name
	 */
	public void SetName(String name) {
		// TODO - implement Guest.SetName

		this.name_ = name;

		throw new UnsupportedOperationException();
	}

	public int GetCcNumber() {
		// TODO - implement Guest.GetCcNumber

		return this.cc_number_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cc_number
	 */
	public void SetCcNumber(int cc_number) {
		// TODO - implement Guest.SetCcNumber

		this.cc_number_ = cc_number;

		throw new UnsupportedOperationException();
	}

	public String GetBillingAddress() {
		// TODO - implement Guest.GetBillingAddress

		return this.billing_address_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param billing_address
	 */
	public void SetBillingAddress(String billing_address) {
		// TODO - implement Guest.SetBillingAddress

		this.billing_address_ = billing_address;

		throw new UnsupportedOperationException();
	}

	public String GetContact() {
		// TODO - implement Guest.GetContact

		return this.contact_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param contact
	 */
	public void SetContact(String contact) {
		// TODO - implement Guest.SetContact

		this.contact_ = contact;

		throw new UnsupportedOperationException();
	}

	public String GetCountry() {
		// TODO - implement Guest.GetCountry

		return this.country_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param country
	 */
	public void SetCountry(String country) {
		// TODO - implement Guest.SetCountry

		this.country_ = country;

		throw new UnsupportedOperationException();
	}

	public Gender GetGender() {
		// TODO - implement Guest.GetGender

		return gender_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param gender
	 */
	public void SetGender(Gender gender) {
		// TODO - implement Guest.SetGender

		this.gender_ = gender;

		throw new UnsupportedOperationException();
	}

	public String GetNationality() {
		// TODO - implement Guest.GetNationality

		return nationality_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nationality
	 */
	public void SetNationality(String nationality) {
		// TODO - implement Guest.SetNationality

		this.nationality_ = nationality;

		throw new UnsupportedOperationException();
	}

	public int getRoomNum() {
		// TODO - implement Guest.SetRoomNum

		return room_num_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param room_num
	 */
	public void SetRoomNum(int room_num) {
		// TODO - implement Guest.SetRoomNum

		this.room_num_ = room_num;

		throw new UnsupportedOperationException();
	}

	public String getPaymentId() {
		// TODO - implement Guest.getPaymentId

		return this.payment_id_;

		// throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param payment_id
	 */
	public void setPaymentId(String payment_id) {
		// TODO - implement Guest.setPaymentId

		this.payment_id_ = payment_id;
	
		throw new UnsupportedOperationException();
	}

}