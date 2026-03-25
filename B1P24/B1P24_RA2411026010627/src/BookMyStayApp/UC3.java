import java.util.HashMap;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    // constructor initializes room availability
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // display inventory
    public void showInventory() {
        System.out.println("Current Room Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " -> " + inventory.get(room) + " available");
        }
    }
}

public class UC3 {

    public static void main(String[] args) {

        System.out.println("Book My Stay App");
        System.out.println("Hotel Booking Management System");
        System.out.println("Version 3.1\n");

        RoomInventory inventory = new RoomInventory();

        inventory.showInventory();
        git config user.name
        git config user.email
        System.out.println("\nChecking availability for Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        System.out.println("\nUpdating availability for Single Room...");
        inventory.updateAvailability("Single Room", 4);

        System.out.println("\nUpdated Inventory:");
        inventory.showInventory();
    }
}