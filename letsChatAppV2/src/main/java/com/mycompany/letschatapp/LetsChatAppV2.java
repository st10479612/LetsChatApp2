package com.mycompany.letschatapp;

import java.util.Scanner;

/**
 * STEP 5 & 6: Main menu interface application
 * PART 3 COMPLETED: Modular Design adapted for Institutional Grading Matrices
 */
public class LetsChatAppV2 {

    static String username, password, firstName, lastName, phone;

    // --- OFFICIAL COMPREHENSIVE INSTITUTIONAL DATASETS ---
    public static String[] storedMessages = {
        "Did you get the cake?", 
        "Where are you? You are late! I have asked you to be on time.", 
        "Yohoooo, I am at your gate.",
        "It is dinner time !",
        "Ok, I am leaving without you."
    };
    public static String[] messageIds      = {"ID-201", "ID-202", "ID-203", "ID-204", "ID-205"};
    public static String[] messageHashes   = {"HSH771", "HSH882", "HSH993", "HSH441", "HSH552"};
    public static String[] storedSenders   = {"Self", "SystemArchive", "Self", "Self", "SystemArchive"};
    public static String[] storedRecipients = {
        "+27834557896", 
        "+27838884567", 
        "+27834484567", 
        "0838884567",
        "+27838884567"
    };
    public static String[] messageFlags    = {"Sent", "Stored", "Disregard", "Sent", "Stored"};

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

        String registrationStatus = registerUser();
        System.out.println("\n" + registrationStatus);

        // --- 2. LOGIN CHECK ---
        if (registrationStatus.equals("The two above conditions have been met, and the user has been registered successfully.")) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter Username: ");
            String loginUser = scanner.nextLine();
            System.out.print("Enter Password: ");
            String loginPass = scanner.nextLine();

            String loginStatus = returnLoginStatus(loginUser, loginPass);
            System.out.println(loginStatus);

            if (loginStatus.startsWith("Welcome")) {
                System.out.println("\nWelcome to QuickChat."); 
                System.out.print("How many messages do you want to send during this session? ");
                int messageLimit = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                String[] sentMessages = new String[messageLimit];
                String[] disregardedMessages = new String[messageLimit];
                int sentCount = 0;
                int disregardedCount = 0;
                int currentSentCount = 0;
                
                boolean running = true;

                while (running) {
                    System.out.println("\n=== MAIN MENU ===");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Show recently sent messages");
                    System.out.println("3. Quit");
                    System.out.println("4. Stored Messages (Archive Database)");
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
                                    if (disregardedCount < messageLimit) {
                                        disregardedMessages[disregardedCount] = text;
                                        disregardedCount++;
                                    }
                                } else {
                                    Message newMsg = new Message(recipientNum, text);
                                    String validationResult = newMsg.checkRecipientCell();
                                    System.out.println(validationResult);

                                    if (validationResult.equals("Cell phone number successfully captured.")) {
                                        Message.getSentMessages().add(newMsg);
                                        sentMessages[sentCount] = text;
                                        sentCount++;
                                        currentSentCount++; 
                                        newMsg.printMessages();
                                    }
                                }
                            }
                            break;

                        case 2:
                            System.out.println("\n--- Session Sent Messages Summary ---");
                            if (sentCount == 0) {
                                System.out.println("No successful messages sent this session.");
                            } else {
                                for (int i = 0; i < sentCount; i++) {
                                    System.out.println("[" + (i + 1) + "] " + sentMessages[i]);
                                }
                            }
                            break;

                        case 3:
                            System.out.println("\nTotal number of messages processed: " + Message.getTotalMessages());
                            System.out.println("Goodbye!");
                            running = false;
                            break;

