package bankapp;
import java.util.Scanner;

public class Menu {

    private BankAccount theAccount;

    public Menu(){
        theAccount = new BankAccount();

    }

    public void displayOptions(){
        System.out.println("Please enter deposit amount: ");
    }

    public double getUserInput(){
        Scanner keyboardInput = new Scanner(System.in);
        double userInput = keyboardInput.nextDouble();
        return userInput;
    }

    public void processUserInput(double amount){
        theAccount.deposit(amount);
    }

    public BankAccount getAccount(){
        return theAccount;
    }
}
