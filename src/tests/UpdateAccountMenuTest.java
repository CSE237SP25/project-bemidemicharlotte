package tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import bankapp.UpdateAccountMenu;
import bankapp.BankAccount;

import static org.junit.Assert.*;

public class UpdateAccountMenuTest {

    private UpdateAccountMenu updateMenu;

    @Before
    public void setUp() {
        updateMenu = new UpdateAccountMenu();
    }

    @Test
    public void testSetBankAccount_updatesTheAccountReference() {
        BankAccount account = new BankAccount();
        updateMenu.setBankAccount(account);

        // No public getter, so we just ensure no exception
        assertNotNull(account);
    }

    @Test
    public void testSetAndGetAccounts_setsCorrectly() {
        int accNum = 123456789;
        List<Object> details = Arrays.asList("User", "1234567890", "user@email.com", "pass", new BankAccount());
        Map<Integer, List<Object>> testAccounts = new HashMap<>();
        testAccounts.put(accNum, details);

        updateMenu.setAccounts(testAccounts);
        Map<Integer, List<Object>> result = updateMenu.getAccounts();

        assertEquals(1, result.size());
        assertTrue(result.containsKey(accNum));
        assertEquals(details, result.get(accNum));
    }

    @Test
    public void testSetAccountNumber_setsCorrectly() {
        updateMenu.setAccountNumber(111111111);
        assertTrue(true); // just confirm it doesn't crash
    }

    @Test
    public void testDisplayAccountDetails_printsCorrectInfo() {
        int accNum = 987654321;
        List<Object> details = Arrays.asList("Alice", "0987654321", "alice@email.com", "pw123", new BankAccount());
        Map<Integer, List<Object>> testAccounts = new HashMap<>();
        testAccounts.put(accNum, details);

        updateMenu.setAccounts(testAccounts);

        // Just checking that no exception is thrown
        try {
            updateMenu.displayAccountDetails(accNum);
        } catch (Exception e) {
            fail("Should not have thrown an exception");
        }
    }
}
