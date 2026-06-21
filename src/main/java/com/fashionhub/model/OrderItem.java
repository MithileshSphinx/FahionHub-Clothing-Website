package com.fashionhub.model;

import java.math.BigDecimal;

public class OrderItem {


private int orderItemId;
private int orderId;
private int productId;
private int sizeId;
private int quantity;
private BigDecimal price;

public OrderItem() {
}

public OrderItem(int orderItemId, int orderId, int productId,
                 int sizeId, int quantity, BigDecimal price) {
    this.orderItemId = orderItemId;
    this.orderId = orderId;
    this.productId = productId;
    this.sizeId = sizeId;
    this.quantity = quantity;
    this.price = price;
}

public int getOrderItemId() {
    return orderItemId;
}

public void setOrderItemId(int orderItemId) {
    this.orderItemId = orderItemId;
}

public int getOrderId() {
    return orderId;
}

public void setOrderId(int orderId) {
    this.orderId = orderId;
}

public int getProductId() {
    return productId;
}

public void setProductId(int productId) {
    this.productId = productId;
}

public int getSizeId() {
    return sizeId;
}

public void setSizeId(int sizeId) {
    this.sizeId = sizeId;
}

public int getQuantity() {
    return quantity;
}

public void setQuantity(int quantity) {
    this.quantity = quantity;
}

public BigDecimal getPrice() {
    return price;
}

public void setPrice(BigDecimal price) {
    this.price = price;
}

@Override
public String toString() {
    return "OrderItem [orderItemId=" + orderItemId +
            ", orderId=" + orderId +
            ", productId=" + productId +
            ", sizeId=" + sizeId +
            ", quantity=" + quantity +
            ", price=" + price + "]";
}


}
