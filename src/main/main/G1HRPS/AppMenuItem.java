package main.G1HRPS;

public enum AppMenuItem {
    Quit(0),
    AddGuest(1),
    AddRoom(2),
    AddRoomServiceMenuItem(3),
    SearchGuest(4),
    SearchRoom(5),
    SearchReservations(6),
    Display(7);

    private final int value;

    private AppMenuItem(int value) {
        this.value = value;
    }

    public int GetValue() {
        return value;
    }
}