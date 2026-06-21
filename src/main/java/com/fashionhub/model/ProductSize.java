package com.fashionhub.model;

public class ProductSize {


private int sizeId;
private int productId;
private String size;
private int stockQuantity;

public ProductSize() {
}

public ProductSize(int sizeId, int productId, String size, int stockQuantity) {
    this.sizeId = sizeId;
    this.productId = productId;
    this.size = size;
    this.stockQuantity = stockQuantity;
}

public int getSizeId() {
    return sizeId;
}

public void setSizeId(int sizeId) {
    this.sizeId = sizeId;
}

public int getProductId() {
    return productId;
}

public void setProductId(int productId) {
    this.productId = productId;
}

public String getSize() {
    return size;
}

public void setSize(String size) {
    this.size = size;
}

public int getStockQuantity() {
    return stockQuantity;
}

public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
}

@Override
public String toString() {
    return "ProductSize [sizeId=" + sizeId +
            ", productId=" + productId +
            ", size=" + size +
            ", stockQuantity=" + stockQuantity + "]";
}


}
