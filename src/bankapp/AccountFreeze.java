package bankapp;

import java.util.Scanner;

public class AccountFreeze {
	private boolean isFrozen;
	private Scanner keyboardInput;
	
    public AccountFreeze() {
        this.isFrozen = false;
        this.keyboardInput = new Scanner(System.in);
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void freeze() {
    	System.out.println("Your account has been frozen.");
        isFrozen = true;
    }

    public void unfreeze() {
    	System.out.println("Your account has been unfrozen.");
        isFrozen = false;
    }

    public String getStatusMessage() {
    	if (isFrozen) {
    		return "Account is frozen. You cannot withdraw money";
    	} else {
    		return "Account is Active";
    	}
    }
    
    public void displayOptions() {
    	System.out.println("Would you like to:");
        System.out.println("1. Freeze account");
        System.out.println("2. Unfreeze account");
        System.out.println("3. Exit");
    }
    
    public int readIntFromPlayer() {
		System.out.println("Enter your choice: ");
		int userChoice = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return userChoice;
	}
}
