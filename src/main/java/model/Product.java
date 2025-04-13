package model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String code;
    private String name;
    private BigDecimal price;
    private int stock;
    private String category;

    // Constructors
    public Product() {}

    public Product(int id, String code, String name, BigDecimal price, int stock, String category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}