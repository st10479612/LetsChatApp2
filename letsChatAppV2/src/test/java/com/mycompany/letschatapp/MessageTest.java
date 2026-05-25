package com.mycompany.letschatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * STEP 1.3: JUnit tests for the Message Class
 */
public class MessageTest {

    @Test
    public void testCheckMessageID_Valid() {
        // Create a message object with valid details
        Message msg = new Message("+27821234567", "Test Message");
        
        // Assert that checkMessageID returns true (ID must be 10 digits or less)
        assertTrue(msg.checkMessageID(), "Message ID should be 10 characters or less.");
    }

    @Test
    public void testCheckRecipientCell_Success() {
        // Create a message with a correctly formatted South African international cell number
        Message msg = new Message("+27821234567", "Hello Friend");
        
        String expected = "Cell phone number successfully captured.";
        String actual = msg.checkRecipientCell();
        
        // Assert that the exact success message string is returned
        assertEquals(expected, actual, "Should return success message for numbers starting with +");
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        // Create a message missing the '+' sign or incorrectly formatted
        Message msg = new Message("0821234567", "Hello Friend");
        
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        String actual = msg.checkRecipientCell();
        
        // Assert that the exact failure message string is returned
        assertEquals(expected, actual, "Should return failure message if international prefix is missing.");
    }
}