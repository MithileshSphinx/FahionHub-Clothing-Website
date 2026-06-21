package com.fashionhub.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {

private int productId;
private String productName;
private String brand;
private int categoryId;
private String description;
private BigDecimal price;
private String imageUrl;
private Timestamp createdAt;

public Product() {
}

public Product(int productId, String productName, String brand, int categoryId,
               String description, BigDecimal price, String imageUrl,
               Timestamp createdAt) {
    this.productId = productId;
    this.productName = productName;
    this.brand = brand;
    this.categoryId = categoryId;
    this.description = description;
    this.price = price;
    this.imageUrl = imageUrl;
    this.createdAt = createdAt;
}

public int getProductId() {
    return productId;
}

public void setProductId(int productId) {
    this.productId = productId;
}

public String getProductName() {
    return productName;
}

public void setProductName(String productName) {
    this.productName = productName;
}

public String getBrand() {
    return brand;
}

public void setBrand(String brand) {
    this.brand = brand;
}

public int getCategoryId() {
    return categoryId;
}

public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public BigDecimal getPrice() {
    return price;
}

public void setPrice(BigDecimal price) {
    this.price = price;
}

public String getImageUrl() {
    return imageUrl;
}

public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
}

public Timestamp getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
}

@Override
public String toString() {
    return "Product [productId=" + productId +
            ", productName=" + productName +
            ", brand=" + brand +
            ", categoryId=" + categoryId +
            ", price=" + price + "]";
}


}
