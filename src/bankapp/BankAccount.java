package bankapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


public class BankAccount {

	private double balance;
	private double spendingLimit;
	public double spending;
	private List<Transaction> transactionHistory;
	private Map<String, SpendingCategory> categoryMap;

	public BankAccount() {
		this.balance = 0;
		this.spendingLimit = Double.MAX_VALUE;
		this.spending=0;
		this.transactionHistory = new ArrayList<>();
		this.categoryMap = new HashMap<>();
	}
	
	public void deposit(double amount, String category) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
		trackSpending(category, amount);
		transactionHistory.add(new Transaction("Deposit", amount, getCurrentTime(), category));
	}

	public void withdraw(double amount, String category) {
		if (amount > balance)   {
			 throw new IllegalArgumentException("Insufficient funds.");
		} 
		if (amount < 0) {
			throw new IllegalArgumentException("You cannot withdraw a negative amount.");
		}
		this.spending=amount;
		this.balance -= amount;
		trackSpending(category, amount);
		transactionHistory.add(new Transaction("Withdrawal", amount, getCurrentTime(), category));
	}
	
	public double getCurrentBalance() {
		return this.balance;
	}
	
	public String getCurrentTime() {
		SimpleDateFormat localDateFormat = new SimpleDateFormat("h:mm:ss a");
        return localDateFormat.format(new Date());
	}

	public double getFinalBalance(FixedDeposit fixedDeposit) {
		transactionHistory.add(new Transaction("Fixed Deposit", fixedDeposit.getFinalDeposit(), getCurrentTime(), "Investment"));
		return this.balance + fixedDeposit.getFinalDeposit();
	}

	public int getTransactionCount() {
	    return transactionHistory.size();
	}
	
	public void viewTransactionHistory() {
		if (getTransactionCount() == 0) {
			System.out.println("No recent transaction history");
		}
		else {
			System.out.println("Your Recent Transaction History:");
			for (Transaction t: transactionHistory) {
				t.showTransaction();
			}
		}
	}

	public void transferTo(BankAccount recipient, double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Transfer amount must be positive.");
		}
		if (this.balance < amount) {
			throw new IllegalArgumentException("Insufficient funds.");
		}

		this.withdraw(amount, "Transfer Sent");
		recipient.deposit(amount, "Transfer Received");

		this.transactionHistory.add(new Transaction("Transfer Sent", amount, getCurrentTime(), "Tansfer"));
		recipient.transactionHistory.add(new Transaction("Transfer Received", amount, getCurrentTime(), "Transfer"));
	}

	public void scheduleTransfer(BankAccount recipient, double amount, int delayInSeconds) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Transfer amount must be positive.");
		}
		if (amount > balance) {
			throw new IllegalArgumentException("Insufficient funds.");
		}

		new Thread(() -> {
			try {
				Thread.sleep(delayInSeconds * 1000);
				this.withdraw(amount, "Scheduled Transfer");
				recipient.deposit(amount, "Scheduled Transfer");
				System.out.println("Transfer of $" + amount + " completed after " + delayInSeconds + " seconds.");
			} catch (InterruptedException e) {
				System.out.println("Transfer interrupted.");
			}
		}).start();
	}
	
	public void setSpendingLimit(double limit) {
		if (limit < 0) {
			throw new IllegalArgumentException("Spending limit must be non-negative.");
		}
		this.spendingLimit = limit;
	}

	public void trackSpending(String category, double amount) {
		if (!categoryMap.containsKey(category)) {
			categoryMap.put(category, new SpendingCategory(category, 100));
		}
		SpendingCategory cat = categoryMap.get(category);
		cat.addSpending(amount);

		if (cat.isOverLimit()) {
			System.out.println("Alert: You’ve exceeded your $" + cat.getThreshold() + " limit for " + category + " spending.");
		}
	}
	public void exportTransactionHistory(String filename) {
		try (FileWriter writer = new FileWriter(filename)) {
			if (transactionHistory.isEmpty()) {
				writer.write("No recent transaction history\n");
			} else {
				writer.write("Transaction History:\n");
				for (Transaction t : transactionHistory) {
					writer.write(t.toString() + "\n");
				}
			}
			System.out.println("Exported to " + filename);
		} catch (IOException e) {
			System.out.println("Error exporting: " + e.getMessage());
		}
	}
}
