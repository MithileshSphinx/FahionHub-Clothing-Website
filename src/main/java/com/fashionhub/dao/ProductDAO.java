package com.fashionhub.dao;

import java.util.List;

import com.fashionhub.model.Product;

public interface ProductDAO {

    boolean addProduct(Product product);

    Product getProductById(int productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(int categoryId);

    List<Product> searchProducts(String keyword);

    List<Product> getLatestProducts();

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    int getProductCount();
}