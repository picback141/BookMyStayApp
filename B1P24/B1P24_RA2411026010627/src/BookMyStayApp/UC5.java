import java.util.*;

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

    @Override
    public String toString() {
        return "Reservation{" +
                "guestName='" + guestName + '\'' +
                ", roomType='" + roomType + '\'' +
                '}';
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation processRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void displayQueue() {
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        requestQueue.addRequest(new Reservation("Alice", "Deluxe"));
        requestQueue.addRequest(new Reservation("Bob", "Standard"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite"));

        requestQueue.displayQueue();

        System.out.println("Processing: " + requestQueue.processRequest());
        System.out.println("Processing: " + requestQueue.processRequest());

        requestQueue.displayQueue();
    }
}