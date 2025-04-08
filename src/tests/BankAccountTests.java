package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.FixedDeposit;

public class BankAccountTests {

	@Test
	public void testSimpleDeposit() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount();
		
		//2. Call the method being tested
		account.deposit(25);
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}
	
	@Test
	public void testNegativeDeposit() {
		//1. Create object to be tested
		BankAccount account = new BankAccount();

		try {
			account.deposit(-25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testSimpleWithdraw() {
		BankAccount account = new BankAccount();
		account.deposit(25.0);
		account.withdraw(10.0);
		assertEquals(account.getCurrentBalance(), 15.0, 0.005);
	}
	
	@Test
	public void testNegativeWithdraw() {
		BankAccount account = new BankAccount();
		try {
			account.withdraw(-5);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testInsufficientWithdraw() {
		BankAccount account = new BankAccount();
		account.deposit(10);
		try {
			account.withdraw(15);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testFinalBalance() {
		// Step 1: Set up the account and deposit
        BankAccount account = new BankAccount();
        account.deposit(1000); 
        // Step 2: Create and process a fixed deposit
        FixedDeposit fd = new FixedDeposit();
        fd.processSelection(1, 2000); 
        double expectedFD = 2000 * Math.pow(1 + 0.0393, 7);
        double expectedTotal = 1000 + expectedFD;
        double actualTotal = account.getFinalBalance(fd);
        // Step 3: Assert
        assertEquals(expectedTotal, actualTotal, 0.01); 
	}

	@Test
	public void testSuccessfulTransfer() {
		BankAccount sender = new BankAccount();
		BankAccount receiver = new BankAccount();

		sender.deposit(100);
		sender.transferTo(receiver, 40);

		assertEquals(60.0, sender.getCurrentBalance(), 0.005);
		assertEquals(40.0, receiver.getCurrentBalance(), 0.005);
	}

	@Test
	public void testTransferWithInsufficientFunds() {
		BankAccount sender = new BankAccount();
		BankAccount receiver = new BankAccount();

		sender.deposit(30);

		try {
			sender.transferTo(receiver, 50);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Insufficient funds.", e.getMessage());
		}
	}

	@Test
	public void testTransferWithNegativeAmount() {
		BankAccount sender = new BankAccount();
		BankAccount receiver = new BankAccount();

		sender.deposit(100);

		try {
			sender.transferTo(receiver, -20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Transfer amount must be positive.", e.getMessage());
		}
	}

	@Test
	public void testScheduledTransferExecutesAfterDelay() throws InterruptedException {
		BankAccount sender = new BankAccount();
		BankAccount receiver = new BankAccount();

		sender.deposit(100);
		sender.scheduleTransfer(receiver, 40, 2);
		Thread.sleep(3000);

		assertEquals(60.0, sender.getCurrentBalance(), 0.005);
		assertEquals(40.0, receiver.getCurrentBalance(), 0.005);
	}

	@Test
	public void testSpendingLimitBlocksWithdrawal() {
		BankAccount account = new BankAccount();
		account.deposit(100);
		account.setSpendingLimit(30);

		try {
			account.withdraw(50);
			fail("Withdrawal over limit should have failed.");
		} catch (IllegalArgumentException e) {
			assertEquals("Amount exceeds your spending limit.", e.getMessage());
		}
	}

	@Test
	public void testSpendingLimitAllowsValidWithdrawal() {
		BankAccount account = new BankAccount();
		account.deposit(100);
		account.setSpendingLimit(80);

		account.withdraw(50);
		assertEquals(50.0, account.getCurrentBalance(), 0.005);
	}

}
