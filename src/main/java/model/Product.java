package model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String code;
    private String name;
    private String description;
    private double price;
    private int stock;
    private Category category;

    public Product() {}

    public Product(int id, String code, String name, String description,
                   double price, int stock, Category category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    // Helper method to get category name (used in table)
    public String getCategoryName() {
        return category != null ? category.getName() : "";
    }
}