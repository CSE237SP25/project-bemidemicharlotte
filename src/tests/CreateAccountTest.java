package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import bankapp.CreateAccount;

public class CreateAccountTest {

    private CreateAccount account;

    @Before
    public void setUp() {
        account = new CreateAccount();
    }

    @Test
    public void testName(){
        account.setName("Alfred");
        assertEquals("Alfred", account.getName());
    }

    @Test
    public void testValidEmail() {
        account.setEmail("test@example.com");
        assertEquals("test@example.com", account.getEmail());
    }

    @Test
    public void testInvalidEmail() {
        try {
            account.setEmail("invalid-email");
            fail(); // Should not reach here
        } catch (IllegalArgumentException e) {
            assertTrue(e != null);
        }
    }

    @Test
    public void testValidPhoneNumber() {
        account.setPhoneNumber("+1 123-456-7890");
        assertEquals("+1 123-456-7890", account.getPhoneNumber());
    }

    @Test
    public void testInvalidPhoneNumber() {
        try {
            account.setPhoneNumber("12345");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e != null);
        }
    }
} // ← this is the closing brace for the class

