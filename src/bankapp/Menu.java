package bankapp;
import java.util.Scanner;

public class Menu {

    private BankAccount theAccount;
    private Scanner keyboardInput;

    public Menu() {
        theAccount = new BankAccount();
        keyboardInput = new Scanner(System.in);
    }
    
    public BankAccount getAccount() {
        return theAccount;
    }

    public void displayOptions() {
    	while (true) {
    		 System.out.println("\n ~ Bank Menu ~");
             System.out.println("1. Deposit");
             System.out.println("2. Withdraw");
             System.out.println("3. View Transaction History");
             System.out.println("4. Check Current Balance");
             // add more options here	
             int userChoice = readIntFromPlayer();
             processUserInput(userChoice);
    	}
    }
    
    private int readIntFromPlayer() {
		System.out.println("Enter your choice: ");
		int userChoice = keyboardInput.nextInt(); 
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
	        // add more cases here like view transaction history, account balance, etc.
	        case 3:
	            // handle view transaction logic
	            break;
	        case 4:
	        	  // handle view account balance logic
	            break;
	        default:
	            System.out.println("Invalid choice. Please enter a number between 1 and 4.");
	    }
    }

    public void handleDeposit() {
    	System.out.println("Please enter deposit amount: ");
        double amountToDeposit = keyboardInput.nextDouble();
        processDeposit(amountToDeposit);
        System.out.println("You deposited into your account: $" + amountToDeposit);
    }
    
    public void processDeposit(double amount) {
        theAccount.deposit(amount);
    }
    
    public void handleWithdrawal() {
    	System.out.println("Please enter withdrawal amount: ");
        double amountToWithdraw = keyboardInput.nextDouble();
        processWithdrawal(amountToWithdraw);
        System.out.println("You withdrew into your account: $" + amountToWithdraw);
    }
    
    public void processWithdrawal(double amount) {
        theAccount.withdraw(amount);
    }
    
    public static void main(String[] args) {
		Menu menu = new Menu();
		menu.displayOptions();
	}
}
