package main.G1HRPS;

import java.util.UUID;

/**
 * Payment class for storing the info of each of the payments.
 * 
 * @author Kim Sang Hyeon
 *
 */
public class Payment {
	/**
	 * Unique generated ID
	 */
	private final UUID payment_id_;
	
	/**
	 * Guest's driving license or passport number
	 */
	private String guest_id_;
	
	/**
	 * Room number this payment is for.
	 */
	private int room_num_;
	
	/**
	 * Discount percentage given to this bill.
	 */
	private int discounts_;
	
	/**
	 * Tax percentage applied to this bill.
	 */
	private int tax_;
	
	/**
	 * Charges based on room price and number of days stayed.
	 */
	private float room_charges_;
	
	/**
	 * Charges based on room services ordered.
	 */
	private float room_service_charges_;
	
	/**
	 * Generated total cost of this bill based on room and room service charges inclusive of discounts and taxes.
	 */
	private float bill_total_;
	
	/**
	 * Status of this payment, settled or otherwise.
	 */
	private PaymentStatus status_;

	/**
	 * Payment Constructor. Used when initializing from database.
	 * 
	 * @param payment_id			Payment ID in UUID format, must be unique from other Payment objects.
	 * @param guest_id             	Guest ID of guest that made payment
	 * @param room_num             	Room associated with this payment
	 * @param discounts            	Discount percentage in the range of (0 - 100)
	 * @param tax                  	Tax on bill total in the range of (0 - 100)
	 * @param room_charges         	Total room charge of this payment
	 * @param room_service_charges 	Total room service charge of this payment
	 * @param bill_total           	After deducting discounts
	 * @param status				Status of payment using Enumset PaymentStatus
	 */
	public Payment(UUID payment_id, String guest_id, int room_num, int discounts, int tax, float room_charges,
			float room_service_charges, float bill_total, PaymentStatus status) {
		payment_id_ = payment_id;
		guest_id_ = guest_id;
		room_num_ = room_num;
		discounts_ = discounts;
		tax_ = tax;
		room_charges_ = room_charges;
		room_service_charges_ = room_service_charges;
		bill_total_ = bill_total;
		status_ = status;
	}

	/**
	 * Constructor to be used when creating new payment when in operation.
	 * 
	 * @param guest_id             	Guest ID of guest that made payment
	 * @param room_num             	Room associated with this payment
	 * @param discounts            	Discount percentage in the range of (0 - 100)
	 * @param tax                  	Tax on bill total in the range of (0 - 100)
	 * @param room_charges         	Total room charge of this payment
	 * @param room_service_charges 	Total room service charge of this payment
	 * @param status				Status of payment using Enumset PaymentStatus
	 */
	public Payment(String guest_id, int room_num, int discounts, int tax, float room_charges,
			float room_service_charges, PaymentStatus status) {
		payment_id_ = UniqueIdGenerator.Generate();
		guest_id_ = guest_id;
		room_num_ = room_num;
		discounts_ = discounts;
		tax_ = tax;
		room_charges_ = room_charges;
		room_service_charges_ = room_service_charges;
		bill_total_ = CalculateBillTotal();
		status_ = status;
	}

	/**
	 * Gets a unique payment ID of this payment.
	 * 
	 * @return UUID of unique payment ID.
	 */
	public UUID GetPaymentID() {
		return payment_id_;
	}

	/**
	 * Sets a new guest_id to this payment. Guest with this guest_id has made this
	 * payment.
	 * 
	 * @param guest_id A string containing guest ID.
	 */
	public void SetGuestID(String guest_id) {
		guest_id_ = guest_id;
	}

	/**
	 * Gets a unique guest ID of guest that made payment.
	 * 
	 * @return A string of Guest ID.
	 */
	public String GetGuestID() {
		return guest_id_;
	}

	/**
	 * Sets new room number to this payment.
	 * 
	 * @param room_num An integer of room number.
	 */
	public void SetRoomNum(int room_num) {
		room_num_ = room_num;
	}

	/**
	 * Gets a room number associated with payment.
	 * 
	 * @return An integer containing room number.
	 */
	public int GetRoomNum() {
		return room_num_;
	}

	/**
	 * Sets new discount rate (i.e. 25%) to this payment.
	 * 
	 * @param discount An integer of discount rate.
	 */
	public void SetDiscountRate(int discount) {
		discounts_ = discount;
	}

	/**
	 * Gets a discount rate to be applied to this payment.
	 * 
	 * @return An integer containing discount rate of this payment.
	 */
	public int GetDiscountRate() {
		return discounts_;
	}

	/**
	 * Sets new room charge of this payment from the 'rate of room per day' and
	 * 'days of stay'.
	 * 
	 * @param days_of_stay An integer of number of days of stay.
	 * @param room_rate    A float of room rate in $.
	 */
	public void SetRoomCharges(int days_of_stay, float room_rate) {
		room_charges_ = room_rate * days_of_stay;
	}

	/**
	 * Gets a room charge associated with this payment.
	 * 
	 * @return A float of room charge of this payment in $.
	 */
	public float GetRoomCharges() {
		return room_charges_;
	}

	/**
	 * Assign new room service charge of this payment.
	 * 
	 * @param room_service_charges A float with room service charge of this payment
	 *                             in $.
	 */
	public void SetRoomServiceCharges(float room_service_charges) {
		room_service_charges_ = room_service_charges;
	}

	/**
	 * Gets total of room service charges associated with this payment.
	 * 
	 * @return A float containing room service charge of this payment.
	 */
	public float GetRoomServiceCharges() {
		return room_service_charges_;
	}

	/**
	 * Gets a new tax rate (i.e. 10%) of this payment.
	 * 
	 * @param tax A float of tax rate.
	 */
	public void SetTax(int tax) {
		tax_ = tax;
	}

	/**
	 * Gets a tax rate associated with this payment.
	 * 
	 * @return A float with tax rate of this payment.
	 */
	public int GetTax() {
		return tax_;
	}

	/**
	 * Sets a new total bill to this payment. Used to overwrite and directly edit
	 * the final total payment bill.
	 * 
	 * @param bill_total A float containing new total bill.
	 */
	public void SetBillTotal(float bill_total) {
		bill_total_ = bill_total;
	}

	/**
	 * Sets the total bill amount by adding room charges and room service charges,
	 * then calculate the discounted price of their sum, and finally add tax to the
	 * final payment amount.
	 * 
	 * @return A float containing total bill of this payment.
	 */
	private float CalculateBillTotal() {
		bill_total_ = (room_charges_ + room_service_charges_) * (1 - (discounts_ / 100)) * (1 + (tax_ / 100));
		return bill_total_;
	}

	/**
	 * Gets a total bill of this payment.
	 * 
	 * @return A float with total bill of this payment.
	 */
	public float GetTotalBill() {
		return bill_total_;
	}

	/**
	 * Sets a new payment status to this payment. It can be set to one of enum types
	 * 'Pending', 'Complete', 'Cancelled'.
	 * 
	 * @param status PaymentStatus type with a new status of this payment.
	 */
	public void SetStatus(PaymentStatus status) {
		status_ = status;
	}

	/**
	 * Gets the current status of this payment.
	 * 
	 * @return PaymentStatus type with current status of this payment.
	 */
	public PaymentStatus GetStatus() {
		return status_;
	}

	/**
	 * @return String
	 */
	@Override
	public String toString() {
		return "| ID: " + this.payment_id_ + " | Guest Id: " + this.guest_id_ + " |\n| Room Num: " + this.room_num_
				+ " | Total: $ " + this.bill_total_ + " |\n";
	}
}