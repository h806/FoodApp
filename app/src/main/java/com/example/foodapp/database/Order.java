package com.example.foodapp.database;

public class Order {
    private String productId;
    private String productName;
    private String Quantity;
    private String price;
    private String discount;


    public Order(String productId, String productName, String quantity, String price, String discount) {
        this.productId = productId;
        this.productName = productName;
        Quantity = quantity;
        this.price = price;
        this.discount = discount;

    }
    public Order(){

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

}
