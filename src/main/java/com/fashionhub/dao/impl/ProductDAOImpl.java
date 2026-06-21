package com.fashionhub.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fashionhub.dao.ProductDAO;
import com.fashionhub.model.Product;
import com.fashionhub.util.DBConnection;

public class ProductDAOImpl implements ProductDAO {

    private static final String INSERT_PRODUCT_SQL =
            "INSERT INTO products (product_name, brand, category_id, description, price, image_url) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_PRODUCT_BY_ID_SQL =
            "SELECT * FROM products WHERE product_id = ?";

    private static final String GET_ALL_PRODUCTS_SQL =
            "SELECT * FROM products ORDER BY created_at DESC";

    private static final String GET_PRODUCTS_BY_CATEGORY_SQL =
            "SELECT * FROM products WHERE category_id = ? ORDER BY created_at DESC";

    private static final String SEARCH_PRODUCTS_SQL =
            "SELECT * FROM products WHERE product_name LIKE ? OR brand LIKE ?";

    private static final String GET_LATEST_PRODUCTS_SQL =
            "SELECT * FROM products ORDER BY created_at DESC LIMIT 8";

    private static final String UPDATE_PRODUCT_SQL =
            "UPDATE products SET product_name=?, brand=?, category_id=?, description=?, price=?, image_url=? " +
            "WHERE product_id=?";

    private static final String DELETE_PRODUCT_SQL =
            "DELETE FROM products WHERE product_id=?";

    private static final String PRODUCT_COUNT_SQL =
            "SELECT COUNT(*) FROM products";

    @Override
    public boolean addProduct(Product product) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_PRODUCT_SQL)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setInt(3, product.getCategoryId());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setBigDecimal(5, product.getPrice());
            preparedStatement.setString(6, product.getImageUrl());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Product getProductById(int productId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(GET_PRODUCT_BY_ID_SQL)) {

            preparedStatement.setInt(1, productId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if(resultSet.next()) {
                    return mapResultSetToProduct(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(GET_ALL_PRODUCTS_SQL);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while(resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {

        List<Product> products = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(GET_PRODUCTS_BY_CATEGORY_SQL)) {

            preparedStatement.setInt(1, categoryId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while(resultSet.next()) {
                    products.add(mapResultSetToProduct(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> searchProducts(String keyword) {

        List<Product> products = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SEARCH_PRODUCTS_SQL)) {

            String searchKeyword = "%" + keyword + "%";

            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while(resultSet.next()) {
                    products.add(mapResultSetToProduct(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getLatestProducts() {

        List<Product> products = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(GET_LATEST_PRODUCTS_SQL);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while(resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public boolean updateProduct(Product product) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(UPDATE_PRODUCT_SQL)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setInt(3, product.getCategoryId());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setBigDecimal(5, product.getPrice());
            preparedStatement.setString(6, product.getImageUrl());
            preparedStatement.setInt(7, product.getProductId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteProduct(int productId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(DELETE_PRODUCT_SQL)) {

            preparedStatement.setInt(1, productId);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getProductCount() {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(PRODUCT_COUNT_SQL);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Product mapResultSetToProduct(ResultSet resultSet)
            throws SQLException {

        Product product = new Product();

        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setBrand(resultSet.getString("brand"));
        product.setCategoryId(resultSet.getInt("category_id"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice((BigDecimal) resultSet.getBigDecimal("price"));
        product.setImageUrl(resultSet.getString("image_url"));
        product.setCreatedAt((Timestamp) resultSet.getTimestamp("created_at"));

        return product;
    }
}