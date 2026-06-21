package com.fashionhub.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionhub.dao.CartItemDAO;
import com.fashionhub.model.CartItem;
import com.fashionhub.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    private static final String ADD_TO_CART_SQL =
            "INSERT INTO cart_items(cart_id, product_id, size_id, quantity) VALUES(?,?,?,?)";

    private static final String UPDATE_QUANTITY_SQL =
            "UPDATE cart_items SET quantity=? WHERE cart_item_id=?";

    private static final String REMOVE_ITEM_SQL =
            "DELETE FROM cart_items WHERE cart_item_id=?";

    private static final String GET_CART_ITEMS_SQL =
            "SELECT * FROM cart_items WHERE cart_id=?";

    private static final String GET_CART_ITEM_SQL =
            "SELECT * FROM cart_items WHERE cart_item_id=?";

    private static final String GET_CART_ITEM_COUNT_SQL =
            "SELECT COUNT(*) FROM cart_items WHERE cart_id=?";

    @Override
    public boolean addToCart(CartItem cartItem) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(ADD_TO_CART_SQL)) {

            ps.setInt(1, cartItem.getCartId());
            ps.setInt(2, cartItem.getProductId());
            ps.setInt(3, cartItem.getSizeId());
            ps.setInt(4, cartItem.getQuantity());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateCartItemQuantity(int cartItemId, int quantity) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_QUANTITY_SQL)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean removeCartItem(int cartItemId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(REMOVE_ITEM_SQL)) {

            ps.setInt(1, cartItemId);

            return ps.executeUpdate() > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<CartItem> getCartItems(int cartId) {

        List<CartItem> cartItems = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_CART_ITEMS_SQL)) {

            ps.setInt(1, cartId);

            try(ResultSet rs = ps.executeQuery()) {

                while(rs.next()) {
                    cartItems.add(mapResultSetToCartItem(rs));
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    @Override
    public CartItem getCartItem(int cartItemId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_CART_ITEM_SQL)) {

            ps.setInt(1, cartItemId);

            try(ResultSet rs = ps.executeQuery()) {

                if(rs.next()) {
                    return mapResultSetToCartItem(rs);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getCartItemCount(int cartId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_CART_ITEM_COUNT_SQL)) {

            ps.setInt(1, cartId);

            try(ResultSet rs = ps.executeQuery()) {

                if(rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private CartItem mapResultSetToCartItem(ResultSet rs) throws Exception {

        CartItem item = new CartItem();

        item.setCartItemId(rs.getInt("cart_item_id"));
        item.setCartId(rs.getInt("cart_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setSizeId(rs.getInt("size_id"));
        item.setQuantity(rs.getInt("quantity"));

        return item;
    }
}