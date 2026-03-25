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

    public String toString() {
        return guestName + " -> " + roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation processRequest() {
        return queue.poll();
    }

    public void displayQueue() {
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        requestQueue.addRequest(new Reservation("Alice", "Deluxe"));
        requestQueue.addRequest(new Reservation("Bob", "Standard"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite"));

        System.out.println("Current Queue:");
        requestQueue.displayQueue();

        System.out.println("Processing Requests:");
        System.out.println(requestQueue.processRequest());
        System.out.println(requestQueue.processRequest());

        System.out.println("Remaining Queue:");
        requestQueue.displayQueue();
    }
}