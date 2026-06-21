<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.fashionhub.controller.OrderServlet.OrderDetail" %>
<%@ page import="com.fashionhub.controller.OrderServlet.OrderItemDetail" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <title>My Orders - FashionHub</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="main-container orders-page-container">

        <div class="orders-header">
            <h1 class="orders-title"><i class="fa-solid fa-box"></i> My Orders</h1>
            <div class="section-line"></div>
        </div>

        <!-- SUCCESS ALERT -->
        <% 
            String successMessage = (String) request.getAttribute("successMessage");
            if (successMessage != null) {
        %>
            <div class="alert alert-success">
                <i class="fa-solid fa-circle-check"></i> <%= successMessage %>
            </div>
        <% } %>

        <% 
            List<OrderDetail> orderDetails = (List<OrderDetail>) request.getAttribute("orderDetails");
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

            if (orderDetails != null && !orderDetails.isEmpty()) {
        %>
            <div class="orders-list">
                <% for (OrderDetail od : orderDetails) { 
                    String statusClass = "status-confirmed";
                    String statusIcon = "fa-circle-check";
                    String status = od.getOrder().getOrderStatus();
                    if ("Cancelled".equalsIgnoreCase(status)) {
                        statusClass = "status-cancelled";
                        statusIcon = "fa-circle-xmark";
                    } else if ("Delivered".equalsIgnoreCase(status)) {
                        statusClass = "status-delivered";
                        statusIcon = "fa-circle-check";
                    } else if ("Shipped".equalsIgnoreCase(status)) {
                        statusClass = "status-shipped";
                        statusIcon = "fa-truck";
                    } else if ("Processing".equalsIgnoreCase(status)) {
                        statusClass = "status-processing";
                        statusIcon = "fa-gear";
                    }
                %>
                    <div class="order-card">
                        <!-- Order Header -->
                        <div class="order-card-header">
                            <div class="order-id-section">
                                <span class="order-id-label">Order</span>
                                <span class="order-id-value">#<%= od.getOrder().getOrderId() %></span>
                            </div>
                            <div class="order-header-meta">
                                <span class="order-date">
                                    <i class="fa-regular fa-calendar"></i>
                                    <%= od.getOrder().getOrderDate() != null ? sdf.format(od.getOrder().getOrderDate()) : "N/A" %>
                                </span>
                                <span class="order-status <%= statusClass %>">
                                    <i class="fa-solid <%= statusIcon %>"></i> <%= status %>
                                </span>
                            </div>
                        </div>

                        <!-- Order Items -->
                        <div class="order-items-list">
                            <% for (OrderItemDetail oid : od.getItems()) { 
                                String imgUrl = "";
                                String productName = "Unknown Product";
                                String sizeName = "N/A";
                                if (oid.getProduct() != null) {
                                    productName = oid.getProduct().getProductName();
                                    imgUrl = oid.getProduct().getImageUrl();
                                    if (imgUrl == null || imgUrl.trim().isEmpty()) {
                                        imgUrl = "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?auto=format&fit=crop&w=600&q=80";
                                    } else if (!imgUrl.startsWith("http://") && !imgUrl.startsWith("https://")) {
                                        imgUrl = request.getContextPath() + "/" + imgUrl;
                                    }
                                }
                                if (oid.getProductSize() != null) {
                                    sizeName = oid.getProductSize().getSize();
                                }
                            %>
                                <div class="order-item-row">
                                    <div class="order-item-img">
                                        <img src="<%= imgUrl %>" alt="<%= productName %>">
                                    </div>
                                    <div class="order-item-info">
                                        <span class="order-item-name"><%= productName %></span>
                                        <span class="order-item-meta">
                                            Size: <strong><%= sizeName %></strong> &bull; Qty: <strong><%= oid.getOrderItem().getQuantity() %></strong>
                                        </span>
                                    </div>
                                    <div class="order-item-price">
                                        <span class="order-item-unit-price">Rs. <%= oid.getOrderItem().getPrice() %> each</span>
                                        <span class="order-item-total-price">Rs. <%= oid.getSubtotal() %></span>
                                    </div>
                                </div>
                            <% } %>
                        </div>

                        <!-- Order Footer -->
                        <div class="order-card-footer">
                            <div class="order-delivery-info">
                                <span class="delivery-label"><i class="fa-solid fa-location-dot"></i> Delivery:</span>
                                <span class="delivery-address">
                                    <%= od.getOrder().getDeliveryName() %>, 
                                    <%= od.getOrder().getDeliveryAddressLine1() %>, 
                                    <%= od.getOrder().getDeliveryCity() %> - <%= od.getOrder().getDeliveryPincode() %>
                                </span>
                            </div>
                            <div class="order-footer-right">
                                <div class="order-total-amount">
                                    <span>Total:</span>
                                    <strong>Rs. <%= od.getOrder().getTotalAmount() %></strong>
                                </div>
                                <div class="order-payment-method">
                                    <i class="fa-solid fa-credit-card"></i> <%= od.getOrder().getPaymentMethod() %>
                                </div>
                                <% if ("Confirmed".equalsIgnoreCase(status) || "Processing".equalsIgnoreCase(status)) { %>
                                    <a href="<%= request.getContextPath() %>/orders/cancel?id=<%= od.getOrder().getOrderId() %>" 
                                       class="btn btn-cancel-order"
                                       onclick="return confirm('Are you sure you want to cancel Order #<%= od.getOrder().getOrderId() %>?');">
                                        <i class="fa-solid fa-ban"></i> Cancel Order
                                    </a>
                                <% } %>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } else { %>
            <div class="empty-orders-view">
                <i class="fa-solid fa-box-open"></i>
                <h2>No orders yet</h2>
                <p>You haven't placed any orders yet. Start shopping to place your first order!</p>
                <a href="<%= request.getContextPath() %>/products" class="btn btn-primary">Start Shopping</a>
            </div>
        <% } %>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

</body>
</html>
