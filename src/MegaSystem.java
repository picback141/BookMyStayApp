import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MegaSystem {

    /* ==============================
       1. Username Availability
       ============================== */
    static class UsernameService {
        private final ConcurrentHashMap<String, Long> users = new ConcurrentHashMap<>();
        private final ConcurrentHashMap<String, Integer> attempts = new ConcurrentHashMap<>();

        public boolean checkAvailability(String username) {
            attempts.merge(username, 1, Integer::sum);
            return !users.containsKey(username);
        }

        public List<String> suggest(String username) {
            List<String> suggestions = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                suggestions.add(username + i);
            }
            return suggestions;
        }

        public String mostAttempted() {
            return attempts.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("None");
        }
    }

    /* ==============================
       2. Flash Sale Inventory
       ============================== */
    static class InventoryService {
        private final ConcurrentHashMap<String, AtomicInteger> stock = new ConcurrentHashMap<>();
        private final ConcurrentHashMap<String, Queue<Long>> waitingList = new ConcurrentHashMap<>();

        public void addProduct(String id, int quantity) {
            stock.put(id, new AtomicInteger(quantity));
            waitingList.put(id, new ConcurrentLinkedQueue<>());
        }

        public String purchase(String id, long userId) {
            AtomicInteger s = stock.get(id);
            if (s.get() > 0) {
                s.decrementAndGet();
                return "Purchase Success";
            } else {
                waitingList.get(id).add(userId);
                return "Added to Waiting List";
            }
        }

        public int checkStock(String id) {
            return stock.get(id).get();
        }
    }

    /* ==============================
       3. DNS Cache with TTL
       ============================== */
    static class DNSEntry {
        String ip;
        long expiry;

        DNSEntry(String ip, int ttlSeconds) {
            this.ip = ip;
            this.expiry = System.currentTimeMillis() + ttlSeconds * 1000;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiry;
        }
    }

    static class DNSCache {
        private final Map<String, DNSEntry> cache = new ConcurrentHashMap<>();
        private int hits = 0;
        private int misses = 0;

        public String resolve(String domain) {
            DNSEntry entry = cache.get(domain);

            if (entry != null && !entry.isExpired()) {
                hits++;
                return entry.ip + " (Cache HIT)";
            }

            misses++;
            String ip = "192.168.1." + new Random().nextInt(255);
            cache.put(domain, new DNSEntry(ip, 10));
            return ip + " (Cache MISS)";
        }

        public void stats() {
            System.out.println("Hits: " + hits + ", Misses: " + misses);
        }
    }

    /* ==============================
       4. Rate Limiter (Token Bucket)
       ============================== */
    static class TokenBucket {
        int tokens;
        final int maxTokens;
        final int refillRate;
        long lastRefill;

        TokenBucket(int maxTokens, int refillRate) {
            this.maxTokens = maxTokens;
            this.refillRate = refillRate;
            this.tokens = maxTokens;
            this.lastRefill = System.currentTimeMillis();
        }

        synchronized boolean allowRequest() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long elapsed = (now - lastRefill) / 1000;
            if (elapsed > 0) {
                tokens = Math.min(maxTokens, tokens + (int) elapsed * refillRate);
                lastRefill = now;
            }
        }
    }

    /* ==============================
       5. Real-Time Analytics
       ============================== */
    static class Analytics {
        private final Map<String, Integer> pageViews = new ConcurrentHashMap<>();

        public void recordVisit(String page) {
            pageViews.merge(page, 1, Integer::sum);
        }

        public void showTopPages() {
            pageViews.entrySet()
                    .stream()
                    .sorted((a, b) -> b.getValue() - a.getValue())
                    .limit(3)
                    .forEach(e -> System.out.println(e.getKey() + " → " + e.getValue()));
        }
    }

    /* ==============================
       6. Two-Sum Fraud Detection
       ============================== */
    static class FraudDetector {
        public boolean hasTwoSum(int[] arr, int target) {
            Set<Integer> set = new HashSet<>();
            for (int num : arr) {
                if (set.contains(target - num))
                    return true;
                set.add(num);
            }
            return false;
        }
    }

    /* ==============================
       7. L1 Cache (LRU)
       ============================== */
    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        LRUCache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    /* ==============================
       MAIN
       ============================== */
    public static void main(String[] args) {

        // Username Service
        UsernameService usernameService = new UsernameService();
        System.out.println("Username available? " + usernameService.checkAvailability("john"));
        System.out.println("Suggestions: " + usernameService.suggest("john"));

        // Inventory
        InventoryService inventory = new InventoryService();
        inventory.addProduct("IPHONE", 2);
        System.out.println(inventory.purchase("IPHONE", 101));
        System.out.println("Stock left: " + inventory.checkStock("IPHONE"));

        // DNS Cache
        DNSCache dns = new DNSCache();
        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));
        dns.stats();

        // Rate Limiter
        TokenBucket bucket = new TokenBucket(5, 1);
        System.out.println("Rate Limit Check: " + bucket.allowRequest());

        // Analytics
        Analytics analytics = new Analytics();
        analytics.recordVisit("/home");
        analytics.recordVisit("/home");
        analytics.recordVisit("/sports");
        analytics.showTopPages();

        // Fraud Detection
        FraudDetector fraud = new FraudDetector();
        int[] transactions = {300, 200, 500};
        System.out.println("Two-Sum Exists? " + fraud.hasTwoSum(transactions, 500));

        // LRU Cache
        LRUCache<String, String> cache = new LRUCache<>(2);
        cache.put("A", "DataA");
        cache.put("B", "DataB");
        cache.put("C", "DataC"); // A removed
        System.out.println("LRU Cache: " + cache.keySet());
    }
}