package main.G1HRPS;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Implements the Supermanager class to handle all the payments made by guests.
 * 
 * @author Kim Sang Hyeon
 *
 */
public class PaymentManager extends DatabaseHandler implements Supermanager<Payment> {
	/**
	 * Internally stored list of payments.
	 */
	private List<Payment> payment_list_;

	/**
	 * Filename of database file.
	 */
	private final String DB_FILENAME = "payment_db.txt";

	/**
	 * Create a Payment Manager.
	 */
	public PaymentManager() {
		payment_list_ = new ArrayList<Payment>();
	}

	/**
	 * Creates new payment and adds it to payment list.
	 * 
	 * @param guest_id             Guest ID of guest that made payment
	 * @param room_num             Room associated with this payment
	 * @param discounts            Discount percentage in the range of (0.0-1.0)
	 * @param tax                  Tax on bill total in the range of (0.0-1.0)
	 * @param room_charges         Total room charge of this payment
	 * @param room_service_charges Total room service charge of this payment
	 * @param status               Status of payment
	 * @return Payment object if successful, else null
	 */
	public Payment CreateNewPayment(String guest_id, int room_num, int discounts, int tax, float room_charges,
			float room_service_charges, PaymentStatus status) {
		Payment new_payment = new Payment(guest_id, room_num, discounts, tax, room_charges, room_service_charges,
				status);
		if (AddToList(new_payment)) {
			return new_payment;
		} else {
			return null;
		}
	}

