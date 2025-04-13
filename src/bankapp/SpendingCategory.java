package bankapp;

public class SpendingCategory {
    private String categoryName;
    private double threshold;
    private double currentTotal;

    public SpendingCategory(String categoryName, double threshold) {
        this.categoryName = categoryName;
        this.threshold = threshold;
        this.currentTotal = 0;
    }

    public void addSpending(double amount) {
        currentTotal += amount;
    }

    public boolean isOverLimit() {
        return currentTotal > threshold;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getThreshold() {
        return threshold;
    }

    public double getCurrentTotal() {
        return currentTotal;
    }
}
