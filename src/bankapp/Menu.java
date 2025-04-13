package bankapp;
import java.util.*;


public class Menu {
	private BankAccount theAccount;
	private FixedDeposit fixedDeposit;
    private CreateAccount newAccount;
    private UpdateAccount updateAccount;
    private Map<Integer, List<Object>> accounts;
    private int currentAccountNumber;

    private Scanner keyboardInput;

    public Menu() {
        theAccount = new BankAccount();
        fixedDeposit = new FixedDeposit();
        keyboardInput = new Scanner(System.in);
        this.accounts = new HashMap<>();
        currentAccountNumber = 0;
    }
    
    public BankAccount getAccount() {
        return theAccount;
    }

    public void setAccounts(Map<Integer, List<Object>> accounts){
        this.accounts = accounts;
    }

    public Map<Integer, List<Object>> getAccounts(){
        return accounts;
    }

    public void currentBankAccount(int accountNumber){
        this.currentAccountNumber = accountNumber;
        this.theAccount = (BankAccount)this.accounts.get(accountNumber).get(4);
    }

    public void displayOptions() {
		 System.out.println("\n ~ Bank Menu ~");
         System.out.println("1. Deposit");
         System.out.println("2. Withdraw");
         System.out.println("3. View Transaction History");
         System.out.println("4. Check Current Balance");
         System.out.println("5. Fixed Deposit");
         System.out.println("6. Transfer Between Accounts");
         System.out.println("7. Update Account Information");
         System.out.println("8. View My Profile");
         System.out.println("9. Schedule a Transfer");
         System.out.println("10. Logout");
         System.out.println("11. Delete Account");
         System.out.println("12. Set Spending Limit");

    }
    
    public int readIntFromPlayer() {
		System.out.println("Enter your choice: ");
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
            	break;
            case 11:
                handleDelete();
                break;
            case 12:
                handleSetSpendingLimit();
                break;
            default:
	            System.out.println("Invalid choice. Please enter a number between 1 and 10");
	    }
    }

    public void handleUpdateAccount() {
        UpdateAccountMenu updateAccountMenu = new UpdateAccountMenu();
        updateAccountMenu.setBankAccount(theAccount);
        updateAccountMenu.setAccountNumber(currentAccountNumber);
        //pass in the account number
        while(true){
            updateAccountMenu.setAccounts(this.accounts);
            updateAccountMenu.displayOptions();
            int userChoice = updateAccountMenu.readIntFromPlayer();
            updateAccountMenu.processuserInput(userChoice);
            //update the account number
            this.accounts = updateAccountMenu.getAccounts();
            if(userChoice == 4){
                break;
            }
        }
    }

    public void handleDelete(){
        this.accounts.remove(this.currentAccountNumber);
    }

    public void displayAccountDetails(int accountNumber){
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + this.accounts.get(accountNumber).get(0));
            System.out.println("Phone Number: " + this.accounts.get(accountNumber).get(1));
            System.out.println("Email: " + this.accounts.get(accountNumber).get(2));
    }

    public void handleDeposit() {
    	System.out.println("Please enter deposit amount: ");
        double amountToDeposit = keyboardInput.nextDouble();
        keyboardInput.nextLine();

        System.out.print("Enter category for this deposit: ");
        String category = keyboardInput.nextLine();

        theAccount.deposit(amountToDeposit, category);
        System.out.println("You deposited $" + amountToDeposit + " into your account under category: " + category);
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
        theAccount.deposit(amount);
    }
    
    public void handleWithdrawal() {
    	System.out.println("Please enter withdrawal amount: ");
        double amountToWithdraw = keyboardInput.nextDouble();
        keyboardInput.nextLine();

        System.out.print("Enter category for this withdrawal: ");
        String category = keyboardInput.nextLine();

        try {
            theAccount.withdraw(amountToWithdraw, category);
            System.out.println("You withdrew $" + amountToWithdraw + " from your account under category: " + category);
        } catch (IllegalArgumentException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }
    
    public void processWithdrawal(double amount) {
        theAccount.withdraw(amount);
    }
    
    public void handleInterest() {
    	fixedDeposit.printTerm();
    	int accountTerm = keyboardInput.nextInt();
    	double accountDeposit = fixedDeposit.getDeposit();
    	double accountCD = processInterest(accountTerm, accountDeposit);	
    	System.out.println("You will eventually get $" + accountCD + " from the fixed deposit");
    }
    
    public double processInterest(int term, double deposit) {
    	fixedDeposit.processSelection(term, deposit);
    	return theAccount.getFinalBalance(fixedDeposit);
    }
    
    public void handleTotalBalance() {
    	FixedDeposit fd = new FixedDeposit();
    	double accountFinalDeposit = theAccount.getFinalBalance(fd);
    	System.out.println("Your final balance is $"+ accountFinalDeposit +".");
    }
    
    public void handleTransfer() {
        System.out.println("Enter amount to transfer: ");
        double amountToTransfer = keyboardInput.nextDouble();

        BankAccount recipientAccount = new BankAccount();

        try {
            theAccount.transferTo(recipientAccount, amountToTransfer);
            System.out.println("Transferred $" + amountToTransfer + " to the recipient account.");
            System.out.println("Your new balance: $" + theAccount.getCurrentBalance());
            System.out.println("Recipient balance: $" + recipientAccount.getCurrentBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
    public void handleScheduledTransfer() {
        BankAccount recipientAccount = new BankAccount();

        System.out.print("Enter amount to transfer: ");
        double amount = keyboardInput.nextDouble();

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
