import java.util.*;

class Reservation {
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

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }
}

class BookingReportService {
    public void displayAll(List<Reservation> history) {
        for (Reservation r : history) {
            System.out.println(r);
        }
    }

    public void summary(List<Reservation> history) {
        Map<String, Integer> count = new HashMap<>();
        for (Reservation r : history) {
            count.put(r.getRoomType(), count.getOrDefault(r.getRoomType(), 0) + 1);
        }
        for (String type : count.keySet()) {
            System.out.println(type + ": " + count.get(type));
        }
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        history.add(new Reservation("Alice", "Deluxe", "D1"));
        history.add(new Reservation("Bob", "Standard", "S1"));
        history.add(new Reservation("Charlie", "Suite", "S2"));
        history.add(new Reservation("David", "Deluxe", "D2"));

        BookingReportService report = new BookingReportService();

        System.out.println("Booking History:");
        report.displayAll(history.getAll());

        System.out.println("Summary Report:");
        report.summary(history.getAll());
    }
}