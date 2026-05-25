package com.mycompany.letschatapp;

import java.util.ArrayList;
import java.util.Random;

/**
 * STEP 2 & 4: Message class implementation
 */
public class Message {
    // --- STEP 2: Plan the Message Class Fields ---
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    
    private static int totalMessages = 0;
    private static ArrayList<Message> sentMessages = new ArrayList<>();

    // --- STEP 3: Plan the Constructor ---
    public Message(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateRandom10DigitID();
        totalMessages++;
        this.messageNumber = totalMessages;
        this.messageHash = generateHash();
    }

    private String generateRandom10DigitID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    private String generateHash() {
        String combined = "HASH" + this.messageID + this.recipient.replace("+", "");
        return combined.toUpperCase();
    }

    // --- STEP 4: Implement Each Method ---
    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (this.recipient.length() <= 19 && this.recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public void printMessages() {
        System.out.println("\n-------------------------------------");
        System.out.println("  MESSAGE DETAILS CAPTURED   ");
        System.out.println("-------------------------------------");
        System.out.println("Message ID     : " + messageID);
        System.out.println("Message Number : " + messageNumber);
        System.out.println("Recipient Cell : " + recipient);
        System.out.println("Message Text   : " + message);
        System.out.println("Message Hash   : " + messageHash);
        System.out.println("-------------------------------------");
    }

    public static int getTotalMessages() {
        return totalMessages;
    }

    public static ArrayList<Message> getSentMessages() {
        return sentMessages;
    }
}