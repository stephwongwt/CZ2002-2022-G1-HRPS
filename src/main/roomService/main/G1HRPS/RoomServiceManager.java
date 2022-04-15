package main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.UUID;
import java.util.StringTokenizer;

/**
 * Manages room service orders from guests
 */
public class RoomServiceManager extends DatabaseHandler implements Supermanager<RoomServiceOrder> {
    private List<RoomServiceOrder> room_service_order_list_;
    private final String db_filename = "roomserviceorder_db.txt";

    /**
     * Constructor for room service manager
     * 
     */
    public RoomServiceManager() {
        this.room_service_order_list_ = new ArrayList<RoomServiceOrder>();
    }

    public RoomServiceOrder CreateNewRoomServiceOrder(String guest_id, int room_number, List<MenuItem> ordered_item_list, String remarks) {
        RoomServiceOrder new_room_service_order = new RoomServiceOrder(guest_id, room_number, ordered_item_list, remarks);
        AddToList(new_room_service_order);
        return new_room_service_order;
    }

    /**
     * Adds a room service order to the order list
     * 
     * @param room_service_order obj to be added
     * @return true if success / false if failed
     */
    @Override
    public boolean AddToList(RoomServiceOrder room_service_order) {
        boolean success = false;
        try {
            success = this.room_service_order_list_.add(room_service_order);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Removes a room service order to the order list
     * 
     * @param room_service_order obj to be removed
     * @return true if success / false if failed
     */
    @Override
    public boolean RemoveFromList(RoomServiceOrder room_service_order) {
        boolean success = false;
        try {
            success = this.room_service_order_list_.remove(room_service_order);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Method overrides the <T> SearchList(String search_text)
     * in Supermanager interface and returns a RoomServiceOrder with room_num
     * matching search text.
     * 
     * @param search_text This is the input that user keys in
     * @return Room if found, else null
     */
    @Override
    public RoomServiceOrder SearchList(String search_text) {
        for (RoomServiceOrder rso : room_service_order_list_) {
            if (rso.GetRsoCode() == UUID.fromString(search_text)) {
                return rso;
            }
        }
        return null;
    }

    /**
     * Returns the full list of room service orders
     * 
     * @return List of room service orders
     */
    public List<RoomServiceOrder> GetList() {
        return this.room_service_order_list_;
    }

    public void InitializeDB() {
        // read String from text file
        ArrayList<String> dbArray = (ArrayList) read(db_filename);
        ArrayList<RoomServiceOrder> dataList = new ArrayList<RoomServiceOrder>();
        for(String st : dbArray){
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer
            UUID room_service_order_code = UUID.fromString(star.nextToken().trim()); // first token
            String guest_id = star.nextToken().trim(); // second token
            int room_number = Integer.parseInt(star.nextToken().trim());
            int quantity = Integer.parseInt(star.nextToken().trim());
            List<MenuItem> ordered_item_list = new ArrayList<MenuItem>();
            for (int j = 0; j < quantity; j++) {
                String name = star.nextToken().trim();
                float price = Float.parseFloat(star.nextToken().trim());
                String description = star.nextToken().trim();
                MenuItem m = new MenuItem(name, price, description);
                ordered_item_list.add(m);
            }
            String remarks = star.nextToken().trim();
            // create object from file data
            RoomServiceOrder rso = new RoomServiceOrder(room_service_order_code, guest_id, room_number, ordered_item_list, remarks);
            // add to Room service order list
            dataList.add(rso);
        }
        this.room_service_order_list_ = dataList;
    }

    public void SaveDB() {
        List<String> rsoData = new ArrayList<String>();
        for (RoomServiceOrder rso : room_service_order_list_) {
            StringBuilder st = new StringBuilder();
            int quantity = rso.GetOrderQuantity();
            st.append(rso.GetRsoCode());
            st.append(SEPARATOR);
            st.append(rso.GetGuestId());
            st.append(SEPARATOR);
            st.append(rso.GetRoomNum());
            st.append(SEPARATOR);
            st.append(rso.GetRemarks());
            st.append(SEPARATOR);
            st.append(quantity);
            st.append(SEPARATOR);
            for (int i = 0; i < quantity; i++) {
                st.append(rso.GetOrderedItemList().get(i).GetName());
                st.append(SEPARATOR);
                st.append(rso.GetOrderedItemList().get(i).GetPrice());
                st.append(SEPARATOR);
                st.append(rso.GetOrderedItemList().get(i).GetDescription());
                st.append(SEPARATOR);
            }
            rsoData.add(st.toString());
        }
        try {
            write(db_filename, rsoData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets order status for the room service order
     * 
     * @param rso
     * @param new_status
     */
    public void SetRsoStatus(RoomServiceOrder rso, OrderStatus new_status) {
        rso.SetStatus(new_status);
    }

    /**
     * Gets the Order status of the room service order
     * 
     * @param rso
     * @return OrderStatus on the service order status
     */
    public OrderStatus GetRsoStatus(RoomServiceOrder rso) {
        return rso.GetStatus();
    }

    /**
     * Returns the room service order object based on room id
     * 
     * @param room_number
     * @return RoomServiceOrder of interest
     */
    public ArrayList<RoomServiceOrder> GetOrderedItemsByRoom(int room_number) {
        ArrayList<RoomServiceOrder> room_service_orders = new ArrayList<>();
        for (RoomServiceOrder order : room_service_order_list_) {
            if ((order.GetRoomNum() == room_number) && (order.GetStatus() == OrderStatus.Delivered)) {
                room_service_orders.add(order);
            }
        }
        return room_service_orders;
    }

    /**
     * Returns the room service order object based on guest id
     * 
     * @param guest_id
     * @return RoomServiceOrder of interest
     */
    public ArrayList<RoomServiceOrder> GetOrderedItemsByGuest(String guest_id) {
        ArrayList<RoomServiceOrder> room_service_orders = new ArrayList<>();
        for (RoomServiceOrder order : room_service_order_list_) {
            if ((order.GetGuestId() == guest_id) && (order.GetStatus() == OrderStatus.Delivered)) {
                room_service_orders.add(order);
            }
        }
        return room_service_orders;
    }
}