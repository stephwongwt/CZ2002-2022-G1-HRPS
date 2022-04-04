package main.G1HRPS;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

public class PaymentManager extends DatabaseHandler implements Supermanager<Payment>, CodeGen {
	private List<Payment> payment_list_;
	private final String db_filename = "payment_db.txt";

	public PaymentManager() {
		
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Payment payment) {

		try{
			payment_list_.add(payment);
		}
		catch(NullPointerException e){
			System.out.println("Payment List not initialized");
			e.printStackTrace();
		}

	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Payment payment) {

		try{
			payment_list_.remove(payment);
		}
		catch(NullPointerException e){
			System.out.println("Payment List not initialized");
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param payment_id
	 */

	@Override
	public Payment SearchList(String payment_id) {
		// TODO - implement PaymentManager.SearchList

		UUID uuid = UUID.fromString(payment_id);

		for(Payment payment : payment_list_){
			if(uuid.equals(payment.GetPaymentID())){
				return payment;
			}
		}

		return null;

	}

	public List<Payment> GetList() {
		return payment_list_;
	}

	public void InitializeDB() {
		// read String from text file
		ArrayList dbArray = (ArrayList) read(db_filename);
		ArrayList dataList = new ArrayList();

		for (int i = 0; i < dbArray.size(); i++) {
			String st = (String) dbArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer

			UUID id = UUID.fromString(star.nextToken().trim()); // first token
			String guest_id = star.nextToken().trim(); // second token
			int room_num = Integer.parseInt(star.nextToken().trim());
			float discounts = Float.parseFloat(star.nextToken().trim());
			float tax = Float.parseFloat(star.nextToken().trim());
			float bill_total = Float.parseFloat(star.nextToken().trim());
			PaymentStatus status = PaymentStatus.valueOf(star.nextToken().trim());
			// create object from file data
			Payment obj = new Payment(id, guest_id, room_num, discounts, tax, bill_total, status);
			// add to Professors list
			dataList.add(obj);
		}
		payment_list_ = dataList;
	}

	public void SaveDB() {
		List<String> paymentData = new ArrayList<String>();

		for (Payment payment : payment_list_) {
			StringBuilder st = new StringBuilder();
			st.append(payment.GetPaymentID());
			st.append(SEPARATOR);
			st.append(payment.GetGuestID());
			st.append(SEPARATOR);
			st.append(payment.GetRoomNum());
			st.append(SEPARATOR);
			st.append(payment.GetDiscount());
			st.append(SEPARATOR);
			st.append(payment.GetTax());
			st.append(SEPARATOR);
			st.append(payment.GetTotalBill());
			st.append(SEPARATOR);
			st.append(payment.GetStatus());
			st.append(SEPARATOR);

			paymentData.add(st.toString());
		}

		try {
			write(db_filename, paymentData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param payment
	 * @param status
	 */
	public void SetPaymentStatus(Payment payment, PaymentStatus status) {

		payment.SetStatus(status);

	}

	public PaymentStatus GetPaymentStatus(Payment payment) {

		return payment.GetStatus();

	}

	/**
	 * Get linked items from room service and room charges and print the list
	 */
	public void GenerateAndPrintBill(Payment payment) {
		
		payment.SetBillTotal();

		System.out.println("-------Payment-------");
		System.out.println("- Days of Stay : ");
		System.out.println("-- Cost of Stay : " + payment.GetRoomCharges());

		// TODO - Print list of all ordered items in room

		System.out.println("-- Cost of Room Service : " + payment.GetRsCharges());
		System.out.println("- Tax : " + payment.GetTax() + "%");
		System.out.println("- Total : " + payment.GetTotalBill());
		
	}

	@Override
	public UUID GenerateCode() {
		UUID uuid = UUID.randomUUID();
		return uuid;
	}
}