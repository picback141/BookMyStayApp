import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Deluxe", 1);
        inventory.put("Standard", 1);
        inventory.put("Suite", 0);
    }

    public void validate(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    public void allocate(String roomType) throws InvalidBookingException {
        validate(roomType);
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class BookingService {
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void book(Reservation r) {
        try {
            inventoryService.allocate(r.getRoomType());
            System.out.println("Booking confirmed for " + r.getGuestName() + " (" + r.getRoomType() + ")");
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed for " + r.getGuestName() + ": " + e.getMessage());
        }
    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        bookingService.book(new Reservation("Alice", "Deluxe"));
        bookingService.book(new Reservation("Bob", "Suite"));
        bookingService.book(new Reservation("Charlie", "Premium"));
        bookingService.book(new Reservation("David", "Deluxe"));
    }
}