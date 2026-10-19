package org.example.utils;

import org.example.exceptions.QuitApplicationException;

import java.util.Scanner;

/**
 * Console input helpers that re-prompt until valid input is entered.
 *
 * <p>All methods read from {@code System.in} through a shared
 * {@link Scanner}. If the user types {@code "quit"} (case-insensitive)
 * at any prompt, a {@link QuitApplicationException} is thrown so the
 * caller can unwind and exit cleanly.</p>
 */
public class InputParser {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * Reads an integer, re-prompting on invalid input.
     *
     * @param prompt the text to display before reading
     * @return the parsed integer
     * @throws QuitApplicationException if the user enters {@code "quit"}
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                checkRequestQuitApplication(line);
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Reads a double, re-prompting on invalid input.
     *
     * @param prompt the text to display before reading
     * @return the parsed double
     * @throws QuitApplicationException if the user enters {@code "quit"}
     */
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                checkRequestQuitApplication(line);
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Reads a non-blank string, re-prompting on empty input.
     *
     * @param prompt the text to display before reading
     * @return the trimmed, non-empty line
     * @throws QuitApplicationException if the user enters {@code "quit"}
     */
    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            checkRequestQuitApplication(line);
            if (!line.isEmpty()) return line;
            System.out.println("Input cannot be blank.");
        }
    }

    /**
     * Reads a yes/no answer ({@code Y} or {@code N}), re-prompting on
     * anything else.
     *
     * @param prompt the text to display before reading
     * @return {@code true} for {@code Y}, {@code false} for {@code N}
     * @throws QuitApplicationException if the user enters {@code "quit"}
     */
    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim().toUpperCase();
            checkRequestQuitApplication(line);
            if (line.equals("Y")) return true;
            if (line.equals("N")) return false;
            System.out.println("Please enter Y or N.");
        }
    }

    /**
     * Prints a "Press Enter to continue" prompt and blocks until the
     * user presses Enter.
     */
    public static void pressEnter() {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }

    /**
     * Throws {@link QuitApplicationException} if the given input is
     * {@code "quit"} (case-insensitive).
     *
     * @param input the user input to inspect
     * @throws QuitApplicationException if the user requested to quit
     */
    public static void checkRequestQuitApplication(String input) {
        if (input.trim().equalsIgnoreCase("quit")) {
            throw new QuitApplicationException();
        }
    }
}