package main.G1HRPS;
private static ArrayList<Room> room_list_;
/**
 * A manager class that stores the Room Object as elements in a list. Search methods
 * parses through the list of rooms, comparing instance variables of each room object with the arguments passed in.
 * @author LiangHee
 *
 */
public RoomManager() {

}

/**
 * Takes in a class object and list to add the object into.
 * @param room This is the room object to be added
 */
public static void AddToList(Room room) {
	room_list_.add(room);
}

/** 
 * This method takes in input info about room from the menu, creates a new room object, and adds that new object 
 * to room_list_.
 * The paramaters are the same as in Room constructor.
 */
public static void addNewRoom(RoomType room_type, float price, String room_number, BedSize bedSize, boolean wifiEnabled, boolean withView, boolean Smoking, RoomStatus status){
	Room room = new Room(room_type, price, room_number, bedSize, wifiEnabled, withView, Smoking, status);
	AddToList(room);
}

/**
 * Takes in user input from menu and searches for the same string in room number and guestname details of room list.
 * If same string found, returns object room
 * @param search_text This is the input that user keys in
 */
public Room SearchList(String search_text) {  
	for(var i=0; i<room_list_.size();i++) { //searching for same Room Name
		if (room_list_.get(i).getRoomNumber()==search_text) {
			return room_list.get(i);
		}
	}
	for(var i=0; i<room_list_.size();i++) {
		for(var j=0; j<room_list_.get(i).getGuestList().size();j++) { //searching for same Guest Name
			if(room_list_.get(i).getGuestList().get(j).getName==search_text) {
				return room_list.get(i);
			}
		}	
	system.out.println("No room with details entered was found");
	}	
	throw new UnsupportedOperationException();
}
/**
 * Removes a room from room_list_, user's input is the room number
 * @param room_num_ Room number that user wants to remove from List.
 */
public void RemoveFromList(String roomNumber) { // it will not work if we just make a new room with the same parameters, since it will be stored in diff address. Suggest taking in roomNumber
	Room room = SearchList(roomNumber);
	system.out.println("Room with same room number found");
	room_list_.remove(room);
}

/**
 * Get room details of a room number
 * @param roomNumber  This is the room number entered by user.
 */
public void GetRoomDetailByRoomNumber(String roomNumber) {
	Room room = SearchList(roomNumber);
	system.out.println("Room with same room number found");
	room.viewRoomDetails;
		}
	}	
}

//does not deal with same names(rare case but still possible, it will print two room's details in this case)
// Possible workaround by adding passport/driving license, but it will cause every room detail check to require to need to enter passport which is inefficient.
/**
 * Get room details by entering name of guest staying in that room
 * @param guestName This is the name of the guest.
 */
