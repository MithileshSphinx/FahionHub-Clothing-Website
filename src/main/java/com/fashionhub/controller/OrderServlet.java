package com.fashionhub.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fashionhub.dao.CartDAO;
import com.fashionhub.dao.CartItemDAO;
import com.fashionhub.dao.OrderDAO;
import com.fashionhub.dao.OrderItemDAO;
import com.fashionhub.dao.ProductDAO;
import com.fashionhub.dao.ProductSizeDAO;
import com.fashionhub.dao.impl.CartDAOImpl;
import com.fashionhub.dao.impl.CartItemDAOImpl;
import com.fashionhub.dao.impl.OrderDAOImpl;
import com.fashionhub.dao.impl.OrderItemDAOImpl;
import com.fashionhub.dao.impl.ProductDAOImpl;
import com.fashionhub.dao.impl.ProductSizeDAOImpl;
import com.fashionhub.model.CartItem;
import com.fashionhub.model.Order;
import com.fashionhub.model.OrderItem;
import com.fashionhub.model.Product;
import com.fashionhub.model.ProductSize;
import com.fashionhub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/checkout", "/place-order", "/orders", "/orders/cancel", "/order-success"})
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CartDAO cartDAO = new CartDAOImpl();
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/checkout".equals(path)) {
            handleCheckoutPage(request, response);
        } else if ("/orders".equals(path)) {
            handleMyOrders(request, response);
        } else if ("/orders/cancel".equals(path)) {
            handleCancelOrder(request, response);
        } else if ("/order-success".equals(path)) {
            handleOrderSuccess(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/place-order".equals(path)) {
            handlePlaceOrder(request, response);
        }
    }

    /**
     * Displays the checkout page with cart items and a delivery address form.
     */
    private void handleCheckoutPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("loggedInUser");
        int cartId = cartDAO.getCartIdByUserId(user.getUserId());

        if (cartId == 0) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        List<CartItem> items = cartItemDAO.getCartItems(cartId);
        if (items.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        List<CartServlet.CartItemDetail> details = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem item : items) {
            Product product = productDAO.getProductById(item.getProductId());
            ProductSize size = productSizeDAO.getSizeById(item.getSizeId());
            if (product != null && size != null) {
                CartServlet.CartItemDetail detail = new CartServlet.CartItemDetail(item, product, size);
                details.add(detail);
                BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getQuantity()));
                subtotal = subtotal.add(itemTotal);
            }
        }

        request.setAttribute("cartItems", details);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    /**
     * Processes the order placement: creates Order + OrderItems from cart, clears cart.
     */
    private void handlePlaceOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("loggedInUser");
        int cartId = cartDAO.getCartIdByUserId(user.getUserId());

        if (cartId == 0) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        List<CartItem> items = cartItemDAO.getCartItems(cartId);
        if (items.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Calculate total
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<CartServlet.CartItemDetail> details = new ArrayList<>();
        for (CartItem item : items) {
            Product product = productDAO.getProductById(item.getProductId());
            ProductSize size = productSizeDAO.getSizeById(item.getSizeId());
            if (product != null && size != null) {
                details.add(new CartServlet.CartItemDetail(item, product, size));
                totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }

        // Get delivery details from checkout form
        String deliveryName = request.getParameter("deliveryName");
        String deliveryPhone = request.getParameter("deliveryPhone");
        String deliveryAddress1 = request.getParameter("deliveryAddress1");
        String deliveryAddress2 = request.getParameter("deliveryAddress2");
        String deliveryCity = request.getParameter("deliveryCity");
        String deliveryState = request.getParameter("deliveryState");
        String deliveryPincode = request.getParameter("deliveryPincode");
        String paymentMethod = request.getParameter("paymentMethod");

        // Validations
        if (deliveryName == null || deliveryName.trim().isEmpty() ||
            deliveryPhone == null || deliveryPhone.trim().isEmpty() ||
            deliveryAddress1 == null || deliveryAddress1.trim().isEmpty() ||
            deliveryCity == null || deliveryCity.trim().isEmpty() ||
            deliveryState == null || deliveryState.trim().isEmpty() ||
            deliveryPincode == null || deliveryPincode.trim().isEmpty()) {

            request.setAttribute("error", "All delivery fields are required.");
            request.setAttribute("cartItems", details);
            request.setAttribute("subtotal", totalAmount);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
            return;
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "Cash on Delivery";
        }

        // Create Order
        Order order = new Order();
        order.setUserId(user.getUserId());
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(paymentMethod.trim());
        order.setOrderStatus("Confirmed");
        order.setDeliveryName(deliveryName.trim());
        order.setDeliveryPhone(deliveryPhone.trim());
        order.setDeliveryAddressLine1(deliveryAddress1.trim());
        order.setDeliveryAddressLine2(deliveryAddress2 != null ? deliveryAddress2.trim() : "");
        order.setDeliveryCity(deliveryCity.trim());
        order.setDeliveryState(deliveryState.trim());
        order.setDeliveryPincode(deliveryPincode.trim());

        boolean orderPlaced = orderDAO.placeOrder(order);

        if (orderPlaced) {
            int orderId = orderDAO.getLatestOrderId();

            // Add order items
            for (CartServlet.CartItemDetail detail : details) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setProductId(detail.getProduct().getProductId());
                orderItem.setSizeId(detail.getProductSize().getSizeId());
                orderItem.setQuantity(detail.getCartItem().getQuantity());
                orderItem.setPrice(detail.getProduct().getPrice());
                orderItemDAO.addOrderItem(orderItem);
            }

            // Clear the cart
            cartDAO.clearCart(cartId);

            // Redirect to order confirmation
            response.sendRedirect(request.getContextPath() + "/order-success?orderId=" + orderId);
        } else {
            request.setAttribute("error", "Failed to place order. Please try again.");
            request.setAttribute("cartItems", details);
            request.setAttribute("subtotal", totalAmount);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        }
    }

    /**
     * Displays the "My Orders" page with all user orders and their items.
     */
    private void handleMyOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("loggedInUser");
        List<Order> orders = orderDAO.getOrdersByUserId(user.getUserId());

        // Build detail list for each order
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(order.getOrderId());
            List<OrderItemDetail> itemDetails = new ArrayList<>();
            for (OrderItem oi : orderItems) {
                Product product = productDAO.getProductById(oi.getProductId());
                ProductSize size = productSizeDAO.getSizeById(oi.getSizeId());
                itemDetails.add(new OrderItemDetail(oi, product, size));
            }
            orderDetails.add(new OrderDetail(order, itemDetails));
        }

        request.setAttribute("orderDetails", orderDetails);

        // Check for success message from order placement
        String success = request.getParameter("success");
        String orderId = request.getParameter("orderId");
        if ("true".equals(success) && orderId != null) {
            request.setAttribute("successMessage",
                    "Order #" + orderId + " placed successfully! Thank you for shopping with FashionHub.");
        }

        // Check for success message from order cancellation
        String cancelled = request.getParameter("cancelled");
        if ("true".equals(cancelled) && orderId != null) {
            request.setAttribute("successMessage",
                    "Order #" + orderId + " was cancelled successfully.");
        }

        request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
    }

    /**
     * Cancels an order.
     */
    private void handleCancelOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdStr = request.getParameter("id");
        User user = (User) request.getSession().getAttribute("loggedInUser");

        if (orderIdStr != null) {
            try {
                int orderId = Integer.parseInt(orderIdStr.trim());
                Order order = orderDAO.getOrderById(orderId);

                // Security: ensure the order belongs to the logged-in user
                if (order != null && order.getUserId() == user.getUserId()) {
                    if (orderDAO.cancelOrder(orderId)) {
                        response.sendRedirect(request.getContextPath() + "/orders?cancelled=true&orderId=" + orderId);
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                // Ignore invalid input
            }
        }

        response.sendRedirect(request.getContextPath() + "/orders");
    }

    /**
     * Displays a dedicated order success summary page.
     */
    private void handleOrderSuccess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("loggedInUser");
        String orderIdStr = request.getParameter("orderId");

        if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/orders");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr.trim());
            Order order = orderDAO.getOrderById(orderId);

            if (order != null && order.getUserId() == user.getUserId()) {
                List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);
                List<OrderItemDetail> itemDetails = new ArrayList<>();
                for (OrderItem oi : orderItems) {
                    Product product = productDAO.getProductById(oi.getProductId());
                    ProductSize size = productSizeDAO.getSizeById(oi.getSizeId());
                    itemDetails.add(new OrderItemDetail(oi, product, size));
                }
                request.setAttribute("orderDetail", new OrderDetail(order, itemDetails));
                request.getRequestDispatcher("/WEB-INF/views/order-success.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            // Ignore
        }

        response.sendRedirect(request.getContextPath() + "/orders");
    }

    // =================== HELPER CLASSES ===================

    /**
     * Wraps an Order with its list of OrderItemDetail objects for display.
     */
    public static class OrderDetail {
        private final Order order;
        private final List<OrderItemDetail> items;

        public OrderDetail(Order order, List<OrderItemDetail> items) {
            this.order = order;
            this.items = items;
        }

        public Order getOrder() {
            return order;
        }

        public List<OrderItemDetail> getItems() {
            return items;
        }
    }

    /**
     * Wraps an OrderItem with Product and ProductSize details for display.
     */
    public static class OrderItemDetail {
        private final OrderItem orderItem;
        private final Product product;
        private final ProductSize productSize;

        public OrderItemDetail(OrderItem orderItem, Product product, ProductSize productSize) {
            this.orderItem = orderItem;
            this.product = product;
            this.productSize = productSize;
        }

        public OrderItem getOrderItem() {
            return orderItem;
        }

        public Product getProduct() {
            return product;
        }

        public ProductSize getProductSize() {
            return productSize;
        }

        public BigDecimal getSubtotal() {
            if (orderItem != null && orderItem.getPrice() != null) {
                return orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
            }
            return BigDecimal.ZERO;
        }
    }
}
