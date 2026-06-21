package com.fashionhub.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionhub.dao.OrderItemDAO;
import com.fashionhub.model.OrderItem;
import com.fashionhub.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {


private static final String ADD_ORDER_ITEM_SQL =
        "INSERT INTO order_items(order_id, product_id, size_id, quantity, price) " +
        "VALUES(?,?,?,?,?)";

private static final String GET_ORDER_ITEM_BY_ID_SQL =
        "SELECT * FROM order_items WHERE order_item_id=?";

private static final String GET_ORDER_ITEMS_BY_ORDER_ID_SQL =
        "SELECT * FROM order_items WHERE order_id=?";

private static final String UPDATE_ORDER_ITEM_SQL =
        "UPDATE order_items SET product_id=?, size_id=?, quantity=?, price=? " +
        "WHERE order_item_id=?";

private static final String DELETE_ORDER_ITEM_SQL =
        "DELETE FROM order_items WHERE order_item_id=?";

@Override
public boolean addOrderItem(OrderItem orderItem) {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps =
                connection.prepareStatement(ADD_ORDER_ITEM_SQL)) {

        ps.setInt(1, orderItem.getOrderId());
        ps.setInt(2, orderItem.getProductId());
        ps.setInt(3, orderItem.getSizeId());
        ps.setInt(4, orderItem.getQuantity());
        ps.setBigDecimal(5, orderItem.getPrice());

        return ps.executeUpdate() > 0;

    } catch(Exception e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public OrderItem getOrderItemById(int orderItemId) {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps =
                connection.prepareStatement(GET_ORDER_ITEM_BY_ID_SQL)) {

        ps.setInt(1, orderItemId);

        try(ResultSet rs = ps.executeQuery()) {

            if(rs.next()) {
                return mapResultSetToOrderItem(rs);
            }
        }

    } catch(Exception e) {
        e.printStackTrace();
    }

    return null;
}

@Override
public List<OrderItem> getOrderItemsByOrderId(int orderId) {

    List<OrderItem> orderItems = new ArrayList<>();

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps =
                connection.prepareStatement(GET_ORDER_ITEMS_BY_ORDER_ID_SQL)) {

        ps.setInt(1, orderId);

        try(ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                orderItems.add(mapResultSetToOrderItem(rs));
            }
        }

    } catch(Exception e) {
        e.printStackTrace();
    }

    return orderItems;
}

@Override
public boolean updateOrderItem(OrderItem orderItem) {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps =
                connection.prepareStatement(UPDATE_ORDER_ITEM_SQL)) {

        ps.setInt(1, orderItem.getProductId());
        ps.setInt(2, orderItem.getSizeId());
        ps.setInt(3, orderItem.getQuantity());
        ps.setBigDecimal(4, orderItem.getPrice());
        ps.setInt(5, orderItem.getOrderItemId());

        return ps.executeUpdate() > 0;

    } catch(Exception e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public boolean deleteOrderItem(int orderItemId) {

    try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps =
                connection.prepareStatement(DELETE_ORDER_ITEM_SQL)) {

        ps.setInt(1, orderItemId);

        return ps.executeUpdate() > 0;

    } catch(Exception e) {
        e.printStackTrace();
    }

    return false;
}

private OrderItem mapResultSetToOrderItem(ResultSet rs)
        throws Exception {

    OrderItem orderItem = new OrderItem();

    orderItem.setOrderItemId(rs.getInt("order_item_id"));
    orderItem.setOrderId(rs.getInt("order_id"));
    orderItem.setProductId(rs.getInt("product_id"));
    orderItem.setSizeId(rs.getInt("size_id"));
    orderItem.setQuantity(rs.getInt("quantity"));
    orderItem.setPrice((BigDecimal) rs.getBigDecimal("price"));

    return orderItem;
}


}
