package model;

public class TransactionItem {
    private int id;
    private Product product;
    private int quantity;
    private double price;

    public TransactionItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getSubtotal() {
        return quantity * price;
    }
}