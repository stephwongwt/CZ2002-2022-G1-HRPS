package main.G1HRPS;

import java.util.List;
import java.sql.Timestamp;
import G1HRPS.CodeGen;
import G1HRPS.Supermanager;

public class PaymentManager implements Supermanager<Payment>, CodeGen {

	private List<Payment> payment_list_;

	public PaymentManager() {

		InitializeDB();

	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Payment payment) {

		try{
			payment_list_.add(payment);
		}
		catch(NullPointerException ex){
			System.out.println("Payment List not initialized");
		}

	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Payment payment) {

		try{
			payment_list_.remove(payment);
		}
		catch(NullPointerException ex){
			System.out.println("Payment List not initialized");
		}

	}

	@Override
	public void SearchList(String payment_id) {
		// TODO - implement PaymentManager.SearchList

		for(Payment payment : payment_list_){
			if(payment_id.equals(payment.GetPaymentID())){
				// return payment;
			}
		}

		// return null;

	}

	public List<Payment> GetList() {

		return payment_list_;

	}

	public void InitializeDB() {



	}

	public void SaveDB() {



	}

	public void SetPaymentStatus(Payment payment, PaymentStatus status) {

		payment.SetStatus(status);

	}

	public PaymentStatus GetPaymentStatus(Payment payment) {
		// TODO - implement PaymentManager.GetPaymentStatus

		return payment.GetStatus();

	}

	/**
	 * Get linked items from room service and room charges and print the list
	 */
	public void GenerateAndPrintBill() {
		// TODO - implement PaymentManager.GenerateAndPrintBill

		System.out.println();
		
	}

	@Override
	public String GenerateCode(String prefix) {
		// TODO Auto-generated method stub
		

		throw new UnsupportedOperationException();
	}
}