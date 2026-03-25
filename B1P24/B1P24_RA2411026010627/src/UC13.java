import java.util.*;

public class PalindromeCheckerApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sentence: ");
        String input = scanner.nextLine();

        String normalized = input.toLowerCase()
                .replaceAll("[^a-z0-9]", "");

        // Run performance test
        benchmark("Stack Strategy", normalized, new StackStrategy());
        benchmark("Deque Strategy", normalized, new DequeStrategy());
        benchmark("Recursive Strategy", normalized, new RecursiveStrategy());

        scanner.close();
    }

    // Benchmark method
    public static void benchmark(String name, String input, PalindromeStrategy strategy) {

        long startTime = System.nanoTime();

        boolean result = strategy.isPalindrome(input);

        long endTime = System.nanoTime();

        long duration = endTime - startTime;

        System.out.println("----------------------------------");
        System.out.println("Algorithm: " + name);
        System.out.println("Result: " + result);
        System.out.println("Execution Time (ns): " + duration);
    }
}

/* ================= Strategy Interface ================= */
interface PalindromeStrategy {
    boolean isPalindrome(String input);
}

/* ================= Stack Strategy ================= */
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

/* ================= Deque Strategy ================= */
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

/* ================= Recursive Strategy ================= */
class RecursiveStrategy implements PalindromeStrategy {

    public boolean isPalindrome(String input) {
        return check(input, 0, input.length() - 1);
    }

    private boolean check(String str, int left, int right) {

        if (left >= right) {
            return true;
        }

        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }

        return check(str, left + 1, right - 1);
    }
}