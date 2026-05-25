package com.mycompany.letschatapp;

import java.util.Scanner;

/**
 * STEP 5 & 6: Main menu interface application
 */
public class LetsChatAppV2 {

    // Global properties allowing user credential verification across methods
    static String username, password, firstName, lastName, phone;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- 1. COLLECT ALL INPUTS FIRST ---
        System.out.println("=== User Registration ===");
        System.out.print("First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Username (Max 5 chars, must include '_'): ");
        username = scanner.nextLine();
        System.out.print("Password (Min 8 chars, Upper, Lower, Number, Special): ");
        password = scanner.nextLine();
        System.out.print("Phone Number (e.g., +27677844509): ");
        phone = scanner.nextLine();

        // Run registration checks through the processing method
        String registrationStatus = registerUser();
        System.out.println("\n" + registrationStatus);

        // --- 2. LOGIN CHECK (STEP 5.1) ---
        if (registrationStatus.equals("The two above conditions have been met, and the user has been registered successfully.")) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter Username: ");
            String loginUser = scanner.nextLine();
            System.out.print("Enter Password: ");
            String loginPass = scanner.nextLine();

            String loginStatus = returnLoginStatus(loginUser, loginPass);
            System.out.println(loginStatus);

            // Access granted if welcome text format matches successfully
            if (loginStatus.startsWith("Welcome")) {
                
                System.out.println("\nWelcome to QuickChat."); 
                
                System.out.print("How many messages do you want to send during this session? ");
                int messageLimit = scanner.nextInt();
                scanner.nextLine(); // Clear scanner buffer
                
                int currentSentCount = 0; 
                boolean running = true;

                // MAIN MENU LOOP
                while (running) {
                    System.out.println("\n=== MAIN MENU ===");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Show recently sent messages");
                    System.out.println("3. Quit");
                    System.out.print("Select an option: ");
                    
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

                    switch (choice) {
                        case 1:
                            if (currentSentCount >= messageLimit) {
                                System.out.println("You have reached your message limit for this session.");
                            } else {
                                System.out.print("Enter recipient cell number (e.g., +27...): ");
                                String recipientNum = scanner.nextLine();

                                System.out.print("Enter your message text: ");
                                String text = scanner.nextLine();

                                if (text.length() > 250) {
                                    System.out.println("Please enter a message of less than 250 characters.");
                                } else {
                                    // Calls your separate Message.java class object
                                    Message newMsg = new Message(recipientNum, text);

                                    String validationResult = newMsg.checkRecipientCell();
                                    System.out.println(validationResult);

                                    if (validationResult.equals("Cell phone number successfully captured.")) {
                                        Message.getSentMessages().add(newMsg);
                                        currentSentCount++; 
                                        newMsg.printMessages();
                                    }
                                }
                            }
                            break;

                        case 2:
                            System.out.println("Coming Soon");
                            break;

                        case 3:
                            System.out.println("\nTotal number of messages processed: " + Message.getTotalMessages());
                            System.out.println("Goodbye!");
                            running = false;
                            break;

                        default:
                            System.out.println("Invalid choice. Please select option 1, 2, or 3.");
                    }
                }
            }
        }

        scanner.close();
    } // End of Main

    // --- REUSED AUTHENTICATION METHODS FROM PART 1 ---

    public static boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity() {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*()].*");
    }

    public static boolean checkCellPhoneNumber() {
        return phone.startsWith("+27") &&
               phone.substring(3).length() >= 9 &&
               phone.substring(3).length() <= 10;
    }

    public static String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        else if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        else if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        else {
            System.out.println("Password successfully captured.");
            System.out.println("Cell number successfully captured.");
            return "The two above conditions have been met, and the user has been registered successfully.";
        }
    }

    public static String returnLoginStatus(String loginUser, String loginPass) {
        if (loginUser.equals(username) && loginPass.equals(password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}