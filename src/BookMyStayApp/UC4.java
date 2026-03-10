import java.util.HashMap;

abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void showDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: $" + price);
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 80);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 120);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 250);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class RoomSearchService {

    private RoomInventory inventory;

    RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    void search(Room[] rooms) {
        System.out.println("Available Rooms:\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.type);

            if (available > 0) {
                room.showDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

public class UC4 {

    public static void main(String[] args) {

        System.out.println("Book My Stay App");
        System.out.println("Hotel Booking Management System\n");

        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.search(rooms);
    }
}