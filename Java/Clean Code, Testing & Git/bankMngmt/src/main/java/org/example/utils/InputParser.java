package org.example.utils;

import org.example.exceptions.QuitApplicationException;

import java.util.Scanner;

public class InputParser {
    private static final Scanner sc = new Scanner(System.in);

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

    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            checkRequestQuitApplication(line);
            if (!line.isEmpty()) return line;
            System.out.println("Input cannot be blank.");
        }
    }

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

    public static void pressEnter() {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }
    public static void checkRequestQuitApplication(String input){
        if(input.trim().equalsIgnoreCase("quit")){
            throw new QuitApplicationException();
        }
    }
}
