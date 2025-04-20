package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import bankapp.BankAccount;
import bankapp.Menu;
import bankapp.LogInMenu;

import java.util.*;

public class MenuTests {

    @Test
    public void testInvalidChoice() {
        Menu m = new Menu();
        m.processUserInput(-1);

        BankAccount account = m.getAccount();
        assertEquals(0, account.getCurrentBalance(), 0.005);
    }

    @Test
    public void testUserDeposit() {
        Menu m = new Menu();
        m.processDeposit(25);

        BankAccount account = m.getAccount();
        assertEquals(25.0, account.getCurrentBalance(), 0.005);
    }

    @Test
    public void testWithdrawal() {
        Menu m = new Menu();
        m.processDeposit(50);
        m.processWithdrawal(25);

        BankAccount account = m.getAccount();
        assertEquals(25.0, account.getCurrentBalance(), 0.005);
    }

    @Test
    public void testDisplayAccountDetails_withExistingAccount_doesNotThrow() {
        LogInMenu logInMenu = new LogInMenu();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("Test User");
        bankAccount.setPhoneNumber("4438101712");
        bankAccount.setEmail("test@gmail.com");
        bankAccount.setPassword("testpass");

        Map<Integer, BankAccount> accounts = new HashMap<>();
        accounts.put(123456789, bankAccount);

        logInMenu.setAccounts(accounts);

        try {
            logInMenu.displayAccountDetails(123456789);
        } catch (Exception e) {
            fail("Should not have thrown an exception");
        }
    }
}
