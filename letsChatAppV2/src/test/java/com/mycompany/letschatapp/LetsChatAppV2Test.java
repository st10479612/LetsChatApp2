package com.mycompany.letschatapp;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Automated Unit Testing Matrix verifying Part 3 requirements
 */
public class LetsChatAppV2Test {
    
    @Test
    public void testFindLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        String actual = LetsChatAppV2.findLongestMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void testSearchByMessageId() {
        String expected = "It is dinner time !";
        String actual = LetsChatAppV2.searchByMessageId("0838884567");
        assertEquals(expected, actual);
    }

    @Test
    public void testSearchByRecipient() {
        String expected = "\"Where are you? You are late! I have asked you to be on time.\" \"Ok, I am leaving without you.\"";
        String actual = LetsChatAppV2.searchByRecipient("+27838884567");
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteMessageByHash() {
        String expected = "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        String actual = LetsChatAppV2.deleteMessageByHash("HSH882");
        assertEquals(expected, actual);
    }
}