package main.payment.main.G1HRPS;

import main.*;

public class Payment {

	private final String payment_id_;
	private int guest_id;
	private float discounts_;
	private float tax_;
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
	public Payment(float discount, float room_charges, String payment_id) {
		// TODO - implement Payment.Payment

		this.discounts_ = discount;
		this.bill_total_ = room_charges;

		this.payment_id_ = payment_id;
		this.guest_id = -1;
		this.tax_ = (float)-1;
		this.status_ = PaymentStatus.Pending;

		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param guest_id
	 */
	public void SetGuestId(int guest_id) {
		// TODO - implement Payment.SetGuestId

		this.guest_id = guest_id;

		throw new UnsupportedOperationException();
	}

	public void SetDiscount(float discount) {
		// TODO - implement Payment.SetDiscount

		this.discounts_ = discount;

		throw new UnsupportedOperationException();
	}

	public void SetRoomCharges() {
		// TODO - implement Payment.SetRoomCharges



		throw new UnsupportedOperationException();
	}

	public void SetRoomServices() {
		// TODO - implement Payment.SetRoomServices



		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Tax
	 */
	public void SetTax(float Tax) {
		// TODO - implement Payment.SetTax

		this.tax_ = Tax;

		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param bill_total
	 */
	public void SetBillTotal(float bill_total) {
		// TODO - implement Payment.SetBillTotal

		this.bill_total_ = bill_total;

		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param status
	 */
	public void SetStatus(PaymentStatus status) {
		// TODO - implement Payment.SetStatus

		this.status_ = status;

		throw new UnsupportedOperationException();
	}

	public int GetGuestId() {
		// TODO - implement Payment.GetGuestId

		return guest_id;

		// throw new UnsupportedOperationException();
	}

	public float GetDiscount() {
		// TODO - implement Payment.GetDiscount

		return this.discounts_;

		// throw new UnsupportedOperationException();
	}

	public void GetRoomCharges() {
		// TODO - implement Payment.GetRoomCharges
		
		

		throw new UnsupportedOperationException();
	}

	public void GetRoomServices() {
		// TODO - implement Payment.GetRoomServices

		throw new UnsupportedOperationException();
	}

	public float GetTax() {
		// TODO - implement Payment.GetTax

		return this.tax_;
		
		// throw new UnsupportedOperationException();
	}

	public float SetBillTotal() {
		// TODO - implement Payment.SetBillTotal



		throw new UnsupportedOperationException();
	}

	public PaymentStatus GetStatus() {
		// TODO - implement Payment.GetStatus

		return this.status_;

		// throw new UnsupportedOperationException();
	}

	public String GetPaymentID(){
		// TODO - implement Payment.GetPaymentID

		return this.payment_id_;
	}

	public float GetTotalBill(){
		// TODO - implement Payment.GetTotalBill

		return this.bill_total_;
	}
}