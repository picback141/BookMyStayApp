import java.util.Scanner;

public class UC9 {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     PALINDROME CHECKER APP     ");
        System.out.println("=================================");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a word: ");
        String word = scanner.nextLine();

        boolean result = isPalindromeRecursive(word, 0, word.length() - 1);

        if (result) {
            System.out.println(word + " is a Palindrome");
        } else {
            System.out.println(word + " is NOT a Palindrome");
        }

        scanner.close();
    }

    // Recursive Palindrome Function
    public static boolean isPalindromeRecursive(String str, int start, int end) {

        // Base Condition
        if (start >= end) {
            return true;
        }

        // Mismatch condition
        if (str.charAt(start) != str.charAt(end)) {
            return false;
        }

        // Recursive Call
        return isPalindromeRecursive(str, start + 1, end - 1);
    }
}