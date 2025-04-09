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
        List<Object> accountDetails = Arrays.asList(
                "Test User", "1234567890", "test@email.com", "testpass", bankAccount
        );
        Map<Integer, List<Object>> accounts = new HashMap<>();
        accounts.put(123456789, accountDetails);

        logInMenu.setAccounts(accounts);

        try {
            logInMenu.displayAccountDetails(123456789);
        } catch (Exception e) {
            fail("Should not have thrown an exception");
        }
    }

    @Test
    public void testStoreAccountInfo_createsAccountWithCorrectData() {
        LogInMenu logInMenu = new LogInMenu();

        String name = "Alice";
        String email = "alice@example.com";
        String phone = "1234567890";
        String password = "pass123";
        BankAccount account = new BankAccount();

        logInMenu.storeAccountInfo(name, email, phone, password, account);

        Map<Integer, List<Object>> accounts = getPrivateAccountsMap(logInMenu);
        assertEquals(1, accounts.size());

        Integer storedAccountNumber = accounts.keySet().iterator().next();
        List<Object> storedData = accounts.get(storedAccountNumber);

        assertEquals(name, storedData.get(0));
        assertEquals(phone, storedData.get(1));
        assertEquals(email, storedData.get(2));
        assertEquals(password, storedData.get(3));
        assertEquals(account, storedData.get(4));
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, List<Object>> getPrivateAccountsMap(LogInMenu menu) {
        try {
            var field = LogInMenu.class.getDeclaredField("accounts");
            field.setAccessible(true);
            return (Map<Integer, List<Object>>) field.get(menu);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }
    }
}