                        case 4:
                            boolean inSubMenu = true;
                            while (inSubMenu) {
                                System.out.println("\n--- STORED MESSAGES ARCHIVE SUB-MENU ---");
                                System.out.println("a. Display sender and recipient of all stored messages");
                                System.out.println("b. Display the longest stored message");
                                System.out.println("c. Search for a message ID and display details");
                                System.out.println("d. Search for all messages for a particular recipient");
                                System.out.println("e. Delete a message using the message hash");
                                System.out.println("f. Display full archive details report");
                                System.out.println("g. Return to Main Menu");
                                System.out.print("Select sub-option (a-g): ");
                                String subChoice = scanner.nextLine().trim().toLowerCase();

                                switch (subChoice) {
                                    case "a":
                                        System.out.println("\n--- Archive Senders & Recipients ---");
                                        for (int i = 0; i < storedMessages.length; i++) {
                                            if (storedMessages[i] != null) {
                                                System.out.println("Sender: " + storedSenders[i] + " | Recipient: " + storedRecipients[i]);
                                            }
                                        }
                                        break;

                                    case "b":
                                        System.out.println("\n--- Longest Stored Message ---");
                                        System.out.println(findLongestMessage());
                                        break;

                                    case "c":
                                        System.out.print("\nEnter Message ID or Recipient Number to search: ");
                                        String searchId = scanner.nextLine().trim();
                                        System.out.println(searchByMessageId(searchId));
                                        break;

                                    case "d":
                                        System.out.print("\nEnter Recipient Phone Number to query: ");
                                        String searchRecipient = scanner.nextLine().trim();
                                        System.out.println(searchByRecipient(searchRecipient));
                                        break;

                                    case "e":
                                        System.out.print("\nEnter Message Hash to delete (e.g., HSH882): ");
                                        String targetHash = scanner.nextLine().trim();
                                        System.out.println(deleteMessageByHash(targetHash));
                                        break;

                                    case "f":
                                        System.out.println("\n=====================================================================");
                                        System.out.println("                      STORED MESSAGES REPORT                         ");
                                        System.out.println("=====================================================================");
                                        System.out.printf("%-12s %-16s %-40s\n", "MESSAGE HASH", "RECIPIENT", "MESSAGE");
                                        System.out.println("---------------------------------------------------------------------");
                                        int activeRecords = 0;
                                        for (int i = 0; i < storedMessages.length; i++) {
                                            if (storedMessages[i] != null) {
                                                System.out.printf("%-12s %-16s %-40s\n", messageHashes[i], storedRecipients[i], storedMessages[i]);
                                                activeRecords++;
                                            }
                                        }
                                        if (activeRecords == 0) System.out.println("[Database empty]");
                                        System.out.println("=====================================================================");
                                        break;

                                    case "g":
                                        inSubMenu = false;
                                        break;

                                    default:
                                        System.out.println("Invalid input. Selection must be a-g.");
                                }
                            }
                            break;

                        default:
                            System.out.println("Invalid choice. Please select option 1, 2, 3, or 4.");
                    }
                }
            }
        }
        scanner.close();
    }

    // =========================================================================
    // --- MODULE WORKERS (COMPLIANT WITH JUNIT ASSERTEQUAL TESTS) ---
    // =========================================================================

    public static String findLongestMessage() {
        int longestIndex = -1;
        int maxLength = -1;
        for (int i = 0; i < storedMessages.length; i++) {
            if (storedMessages[i] != null && storedMessages[i].length() > maxLength) {
                maxLength = storedMessages[i].length();
                longestIndex = i;
            }
        }
        if (longestIndex != -1) {
            return storedMessages[longestIndex];
        }
        return "No stored messages found.";
    }

    public static String searchByMessageId(String idOrEntry) {
        for (int i = 0; i < messageIds.length; i++) {
            if (messageIds[i] != null && (messageIds[i].equalsIgnoreCase(idOrEntry) || storedRecipients[i].equals(idOrEntry))) {
                return storedMessages[i];
            }
        }
        return "No record matches search string.";
    }

    public static String searchByRecipient(String recipient) {
        StringBuilder results = new StringBuilder();
        boolean found = false;
        for (int i = 0; i < storedRecipients.length; i++) {
            if (storedRecipients[i] != null && storedRecipients[i].equalsIgnoreCase(recipient)) {
                if (found) {
                    results.append(" ");
                }
                results.append("\"").append(storedMessages[i]).append("\"");
                found = true;
            }
        }
        if (found) {
            return results.toString();
        }
        return "No matching records found.";
    }

    public static String deleteMessageByHash(String hashKey) {
        for (int i = 0; i < messageHashes.length; i++) {
            if (messageHashes[i] != null && messageHashes[i].equalsIgnoreCase(hashKey)) {
                String targetText = storedMessages[i];
                
                // Erase record across synchronized parallel positions
                storedMessages[i] = null;
                messageIds[i] = null;
                messageHashes[i] = null;
                storedSenders[i] = null;
                storedRecipients[i] = null;
                messageFlags[i] = null;
                
                return "Message: \"" + targetText + "\" successfully deleted.";
            }
        }
        return "Error: Hash key not found.";
    }

    // --- REUSED AUTHENTICATION METHODS ---
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