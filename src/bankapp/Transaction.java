package bankapp;

public class Transaction {
	
	private String type;
	private double amount;
	private String time;
	private String category;

	public Transaction(String type, double amount, String time, String category) {
		this.type = type;
		this.amount = amount;
		this.time = time;
		this.category = category;
	}

	public void showTransaction() {
		System.out.println("[" + time + "] " + type + ": $" + amount + " (" + category + ")");
	}

	public String getCategory() {
		return category;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "[" + time + "] " + type + ": $" + amount + (category != null ? " (Category: " + category + ")" : "");
	}

}