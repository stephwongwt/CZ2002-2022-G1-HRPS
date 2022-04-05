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
	 * Creates a new object related to the manager type ands add it to list
	 * 
	 * @param id
	 * @param guest_id
	 * @param room_num
	 * @param discounts
	 * @param tax
	 * @param bill_total
	 * @param status
	 */

	public void AddNewObject(UUID id, String guest_id, int room_num, float discounts, float tax, float bill_total, PaymentStatus status) {

		Payment new_payment = new Payment(id, guest_id, room_num, discounts, tax, bill_total, status);

		AddToList(new_payment);

	}

	/**
	 * Takes in an class object and list to add the object into.
	 * 
	 * @param payment
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
	 * 
	 * @param payment
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
	 * Method overrides the <T> SearchList(String search_text)
	 * in Supermanager interface and returns a payment with
	 * matching search text
	 * 
	 * @param 	payment_id	parameter is given as String and
	 * 						is used to get the corresponding
	 * 						UUID of payment
	 * 
	 * @return	payment with the matching payment_id
	 */

	@Override
	public Payment SearchList(String key) {

		UUID uuid = UUID.fromString(key);

		for(Payment payment : payment_list_){
			if(uuid.equals(payment.GetPaymentID())){
				return payment;
			}
		}

		return null;

	}

	/**
	 * 
	 * @param room_num
	 * @return pending payment on the room with matching room number
	 */

	public Payment SearchList(int room_num) {

		for(Payment payment : payment_list_){
			if((room_num == payment.GetRoomNum()) && 
				(payment.GetStatus() == PaymentStatus.Pending)){
				return payment;
			}
		}

		return null;

	}

	/**
	 * 
	 * @return the list of all payments
	 */

	public List<Payment> GetList() {
		return payment_list_;
	}

	/**
	 * Takes new payment status and assigns it to the given payment
	 * 
	 * @param payment
	 * @param status
	 */
	public void SetPaymentStatus(Payment payment, PaymentStatus status) {

		payment.SetStatus(status);

	}

	/**
	 *
	 * @param payment
	 * @return	PaymentStatus of the given payment
	 */

	public PaymentStatus GetPaymentStatus(Payment payment) {

		return payment.GetStatus();

	}

	/**
	 * Get linked items from room service and room charges and print the list
	 * 
	 * @param payment
	 * @param days_of_stay
	 * @param rs_order_list
	 */
	public void GenerateAndPrintBill(Payment payment, int days_of_stay, List<RoomServiceOrder> rs_order_list) {
		
		payment.SetRsCharges(rs_order_list);
		payment.SetBillTotal();

		System.out.println("-------Payment-------");
		System.out.println("- Days of Stay : " + days_of_stay);
		System.out.println("-- Cost of Stay : " + payment.GetRoomCharges());

		// TODO #9 - Need to print room service order items and their totals

		System.out.println("-- Cost of Room Service : " + payment.GetRsCharges());
		System.out.println("- Tax : " + payment.GetTax() + "%");
		System.out.println("- Total : " + payment.GetTotalBill());
		
	}

	/**
	 * Used to generate the payment_id of a new payment
	 */

	@Override
	public UUID GenerateCode() {
		UUID uuid = UUID.randomUUID();
		return uuid;
	}

	/**
	 * Payment data read as list of Strings are converted to list of payment class
	 * 
	 *
	 */

	public void InitializeDB() {
		// read String from text file
		ArrayList dbArray = (ArrayList) read(db_filename);
		ArrayList dataList = new ArrayList();

		for (int i = 0; i < dbArray.size(); i++) {
			String st = (String) dbArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer

			UUID id = UUID.fromString(star.nextToken().trim()); // first token 
			String guest_id = star.nextToken().trim(); 			// second token
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

	/**
	 * Each payment data in the list is turned into formatted String and
	 * written into each line of file named "payment_db.txt"
	 */

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
}