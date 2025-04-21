package bankapp;
import java.util.Scanner;
public class CategorizeSpending {
	
	private BankAccount theAccount;
	
	private double food=0;
	private double groceries=0;
	private double transportation=0;
	private double clothes=0;
	private double other=0;
	private Scanner keyboardInput;
	public CategorizeSpending(BankAccount account) {
		this.theAccount= account;
		keyboardInput = new Scanner(System.in);
	}
	
	
	public void viewCategory() {
		System.out.println("1. Food");
		System.out.println("2. Groceries");
		System.out.println("3. Transportation");
		System.out.println("4. Clothes");
		System.out.println("5. Other");
		System.out.println("6. Exit");
	}
		
	public void processCategory(int choice) {
		switch (choice) {
			case 1 -> food += theAccount.spending;
			case 2 -> groceries += theAccount.spending;
			case 3 -> transportation += theAccount.spending;
			case 4 -> clothes += theAccount.spending;
			default -> other += theAccount.spending;
		}
		 theAccount.spending = 0.0;
	}
	
	public double getFoodSpending() { return food; }
	public double getGroceriesSpending() { return groceries; }
	public double getTransportationSpending() { return transportation; }
	public double getClothesSpending() { return clothes; }
	public double getOtherSpending() { return other; }
	
	public void showCategory() {
	    System.out.println("You spend $"+food+"on food");
	    System.out.println("You spend $"+groceries+"on groceries");
	    System.out.println("You spend $"+transportation+"on transportation");
	    System.out.println("You spend $"+clothes+"on clothes");
	}
}
