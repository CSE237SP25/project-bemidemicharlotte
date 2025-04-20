package bankapp;
import java.util.*;


public class LogInMenu {
    private Map<Integer, List<Object>> accounts;
    private Scanner keyboardInput;
    private CreateAccount newAccount;
    private int currentAccountNumber;
    private LogIn login;
    
    public LogInMenu(){
        this.accounts = new HashMap<>();
        keyboardInput = new Scanner(System.in);
        this.login = new LogIn();
        this.currentAccountNumber = 0;
    }

    public void displayOptions(){
        System.out.println("\n ~ Sign Up/Log In ~");
        System.out.println("1. Create Account");
        System.out.println("2. Log In");
        System.out.println("3. Reset Password");
    }

    public void secretQuestionOptions(){
        System.out.println();
        System.out.println("Please Select a Security Question:");
        System.out.println("1. What is your mother's maiden name?");
        System.out.println("2. What is the name of your elementary school?");
        System.out.println("3. What is the name of your first pet?");
    }

    public void setAccounts(Map<Integer, List<Object>> accounts){
        this.accounts = accounts;
    }

    public int readIntFromPlayer() {
		System.out.println("Enter your choice: ");
		int userChoice = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return userChoice;
	}

    public void processUserInput(int userChoice){
        switch(userChoice){
            case 1:
                handleCreateAccount();
                break;
            case 2:
                handleLogIn();
                break;
            case 3:
                handleResetPassword();
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

    public String securityQuestion(int userChoice){
        if(userChoice == 1){
            return "What is your mother's maiden name?";
        }
        else if(userChoice == 2){
            return "What is the name of your elementary school?";
        }
        else if(userChoice == 3){
            return "What is the name of your first pet?";
        }
        else {
            throw new IllegalArgumentException("Invalid choice, please pick a number between 1 and 3.");
        }
    }

    public void handleCreateAccount() {
        newAccount = new CreateAccount();
        System.out.println("Please Enter your name: ");
        String name = keyboardInput.nextLine();
        System.out.println("Please Enter your email: ");
        String email = keyboardInput.nextLine();
        System.out.println("Please Enter your phone number: ");
        String phoneNumber = keyboardInput.nextLine();
        System.out.println("Please Enter A Password: ");
        String password = keyboardInput.nextLine();
        secretQuestionOptions();
        int securityQuestionInt = keyboardInput.nextInt();
        keyboardInput.nextLine();
        String securityQuestion = "";
        String securityAnswer = "";
        try {
            securityQuestion = securityQuestion(securityQuestionInt);
            System.out.println("Please Answer Your Security Question (" + securityQuestion + "):");
            securityAnswer = keyboardInput.nextLine().toLowerCase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            newAccount.setName(name);
            newAccount.setEmail(email);
            newAccount.setPhoneNumber(phoneNumber);
            newAccount.setPassword(password);
            BankAccount bankAccount = new BankAccount();
            storeAccountInfo(name, email, phoneNumber, password, bankAccount, securityQuestion, securityAnswer);
        } catch (java.lang.Exception e) {
            System.out.println("Could not create account because: " + e.getMessage());
        }

    }

    public void storeAccountInfo(String name, String email, String phoneNumber, String password, BankAccount bankAccount, String securityQuestion, String securityAnswer) {
        int accountNumber = (int) (Math.random() * 900_000_000) + 100_000_000;
        currentAccountNumber = accountNumber;
        List<Object> accountDetails = new ArrayList<>();
        accountDetails.add(name);
        accountDetails.add(phoneNumber);
        accountDetails.add(email);
        accountDetails.add(password);
        accountDetails.add(bankAccount);
        accountDetails.add(securityQuestion);
        accountDetails.add(securityAnswer);
        this.accounts.put(accountNumber, accountDetails);
        login.setAccounts(accounts);
        System.out.println("Your account has been created.");
        displayAccountDetails(accountNumber);
    }

    public void displayAccountDetails(int accountNumber){
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + this.accounts.get(accountNumber).get(0));
            System.out.println("Phone Number: " + this.accounts.get(accountNumber).get(1));
            System.out.println("Email: " + this.accounts.get(accountNumber).get(2));
    }

    public void handleLogIn(){
        System.out.println("Please Enter Your Account Number");
        int accountNumber = keyboardInput.nextInt();
        keyboardInput.nextLine();
        try {
            login.accountCorrect(accountNumber);
            System.out.println("Please Enter Password");
            String password = keyboardInput.nextLine();
            login.correctPassword(accountNumber, password);
            //display the menu, pass the account Number and account map
            Menu menu = new Menu();
            menu.setAccounts(accounts);
            menu.currentBankAccount(accountNumber);
            while (true) {
			    menu.displayOptions();
                setAccounts(menu.getAccounts());
			    int userChoice = menu.readIntFromPlayer();
	            menu.processUserInput(userChoice);
	            if (userChoice == 13 || userChoice == 14) {
	        	    break;
	            }
		    }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleResetPassword(){
        System.out.println();
        System.out.println("Please Enter Your Account Number");
        int accountNumber = keyboardInput.nextInt();
        keyboardInput.nextLine();
        try {
            login.accountCorrect(accountNumber);
            System.out.println(this.accounts.get(accountNumber).get(5));
            String answer = keyboardInput.nextLine();
            if(answer.toLowerCase().equals(this.accounts.get(accountNumber).get(6))){
                System.out.println("Enter new Password:");
                String newPassword = keyboardInput.nextLine();
                this.accounts.get(accountNumber).set(3, newPassword);
                System.out.println("Your password has been updated, please log in with your new password");
            }
            else{
                System.out.println("Incorrect Answer");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
