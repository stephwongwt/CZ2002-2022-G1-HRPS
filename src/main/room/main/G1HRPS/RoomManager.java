package main.G1HRPS;
import java.util.ArrayList;
/**
 * A manager class that stores the Room Object as elements in a list. Search methods
 * parses through the list of rooms, comparing instance variables of each room object with the arguments passed in.
 * @author LiangHee
 *
 */
public class RoomManager{
private static ArrayList<Room> room_list_;
public RoomManager() {
}

/**
 * Takes in a class object and list to add the object into.
 * Will reject adding a room with room number that already exists.
 * @param room This is the room object to be added
 */
public static void AddToList(Room room) {
	for(var i=0; i<room_list_.size();i++) {
		if(room_list_.get(i).GetRoomNumber()==room.GetRoomNumber()) {
			System.out.println("Room number already exists in database.");
		}
		else {
			room_list_.add(room);
		}
	}
}

/** 
 * This method takes in input info about room from the menu, creates a new room object, and adds that new object 
 * to room_list_.
 * The parameters are the same as in Room constructor.
 */
public static void AddNewRoom(RoomType room_type, float price, int room_number, BedSize bedSize, boolean wifiEnabled, boolean withView, boolean Smoking, RoomStatus status){
	Room room = new Room(room_type, price, room_number, bedSize, wifiEnabled, withView, Smoking, status);
	AddToList(room);
}

/**
 * Takes in user input from menu and searches 
 * through room_list_ for guest name with the same string. 
 * If same string found, returns object room
 * @param search_text This is the input that user keys in
 */
public Room SearchList(String search_text) {  
	
	for(var i=0; i<room_list_.size();i++) {
		for(var j=0; j<room_list_.get(i).GetGuestList().size();j++) { //searching for same Guest Name
			if(room_list_.get(i).GetGuestList().get(j).getName==search_text) {
				return room_list_.get(i);
			}
		}	
	}	
	throw new UnsupportedOperationException();
}
/**
 * Takes in user input from menu and searches through
 *  room_list_ for room number with the same integer.
 *  If same Integer found, returns object room 
 */
public Room SearchList(int search_int) {
	for(var i=0; i<room_list_.size();i++) { //searching for same Room Name
		if (room_list_.get(i).GetRoomNumber()==search_int) {
			return room_list_.get(i);
		}
	}
}
/**
 * Removes a room from room_list_, user's input is the room number
 * @param room_num_ Room number that user wants to remove from List.
 */
public void RemoveFromList(String roomNumber) { 
	Room room = SearchList(roomNumber);
	room_list_.remove(room);
}

/**
 * Returns an arrayList List<Room>, room_list
 * @return room_list_  arrayList that is returned.
 */
public List<Room> GetList() {
	return room_list_;
}
}
public void InitializeDB() {
	// TODO - implement RoomManager.InitializeDB
	throw new UnsupportedOperationException();
}

public void SaveDB() {
	// TODO - implement RoomManager.SaveDB
	throw new UnsupportedOperationException();
}

/** Assign a guest to a room, guest is stored as an element in a guest list
 * 
 * @param guest This is the guest to be added
 * @param roomNumber This is the room number of the room that the guest will be added to
 */
public void CheckInRoom(Guest guest, String roomNumber) {
	for(var i=0; i<room_list_.size();i++) {
		if (room_list_.get(i).GetRoomNumber()==roomNumber) {
			room_list_.get(i).AddGuest(guest);
			room_list_.get(i).SetStatus(RoomStatus.Occupied);
			break;
		}
		else
			System.out.println("room number does not exist");
	}
	throw new UnsupportedOperationException();
}
/**
 * removes all guests elements from the guest list of a room
 * @param roomNumber This is the room number to remove guest elements from.
 */
public void CheckOutAllGuest(int roomNumber) {
	for(var i=0; i<room_list_.size();i++) {
		if (room_list_.get(i).GetRoomNumber()==roomNumber) {
			room_list_.get(i).ClearGuests();
			room_list_.get(i).SetStatus(RoomStatus.Vacant);		
			break;
		}
		else
			System.out.println("room number does not exist");
	}
	throw new UnsupportedOperationException();
}
/**
 * Removes one guest element from the guest list
 * @param roomNumber This is the room number to remove guest element from
 * @param guestName This is the guest name of guest element to be removed
 */
public void CheckOutOneGuest(int roomNumber,String guestName) {//note: guest Name required from user is full name of guest
	for(var i=0; i<room_list_.size();i++) { 
		if (room_list_.get(i).GetRoomNumber()==roomNumber) {   //if room matches room number
			for(var j=0; j<room_list_.get(i).GetGuestList().size();j++) {
				if(room_list_.get(i).GetGuestList().get(j).getName==guestName) { // if guest name matches guest
					room_list_.get(i).CheckOutGuest(guestName);
					break;
				}
				if (room_list_.get(i).GetGuestList().size()==0) { // if guest list is empty, set room status to vacant
					room_list_.get(i).SetStatus(RoomStatus.Vacant);
				}
			}
		
		}
	}
}
/**
 * Sets a room status to vacant, occupied, reserved, maintenance 
 * This method is primarily to set room status to reserved and maintenance,
 * as rooms are auto set to vacant and occupied in check in and check out.
 * @param roomNumber This is room number of the room that status will be changed
 * @param status This is the status to be changed into
 */
public void SetRoomStatus(int roomNumber, RoomStatus status) {
	for(var i=0; i<room_list_.size();i++) {
		if (room_list_.get(i).GetRoomNumber()==roomNumber) {
			room_list_.get(i).SetStatus(status);
		}
	throw new UnsupportedOperationException();
}
}

