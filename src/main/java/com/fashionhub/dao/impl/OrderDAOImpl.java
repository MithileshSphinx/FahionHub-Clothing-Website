package com.fashionhub.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fashionhub.dao.OrderDAO;
import com.fashionhub.model.Order;
import com.fashionhub.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {


private static final String PLACE_ORDER_SQL =
        "INSERT INTO orders(user_id, total_amount, payment_method, order_status, " +
        "delivery_name, delivery_phone, delivery_address_line1, delivery_address_line2, " +
        "delivery_city, delivery_state, delivery_pincode) " +
        "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

private static final String GET_LATEST_ORDER_ID_SQL =
        "SELECT MAX(order_id) FROM orders";

private static final String GET_ORDER_BY_ID_SQL =
        "SELECT * FROM orders WHERE order_id=?";

private static final String GET_ORDERS_BY_USER_SQL =
        "SELECT * FROM orders WHERE user_id=? ORDER BY order_date DESC";

private static final String UPDATE_STATUS_SQL =
        "UPDATE orders SET order_status=? WHERE order_id=?";

private static final String CANCEL_ORDER_SQL =
        "UPDATE orders SET order_status='Cancelled' WHERE order_id=?";

@Override
public boolean placeOrder(Order order) {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(PLACE_ORDER_SQL)) {

        ps.setInt(1, order.getUserId());
        ps.setBigDecimal(2, order.getTotalAmount());
        ps.setString(3, order.getPaymentMethod());
        ps.setString(4, order.getOrderStatus());

        ps.setString(5, order.getDeliveryName());
        ps.setString(6, order.getDeliveryPhone());
        ps.setString(7, order.getDeliveryAddressLine1());
        ps.setString(8, order.getDeliveryAddressLine2());
        ps.setString(9, order.getDeliveryCity());
        ps.setString(10, order.getDeliveryState());
        ps.setString(11, order.getDeliveryPincode());

        return ps.executeUpdate() > 0;

    } catch(Exception e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public int getLatestOrderId() {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(GET_LATEST_ORDER_ID_SQL);
        ResultSet rs = ps.executeQuery()) {

        if(rs.next()) {
            return rs.getInt(1);
        }

    } catch(Exception e) {
        e.printStackTrace();
    }

    return 0;
}

@Override
public Order getOrderById(int orderId) {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(GET_ORDER_BY_ID_SQL)) {

        ps.setInt(1, orderId);

        try(ResultSet rs = ps.executeQuery()) {

            if(rs.next()) {
                return mapResultSetToOrder(rs);
            }
        }

    } catch(Exception e) {
        e.printStackTrace();
    }

    return null;
}

@Override
public List<Order> getOrdersByUserId(int userId) {

    List<Order> orders = new ArrayList<>();

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(GET_ORDERS_BY_USER_SQL)) {

        ps.setInt(1, userId);

        try(ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        }

    } catch(Exception e) {
        e.printStackTrace();
    }

    return orders;
}

@Override
public boolean updateOrderStatus(int orderId, String status) {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE_STATUS_SQL)) {

        ps.setString(1, status);
        ps.setInt(2, orderId);

        return ps.executeUpdate() > 0;

    } catch(Exception e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public boolean cancelOrder(int orderId) {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(CANCEL_ORDER_SQL)) {

        ps.setInt(1, orderId);

        return ps.executeUpdate() > 0;

    } catch(Exception e) {
        e.printStackTrace();
    }

    return false;
}

private Order mapResultSetToOrder(ResultSet rs) throws Exception {

    Order order = new Order();

    order.setOrderId(rs.getInt("order_id"));
    order.setUserId(rs.getInt("user_id"));
    order.setOrderDate((Timestamp) rs.getTimestamp("order_date"));
    order.setTotalAmount((BigDecimal) rs.getBigDecimal("total_amount"));
    order.setPaymentMethod(rs.getString("payment_method"));
    order.setOrderStatus(rs.getString("order_status"));

    order.setDeliveryName(rs.getString("delivery_name"));
    order.setDeliveryPhone(rs.getString("delivery_phone"));
    order.setDeliveryAddressLine1(rs.getString("delivery_address_line1"));
    order.setDeliveryAddressLine2(rs.getString("delivery_address_line2"));
    order.setDeliveryCity(rs.getString("delivery_city"));
    order.setDeliveryState(rs.getString("delivery_state"));
    order.setDeliveryPincode(rs.getString("delivery_pincode"));

    return order;
}


}
