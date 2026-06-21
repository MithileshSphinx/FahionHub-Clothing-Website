package com.fashionhub.dao;

public interface CartDAO {

    boolean createCart(int userId);

    int getCartIdByUserId(int userId);

    boolean clearCart(int cartId);
}