package com.fashionhub.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.fashionhub.dao.CartDAO;
import com.fashionhub.util.DBConnection;

public class CartDAOImpl implements CartDAO {

    private static final String CREATE_CART_SQL =
            "INSERT INTO cart(user_id) VALUES(?)";

    private static final String GET_CART_ID_SQL =
            "SELECT cart_id FROM cart WHERE user_id=?";

    private static final String CLEAR_CART_SQL =
            "DELETE FROM cart_items WHERE cart_id=?";

    @Override
    public boolean createCart(int userId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(CREATE_CART_SQL)) {

            ps.setInt(1, userId);

            return ps.executeUpdate() > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getCartIdByUserId(int userId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_CART_ID_SQL)) {

            ps.setInt(1, userId);

            try(ResultSet rs = ps.executeQuery()) {

                if(rs.next()) {
                    return rs.getInt("cart_id");
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean clearCart(int cartId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(CLEAR_CART_SQL)) {

            ps.setInt(1, cartId);

            return ps.executeUpdate() >= 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}