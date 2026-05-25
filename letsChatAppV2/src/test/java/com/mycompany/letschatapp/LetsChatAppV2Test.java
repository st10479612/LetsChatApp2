package com.mycompany.letschatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional tests for User Authentication Logic
 */
public class LetsChatAppV2Test {

    @Test
    public void testCheckUserName_Valid() {
        // Set up a valid username (under 5 characters and contains an underscore)
        LetsChatAppV2.username = "Nth_M";
        assertTrue(LetsChatAppV2.checkUserName(), "Username should be valid if it contains '_' and is <= 5 characters.");
    }

    @Test
    public void testCheckUserName_Invalid() {
        // Set up an invalid username (too long and no underscore)
        LetsChatAppV2.username = "Nthabiseng";
        assertFalse(LetsChatAppV2.checkUserName(), "Username should fail if it exceeds 5 characters or lacks an underscore.");
    }

    @Test
    public void testCheckPasswordComplexity_Success() {
        // Set up a strong password matching all criteria
        LetsChatAppV2.password = "SecureP@ss123";
        assertTrue(LetsChatAppV2.checkPasswordComplexity(), "Password should pass complexity checks.");
    }
}