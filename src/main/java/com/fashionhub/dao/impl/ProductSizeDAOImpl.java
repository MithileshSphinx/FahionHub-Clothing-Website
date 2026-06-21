package com.fashionhub.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionhub.dao.ProductSizeDAO;
import com.fashionhub.model.ProductSize;
import com.fashionhub.util.DBConnection;

public class ProductSizeDAOImpl implements ProductSizeDAO {

    private static final String INSERT_SQL =
            "INSERT INTO product_sizes(product_id, size, stock_quantity) VALUES(?,?,?)";

    private static final String GET_BY_ID_SQL =
            "SELECT * FROM product_sizes WHERE size_id=?";

    private static final String GET_BY_PRODUCT_SQL =
            "SELECT * FROM product_sizes WHERE product_id=?";

    private static final String UPDATE_SQL =
            "UPDATE product_sizes SET product_id=?, size=?, stock_quantity=? WHERE size_id=?";

    private static final String UPDATE_STOCK_SQL =
            "UPDATE product_sizes SET stock_quantity=? WHERE size_id=?";

    private static final String DELETE_SQL =
            "DELETE FROM product_sizes WHERE size_id=?";

    @Override
    public boolean addProductSize(ProductSize productSize) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, productSize.getProductId());
            ps.setString(2, productSize.getSize());
            ps.setInt(3, productSize.getStockQuantity());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ProductSize getSizeById(int sizeId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_SQL)) {

            ps.setInt(1, sizeId);

            try(ResultSet rs = ps.executeQuery()) {

                if(rs.next()) {
                    return mapResultSetToProductSize(rs);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ProductSize> getSizesByProductId(int productId) {

        List<ProductSize> sizes = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_BY_PRODUCT_SQL)) {

            ps.setInt(1, productId);

            try(ResultSet rs = ps.executeQuery()) {

                while(rs.next()) {
                    sizes.add(mapResultSetToProductSize(rs));
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return sizes;
    }

    @Override
    public boolean updateProductSize(ProductSize productSize) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {

            ps.setInt(1, productSize.getProductId());
            ps.setString(2, productSize.getSize());
            ps.setInt(3, productSize.getStockQuantity());
            ps.setInt(4, productSize.getSizeId());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateStock(int sizeId, int stockQuantity) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_STOCK_SQL)) {

            ps.setInt(1, stockQuantity);
            ps.setInt(2, sizeId);

            return ps.executeUpdate() > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteProductSize(int sizeId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, sizeId);

            return ps.executeUpdate() > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private ProductSize mapResultSetToProductSize(ResultSet rs)
            throws SQLException {

        ProductSize size = new ProductSize();

        size.setSizeId(rs.getInt("size_id"));
        size.setProductId(rs.getInt("product_id"));
        size.setSize(rs.getString("size"));
        size.setStockQuantity(rs.getInt("stock_quantity"));

        return size;
    }
}