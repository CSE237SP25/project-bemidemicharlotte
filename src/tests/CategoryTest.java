package tests;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bankapp.SpendingCategoryManager;
import bankapp.BankAccount;
public class CategoryTest {
	
	
	
	 @Test
	    public void testFoodCategorySpending() {
			BankAccount account = new BankAccount();
			SpendingCategoryManager cs = new SpendingCategoryManager(account); 
			account.deposit(200, "General");
			account.spending = 100;  
			cs.processCategory(1);      
			assertEquals(100, cs.getFoodSpending(), 0.001);
			assertEquals(0.0, cs.getGroceriesSpending(), 0.001);
	        assertEquals(0.0, cs.getOtherSpending(), 0.001);
	    }
	 @Test
	    public void testMultipleCategorySpending() {
			BankAccount account = new BankAccount();
			SpendingCategoryManager cs = new SpendingCategoryManager(account); 
			account.deposit(200, "General");
			
			account.spending = 100;  
			cs.processCategory(4);  
			
			
			account.spending=100;
			cs.processCategory(1);
			assertEquals(100, cs.getClothesSpending(), 0.001);
			assertEquals(100, cs.getFoodSpending(), 0.001);
			assertEquals(0.0, cs.getGroceriesSpending(), 0.001);
	        assertEquals(0.0, cs.getOtherSpending(), 0.001);
	    }
}