/**
 * Vacant room numbers in room_list_, organized by room type .
 * e.g. Standard: Number: 2 out of 15 vacant
 * 				  Available Rooms: 0221 , 0222, 0233, 0244, 0255
 * 		VIP:      Number: 3 out of 5 vacant
 */
public void GetRoomStatisticsByTypeOccupancyRate() {
	int singleTotalCount = 0;
	int singleVacantCount = 0;
	String singleVacantRoomNumber="";
	int standardTotalCount = 0;
	int standardVacantCount = 0;
	String standardVacantRoomNumber="";
	int deluxeTotalCount = 0;
	int deluxeVacantCount = 0;
	String deluxeVacantRoomNumber="";
	int suiteTotalCount = 0;
	int suiteVacantCount = 0;
	String suiteVacantRoomNumber="";
	int VIPTotalCount = 0;
	int VIPVacantCount = 0;
	String VIPVacantRoomNumber="";
	
	for(var i=0; i<room_list_.size();i++) {
		RoomType type = room_list_.get(i).GetRoomType();
		switch(type) { //This switch block acts as a counter for different room types
		
		case Single:
			singleTotalCount+=1;
			if(room_list_.get(i).GetStatus()==RoomStatus.Vacant) {
				singleVacantRoomNumber=singleVacantRoomNumber+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
				singleVacantCount+=1;
			}
			break;	
		case Standard:
			standardTotalCount+=1;
			if(room_list_.get(i).GetStatus()==RoomStatus.Vacant) {
				standardVacantRoomNumber=standardVacantRoomNumber+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
				standardVacantCount+=1;
			}
			break;
		case Deluxe:
			deluxeTotalCount+=1;
			if(room_list_.get(i).GetStatus()==RoomStatus.Vacant) {
				deluxeVacantRoomNumber=deluxeVacantRoomNumber+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
				deluxeVacantCount+=1;
			}
			break;
		case Suite:
			suiteTotalCount+=1;
			if(room_list_.get(i).GetStatus()==RoomStatus.Vacant) {
				suiteVacantRoomNumber=suiteVacantRoomNumber+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
				suiteVacantCount+=1;
			}
			break;
		case VIP:
			VIPTotalCount+=1;
			if(room_list_.get(i).GetStatus()==RoomStatus.Vacant) {
				VIPVacantRoomNumber=VIPVacantRoomNumber+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
				VIPVacantCount+=1;
			}
			break;
		default:
			break;	
		}
	singleVacantRoomNumber =singleVacantRoomNumber.substring(1,singleVacantRoomNumber.length()-1); // removes the last comma in this String
	standardVacantRoomNumber =standardVacantRoomNumber.substring(1,standardVacantRoomNumber.length()-1);
	suiteVacantRoomNumber =suiteVacantRoomNumber.substring(1,suiteVacantRoomNumber.length()-1);
	VIPVacantRoomNumber =VIPVacantRoomNumber.substring(1,VIPVacantRoomNumber.length()-1);
	deluxeVacantRoomNumber =deluxeVacantRoomNumber.substring(1,deluxeVacantRoomNumber.length()-1);
	System.out.println("Single: Number: "+ singleVacantCount +" out of "+singleTotalCount+" vacant");
	System.out.println("        Available Rooms: "+singleVacantRoomNumber);
	System.out.println("Standard: Number: "+ standardVacantCount +" out of "+standardTotalCount+" vacant");
	System.out.println("        Available Rooms: "+standardVacantRoomNumber);
	System.out.println("Deluxe: Number: "+ deluxeVacantCount +" out of "+deluxeTotalCount+" vacant");
	System.out.println("        Available Rooms: "+deluxeVacantRoomNumber);
	System.out.println("Suite: Number: "+ suiteVacantCount +" out of "+suiteTotalCount+" vacant");
	System.out.println("        Available Rooms: "+suiteVacantRoomNumber);
	System.out.println("VIP: Number: "+ VIPVacantCount +" out of "+VIPTotalCount+" vacant");
	System.out.println("        Available Rooms: "+VIPVacantRoomNumber);
	
	}
}

/**
 * All room numbers, organized by status.
 * e.g. Vacant : 02-35,02-36,02-38
 * 		Occupied:02-39,02-61
 */
public void GetRoomStatisticsByStatus() {
	String vacantRoomNumbers;
	String occupiedRoomNumbers;
	String reservedRoomNumbers;
	String maintenanceRoomNumbers;
	for(var i=0; i<room_list_.size();i++) {
		RoomStatus status= room_list_.get(i).GetStatus();
		switch(status) {
		
		case Vacant:
			vacantRoomNumbers= vacantRoomNumbers+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
			break;
		case Occupied:
			occupiedRoomNumbers=occupiedRoomNumbers+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
			break;
		case Reserved:
			reservedRoomNumbers=reservedRoomNumbers+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
			break;
		case Maintenance:
			maintenanceRoomNumbers=maintenanceRoomNumbers+Integer.toString(room_list_.get(i).GetRoomNumber())+",";
			break;
		default:
			break;
		}
	}
	System.out.println("Vacant: ");
	System.out.println("       Rooms:"+vacantRoomNumbers.substring(1,vacantRoomNumbers.length()-1));
	System.out.println("Occupied:");
	System.out.println("       Rooms:"+occupiedRoomNumbers.substring(1,vacantRoomNumbers.length()-1));
	System.out.println("Reserved: ");
	System.out.println("       Rooms:"+reservedRoomNumbers.substring(1,reservedRoomNumbers.length()-1));
	System.out.println("Maintenance: ");
	System.out.println("       Rooms:"+maintenanceRoomNumbers.substring(1,maintenanceRoomNumbers.length()-1));
}

}