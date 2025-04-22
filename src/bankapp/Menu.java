package bankapp;

import java.util.*;

public class Menu {
	private BankAccount theAccount;
	private FixedDeposit fixedDeposit;
	private SpendingCategoryManager categorizeSpending;
	private MoneyManagement moneyManagement;
	private Map<Integer, BankAccount> accounts;
	private int currentAccountNumber;
	private Scanner keyboardInput;
	private LogInMenu login;
	private boolean freezeAccountStatus;

	public Menu() {
		theAccount = new BankAccount();
		fixedDeposit = new FixedDeposit();
		keyboardInput = new Scanner(System.in);
		this.accounts = new HashMap<>();
		moneyManagement = new MoneyManagement();
		categorizeSpending = new SpendingCategoryManager(theAccount);
		currentAccountNumber = 0;
		login = new LogInMenu();
		freezeAccountStatus = false;
	}

	public BankAccount getAccount() {
		return theAccount;
	}

	public void setAccounts(Map<Integer, BankAccount> accounts) {
		this.accounts = accounts;
	}

	public Map<Integer, BankAccount> getAccounts() {
		return accounts;
	}

	public void currentBankAccount(int accountNumber) {
		this.currentAccountNumber = accountNumber;
		this.theAccount = (BankAccount) this.accounts.get(accountNumber);
		this.categorizeSpending = new SpendingCategoryManager(this.theAccount);
	}

	public void displayOptions() {
		System.out.println("\n ~ Bank Menu ~");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. View Transaction History");
		System.out.println("4. Check Current Balance");
		System.out.println("5. Fixed Deposit");
		System.out.println("6. Transfer To Different Account");
		System.out.println("7. Update Account Information");
		System.out.println("8. View My Profile");
		System.out.println("9. Schedule a Transfer");
		System.out.println("10. Set Spending Limit");
		System.out.println("11. Categorize Spending");
		System.out.println("12. Money Management Advice");
		System.out.println("13. Logout");
		System.out.println("14. Delete Account");
		System.out.println("15. Export Transaction History to File");
		System.out.println("16. Account Freeze Control");
	}

	public int readIntFromPlayer() {
		System.out.println("\n Enter your choice: ");
		int userChoice = keyboardInput.nextInt();
		keyboardInput.nextLine();
		return userChoice;
	}

	public void processUserInput(int userChoice) {
		switch (userChoice) {
		case 1:
			handleDeposit();
			break;
		case 2:
			handleWithdrawal();
			break;
		case 3:
			theAccount.viewTransactionHistory();
			break;
		case 4:
			handleTotalBalance();
			break;
		case 5:
			handleInterest();
			break;
		case 6:
			handleTransfer();
			break;
		case 7:
			handleUpdateAccount();
			break;
		case 8:
			handleViewProfile();
			break;
		case 9:
			handleScheduledTransfer();
			break;
		case 10:
			handleSetSpendingLimit();
			break;
		case 11:
			handleCategory();
			break;
		case 12:
			handleManagement();
			break;
		case 13:
			break;
		// logout
		case 14:
			handleDelete();
			break;
		case 15:
			handleExportHistory();
			break;
		case 16:
			handleAccountFreeze();
			break;
		default:
			System.out.println("Invalid choice. Please enter a number between 1 and 14");
		}
	}

	public void handleUpdateAccount() {
		UpdateAccountMenu updateAccountMenu = new UpdateAccountMenu();
		updateAccountMenu.setBankAccount(theAccount);
		updateAccountMenu.setAccountNumber(currentAccountNumber);
		// pass in the account number
		while (true) {
			updateAccountMenu.displayOptions();
			int userChoice = updateAccountMenu.readIntFromPlayer();
			updateAccountMenu.processuserInput(userChoice);
			// update the account number
			this.theAccount = updateAccountMenu.getBankAccount();
			if (userChoice == 4) {
				break;
			}
		}
	}

	public void handleDelete() {
		this.accounts.remove(this.currentAccountNumber);
	}

	public void displayAccountDetails(int accountNumber) {
		System.out.println("Account Number: " + accountNumber);
		System.out.println("Name: " + theAccount.getName());
		System.out.println("Phone Number: " + theAccount.getPhoneNumber());
		System.out.println("Email: " + theAccount.getEmail());
	}

	public void handleDeposit() {
		try {
			System.out.print("Enter category for this deposit: ");
			String category = keyboardInput.nextLine();
			System.out.println("Please enter deposit amount: ");
			double amountToDeposit = keyboardInput.nextDouble();
			theAccount.deposit(amountToDeposit, category);
			System.out.println("You deposited $" + amountToDeposit + " into your account under category: " + category);
		} catch (IllegalArgumentException e) {
			System.out.println("Deposit failed: " + e.getMessage());
		}
		;
	}

	public void handleViewProfile() {
		System.out.println("~ My Profile ~");
		if (this.accounts.size() != 0) {
			displayAccountDetails(currentAccountNumber);
		} else {
			System.out.println("Please create an account first");
		}
	}

	public void processDeposit(double amount) {
		theAccount.deposit(amount, "General");
	}

