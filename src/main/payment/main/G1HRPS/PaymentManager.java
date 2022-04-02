package main.payment.main.G1HRPS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import main.main.G1HRPS.CodeGen;
import main.main.G1HRPS.Supermanager;

public class PaymentManager implements Supermanager<Payment>, CodeGen {

	private List<Payment> payment_list_;
	public static final String SEPARATOR = "|";

	public PaymentManager() {
		// TODO - implement PaymentManager.PaymentManager

		InitializeDB();

	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Payment payment) {
		// TODO - implement PaymentManager.AddToList

		payment_list_.add(payment);

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

	// public static List<Payment> readGuests(String filename) throws IOException {
	// 	List<String> stringArray = read(filename);
	// 	List<Payment> guestList = new ArrayList<>();

	// 	for(String st : stringArray){
	// 		StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			
	// 		String payment_id = star.nextToken().trim();
	// 		int guest_id = Integer.parseInt(star.nextToken().trim());
	// 		float discounts = Float.parseFloat(star.nextToken().trim());
	// 		float tax = Float.parseFloat(star.nextToken().trim());
	// 		float bill_total = Float.parseFloat(star.nextToken().trim());
	// 		PaymentStatus status = PaymentStatus.valueOf(star.nextToken().trim());
			
	// 		Payment payment = new Payment(discounts, bill_total, payment_id);

	// 		payment.SetTax(tax);
	// 		payment.SetGuestId(guest_id);
	// 		payment.SetStatus(status);

	// 		guestList.add(payment);
	// 	}

	// 	return guestList;
	// }

	// public static void savePayments(String fileName, List<Payment> paymentList) throws IOException {
	// 	List<String> paymentData = new ArrayList<>();

	// 	for(Payment payment : paymentList){
	// 		StringBuilder st = new StringBuilder();

	// 		st.append(payment.GetPaymentID());
	// 		st.append(SEPARATOR);
	// 		st.append(payment.GetGuestId());
	// 		st.append(SEPARATOR);
	// 		st.append(payment.GetDiscount());
	// 		st.append(SEPARATOR);
	// 		st.append(payment.GetTax());
	// 		st.append(SEPARATOR);
	// 		st.append(payment.GetTotalBill());
	// 		st.append(SEPARATOR);
	// 		st.append(payment.GetStatus().toString());
	// 		st.append(SEPARATOR);

	// 		paymentData.add(st.toString());
	// 	}
	// 	write(fileName, paymentData);
	// }

	// public static List<String> read(String fileName) throws IOException {
	// 	List<String> data = new ArrayList<>();
	// 	Scanner sc = new Scanner(new FileInputStream(fileName));
	// 	try{
	// 		while(sc.hasNextLine()){
	// 			data.add(sc.nextLine());
	// 		}
	// 	}		
	// 	finally{
	// 		sc.close();
	// 	}

	// 	return data;
	// }

	// public static void write(String fileName, List<String> data) throws IOException {
	// 	PrintWriter out = new PrintWriter(fileName);

	// 	try{
	// 		for(int i = 0; i < data.size(); i++){
	// 			out.println(data.get(i));
	// 		}
	// 	}
	// 	finally{
	// 		out.close();
	// 	}
	// }
}