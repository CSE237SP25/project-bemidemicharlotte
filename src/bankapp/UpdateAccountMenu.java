package bankapp;
import java.util.*;

public class UpdateAccountMenu {
    private BankAccount theAccount;
    private Scanner keyboardInput;
    private Map<Integer, List<Object>> accounts;
    private int accountNumber;
    private UpdateAccount updateAccount;


    public UpdateAccountMenu(){
        this.theAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
        this.accounts = new HashMap<>();
        this.accountNumber = 0;
    }

    public void setBankAccount(BankAccount account){
        this.theAccount = account;
    }

    public void setAccounts(Map<Integer, List<Object>> accounts){
        this.accounts = accounts;
        this.updateAccount = new UpdateAccount(accounts);
    }

    public void setAccountNumber(int accountNumber){
        this.accountNumber = accountNumber;
    }

    public Map<Integer, List<Object>> getAccounts(){
        return accounts;
    }

     public void displayOptions(){
        System.out.println("What Information do you want to update?: ");
        System.out.println("1. Name");
        System.out.println("2. Phone Number");
        System.out.println("3. Email");
        System.out.println("4. Exit");
    }

    public int readIntFromPlayer() {
		System.out.println("Enter your choice: ");
		int userChoice = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return userChoice;
	}

    public void processuserInput(int userChoice){
        switch(userChoice){
            case 1:
                handleUpdateName(accountNumber);
                break;
            case 2:
                handleUpdatePhoneNumber(accountNumber);
                break;
            case 3:
                handleUpdateEmail(accountNumber);
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

    public void handleUpdateName(int accountNumber){
        System.out.println("Please enter your updated name: ");
        String name = keyboardInput.nextLine();
        try{
            this.accounts = updateAccount.updateName(accountNumber, name);
            System.out.println("Please Find Below Your Updated Information.");
            displayAccountDetails(accountNumber);
            System.out.println();
        } catch(java.lang.Exception e) {
            System.out.println( e.getMessage());
            System.out.println();
        }
    }

    public void handleUpdatePhoneNumber(int accountNumber){
        System.out.println("Please enter your new phone number: ");
        String phoneNumber = keyboardInput.nextLine();
        try{
            this.accounts = updateAccount.updatePhoneNumber(accountNumber, phoneNumber);
            System.out.println("Please Find Below Your Updated Information.");
            displayAccountDetails(accountNumber);
            System.out.println();
        } catch(java.lang.Exception e) {
            System.out.println( e.getMessage());
            System.out.println();
        }
    }

    public void handleUpdateEmail(int accountNumber){
        System.out.println("Please enter your new email address");
        String email = keyboardInput.nextLine();
        try{
            this.accounts = updateAccount.updateEmail(accountNumber, email);
            System.out.println("Please Find Below Your Updated Information.");
            displayAccountDetails(accountNumber);
            System.out.println();
        } catch(java.lang.Exception e) {
            System.out.println( e.getMessage());
            System.out.println();
        }
    }

    public void displayAccountDetails(int accountNumber){
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + this.accounts.get(accountNumber).get(0));
            System.out.println("Phone Number: " + this.accounts.get(accountNumber).get(1));
            System.out.println("Email: " + this.accounts.get(accountNumber).get(2));
    }


}