	public void handleWithdrawal() {
		try {
			System.out.print("Enter category for this withdrawal: ");
			String category = keyboardInput.nextLine();
			System.out.println("Please enter withdrawal amount: ");
			double amountToWithdraw = keyboardInput.nextDouble();
			theAccount.withdraw(amountToWithdraw, category);
			System.out.println("You withdrew $" + amountToWithdraw + " from your account under category: " + category);
		} catch (IllegalArgumentException e) {
			System.out.println("Withdrawal failed: " + e.getMessage());
		}
	}

	public void processWithdrawal(double amount) {
		theAccount.withdraw(amount, "SomeCategory");
	}

	public void handleInterest() {
		while (true) {
			fixedDeposit.printTerm();
			int accountTerm = keyboardInput.nextInt();
			if (accountTerm == 4) {
				handleBackToMenu();
				break;
			}
			double accountDeposit = fixedDeposit.getDeposit();
			double accountCD = processInterest(accountTerm, accountDeposit);
			System.out.println("You will eventually get $" + accountCD + " from the fixed deposit");
		}
	}

	public double processInterest(int term, double deposit) {
		fixedDeposit.processSelection(term, deposit);
		return theAccount.getFinalBalance(fixedDeposit);
	}

	public void handleTotalBalance() {
		FixedDeposit fd = new FixedDeposit();
		double accountFinalDeposit = theAccount.getFinalBalance(fd);
		System.out.println("Your current balance is $" + accountFinalDeposit + ".");
	}

	public void handleTransfer() {
		AccountFreeze acc = theAccount.getFreezeStatus();
		if (acc.isFrozen()) {
			System.out.println("Transfer failed: Your account is frozen. Transfers are not allowed.");
			return; // Exit the method early
		}
		System.out.println("Enter account number to transfer to, or 0 to go back: ");
		int receivingAccountNumber = keyboardInput.nextInt();
		if (receivingAccountNumber == 0) {
			handleBackToMenu();
			return;
		}
		if (this.accounts.containsKey(receivingAccountNumber)) {
			BankAccount recipientAccount = this.accounts.get(receivingAccountNumber);
			try {
				System.out.println("Enter amount to transfer: ");
				double amountToTransfer = keyboardInput.nextDouble();
				keyboardInput.nextLine();
				theAccount.transferTo(recipientAccount, amountToTransfer);
				System.out.println("Transferred $" + amountToTransfer + " to the recipient account.");
				System.out.println("Your new balance: $" + theAccount.getCurrentBalance());
			} catch (IllegalArgumentException e) {
				System.out.println("Transfer failed: " + e.getMessage());
			}
		} else {
			System.out.println("Incorrect Account Number");
		}
	}

	public void handleScheduledTransfer() {
		AccountFreeze acc = theAccount.getFreezeStatus();
		if (acc.isFrozen()) {
			System.out.println("Scheduling failed: Your account is frozen.");
			return;
		}
		BankAccount recipientAccount = new BankAccount();
		System.out.print("Enter amount to transfer, or 0 to go back: ");
		double amount = keyboardInput.nextDouble();
		if (amount == 0) {
			handleBackToMenu();
			return;
		}
		System.out.print("Enter delay time in seconds: ");
		int delayInSeconds = keyboardInput.nextInt();
		try {
			theAccount.scheduleTransfer(recipientAccount, amount, delayInSeconds);
			System.out.println("Transfer of $" + amount + " scheduled in " + delayInSeconds + " seconds.");
		} catch (IllegalArgumentException e) {
			System.out.println("Scheduling failed: " + e.getMessage());
		}
	}

	public void handleSetSpendingLimit() {
		while (true) {
			System.out.print("Enter your desired spending limit: ");
			double limit = keyboardInput.nextDouble();
			try {
				theAccount.setSpendingLimit(limit);
				System.out.println("Spending limit set to $" + limit);
			} catch (IllegalArgumentException e) {
				System.out.println("Failed to set limit: " + e.getMessage());
			}
		}
	}

	public void handleCategory() {
		while (true) {
			System.out.println("Select a category for you last withdrawal:");
			categorizeSpending.viewCategory();
			int choice = keyboardInput.nextInt();
			if (choice == 6) {
				handleBackToMenu();
				break;
			}
			categorizeSpending.processCategory(choice);
			categorizeSpending.showCategory();
		}
	}

	public void handleManagement() {
		while (true) {
			System.out.println("Select a goal for your money:");
			moneyManagement.goalAdvisory();
			int choice = keyboardInput.nextInt();
			if (choice == 1) {
				System.out.print("Enter your monthly income: ");
				double income = keyboardInput.nextDouble();
				moneyManagement.setIncome(income);
			}
			if (choice == 4) {
				handleBackToMenu();
				break;
			}
			moneyManagement.adviseNavigation(choice);
		}
	}

	public void handleBackToMenu() {
		displayOptions();
	}

	public void handleExportHistory() {
		System.out.print("Enter a filename to save history (e.g. history.txt): ");
		keyboardInput.nextLine();
		String filename = keyboardInput.nextLine();
		theAccount.exportTransactionHistory(filename);
	}

	public void handleAccountFreeze() {
		AccountFreeze freeze = theAccount.getFreezeStatus();
		System.out.println("Your current account status: " + (freeze.isFrozen() ? "Frozen" : "Active"));
		freeze.displayOptions();
		int choice = freeze.readIntFromPlayer();
		switch (choice) {
		case 1:
			freeze.freeze();
			break;
		case 2:
			freeze.unfreeze();
			break;
		case 3:
			handleBackToMenu();
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
}