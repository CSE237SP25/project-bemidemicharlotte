package bankapp;
import java.util.*;

public class UpdateAccountMenu {
    private BankAccount theAccount;
    private Scanner keyboardInput;
    private int accountNumber;


    public UpdateAccountMenu(){

        this.theAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
        this.accountNumber = 0;
    }

    public void setBankAccount(BankAccount account){
        this.theAccount = account;
    }

    public BankAccount getBankAccount(){
        return this.theAccount;
    }

    public void setAccountNumber(int accountNumber){
        this.accountNumber = accountNumber;
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
            theAccount.setName(name);
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
            theAccount.setPassword(phoneNumber);
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
            theAccount.setEmail(email);
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
            System.out.println("Name: " + theAccount.getName());
            System.out.println("Phone Number: " + theAccount.getPhoneNumber());
            System.out.println("Email: " + theAccount.getEmail());
    }

}