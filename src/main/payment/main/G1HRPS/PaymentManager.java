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

	/**
	 * Create a Payment Manager.
	 */

	public PaymentManager() {
		payment_list_ = new ArrayList<Payment>();
	}

	/**
	 * Takes a payment and add it to payment list.
	 * 
	 * @param payment	New Payment object to be added to list of Payment objects.
	 */
	public void AddToList(Payment payment) {
		boolean success;

		try{
			success = payment_list_.add(payment);
			if(success)
				System.out.println("Payment added to list");
			else {
				System.out.println("Payment of UUID: " + payment.GetPaymentID() + " not added to list");
			}
		}
		catch(NullPointerException e){
			System.out.println("Payment List not initialized");
			e.printStackTrace();
		}

	}

	/**
	 * Removes a matching payment from the payment list.
	 * 
	 * @param payment	Payment object to be removed from the list of Payment objects.
	 */
	public void RemoveFromList(Payment payment) {
		boolean success;

		try{
			success = payment_list_.remove(payment);
			if(success){
				System.out.println("Payment removed from list");
			}
			else{
				System.out.println("Payment of UUID: " + payment.GetPaymentID() + " not removed from list");
			}
		}
		catch(NullPointerException e){
			System.out.println("Payment List not initialized");
			e.printStackTrace();
		}

	}

	/**
	 * Method overrides the <T> SearchList(String search_text)
	 * in Supermanager interface and returns a payment with
	 * matching search text.
	 * 
	 * @param 	payment_id	String converted payment ID that was originally of type UUID.
	 * 
	 * @return	payment with the matching payment ID.
	 */
	
	@Override
	public Payment SearchList(String payment_id) {

		UUID uuid = UUID.fromString(payment_id);

		for(Payment payment : payment_list_){
			if(uuid.equals(payment.GetPaymentID())){
				return payment;
			}
		}

		return null;

	}

	/**
	 * Search for any pending payment of a room with given room number.
	 * Any such room is currently occupied and not checked-out.
	 * 
	 * @param room_num
	 * @return	Payment pending on the room.
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
	 * Gets payment list.
	 * 
	 * @return List containing all payments.
	 */

	public List<Payment> GetList() {
		return payment_list_;
	}

	/**
	 * Sets a new payment status to the given payment.
	 * 
	 * @param payment	
	 * @param new_status	
	 */
	public void SetPaymentStatus(Payment payment, PaymentStatus new_status) {

		payment.SetStatus(new_status);

	}

	/**
	 * Gets the current payment status of the given payment.
	 *
	 * @param payment
	 * @return	PaymentStatus of the payment.
	 */

	public PaymentStatus GetPaymentStatus(Payment payment) {

		return payment.GetStatus();

	}

	/**
	 * Generates a new payment with the given arguments and prints the bill.
	 * After printing the bill, the method returns the Payment object.
	 * 
	 * @param guest_id
	 * @param room_num
	 * @param days_of_stay
	 * @param room_charge
	 * @param discounts
	 * @param tax
	 * @param room_service_orders
	 * @return	new Payment object created from given arguments.
	 */

	public Payment GenerateAndPrintBill(String guest_id, int room_num, int days_of_stay, float room_charge, float discounts, float tax, List<RoomServiceOrder> room_service_orders) {
	
		int index;
		boolean success;
		float rate, init_total_bill;
		float total_room_service_charges, cost_of_stay, price_of_order;

		index = 1;
		init_total_bill = 0;
		success = false;
		total_room_service_charges = 0;
		cost_of_stay = days_of_stay * room_charge;
		
		System.out.println("-------Payment-------");
		System.out.println("- Days of Stay : " + days_of_stay);
		System.out.printf("--- Cost of Stay ($) : %.2f\n", cost_of_stay);
		System.out.println("- Room services ordered");
		for(RoomServiceOrder each_order : room_service_orders) {
			price_of_order = each_order.CalTotalPrice();
			System.out.println("--- Room Service Order [" + index + "]:");

			// for(MenuItem menu : each_order.GetOrderedItemList()) {
			// 	System.out.println("\t-- " + menu);
			// }

			System.out.println("---- Order Total : " + price_of_order);

			total_room_service_charges += price_of_order;
			index++;
		}

		Payment new_payment = new Payment(GenerateCode(), guest_id, room_num, discounts, tax, cost_of_stay, total_room_service_charges, init_total_bill, PaymentStatus.Pending);

		new_payment.CalculateBillTotal();
	
		System.out.printf("-- Cost of Room Service ($) : %.2f\n", total_room_service_charges);
		System.out.printf("- Discounts : %.2f (%)\n", discounts);
		System.out.printf("- Tax : %.2f (%)\n", tax);
		System.out.printf("- Total ($) : %.2f\n", new_payment.GetTotalBill());
		
		return new_payment;
	}

	/**
	 * Used to generate the payment_id of a new payment.
	 * 
	 * @return UUID unique to this payment.
	 */

	@Override
	public UUID GenerateCode() {
		UUID uuid = UUID.randomUUID();
		return uuid;
	}

	/**
	 * Payment data read as list of Strings are converted to a list of payment class.
	 * </p>
	 * Each String object data is parsed and used to create each Payment object data in list.
	 */

	public void InitializeDB() {
		// read String from text file
		ArrayList<String> dbArray = (ArrayList) read(db_filename);
		ArrayList<Payment> dataList = new ArrayList<Payment>();

		for (int i = 0; i < dbArray.size(); i++) {
			String st = (String) dbArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer

			UUID id = UUID.fromString(star.nextToken().trim()); // first token 
			String guest_id = star.nextToken().trim(); 			// second token
			int room_num = Integer.parseInt(star.nextToken().trim()); 
			float discounts = Float.parseFloat(star.nextToken().trim());
			float tax = Float.parseFloat(star.nextToken().trim());
			float room_charges = Float.parseFloat(star.nextToken().trim());
			float room_service_charges = Float.parseFloat(star.nextToken().trim());
			float bill_total = Float.parseFloat(star.nextToken().trim());
			PaymentStatus status = PaymentStatus.valueOf(star.nextToken().trim());
			// create object from file data
			Payment obj = new Payment(id, guest_id, room_num, discounts, tax, room_charges, room_service_charges, bill_total, status);
			// add to Professors list
			dataList.add(obj);
		}
		payment_list_ = dataList;
	}

	/**
	 * Each payment data in the list is turned into formatted String and
	 * written into each line of file named "payment_db.txt".
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
			st.append(payment.GetDiscountRate());
			st.append(SEPARATOR);
			st.append(payment.GetTax());
			st.append(SEPARATOR);
			st.append(payment.GetRoomCharges());
			st.append(SEPARATOR);
			st.append(payment.GetRoomServiceCharges());
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