package com.fashionhub.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fashionhub.dao.CartDAO;
import com.fashionhub.dao.CartItemDAO;
import com.fashionhub.dao.ProductDAO;
import com.fashionhub.dao.ProductSizeDAO;
import com.fashionhub.dao.impl.CartDAOImpl;
import com.fashionhub.dao.impl.CartItemDAOImpl;
import com.fashionhub.dao.impl.ProductDAOImpl;
import com.fashionhub.dao.impl.ProductSizeDAOImpl;
import com.fashionhub.model.CartItem;
import com.fashionhub.model.Product;
import com.fashionhub.model.ProductSize;
import com.fashionhub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/delete"})
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CartDAO cartDAO = new CartDAOImpl();
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();

        if ("/cart".equals(path)) {
            handleViewCart(request, response);
        } else if ("/cart/delete".equals(path)) {
            handleDeleteCartItem(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();

        if ("/cart/add".equals(path)) {
            handleAddToCart(request, response);
        } else if ("/cart/update".equals(path)) {
            handleUpdateCartItem(request, response);
        }
    }

    private void handleViewCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User) request.getSession().getAttribute("loggedInUser");
        int cartId = cartDAO.getCartIdByUserId(user.getUserId());
        if (cartId == 0) {
            cartDAO.createCart(user.getUserId());
            cartId = cartDAO.getCartIdByUserId(user.getUserId());
        }

        List<CartItem> items = cartItemDAO.getCartItems(cartId);
        List<CartItemDetail> details = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem item : items) {
            Product product = productDAO.getProductById(item.getProductId());
            ProductSize size = productSizeDAO.getSizeById(item.getSizeId());
            if (product != null && size != null) {
                CartItemDetail detail = new CartItemDetail(item, product, size);
                details.add(detail);
                
                BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getQuantity()));
                subtotal = subtotal.add(itemTotal);
            }
        }

        request.setAttribute("cartItems", details);
        request.setAttribute("subtotal", subtotal);
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User) request.getSession().getAttribute("loggedInUser");
        String productIdStr = request.getParameter("productId");
        String sizeIdStr = request.getParameter("sizeId");
        String quantityStr = request.getParameter("quantity");

        if (productIdStr == null || sizeIdStr == null || quantityStr == null) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr.trim());
            int sizeId = Integer.parseInt(sizeIdStr.trim());
            int quantity = Integer.parseInt(quantityStr.trim());

            int cartId = cartDAO.getCartIdByUserId(user.getUserId());
            if (cartId == 0) {
                cartDAO.createCart(user.getUserId());
                cartId = cartDAO.getCartIdByUserId(user.getUserId());
            }

            // Check if item already exists in cart, then update its quantity instead of creating a new item
            List<CartItem> existingItems = cartItemDAO.getCartItems(cartId);
            CartItem targetItem = null;
            for (CartItem item : existingItems) {
                if (item.getProductId() == productId && item.getSizeId() == sizeId) {
                    targetItem = item;
                    break;
                }
            }

            boolean success;
            if (targetItem != null) {
                success = cartItemDAO.updateCartItemQuantity(targetItem.getCartItemId(), targetItem.getQuantity() + quantity);
            } else {
                CartItem newItem = new CartItem();
                newItem.setCartId(cartId);
                newItem.setProductId(productId);
                newItem.setSizeId(sizeId);
                newItem.setQuantity(quantity);
                success = cartItemDAO.addToCart(newItem);
            }

            if (success) {
                response.sendRedirect(request.getContextPath() + "/cart");
            } else {
                response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productId + "&error=failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/products");
        }
    }

    private void handleUpdateCartItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String cartItemIdStr = request.getParameter("cartItemId");
        String quantityStr = request.getParameter("quantity");

        if (cartItemIdStr != null && quantityStr != null) {
            try {
                int cartItemId = Integer.parseInt(cartItemIdStr.trim());
                int quantity = Integer.parseInt(quantityStr.trim());
                if (quantity > 0) {
                    cartItemDAO.updateCartItemQuantity(cartItemId, quantity);
                } else {
                    cartItemDAO.removeCartItem(cartItemId);
                }
            } catch (NumberFormatException e) {
                // Ignore invalid input
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private void handleDeleteCartItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String cartItemIdStr = request.getParameter("id");
        if (cartItemIdStr != null) {
            try {
                int cartItemId = Integer.parseInt(cartItemIdStr.trim());
                cartItemDAO.removeCartItem(cartItemId);
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    // Helper class to wrap item details
    public static class CartItemDetail {
        private final CartItem cartItem;
        private final Product product;
        private final ProductSize productSize;

        public CartItemDetail(CartItem cartItem, Product product, ProductSize productSize) {
            this.cartItem = cartItem;
            this.product = product;
            this.productSize = productSize;
        }

        public CartItem getCartItem() {
            return cartItem;
        }

        public Product getProduct() {
            return product;
        }

        public ProductSize getProductSize() {
            return productSize;
        }

        public BigDecimal getTotalPrice() {
            return product.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
        }
    }
}
