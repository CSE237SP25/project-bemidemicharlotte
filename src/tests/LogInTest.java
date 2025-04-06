package tests;


import java.util.*;
import org.junit.jupiter.api.Assertions.*;
import bankapp.LogIn;

import org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LogInTest {

    private LogIn logIn;
    private Map<Integer, List<Object>> sampleAccounts;

    @BeforeEach
    public void setUp() {
        logIn = new LogIn();

        // Sample account data: [name, age, email, password]
        sampleAccounts = new HashMap<>();
        sampleAccounts.put(12345, Arrays.asList("Alice", 25, "alice@example.com", "password123"));
        sampleAccounts.put(67890, Arrays.asList("Bob", 30, "bob@example.com", "securepass"));

        logIn.setAccounts(sampleAccounts);
    }

    @Test
    public void testAccountCorrect_validAccount() {
        assertDoesNotThrow(() -> logIn.accountCorrect(12345));
    }

    @Test
    public void testAccountCorrect_invalidAccount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> logIn.accountCorrect(99999));
        assertEquals("Invalid Account Number", exception.getMessage());
    }

    @Test
    public void testCorrectPassword_validPassword() {
        assertDoesNotThrow(() -> logIn.correctPassword(67890, "securepass"));
    }

    @Test
    public void testCorrectPassword_invalidPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> logIn.correctPassword(67890, "wrongpass"));
        assertEquals("Incorrect Password", exception.getMessage());
    }

    @Test
    public void testSetAccounts_overwriteAccounts() {
        Map<Integer, List<Object>> newAccounts = new HashMap<>();
        newAccounts.put(11111, Arrays.asList("Charlie", 40, "charlie@example.com", "charlie123"));
        logIn.setAccounts(newAccounts);

        assertDoesNotThrow(() -> logIn.accountCorrect(11111));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> logIn.accountCorrect(12345));
        assertEquals("Invalid Account Number", exception.getMessage());
    }
}
