abstract class Room {

    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void showRoomDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: $" + price);
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

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("Book My Stay App");
        System.out.println("Hotel Booking Management System");
        System.out.println("Version 2.1\n");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Available Rooms\n");

        single.showRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doubleRoom.showRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.showRoomDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Application finished.");
    }
}