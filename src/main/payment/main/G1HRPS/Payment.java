package main.G1HRPS;

import java.util.List;

public class Payment {

	private final String payment_id_;
	private int room_num_;
	private float discounts_;
	private float tax_;
	private float room_charges_;
	private float rs_charges_;
	/**
	 * After discounts
	 */
	private float bill_total_;
	private PaymentStatus status_;

	/**
	 * 
	 * @param discount
	 * @param room_charges
	 */
	public Payment(int room_num, float room_charges, String payment_id) {

		payment_id_ = payment_id;
		room_num_ = room_num;
		discounts_ = 0;
		tax_ = 0;
		room_charges_ = 0;
		rs_charges_ = 0;
		status_ = PaymentStatus.Pending;
	}

	/**
	 * 
	 * @param guest_id
	 */
	public void SetRoomNum(int room_num) {

		room_num_ = room_num;

	}

	public void SetDiscount(float discount) {

		discounts_ = discount;

	}

	public void SetRoomCharges(float room_charges) {

		room_charges_ = room_charges;

	}

	public void SetRoomServices(List<RoomServiceOrder> room_service_order_list) {
		// TODO - Find a straightforward way to get total cost of each rso

		int room_num;

		for(RoomServiceOrder rsOrder : room_service_order_list){
			room_num = rsOrder.GetRoomNum();
			if(this.room_num_ == room_num){
				// rs_charges_ += rsOrder.
			}
		}

	}

	/**
	 * 
	 * @param Tax
	 */
	public void SetTax(float tax) {

		tax_ = tax;

	}

	/**
	 * 
	 * @param bill_total
	 */
	public void SetBillTotal(float bill_total) {

		bill_total_ = bill_total;

	}

	/**
	 * 
	 * @param status
	 */
	public void SetStatus(PaymentStatus status) {

		status_ = status;

	}

	public int GetRoomNum() {

		return room_num_;
		
	}

	public float GetDiscount() {

		return discounts_;

	}

	public float GetRoomCharges() {
		
		return room_charges_;

	}

	public float GetRsCharges() {

		return rs_charges_;

	}

	public float GetTax() {

		return tax_;
		
	}

	public float SetBillTotal() {

		bill_total_ = ((room_charges_ + rs_charges_) * (1-discounts_)) * (1 + tax_);

		return bill_total_;

	}

	public PaymentStatus GetStatus() {

		return status_;

	}

	public String GetPaymentID(){

		return payment_id_;

	}

	public float GetTotalBill(){

		return bill_total_;

	}
}