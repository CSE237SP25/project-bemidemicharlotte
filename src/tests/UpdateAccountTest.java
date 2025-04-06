package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import bankapp.UpdateAccount;

import java.util.*;

public class UpdateAccountTest {

    private Map<Integer, List<Object>> accounts;
    private UpdateAccount updateAccount;

    @Before
    public void setUp() {
        accounts = new HashMap<>();
        // Initialize dummy account with: name, phone number, email
        accounts.put(123456789, new ArrayList<>(Arrays.asList("John Doe", "123-456-7890", "john@example.com")));
        updateAccount = new UpdateAccount(accounts);
    }

    @Test
    public void testUpdateName_Valid() {
        updateAccount.updateName(123456789, "Jane Smith");
        assertEquals("Jane Smith", accounts.get(123456789).get(0));
    }

    @Test
    public void testUpdatePhoneNumber_Valid() {
        updateAccount.updatePhoneNumber(123456789, "(314) 555-7890");
        assertEquals("(314) 555-7890", accounts.get(123456789).get(1));
    }

    @Test
    public void testUpdatePhoneNumber_Invalid() {
        try {
            updateAccount.updatePhoneNumber(123456789, "invalid-number");
            fail("Expected IllegalArgumentException for invalid phone number");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid phone number format.", e.getMessage());
        }
    }

    @Test
    public void testUpdateEmail_Valid() {
        updateAccount.updateEmail(123456789, "jane.smith@example.org");
        assertEquals("jane.smith@example.org", accounts.get(123456789).get(2));
    }

    @Test
    public void testUpdateEmail_Invalid() {
        try {
            updateAccount.updateEmail(123456789, "bad-email");
            fail("Expected IllegalArgumentException for invalid email");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid email format.", e.getMessage());
        }
    }

    @Test
    public void testUpdateInvalidAccountNumber() {
        try {
            updateAccount.updateName(999999999, "Someone");
            fail("Expected IllegalArgumentException for non-existent account number");
        } catch (IllegalArgumentException e) {
            assertEquals("Account number does not exist, please retry with a valid account number", e.getMessage());
        }
    }
}
