<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionhub.controller.CartServlet.CartItemDetail" %>
<%@ page import="com.fashionhub.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <title>Checkout - FashionHub</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="main-container checkout-page-container">

        <div class="checkout-header">
            <h1 class="checkout-title">Checkout</h1>
            <div class="section-line"></div>
        </div>

        <!-- ALERTS -->
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="alert alert-danger">
                <i class="fa-solid fa-circle-exclamation"></i> <%= error %>
            </div>
        <% } %>

        <% 
            List<CartItemDetail> cartItems = (List<CartItemDetail>) request.getAttribute("cartItems");
            java.math.BigDecimal subtotal = (java.math.BigDecimal) request.getAttribute("subtotal");
            User user = (User) request.getAttribute("user");
        %>

        <form action="<%= request.getContextPath() %>/place-order" method="post" class="checkout-form">

            <div class="checkout-layout">

                <!-- DELIVERY DETAILS -->
                <div class="checkout-delivery-section">
                    <div class="checkout-section-card">
                        <h3 class="checkout-section-title">
                            <i class="fa-solid fa-truck-fast"></i> Delivery Details
                        </h3>

                        <div class="checkout-form-grid">
                            <div class="form-group">
                                <label for="deliveryName" class="form-label">Full Name *</label>
                                <div class="input-icon-wrapper">
                                    <i class="fa-solid fa-user"></i>
                                    <input type="text" name="deliveryName" id="deliveryName" class="form-control"
                                           value="<%= user != null ? user.getFullName() : "" %>"
                                           placeholder="Receiver's full name" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="deliveryPhone" class="form-label">Phone Number *</label>
                                <div class="input-icon-wrapper">
                                    <i class="fa-solid fa-phone"></i>
                                    <input type="tel" name="deliveryPhone" id="deliveryPhone" class="form-control"
                                           value="<%= user != null ? user.getPhone() : "" %>"
                                           placeholder="Contact number" required>
                                </div>
                            </div>

                            <div class="form-group full-width-field">
                                <label for="deliveryAddress1" class="form-label">Address Line 1 *</label>
                                <div class="input-icon-wrapper">
                                    <i class="fa-solid fa-house"></i>
                                    <input type="text" name="deliveryAddress1" id="deliveryAddress1" class="form-control"
                                           value="<%= user != null && user.getAddress() != null ? user.getAddress() : "" %>"
                                           placeholder="House/Flat number, Street name" required>
                                </div>
                            </div>

                            <div class="form-group full-width-field">
                                <label for="deliveryAddress2" class="form-label">Address Line 2</label>
                                <div class="input-icon-wrapper">
                                    <i class="fa-solid fa-location-dot"></i>
                                    <input type="text" name="deliveryAddress2" id="deliveryAddress2" class="form-control"
                                           placeholder="Landmark, Area (optional)">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="deliveryCity" class="form-label">City *</label>
                                <div class="input-icon-wrapper">
                                    <i class="fa-solid fa-city"></i>
                                    <input type="text" name="deliveryCity" id="deliveryCity" class="form-control"
                                           value="<%= user != null && user.getCity() != null ? user.getCity() : "" %>"
                                           placeholder="City name" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="deliveryState" class="form-label">State *</label>
                                <div class="input-icon-wrapper">
                                    <i class="fa-solid fa-map"></i>
                                    <input type="text" name="deliveryState" id="deliveryState" class="form-control"
                                           value="<%= user != null && user.getState() != null ? user.getState() : "" %>"
                                           placeholder="State name" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="deliveryPincode" class="form-label">Pincode *</label>
                                <div class="input-icon-wrapper">
                                    <i class="fa-solid fa-map-pin"></i>
                                    <input type="text" name="deliveryPincode" id="deliveryPincode" class="form-control"
                                           value="<%= user != null && user.getPincode() != null ? user.getPincode() : "" %>"
                                           placeholder="6-digit pincode" required>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- PAYMENT METHOD -->
                    <div class="checkout-section-card">
                        <h3 class="checkout-section-title">
                            <i class="fa-solid fa-credit-card"></i> Payment Method
                        </h3>
                        <div class="payment-options">
                            <label class="payment-option">
                                <input type="radio" name="paymentMethod" value="Cash on Delivery" checked>
                                <span class="payment-option-label">
                                    <i class="fa-solid fa-money-bill-wave"></i>
                                    <span>
                                        <strong>Cash on Delivery</strong>
                                        <small>Pay when you receive your order</small>
                                    </span>
                                </span>
                            </label>
                            <label class="payment-option">
                                <input type="radio" name="paymentMethod" value="UPI">
                                <span class="payment-option-label">
                                    <i class="fa-solid fa-mobile-screen-button"></i>
                                    <span>
                                        <strong>UPI Payment</strong>
                                        <small>GPay, PhonePe, Paytm</small>
                                    </span>
                                </span>
                            </label>
                            <label class="payment-option">
                                <input type="radio" name="paymentMethod" value="Credit/Debit Card">
                                <span class="payment-option-label">
                                    <i class="fa-solid fa-credit-card"></i>
                                    <span>
                                        <strong>Credit / Debit Card</strong>
                                        <small>Visa, MasterCard, RuPay</small>
                                    </span>
                                </span>
                            </label>
                        </div>
                    </div>
                </div>

                <!-- ORDER SUMMARY SIDEBAR -->
                <aside class="checkout-summary">
                    <div class="summary-card">
                        <h3 class="summary-title">Order Summary</h3>
                        <hr class="detail-divider">

                        <div class="checkout-items-list">
                            <% if (cartItems != null) {
                                for (CartItemDetail detail : cartItems) {
                                    String imgUrl = detail.getProduct().getImageUrl();
                                    if (imgUrl == null || imgUrl.trim().isEmpty()) {
                                        imgUrl = "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?auto=format&fit=crop&w=600&q=80";
                                    } else if (!imgUrl.startsWith("http://") && !imgUrl.startsWith("https://")) {
                                        imgUrl = request.getContextPath() + "/" + imgUrl;
                                    }
                            %>
                                <div class="checkout-item">
                                    <div class="checkout-item-img">
                                        <img src="<%= imgUrl %>" alt="<%= detail.getProduct().getProductName() %>">
                                    </div>
                                    <div class="checkout-item-info">
                                        <span class="checkout-item-name"><%= detail.getProduct().getProductName() %></span>
                                        <span class="checkout-item-meta">
                                            Size: <%= detail.getProductSize().getSize() %> | Qty: <%= detail.getCartItem().getQuantity() %>
                                        </span>
                                    </div>
                                    <span class="checkout-item-price">Rs. <%= detail.getTotalPrice() %></span>
                                </div>
                            <% } } %>
                        </div>

                        <hr class="detail-divider">

                        <div class="summary-row">
                            <span>Subtotal</span>
                            <span>Rs. <%= subtotal %></span>
                        </div>
                        <div class="summary-row">
                            <span>Shipping</span>
                            <span class="free-shipping">FREE</span>
                        </div>

                        <hr class="detail-divider">

                        <div class="summary-row total-row">
                            <span>Total</span>
                            <span>Rs. <%= subtotal %></span>
                        </div>

                        <button type="submit" class="btn btn-primary btn-block btn-checkout btn-place-order">
                            <i class="fa-solid fa-bag-shopping"></i> Place Order
                        </button>

                        <a href="<%= request.getContextPath() %>/cart" class="continue-shopping">
                            <i class="fa-solid fa-arrow-left"></i> Back to Cart
                        </a>
                    </div>
                </aside>

            </div>

        </form>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

</body>
</html>
