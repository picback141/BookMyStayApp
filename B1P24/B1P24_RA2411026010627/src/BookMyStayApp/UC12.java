import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public String toString() {
        return guestName + " -> " + roomType + " (" + roomId + ")";
    }
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> history;

    public SystemState(Map<String, Integer> inventory, List<Reservation> history) {
        this.inventory = inventory;
        this.history = history;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    public void save(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("State saved.");
        } catch (IOException e) {
            System.out.println("Error saving state.");
        }
    }

    public SystemState load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("State loaded.");
            return (SystemState) in.readObject();
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 2);
        inventory.put("Suite", 1);
    }

    public void setInventory(Map<String, Integer> data) {
        inventory = data;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public boolean allocate(String type) {
        if (!inventory.containsKey(type) || inventory.get(type) <= 0) return false;
        inventory.put(type, inventory.get(type) - 1);
        return true;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void setHistory(List<Reservation> data) {
        history = data;
    }

    public List<Reservation> getHistory() {
        return history;
    }

    public void add(Reservation r) {
        history.add(r);
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        PersistenceService persistence = new PersistenceService();
        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        SystemState state = persistence.load();

        if (state != null) {
            inventory.setInventory(state.inventory);
            history.setHistory(state.history);
        }

        int counter = history.getHistory().size() + 1;

        String[] guests = {"Alice", "Bob", "Charlie"};
        String[] types = {"Deluxe", "Standard", "Suite"};

        for (int i = 0; i < guests.length; i++) {
            String type = types[i];
            if (inventory.allocate(type)) {
                String roomId = type.substring(0, 1).toUpperCase() + counter++;
                Reservation r = new Reservation(guests[i], type, roomId);
                history.add(r);
                System.out.println("Confirmed: " + r);
            } else {
                System.out.println("Rejected: " + guests[i]);
            }
        }

        persistence.save(new SystemState(inventory.getInventory(), history.getHistory()));
    }
}