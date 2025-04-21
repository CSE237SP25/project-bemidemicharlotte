package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

import bankapp.UpdateAccountMenu;
import bankapp.BankAccount;


public class TransactionTests {
	
	@Test
	public void testEmptyTransaction() {
		BankAccount account = new BankAccount();
		assertEquals(0, account.getTransactionCount());
	}
	
	@Test
	public void testTransactionCount() {
		BankAccount account = new BankAccount();
		account.deposit(25, "General");
		account.deposit(50, "General");
		assertEquals(2, account.getTransactionCount());
	}
	
	@Test
	public void testBothTransactions() {
		BankAccount account = new BankAccount();
		account.deposit(25, "General");
		account.withdraw(20, "General");
		account.withdraw(5, "General");
		assertEquals(3, account.getTransactionCount());
	}
}