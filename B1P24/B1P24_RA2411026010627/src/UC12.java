import java.util.*;

public class PalindromeCheckerApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("     PALINDROME CHECKER APP     ");
        System.out.println("=================================");

        System.out.println("Choose Algorithm:");
        System.out.println("1. Stack Based");
        System.out.println("2. Deque Based");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter a word: ");
        String input = scanner.nextLine();

        // Normalize
        String normalized = input.toLowerCase()
                .replaceAll("[^a-z0-9]", "");

        PalindromeStrategy strategy;

        // Inject strategy dynamically
        if (choice == 1) {
            strategy = new StackStrategy();
        } else {
            strategy = new DequeStrategy();
        }

        PalindromeService service = new PalindromeService(strategy);

        boolean result = service.check(normalized);

        if (result) {
            System.out.println("Palindrome ✅");
        } else {
            System.out.println("Not Palindrome ❌");
        }

        scanner.close();
    }
}

/* ===========================
   Strategy Interface
   =========================== */
interface PalindromeStrategy {
    boolean isPalindrome(String input);
}

/* ===========================
   Stack Implementation
   =========================== */
class StackStrategy implements PalindromeStrategy {

    public boolean isPalindrome(String input) {

        Stack<Character> stack = new Stack<>();

        for (char ch : input.toCharArray()) {
            stack.push(ch);
        }

        for (char ch : input.toCharArray()) {
            if (ch != stack.pop()) {
                return false;
            }
        }

        return true;
    }
}

/* ===========================
   Deque Implementation
   =========================== */
class DequeStrategy implements PalindromeStrategy {

    public boolean isPalindrome(String input) {

        Deque<Character> deque = new LinkedList<>();

        for (char ch : input.toCharArray()) {
            deque.addLast(ch);
        }

        while (deque.size() > 1) {
            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }

        return true;
    }
}

/* ===========================
   Service Class
   =========================== */
class PalindromeService {

    private PalindromeStrategy strategy;

    public PalindromeService(PalindromeStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean check(String input) {
        return strategy.isPalindrome(input);
    }
}