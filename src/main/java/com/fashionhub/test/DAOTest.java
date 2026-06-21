package com.fashionhub.test;

import java.util.List;

import com.fashionhub.dao.*;
import com.fashionhub.dao.impl.*;
import com.fashionhub.model.*;

public class DAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAOImpl();
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();
        CartDAO cartDAO = new CartDAOImpl();
        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

        /* =======================================
         * USER TEST
         * ======================================= */
        System.out.println("\n========== USER TEST ==========");

        User user = userDAO.getUserById(1);

        if(user != null) {

            System.out.println(
                    user.getUserId() + " | " +
                    user.getFullName() + " | " +
                    user.getEmail() + " | " +
                    user.getPhone());
        }

        /* =======================================
         * CATEGORY TEST
         * ======================================= */
        System.out.println("\n========== CATEGORY TEST ==========");

        List<Category> categories =
                categoryDAO.getAllCategories();

        for(Category category : categories) {

            System.out.println(
                    category.getCategoryId() + " | " +
                    category.getCategoryName());
        }

        /* =======================================
         * PRODUCT TEST
         * ======================================= */
        System.out.println("\n========== PRODUCT TEST ==========");

        List<Product> products =
                productDAO.getAllProducts();

        for(Product product : products) {

            System.out.println(
                    product.getProductId() + " | " +
                    product.getProductName() + " | " +
                    product.getBrand() + " | " +
                    product.getPrice());
        }

        /* =======================================
         * PRODUCT SIZE TEST
         * ======================================= */
        System.out.println("\n========== PRODUCT SIZE TEST ==========");

        List<ProductSize> sizes =
                productSizeDAO.getSizesByProductId(1);

        for(ProductSize size : sizes) {

            System.out.println(
                    size.getSizeId() + " | " +
                    size.getSize() + " | " +
                    size.getStockQuantity());
        }

        /* =======================================
         * CART TEST
         * ======================================= */
        System.out.println("\n========== CART TEST ==========");

        int cartId =
                cartDAO.getCartIdByUserId(1);

        System.out.println(
                "Cart Id For User 1 : " + cartId);

        /* =======================================
         * CART ITEM TEST
         * ======================================= */
        System.out.println("\n========== CART ITEM TEST ==========");

        List<CartItem> cartItems =
                cartItemDAO.getCartItems(cartId);

        for(CartItem item : cartItems) {

            System.out.println(
                    item.getCartItemId() + " | " +
                    item.getProductId() + " | " +
                    item.getSizeId() + " | " +
                    item.getQuantity());
        }

        /* =======================================
         * ORDER TEST
         * ======================================= */
        System.out.println("\n========== ORDER TEST ==========");

        List<Order> orders =
                orderDAO.getOrdersByUserId(1);

        for(Order order : orders) {

            System.out.println(
                    order.getOrderId() + " | " +
                    order.getTotalAmount() + " | " +
                    order.getOrderStatus());
        }

        /* =======================================
         * ORDER ITEM TEST
         * ======================================= */
        System.out.println("\n========== ORDER ITEM TEST ==========");

        List<OrderItem> orderItems =
                orderItemDAO.getOrderItemsByOrderId(1);

        for(OrderItem item : orderItems) {

            System.out.println(
                    item.getOrderItemId() + " | " +
                    item.getProductId() + " | " +
                    item.getQuantity() + " | " +
                    item.getPrice());
        }

        System.out.println("\n========== TEST COMPLETED ==========");
    }
}