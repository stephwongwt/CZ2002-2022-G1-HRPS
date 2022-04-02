package main.payment.main.G1HRPS;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import main.main.G1HRPS.CodeGen;
import main.main.G1HRPS.Supermanager;

public class PaymentManager implements Supermanager<Payment>, CodeGen {

	private List<Payment> payment_list_;

	public PaymentManager() {
		// TODO - implement PaymentManager.PaymentManager

		InitializeDB();

		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Payment payment) {
		// TODO - implement PaymentManager.AddToList

		payment_list_.add(payment);

		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Payment payment) {
		// TODO - implement PaymentManager.RemoveFromList

		payment_list_.remove(payment);

		throw new UnsupportedOperationException();
	}

	@Override
	public void SearchList(String payment_id) {
		// TODO - implement PaymentManager.SearchList

		for(Payment payment : payment_list_){
			if(payment_id.equals(payment.GetPaymentID())){

			}
		}

		throw new UnsupportedOperationException();
	}

	public List<Payment> GetList() {
		// TODO - implement PaymentManager.GetList

		return payment_list_;

		// throw new UnsupportedOperationException();
	}

	public void InitializeDB() {
		// TODO - implement PaymentManager.InitializeDB

		

		throw new UnsupportedOperationException();
	}

	public void SaveDB() {
		// TODO - implement PaymentManager.SaveDB



		throw new UnsupportedOperationException();
	}

	public void SetPaymentStatus(Payment payment, PaymentStatus status) {
		// TODO - implement PaymentManager.SetPaymentStatus

		payment.SetStatus(status);

		throw new UnsupportedOperationException();
	}

	public PaymentStatus GetPaymentStatus(Payment payment) {
		// TODO - implement PaymentManager.GetPaymentStatus

		return payment.GetStatus();

		// throw new UnsupportedOperationException();
	}

	/**
	 * Get linked items from room service and room charges and print the list
	 */
	public void GenerateAndPrintBill() {
		// TODO - implement PaymentManager.GenerateAndPrintBill

		

		throw new UnsupportedOperationException();
	}

	@Override
	public String GenerateCode(String prefix) {
		// TODO Auto-generated method stub
		long stamp;

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		stamp = timestamp.getTime();

		return prefix + String.valueOf(stamp);
	}
}