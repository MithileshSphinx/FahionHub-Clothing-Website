<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.fashionhub.controller.OrderServlet.OrderDetail" %>
<%@ page import="com.fashionhub.controller.OrderServlet.OrderItemDetail" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <title>Order Confirmed - FashionHub</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="main-container order-success-page-container">

        <% 
            OrderDetail od = (OrderDetail) request.getAttribute("orderDetail");
            if (od != null) {
        %>
            <div class="success-card-wrapper">
                
                <!-- Animated Checkmark -->
                <div class="checkmark-animation-container">
                    <div class="checkmark-circle">
                        <i class="fa-solid fa-check"></i>
                    </div>
                </div>

                <h1 class="success-title">Order Confirmed!</h1>
                <p class="success-subtitle">Thank you for your order, <%= od.getOrder().getDeliveryName() %>! We've received your request and are preparing it.</p>
                <div class="success-order-id">Order ID: <strong>#<%= od.getOrder().getOrderId() %></strong></div>

                <div class="success-layout-grid">
                    
                    <!-- Order Details summary card -->
                    <div class="success-section-card">
                        <h3 class="success-section-title"><i class="fa-solid fa-file-invoice"></i> Billing & Shipping Info</h3>
                        
                        <div class="info-row">
                            <span class="info-label">Payment Method:</span>
                            <span class="info-value"><%= od.getOrder().getPaymentMethod() %></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">Status:</span>
                            <span class="info-value order-status status-confirmed" style="font-size: 11px; padding: 3px 10px;">
                                <i class="fa-solid fa-circle-check"></i> <%= od.getOrder().getOrderStatus() %>
                            </span>
                        </div>
                        
                        <hr class="detail-divider">
                        
                        <div class="delivery-address-block">
                            <span class="info-label" style="display:block; margin-bottom: 5px;"><i class="fa-solid fa-location-dot" style="color:var(--primary);"></i> Shipping Address:</span>
                            <p class="address-text">
                                <strong><%= od.getOrder().getDeliveryName() %></strong><br>
                                <%= od.getOrder().getDeliveryAddressLine1() %><br>
                                <% if (od.getOrder().getDeliveryAddressLine2() != null && !od.getOrder().getDeliveryAddressLine2().isEmpty()) { %>
                                    <%= od.getOrder().getDeliveryAddressLine2() %><br>
                                <% } %>
                                <%= od.getOrder().getDeliveryCity() %>, <%= od.getOrder().getDeliveryState() %> - <%= od.getOrder().getDeliveryPincode() %><br>
                                Phone: <%= od.getOrder().getDeliveryPhone() %>
                            </p>
                        </div>
                    </div>

                    <!-- Items Summary Card -->
                    <div class="success-section-card">
                        <h3 class="success-section-title"><i class="fa-solid fa-basket-shopping"></i> Items Ordered</h3>
                        
                        <div class="success-items-list">
                            <% for (OrderItemDetail itemDetail : od.getItems()) { 
                                String imgUrl = "";
                                String productName = "Unknown Product";
                                String sizeName = "N/A";
                                if (itemDetail.getProduct() != null) {
                                    productName = itemDetail.getProduct().getProductName();
                                    imgUrl = itemDetail.getProduct().getImageUrl();
                                    if (imgUrl == null || imgUrl.trim().isEmpty()) {
                                        imgUrl = "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?auto=format&fit=crop&w=600&q=80";
                                    } else if (!imgUrl.startsWith("http://") && !imgUrl.startsWith("https://")) {
                                        imgUrl = request.getContextPath() + "/" + imgUrl;
                                    }
                                }
                                if (itemDetail.getProductSize() != null) {
                                    sizeName = itemDetail.getProductSize().getSize();
                                }
                            %>
                                <div class="success-item-row">
                                    <div class="success-item-img">
                                        <img src="<%= imgUrl %>" alt="<%= productName %>">
                                    </div>
                                    <div class="success-item-info">
                                        <span class="success-item-name"><%= productName %></span>
                                        <span class="success-item-meta">Size: <%= sizeName %> | Qty: <%= itemDetail.getOrderItem().getQuantity() %></span>
                                    </div>
                                    <span class="success-item-price">Rs. <%= itemDetail.getSubtotal() %></span>
                                </div>
                            <% } %>
                        </div>

                        <hr class="detail-divider">

                        <div class="summary-row">
                            <span>Subtotal</span>
                            <span>Rs. <%= od.getOrder().getTotalAmount() %></span>
                        </div>
                        <div class="summary-row">
                            <span>Shipping</span>
                            <span class="free-shipping">FREE</span>
                        </div>
                        <hr class="detail-divider">
                        <div class="summary-row total-row" style="margin: 0 !important; font-size: 16px;">
                            <span>Total Amount</span>
                            <span style="color: var(--primary) !important; font-weight: 700;">Rs. <%= od.getOrder().getTotalAmount() %></span>
                        </div>
                    </div>
                </div>

                <div class="success-actions">
                    <a href="<%= request.getContextPath() %>/products" class="btn btn-primary">
                        <i class="fa-solid fa-bag-shopping"></i> Continue Shopping
                    </a>
                    <a href="<%= request.getContextPath() %>/orders" class="btn btn-secondary">
                        <i class="fa-solid fa-clock-rotate-left"></i> View All Orders
                    </a>
                </div>

            </div>
        <% } else { %>
            <div class="no-results">
                <i class="fa-solid fa-circle-xmark" style="color:var(--danger);"></i>
                <h3>Order Details Not Found</h3>
                <p>We couldn't retrieve the details for this order. Please visit your order history.</p>
                <a href="<%= request.getContextPath() %>/orders" class="btn btn-primary" style="margin-top: 15px;">Go to Orders</a>
            </div>
        <% } %>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

</body>
</html>
