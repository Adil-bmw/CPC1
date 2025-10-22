public class SalesRecord {
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    private String date;

    public SalesRecord(int productId, String productName, int quantity, double price, String date) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getDate() { return date; }

    public double getTotalAmount() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return String.format("%s: %d x %.2f тг = %.2f тг",
                productName, quantity, price, getTotalAmount());
    }
}

