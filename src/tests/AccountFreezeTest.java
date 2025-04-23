package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.AccountFreeze;

public class AccountFreezeTest {

	private BankAccount account;

	@BeforeEach
	public void setUp() {
		account = new BankAccount();
		account.deposit(100, "General");
		AccountFreeze accControl = account.getFreezeStatus();
		accControl.freeze();
	}

	@Test
	public void testFrozenAccountWithdraw() {
		try {
			account.withdraw(50, "General");
			fail("Expected IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertEquals("Account is frozen. Withdrawals are not allowed.", e.getMessage());
		}
		assertEquals(100.0, account.getCurrentBalance(), 0.001);
	}

	@Test
	public void testFrozenAccountScheduleTransfer() {
		try {
			BankAccount recipient = new BankAccount();
			account.scheduleTransfer(recipient, 20, 1);
			fail("Expected IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertEquals("Account is frozen. Transfers are not allowed.", e.getMessage());
		}

	}

	@Test
	public void testFrozenAccountTransfer() {
		try {
			BankAccount recipient = new BankAccount();
			account.transferTo(recipient, 20);
			fail("Expected IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertEquals("Account is frozen. Transfers are not allowed.", e.getMessage());
		}
	}
}