package bankapp;

import java.util.*;

public class LogIn{
    public Map<Integer, List<Object>> accounts;

    public LogIn(){
        this.accounts = new HashMap<>();
    }

    public void setAccounts(Map<Integer, List<Object>> accounts){
        this.accounts = accounts;
    }

    public void accountCorrect(int accountNumber){
        if(!accounts.containsKey(accountNumber)){
            throw new IllegalArgumentException("Invalid Account Number");
        }
    
    }

    public void correctPassword(int accountNumber, String password){
        if(!accounts.get(accountNumber).get(3).equals(password)){
            throw new IllegalArgumentException("Incorrect Password");
        }
    }

}