package tests;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

import bankapp.LogIn;
import bankapp.BankAccount;

public class LogInTest {

    private LogIn login;

    @Before
    public void setUp() {
        login = new LogIn();

        // Prepare dummy accounts
        Map<Integer, BankAccount> accounts = new HashMap<>();
        BankAccount aliceBankAccount = new BankAccount();
        aliceBankAccount.setName("Alice");
        aliceBankAccount.setPassword("pass123");

        // Let's say each account has: [name, age, balance, password]
        accounts.put(1001, aliceBankAccount);
        login.setAccounts(accounts);
    }

    @Test
    public void testAccountCorrect_withValidAccountNumber_doesNotThrow() {
        try {
            login.accountCorrect(1001);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void testAccountCorrect_withInvalidAccountNumber_throwsException() {
        try {
            login.accountCorrect(9999);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid Account Number", e.getMessage());
        }
    }

    @Test
    public void testCorrectPassword_withValidPassword_doesNotThrow() {
        try {
            login.correctPassword(1001, "pass123");
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void testCorrectPassword_withInvalidPassword_throwsException() {
        try {
            login.correctPassword(1001, "wrongpass");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect Password", e.getMessage());
        }
    }
}