	/**
	 * Overloads the SearchList function. Search for any pending payment of a room
	 * with given room number. Any such room is currently occupied and not
	 * checked-out.
	 * 
	 * @param room_num Room number to be searched for.
	 * @return Payment pending on the room.
	 */
	public Payment SearchList(int room_num) {
		for (Payment payment : payment_list_) {
			if ((room_num == payment.GetRoomNum()) && (payment.GetStatus() == PaymentStatus.Pending)) {
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
	@Override
	public List<Payment> GetList() {
		return payment_list_;
	}

	/**
	 * Sets a new payment status to the given payment.
	 * 
	 * @param payment    Object to set new status
	 * @param new_status Status to be set for object
	 */
	public void SetPaymentStatus(Payment payment, PaymentStatus new_status) {
		payment.SetStatus(new_status);
	}

	/**
	 * Gets the current payment status of the given payment.
	 *
	 * @param payment Object to get status of.
	 * @return PaymentStatus of the payment.
	 */
	public PaymentStatus GetPaymentStatus(Payment payment) {
		return payment.GetStatus();
	}

	/**
	 * Generates a new payment using given number of days of stay and inputs the
	 * bill into string format to be printed.
	 * 
	 * @param guest_id            Id of guest responsible for this payment
	 * @param room_num            Room number for this payment
	 * @param days_of_stay        Number of days checked in for this room number
	 * @param room_charge         Price of room per night
	 * @param discounts           Discount percentage to be applied
	 * @param tax                 Tax percentage to be applied
	 * @param room_service_orders List of room services ordered
	 * @return Pair.A: String of Bill to be printed, Pair.B: New Payment object.
	 */
	public Pair<String, Payment> GenerateAndPrintBill(String guest_id, int room_num, int days_of_stay,
			float room_charge, int discounts, int tax, List<RoomServiceOrder> room_service_orders) {
		String bill_string = "";
		float total_room_service_charges, cost_of_stay, price_of_order;
		total_room_service_charges = 0;

		cost_of_stay = days_of_stay * room_charge;

		bill_string += String.format("- Days of Stay: %d, Cost: $ %.2f\n", days_of_stay, cost_of_stay);
		String room_services_ordered = "|-|Room services ordered|-|\n";
		for (int i = 0; i < room_service_orders.size(); i++) {
			RoomServiceOrder each_order = room_service_orders.get(i);
			price_of_order = each_order.CalculateOrderTotalPrice();
			room_services_ordered += "  Room Service Order [" + (i + 1) + "]\n";
			room_services_ordered += each_order.MenuItemstoString();
			total_room_service_charges += price_of_order;
		}
		room_services_ordered += String.format("  Room Service Total : $ %.2f\n", total_room_service_charges);
		bill_string += room_services_ordered;

		Payment new_payment = new Payment(guest_id, room_num, discounts, tax, cost_of_stay, total_room_service_charges,
				PaymentStatus.Pending);
		bill_string += String.format("- Discount Rate: %d %%\n", discounts);
		bill_string += String.format("- Tax Rate: %d %%\n", tax);
		bill_string += String.format("- Bill Total: $ %.2f\n", new_payment.GetTotalBill());
		Pair<String, Payment> return_pair = Pair.makePair(bill_string, new_payment);
		return return_pair;
	}

	/**
	 * Generates a new payment using given check in date to calculate days of stay
	 * and inputs the bill into string format to be printed.
	 * 
	 * @param guest_id            Id of guest responsible for this payment
	 * @param room_num            Room number for this payment
	 * @param check_in_date       Date in which guest checked in, to be calculated
	 *                            with today's date to get days of stay
	 * @param room_charge         Price of room per night
	 * @param discounts           Discount percentage to be applied
	 * @param tax                 Tax percentage to be applied
	 * @param room_service_orders List of room services ordered
	 * @return Pair.A: String of Bill to be printed, Pair.B: New Payment object.
	 */
	public Pair<String, Payment> GenerateAndPrintBill(String guest_id, int room_num, LocalDateTime check_in_date,
			float room_charge, int discounts, int tax, List<RoomServiceOrder> room_service_orders) {
		String bill_string = "";
		float total_room_service_charges, cost_of_stay, price_of_order;
		total_room_service_charges = 0;

		long days_of_stay = ChronoUnit.DAYS.between(check_in_date, LocalDateTime.now());
		cost_of_stay = days_of_stay * room_charge;

		bill_string += String.format("- Days of Stay: %d, Cost: $ %.2f\n", days_of_stay, cost_of_stay);
		String room_services_ordered = "|-|Room services ordered|-|\n";
		for (int i = 0; i < room_service_orders.size(); i++) {
			RoomServiceOrder each_order = room_service_orders.get(i);
			price_of_order = each_order.CalculateOrderTotalPrice();
			room_services_ordered += "  Room Service Order [" + (i + 1) + "]\n";
			room_services_ordered += each_order.MenuItemstoString();
			total_room_service_charges += price_of_order;
		}
		room_services_ordered += String.format("  Room Service Total : $ %.2f\n", total_room_service_charges);
		bill_string += room_services_ordered;

		Payment new_payment = new Payment(guest_id, room_num, discounts, tax, cost_of_stay, total_room_service_charges,
				PaymentStatus.Pending);
		bill_string += String.format("- Discount Rate: %d %%\n", discounts);
		bill_string += String.format("- Tax Rate: %d %%\n", tax);
		bill_string += String.format("- Bill Total: $ %.2f\n", new_payment.GetTotalBill());
		Pair<String, Payment> return_pair = Pair.makePair(bill_string, new_payment);
		return return_pair;
	}

	/**
	 * Tokenize each line in the database into an object.
	 */
	@Override
	public void InitializeDB() {
		// read String from text file
		ArrayList<String> dbArray = (ArrayList) read(DB_FILENAME);
		ArrayList<Payment> dataList = new ArrayList<Payment>();
		for (String st : dbArray) {
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer
			UUID id = UUID.fromString(star.nextToken().trim()); // first token
			String guest_id = star.nextToken().trim(); // second token
			int room_num = Integer.parseInt(star.nextToken().trim());
			int discounts = Integer.parseInt(star.nextToken().trim());
			int tax = Integer.parseInt(star.nextToken().trim());
			float room_charges = Float.parseFloat(star.nextToken().trim());
			float room_service_charges = Float.parseFloat(star.nextToken().trim());
			float bill_total = Float.parseFloat(star.nextToken().trim());
			PaymentStatus status = PaymentStatus.valueOf(star.nextToken().trim());
			Payment obj = new Payment(id, guest_id, room_num, discounts, tax, room_charges, room_service_charges,
					bill_total, status);
			dataList.add(obj);
		}
		this.payment_list_ = dataList;
	}

	/**
	 * Data list is turned into formatted String and written the file named
	 * DB_FILENAME.
	 */
	@Override
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
			write(DB_FILENAME, paymentData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes a payment and add it to payment list.
	 * 
	 * @param payment New Payment object to be added to list of Payment objects.
	 * @return true if success / false if failed
	 */
	@Override
	public boolean AddToList(Payment payment) {
		boolean success = false;
		Payment found = SearchList(payment.GetPaymentID().toString());
		if (found == null) {
			try {
				success = payment_list_.add(payment);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * Removes a matching payment from the payment list.
	 * 
	 * @param payment Payment object to be removed from the list of Payment objects.
	 * @return true if success / false if failed
	 */
	@Override
	public boolean RemoveFromList(Payment payment) {
		boolean success = false;
		Payment found = SearchList(payment.GetPaymentID().toString());
		if (found != null) {
			try {
				success = payment_list_.remove(found);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * Method overrides the SearchList(String search_text) in Supermanager
	 * interface and returns a payment with matching search text.
	 * 
	 * @param payment_id String converted payment ID that was originally of type
	 *                   UUID.
	 * @return payment with the matching payment ID.
	 */
	@Override
	public Payment SearchList(String payment_id) {
		for (Payment payment : payment_list_) {
			if (payment.GetPaymentID().toString().equals(payment_id)) {
				return payment;
			}
		}
		return null;
	}
}