public void GetRoomDetailByGuestName(String guestName) {
	Room room = SearchList(guestName);
	system.out.println("Room with same guest name found");
	room.viewRoomDetails;

/**
 * Checks whether a room is vacant or not.(currently)
 * @param roomNumber Room number of room to be checked.
 */
public void CheckRoomAvailabilityByRoomNumber(String roomNumber) {// consider using room type to get max number of ppl staying in the room, and compare that to guest list of room
		for(var i=0; i<room_list_.size();i++) {
			if (room_list_.get(i).getRoomNumber()==roomNumber) {
				room_list_.get(i).checkRoomAvailability();;
				break;
			}
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
public void CheckInRoom(Guest guest, String roomNumber) {//consider printing out room is occupied if user atttempts to add guest to room awlr with ppl
	for(var i=0; i<room_list_.size();i++) {
		if (room_list_.get(i).getRoomNumber()==roomNumber) {
			room_list_.get(i).addGuest(guest);
			room_list_.get(i).setStatus(Occupied);
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
public void CheckOutAllGuest(String roomNumber) {
	for(var i=0; i<room_list_.size();i++) {
		if (room_list_.get(i).getRoomNumber()==roomNumber) {
			room_list_.get(i).clearGuests();
			room_list_.get(i).setStatus(Vacant);		
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
public void CheckOutOneGuest(String roomNumber,String guestName) {//note: guest Name required from user is full name of guest
	for(var i=0; i<room_list_.size();i++) { 
		if (room_list_.get(i).getRoomNumber()==roomNumber) {   //if room matches room number
			for(var j=0; j<room_list_.get(i).getGuestList().size();j++) {
				if(room_list_.get(i).getGuestList().get(j).getName==guestName) { // if guest name matches guest
					room_list_.get(i).checkOutGuest(guestName);
					break;
				}
				if (room_list_.get(i).getGuestList().size()==0) { // if guest list is empty, set room status to vacant
					room_list_.get(i).setStatus(Vacant);
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
public void SetRoomStatus(String roomNumber, RoomStatus status) {
	for(var i=0; i<room_list_.size();i++) {
		if (room_list_.get(i).getRoomNumber()==roomNumber) {
			room_list_.get(i).setStatus(status);
		}
	throw new UnsupportedOperationException();
}
}

/**
 * All room numbers in room_list_, organized by room type .
 * e.g. Standard: Number: 2 out of 15 occupied 
 * 				  Rooms: 02-21,02-22,02-33,02-44,02-55
 * 		VIP:      Number: 3 out of 5 occupied
 */
public void GetRoomStatisticsByTypeOccupancyRate() {
	int singleTotalCount;
	int singleVacantCount;
	String singleRoomNumber="";
	int standardTotalCount;
	int standardVacantCount;
	String standardRoomNumber="";
	int deluxeTotalCount;
	int deluxeVacantCount;
	String deluxeRoomNumber="";
	int suiteTotalCount;
	int suiteVacantCount;
	String suiteRoomNumber="";
	int VIPTotalCount;
	int VIPVacantCount;
	String VIPRoomNumber="";
	
	for(var i=0; i<room_list_.size();i++) {
		RoomType type = room_list_.get(i).getRoomType();
		switch(type) { //This switch block acts as a counter for different room types
		
		case Single:
			singleTotalCount+=1;
			singleRoomNumber=singleRoomNumber+room_list_.get(i).getRoomNumber()+",";
			if(room_list_.get(i).getStatus()==RoomStatus.Vacant) {
				singleVacantCount+=1;
			}
			break;	
		case Standard:
			standardTotalCount+=1;
			if(room_list_.get(i).getStatus()==RoomStatus.Vacant) {
				standardVacantCount+=1;
				standardRoomNumber=singleRoomNumber+room_list_.get(i).getRoomNumber()+",";
			}
			break;
		case Deluxe:
			deluxeTotalCount+=1;
			deluxeRoomNumber=deluxeRoomNumber+room_list_.get(i).getRoomNumber()+",";
			if(room_list_.get(i).getStatus()==RoomStatus.Vacant) {
				deluxeVacantCount+=1;
			}
			break;
		case Suite:
			suiteTotalCount+=1;
			suiteRoomNumber=suiteRoomNumber+room_list_.get(i).getRoomNumber()+",";
			if(room_list_.get(i).getStatus()==RoomStatus.Vacant) {
				suiteVacantCount+=1;
			}
			break;
		case VIP:
			VIPTotalCount+=1;
			VIPRoomNumber=VIPRoomNumber+room_list_.get(i).getRoomNumber()+",";
			if(room_list_.get(i).getStatus()==RoomStatus.Vacant) {
				VIPVacantCount+=1;
			}
			break;
		default:
			break;

			
		}
	singleRoomNumber =singleRoomNumber.substring(1,singleRoomNumber.length()-1); // removes the last comma in this String
	standardRoomNumber =standardRoomNumber.substring(1,standardRoomNumber.length()-1);
	suiteRoomNumber =suiteRoomNumber.substring(1,suiteRoomNumber.length()-1);
	VIPRoomNumber =VIPRoomNumber.substring(1,VIPRoomNumber.length()-1);
	deluxeRoomNumber =deluxeRoomNumber.substring(1,deluxeRoomNumber.length()-1);
	System.out.println("Single: Number: "+ singleVacantCount +" out of "+singleTotalCount+" occupied");
	System.out.println("        Rooms: "+singleRoomNumber);
	System.out.println("Standard: Number: "+ standardVacantCount +" out of "+standardTotalCount+" occupied");
	System.out.println("        Rooms: "+standardRoomNumber);
	System.out.println("Deluxe: Number: "+ deluxeVacantCount +" out of "+deluxeTotalCount+" occupied");
	System.out.println("        Rooms: "+deluxeRoomNumber);
	System.out.println("Suite: Number: "+ suiteVacantCount +" out of "+suiteTotalCount+" occupied");
	System.out.println("        Rooms: "+suiteRoomNumber);
	System.out.println("VIP: Number: "+ VIPVacantCount +" out of "+VIPTotalCount+" occupied");
	System.out.println("        Rooms: "+VIPRoomNumber);
	
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
		RoomStatus status= room_list_.get(i).getStatus();
		switch(status) {
		
		case Vacant:
			vacantRoomNumbers= vacantRoomNumbers+room_list_.get(i).getRoomNumber()+",";
			break;
		case Occupied:
			occupiedRoomNumbers=occupiedRoomNumbers+room_list_.get(i).getRoomNumber()+",";
			break;
		case Reserved:
			reservedRoomNumbers=reservedRoomNumbers+room_list_.get(i).getRoomNumber()+",";
			break;
		case Maintenance:
			maintenanceRoomNumbers=maintenanceRoomNumbers+room_list_.get(i).getRoomNumber()+",";
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