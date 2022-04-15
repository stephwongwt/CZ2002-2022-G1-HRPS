package main.G1HRPS;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class RoomServiceOrder {
    private UUID room_service_order_code_;
    private String guest_id_;
    private int room_number_;
    private final Timestamp time_created_;
    private Timestamp time_completed_;
    private List<MenuItem> ordered_item_list_;
    private String remarks_;
    private OrderStatus status_;
    private int order_quantity_;

    /**
     * Constructor for room service order
     * 
     * @param room_service_order_code
     * @param guest_id
     * @param room_number
     * @param ordered_item_list
     * @param remarks
     */
    public RoomServiceOrder(String guest_id, int room_number, List<MenuItem> ordered_item_list, String remarks) {
        this.room_service_order_code_ = UniqueIdGenerator.Generate();
        this.guest_id_ = guest_id;
        this.room_number_ = room_number;
        this.ordered_item_list_ = new ArrayList<MenuItem>(ordered_item_list);
        this.remarks_ = remarks;

        this.time_created_ = new Timestamp(System.currentTimeMillis());
        this.status_ = OrderStatus.Confirmed;
        this.order_quantity_ = this.ordered_item_list_.size();
    }
    
    public RoomServiceOrder(UUID room_service_order_code, String guest_id, int room_number, List<MenuItem> ordered_item_list, String remarks) {
        this.room_service_order_code_ = room_service_order_code;
        this.guest_id_ = guest_id;
        this.room_number_ = room_number;
        this.ordered_item_list_ = new ArrayList<MenuItem>(ordered_item_list);
        this.remarks_ = remarks;

        this.time_created_ = new Timestamp(System.currentTimeMillis());
        this.status_ = OrderStatus.Confirmed;
        this.order_quantity_ = this.ordered_item_list_.size();
    }

    /**
     * Gets the room service order code
     * 
     * @return UUID on the code for the room service order
     */
    public UUID GetRsoCode() {
        return this.room_service_order_code_;
    }

    /**
     * Sets the ID for room service_order_code
     * 
     * @param room_service_order_code_
     */
    public void SetRsoCode(String room_service_order_code) {
        this.room_service_order_code_ = UUID.fromString(room_service_order_code);
    }

    /**
     * Get the time stamp when order was delivered
     * 
     * @return Timestamp when order was created
     */
    public Timestamp GetTimeCreated() {
        return this.time_created_;
    }

    /**
     * Gets the time stamp when order was delivered
     * 
     * @return Timestamp when order was delivered
     */
    public Timestamp GetTimeCompleted() {
        return this.time_completed_;
    }

    /**
     * Sets the time completed for the room service order
     * 
     * @param time_completed
     */
    public void SetTimeCompleted(Timestamp time_completed) {
        this.time_completed_ = time_completed;
    }

    /**
     * Gets the list of ordered menu items
     * 
     * @return List containing the list of MenuItem
     */
    public List<MenuItem> GetOrderedItemList() {
        return this.ordered_item_list_;
    }

    /**
     * Sets the ordered item list
     * 
     * @param item_list
     */
    public void SetOrderedItemList(List<MenuItem> item_list) {
        this.ordered_item_list_ = item_list;
    }

    /**
     * Gets the room number
     * 
     * @return int containing the room number
     */
    public int GetRoomNum() {
        return this.room_number_;
    }

    /**
     * Sets the room number for the room service order
     * 
     * @param room_num
     */
    public void SetRoomNum(int room_num) {
        this.room_number_ = room_num;
    }

    /**
     * Return remarks for the room service order
     * 
     * @return String containing remarks for the room service order
     */
    public String GetRemarks() {
        return this.remarks_;
    }

    /**
     * Sets remarks for the room service order
     * 
     * @param remarks String for the remarks
     */
    public void SetRemarks(String remarks) {
        this.remarks_ = remarks;
    }

    /**
     * Gets the order status of the room service order
     * 
     * @return OrderStatus status of the order
     */
    public OrderStatus GetStatus() {
        return this.status_;
    }

    /**
     * Set the order status of the room service order. Updates time stamp when order
     * is delivered.
     * 
     * @param status enum OrderStatus on the status
     */
    public void SetStatus(OrderStatus status) {
        this.status_ = status;
        if (this.status_ == OrderStatus.Delivered) {
            // create only first time
            if (this.time_completed_ != null) {
                this.time_completed_ = new Timestamp(System.currentTimeMillis());
            }
        }
    }

    /**
     * Generates string for all the menu items for the room service order in
     * separate lines
     * 
     * @return String containing the list of menu items
     */
    public String MenuItemstoString() {
        String Textout = "";
        for (MenuItem menu_item : ordered_item_list_) {
            Textout.concat(menu_item.toString() + "\n");
        }
        return Textout;
    }

    /**
     * Returns String for guest ID
     * 
     * @return String guest ID
     */
    public String GetGuestId() {
        return guest_id_;
    }

    /**
     * Calculates price of all the menu items in the room service order
     * 
     * @return float containing the total price
     */
    /**
     * Gets number of orders for the room service order
     * 
     * @return int containing number of orders
     */
    public int GetOrderQuantity() {
        return order_quantity_;
    }

    /**
     * Gets total price of room service order based on all the menu items in the
     * ordered list
     * 
     * @return float containing the total price
     */
    public float CalTotalPrice() {
        float total_price = 0.0f;
        for (int i = 0; i < this.ordered_item_list_.size(); i++) {
            total_price += this.ordered_item_list_.get(i).GetPrice();
        }
        return total_price;
    }

    /**
     * Generate string for printing room service order at search menu
     */
    @Override
    public String toString() {
        return ("Room Service Order Code: " + this.room_service_order_code_ + " created at " + this.time_created_ + " Remarks: " + this.remarks_);
    }
}