package bankapp;
import java.util.Scanner;

public class MoneyManagement {
    private double income;
	private double suggestedSaving;
	
	public void setIncome(double income) {
	    if (income < 0) {
	        throw new IllegalArgumentException("Income cannot be negative.");
	    }
	    this.income = income;
	}
	
    public void goalAdvisory() {
        System.out.println("1. Save for a home");
        System.out.println("2. Retirement");
        System.out.println("3. Emergency fund");
    }
        
   public void adviseNavigation(int choice) {
        switch (choice) {
            case 1 -> saveForHomeAdvice(income);
            case 2 -> retirementAdvice();
            case 3 -> emergencyFundAdvice();
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }
   
    private void saveForHomeAdvice(double income) {
        System.out.println("Saving for a Home:");
        System.out.println("- Consider a high-yield savings account.");
        System.out.println("- Get mortgage pre-approval to know your limits.");
        suggestedSaving = 0.2 * income;
        System.out.println("You should aim to save at least $" + suggestedSaving + " every month.");
    }
    
    private void retirementAdvice() {
        System.out.println("Retirement Planning Tips:");
        System.out.println("- Invest in Mutual Funds: Professionally managed portfolios.");
        System.out.println("- Open retirement accounts (IRAs, 401(k)s) for tax advantages.");
        System.out.println("- Start early to maximize compound growth.");
    }
    
    private void emergencyFundAdvice() {
        System.out.println("Emergency Fund Tips:");
        System.out.println("- Use a liquid savings account for quick access.");
        System.out.println("- Consider having 3–6 months of expenses saved.");
        System.out.println("- Credit lines (like HELOCs or cards) are backup options.");
    }
    
    public double getIncome() {
        return this.income;
    }
    
    public double getSuggestedSaving() {
    	return this.suggestedSaving;
    }  
}
