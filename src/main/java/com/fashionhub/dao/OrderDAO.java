package com.fashionhub.dao;

import java.util.List;

import com.fashionhub.model.Order;

public interface OrderDAO {

    boolean placeOrder(Order order);

    int getLatestOrderId();

    Order getOrderById(int orderId);

    List<Order> getOrdersByUserId(int userId);

    boolean updateOrderStatus(int orderId, String status);

    boolean cancelOrder(int orderId);
}