package bankapp;

public class Main {
	 public static void main(String[] args) {
		LogInMenu logInMenu = new LogInMenu();
		while(true){
			logInMenu.displayOptions();
			int userChoice = logInMenu.readIntFromPlayer();
			logInMenu.processUserInput(userChoice);
		}
	}
}
