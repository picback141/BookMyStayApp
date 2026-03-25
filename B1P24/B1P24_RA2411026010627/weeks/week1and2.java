import java.util.*;

class AllInOneHashSystems {

    static Map<String, Integer> usernameMap = new HashMap<>();
    static Map<String, Integer> usernameAttempts = new HashMap<>();

    static boolean checkUsername(String name) {
        usernameAttempts.put(name, usernameAttempts.getOrDefault(name, 0) + 1);
        return !usernameMap.containsKey(name);
    }

    static List<String> suggest(String name) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            list.add(name + i);
        }
        return list;
    }

    static Map<String, Integer> inventory = new HashMap<>();
    static Queue<String> waitingList = new LinkedList<>();

    static void initInventory() {
        inventory.put("IPHONE", 2);
    }

    static synchronized void purchase(String user) {
        if (inventory.get("IPHONE") > 0) {
            inventory.put("IPHONE", inventory.get("IPHONE") - 1);
            System.out.println(user + " purchased. Remaining: " + inventory.get("IPHONE"));
        } else {
            waitingList.add(user);
            System.out.println(user + " added to waiting list");
        }
    }

    static class DNSEntry {
        String ip;
        long expiry;

        DNSEntry(String ip, long ttl) {
            this.ip = ip;
            this.expiry = System.currentTimeMillis() + ttl;
        }
    }

    static Map<String, DNSEntry> dnsCache = new HashMap<>();

    static String resolve(String domain) {
        if (dnsCache.containsKey(domain)) {
            DNSEntry e = dnsCache.get(domain);
            if (System.currentTimeMillis() < e.expiry) {
                return "Cache HIT: " + e.ip;
            }
        }
        String ip = "1.1.1." + new Random().nextInt(100);
        dnsCache.put(domain, new DNSEntry(ip, 5000));
        return "Cache MISS: " + ip;
    }

    static Map<String, Integer> pageViews = new HashMap<>();
    static Map<String, Set<String>> uniqueUsers = new HashMap<>();

    static void processEvent(String page, String user) {
        pageViews.put(page, pageViews.getOrDefault(page, 0) + 1);
        uniqueUsers.putIfAbsent(page, new HashSet<>());
        uniqueUsers.get(page).add(user);
    }

    static void showTopPages() {
        pageViews.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(3)
                .forEach(System.out::println);
    }

    static Map<String, Integer> rateLimit = new HashMap<>();

    static boolean allowRequest(String user) {
        rateLimit.put(user, rateLimit.getOrDefault(user, 0) + 1);
        return rateLimit.get(user) <= 5;
    }

    static int[] twoSum(int[] arr, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int comp = target - arr[i];
            if (map.containsKey(comp)) {
                return new int[]{map.get(comp), i};
            }
            map.put(arr[i], i);
        }
        return new int[]{-1, -1};
    }

    static Map<String, Integer> searchFreq = new HashMap<>();

    static List<String> autocomplete(String prefix) {
        List<String> res = new ArrayList<>();
        for (String key : searchFreq.keySet()) {
            if (key.startsWith(prefix)) res.add(key);
        }
        res.sort((a, b) -> searchFreq.get(b) - searchFreq.get(a));
        return res.subList(0, Math.min(3, res.size()));
    }

    static class LRUCache extends LinkedHashMap<String, String> {
        int capacity;

        LRUCache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > capacity;
        }
    }

    public static void main(String[] args) {

        System.out.println(checkUsername("john"));
        System.out.println(suggest("john"));

        initInventory();
        purchase("User1");
        purchase("User2");
        purchase("User3");

        System.out.println(resolve("google.com"));
        System.out.println(resolve("google.com"));

        processEvent("/home", "u1");
        processEvent("/home", "u2");
        processEvent("/about", "u1");
        showTopPages();

        System.out.println(allowRequest("client1"));
        System.out.println(allowRequest("client1"));

        int[] arr = {2, 7, 11, 15};
        System.out.println(Arrays.toString(twoSum(arr, 9)));

        searchFreq.put("java", 10);
        searchFreq.put("javascript", 8);
        searchFreq.put("python", 6);
        System.out.println(autocomplete("ja"));

        LRUCache cache = new LRUCache(2);
        cache.put("A", "1");
        cache.put("B", "2");
        cache.get("A");
        cache.put("C", "3");
        System.out.println(cache);
    }
}