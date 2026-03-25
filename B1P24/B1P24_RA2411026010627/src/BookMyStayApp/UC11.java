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
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
        notifyAll();
    }

    public synchronized Reservation getNextRequest() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue.poll();
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 2);
        inventory.put("Suite", 1);
    }

    public synchronized boolean isAvailable(String roomType) {
        return inventory.containsKey(roomType) && inventory.get(roomType) > 0;
    }

    public synchronized boolean allocate(String roomType) {
        if (!inventory.containsKey(roomType)) return false;
        if (inventory.get(roomType) <= 0) return false;
        inventory.put(roomType, inventory.get(roomType) - 1);
        return true;
    }
}

class BookingService {
    private InventoryService inventoryService;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private int counter = 1;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void process(Reservation r) {
        synchronized (this) {
            String type = r.getRoomType();

            if (!inventoryService.allocate(type)) {
                System.out.println(Thread.currentThread().getName() + " Rejected: " + r.getGuestName());
                return;
            }

            String roomId = generateRoomId(type);
            allocatedRoomIds.add(roomId);

            System.out.println(Thread.currentThread().getName() + " Confirmed: " + r.getGuestName() + " -> " + roomId);
        }
    }

    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + counter++;
        } while (allocatedRoomIds.contains(id));
        return id;
    }
}

class BookingProcessor extends Thread {
    private BookingRequestQueue queue;
    private BookingService bookingService;

    public BookingProcessor(BookingRequestQueue queue, BookingService bookingService, String name) {
        super(name);
        this.queue = queue;
        this.bookingService = bookingService;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            Reservation r = queue.getNextRequest();
            bookingService.process(r);
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Deluxe"));
        queue.addRequest(new Reservation("Bob", "Standard"));
        queue.addRequest(new Reservation("Charlie", "Suite"));
        queue.addRequest(new Reservation("David", "Suite"));
        queue.addRequest(new Reservation("Eve", "Deluxe"));
        queue.addRequest(new Reservation("Frank", "Standard"));

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        Thread t1 = new BookingProcessor(queue, bookingService, "Thread-1");
        Thread t2 = new BookingProcessor(queue, bookingService, "Thread-2");

        t1.start();
        t2.start();
    }
}