package tests;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import bankapp.MoneyManagement;

public class MoneyManagementTest {
	
	 @Test
	    public void testSaveForHomeAdviceOutput() {
	        MoneyManagement mm = new MoneyManagement();
	        
	        mm.setIncome(5000);
	        mm.adviseNavigation(1);


	        assertEquals(1000.0, mm.getSuggestedSaving(), 0.001);
	    }
}
