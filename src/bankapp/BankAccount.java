package bankapp;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;

public class BankAccount {

	private double balance;
	private double spendingLimit;
	public double spending;
	private List<Transaction> transactionHistory;
	private Map<String, SpendingCategory> categoryMap;
	private String email;
	private String phoneNumber;
	private int accountNumber;
	private String name;
	private String password;
	private String securityQuestion;
	private String securityAnswer;
	private Map<Integer, List<Object>> accounts;
	private AccountFreeze freezeStatus;

	public BankAccount() {
		this.balance = 0;
		this.spendingLimit = Double.MAX_VALUE;
		this.spending = 0;
		this.transactionHistory = new ArrayList<>();
		this.categoryMap = new HashMap<>();
		this.freezeStatus = new AccountFreeze();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);

		if (matcher.matches()) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("Invalid email format.");
		}
	}

	public void setPhoneNumber(String phoneNumber) {
		String phoneRegex = "^(\\+\\d{1,3}\\s?)?(\\(\\d{3}\\)|\\d{3})[-.\\s]?\\d{3}[-.\\s]?\\d{4}$";
		Pattern pattern = Pattern.compile(phoneRegex);
		Matcher matcher = pattern.matcher(phoneNumber);

		if (matcher.matches()) {
			this.phoneNumber = phoneNumber;
		} else {
			throw new IllegalArgumentException("Invalid phone number format.");
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSecurityQuestion(String question) {
		this.securityQuestion = question;
	}

	public void setAnswer(String answer) {
		this.securityAnswer = answer.toLowerCase();
	}

	public String getName() {
		return this.name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public String getSecurityQuestion() {
		return this.securityQuestion;
	}

	public String getSecurityAnswer() {
		return this.securityAnswer;
	}

	public void deposit(double amount, String category) {
		if (amount < 0) {
			throw new IllegalArgumentException("You cannot deposit a negative amount.");
		}
		this.balance += amount;
		transactionHistory.add(new Transaction("Deposit", amount, getCurrentTime(), category));
	}

	public void withdraw(double amount, String category) {
		if (freezeStatus.isFrozen()) {
			throw new IllegalArgumentException("Account is frozen. Withdrawals are not allowed.");
		}
		if (amount > balance) {
			throw new IllegalArgumentException("Insufficient funds.");
		}
		if (amount < 0) {
			throw new IllegalArgumentException("You cannot withdraw a negative amount.");
		}
		if (amount > spendingLimit) {
			throw new IllegalArgumentException("Amount exceeds your spending limit.");
		}
		if (amount > 1000) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("You are attempting to withdraw a large amount: $" + amount);
			System.out.print("Type 'yes' to confirm: ");
			String input = scanner.nextLine();
			if (!input.equalsIgnoreCase("yes")) {
				System.out.println("Withdrawal cancelled.");
				return;
			}
		}

		this.spending = amount;
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
		transactionHistory
				.add(new Transaction("Fixed Deposit", fixedDeposit.getFinalDeposit(), getCurrentTime(), "Investment"));
		return this.balance + fixedDeposit.getFinalDeposit();
	}

	public int getTransactionCount() {
		return transactionHistory.size();
	}

	public void viewTransactionHistory() {
		if (getTransactionCount() == 0) {
			System.out.println("No recent transaction history");
		} else {
			System.out.println("Your Recent Transaction History:");
			for (Transaction t : transactionHistory) {
				t.showTransaction();
			}
		}
	}

	public void transferTo(BankAccount recipient, double amount) {
		if (freezeStatus.isFrozen()) {
			throw new IllegalArgumentException("Account is frozen. Transfers are not allowed.");
		}
		if (amount <= 0) {
			throw new IllegalArgumentException("Transfer amount must be positive.");
		}
		if (this.balance < amount) {
			throw new IllegalArgumentException("Insufficient funds.");
		}

		this.withdraw(amount, "Transfer Sent");
		recipient.deposit(amount, "Transfer Received");
	}

	public void scheduleTransfer(BankAccount recipient, double amount, int delayInSeconds) {
		if (freezeStatus.isFrozen()) {
			throw new IllegalArgumentException("Account is frozen. Transfers are not allowed.");
		}
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
			categoryMap.put(category, new SpendingCategory(category, spendingLimit));
		}
		SpendingCategory cat = categoryMap.get(category);
		cat.addSpending(amount);

		if (cat.isOverLimit()) {
			System.out.println(
					"Alert: You’ve exceeded your $" + cat.getThreshold() + " limit for " + category + " spending.");
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

	public AccountFreeze getFreezeStatus() {
		return freezeStatus;
	}
}
