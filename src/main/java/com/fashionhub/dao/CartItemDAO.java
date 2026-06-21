package com.fashionhub.dao;

import java.util.List;

import com.fashionhub.model.CartItem;

public interface CartItemDAO {

    boolean addToCart(CartItem cartItem);

    boolean updateCartItemQuantity(int cartItemId, int quantity);

    boolean removeCartItem(int cartItemId);

    List<CartItem> getCartItems(int cartId);

    CartItem getCartItem(int cartItemId);

    int getCartItemCount(int cartId);
}