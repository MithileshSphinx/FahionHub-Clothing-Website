<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionhub.controller.CartServlet.CartItemDetail" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <title>My Shopping Cart - FashionHub</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="main-container cart-page-container">
        
        <div class="cart-header">
            <h1 class="cart-title">Your Shopping Cart</h1>
            <div class="section-line"></div>
        </div>

        <% 
            List<CartItemDetail> cartItems = (List<CartItemDetail>) request.getAttribute("cartItems");
            java.math.BigDecimal subtotal = (java.math.BigDecimal) request.getAttribute("subtotal");
            if (cartItems != null && !cartItems.isEmpty()) {
        %>
            <div class="cart-layout">
                
                <!-- CART ITEMS LIST -->
                <div class="cart-items-wrapper">
                    <% 
                        for (CartItemDetail detail : cartItems) {
                            String imgUrl = detail.getProduct().getImageUrl();
                            if (imgUrl == null || imgUrl.trim().isEmpty()) {
                                imgUrl = "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?auto=format&fit=crop&w=600&q=80";
                            } else if (!imgUrl.startsWith("http://") && !imgUrl.startsWith("https://")) {
                                imgUrl = request.getContextPath() + "/" + imgUrl;
                            }
                    %>
                        <div class="cart-item-card">
                            <div class="cart-item-image">
                                <img src="<%= imgUrl %>" alt="<%= detail.getProduct().getProductName() %>">
                            </div>
                            <div class="cart-item-details">
                                <span class="cart-item-brand"><%= detail.getProduct().getBrand() %></span>
                                <h3 class="cart-item-name">
                                    <a href="<%= request.getContextPath() %>/product-detail?id=<%= detail.getProduct().getProductId() %>">
                                        <%= detail.getProduct().getProductName() %>
                                    </a>
                                </h3>
                                <div class="cart-item-meta">
                                    <span>Size: <strong><%= detail.getProductSize().getSize() %></strong></span>
                                    <span>Price: <strong>Rs. <%= detail.getProduct().getPrice() %></strong></span>
                                </div>
                            </div>
                            <div class="cart-item-qty">
                                <form action="<%= request.getContextPath() %>/cart/update" method="post" class="cart-qty-form">
                                    <input type="hidden" name="cartItemId" value="<%= detail.getCartItem().getCartItemId() %>">
                                    <div class="quantity-input-wrapper">
                                        <button type="button" class="qty-btn" onclick="updateQty(this, -1)">-</button>
                                        <input type="number" name="quantity" class="qty-input cart-qty-value" value="<%= detail.getCartItem().getQuantity() %>" min="1" max="10" readonly>
                                        <button type="button" class="qty-btn" onclick="updateQty(this, 1)">+</button>
                                    </div>
                                </form>
                            </div>
                            <div class="cart-item-total">
                                <div class="total-price">Rs. <%= detail.getTotalPrice() %></div>
                                <a href="<%= request.getContextPath() %>/cart/delete?id=<%= detail.getCartItem().getCartItemId() %>" class="btn-remove-item" title="Remove item">
                                    <i class="fa-solid fa-trash-can"></i>
                                </a>
                            </div>
                        </div>
                    <% } %>
                </div>

                <!-- ORDER SUMMARY -->
                <aside class="cart-summary">
                    <div class="summary-card">
                        <h3 class="summary-title">Order Summary</h3>
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

                        <a href="<%= request.getContextPath() %>/checkout" class="btn btn-primary btn-block btn-checkout">
                            Proceed to Checkout <i class="fa-solid fa-credit-card"></i>
                        </a>
                        
                        <a href="<%= request.getContextPath() %>/products" class="continue-shopping">
                            <i class="fa-solid fa-arrow-left"></i> Continue Shopping
                        </a>
                    </div>
                </aside>

            </div>
        <% } else { %>
            <div class="empty-cart-view">
                <i class="fa-solid fa-cart-shopping"></i>
                <h2>Your cart is empty</h2>
                <p>Browse our collections to add premium clothing and footwear to your wardrobe.</p>
                <a href="<%= request.getContextPath() %>/products" class="btn btn-primary">Start Shopping</a>
            </div>
        <% } %>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

    <script>
        function updateQty(btn, change) {
            var form = btn.closest('form');
            var input = form.querySelector('.cart-qty-value');
            var currentVal = parseInt(input.value);
            var newVal = currentVal + change;
            if (newVal >= 1 && newVal <= 10) {
                input.value = newVal;
                form.submit();
            }
        }
    </script>
</body>
</html>
