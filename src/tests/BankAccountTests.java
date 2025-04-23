package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.FixedDeposit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BankAccountTests {

	@Test
	public void testSimpleDeposit() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount();
		
		//2. Call the method being tested
		account.deposit(25, "General");
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}
	
	@Test
	public void testNegativeDeposit() {
		//1. Create object to be tested
		BankAccount account = new BankAccount();

		try {
			account.deposit(-25, "General");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testSimpleWithdraw() {
		BankAccount account = new BankAccount();
		account.deposit(25.0, "General");
		account.withdraw(10.0, "General");
		assertEquals(account.getCurrentBalance(), 15.0, 0.005);
	}
	
	@Test
	public void testNegativeWithdraw() {
		BankAccount account = new BankAccount();
		try {
			account.withdraw(-5, "General");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testInsufficientWithdraw() {
		BankAccount account = new BankAccount();
		account.deposit(10, "General");
		try {
			account.withdraw(15, "General");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testFinalBalance() {
		// Step 1: Set up the account and deposit
        BankAccount account = new BankAccount();
        account.deposit(1000, "General"); 
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

		sender.deposit(100, "General");
		sender.transferTo(receiver, 40);

		assertEquals(60.0, sender.getCurrentBalance(), 0.005);
		assertEquals(40.0, receiver.getCurrentBalance(), 0.005);
	}

	@Test
	public void testTransferWithInsufficientFunds() {
		BankAccount sender = new BankAccount();
		BankAccount receiver = new BankAccount();

		sender.deposit(30, "General");

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

		sender.deposit(100, "General");

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

		sender.deposit(100, "General");
		sender.scheduleTransfer(receiver, 40, 2);
		Thread.sleep(3000);

		assertEquals(60.0, sender.getCurrentBalance(), 0.005);
		assertEquals(40.0, receiver.getCurrentBalance(), 0.005);
	}
	


	@Test
	public void testSpendingLimitBlocksWithdrawal() {
		BankAccount account = new BankAccount();
		account.deposit(100, "General");
		account.setSpendingLimit(30);

		try {
			account.withdraw(50, "General");
			fail("Withdrawal over limit should have failed.");
		} catch (IllegalArgumentException e) {
			assertEquals("Amount exceeds your spending limit.", e.getMessage());
		}
	}

	@Test
	public void testSpendingLimitAllowsValidWithdrawal() {
		BankAccount account = new BankAccount();
		account.deposit(100, "General");
		account.setSpendingLimit(80);

		account.withdraw(50, "General");
		assertEquals(50.0, account.getCurrentBalance(), 0.005);
	}

	@Test
	public void testCategoryAlertTriggered() {
		BankAccount account = new BankAccount();
		account.deposit(50, "Food");
		account.deposit(60, "Food");
		assertEquals(110.0, account.getCurrentBalance(), 0.005);
	}

	@Test
	public void testCategoryTotalTracksCorrectly() {
		BankAccount account = new BankAccount();
		account.deposit(40, "Travel");
		account.deposit(30, "Travel");
		assertEquals(70.0, account.getCurrentBalance(), 0.005);
	}

	@Test
	public void testExportTransactionHistory() {
		BankAccount account = new BankAccount();
		account.deposit(100, "Test");
		account.withdraw(20, "Test");

		String filename = "test_history_output.txt";
		account.exportTransactionHistory(filename);

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String content = reader.readLine();
			assertTrue(content.contains("Transaction History") || content.contains("Deposit") || content.contains("Withdrawal"));
		} catch (IOException e) {
			fail("Failed to read exported file.");
		}
	}
	
	 @Test
	    public void testLargeWithdrawalConfirmed() {
		 	BankAccount account=new BankAccount();
		 	
		 	account.deposit(200000, "Food");
	        String input = "yes\n"; 
	        System.setIn(new ByteArrayInputStream(input.getBytes()));

	        double originalBalance = account.getCurrentBalance();

	        account.withdraw(150000, "Food");
	        double expected = originalBalance - 150000;

	        assertEquals(expected, account.getCurrentBalance(), 0.001);
	    }

	    @Test
	    public void testLargeWithdrawalCancelled() {
	    	BankAccount account=new BankAccount();
	    	
		 	account.deposit(200000, "Food");
	        String input = "no\n"; 
	        System.setIn(new ByteArrayInputStream(input.getBytes()));

	        double originalBalance = account.getCurrentBalance();

	        account.withdraw(150000, "Food");

	        
	        assertEquals(originalBalance, account.getCurrentBalance(), 0.001);
	    }
	}

