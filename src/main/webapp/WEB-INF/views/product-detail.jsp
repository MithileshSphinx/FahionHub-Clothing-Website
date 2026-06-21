<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionhub.model.Product" %>
<%@ page import="com.fashionhub.model.ProductSize" %>
<%@ page import="com.fashionhub.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <% 
        Product product = (Product) request.getAttribute("product"); 
    %>
    <title><%= product != null ? product.getProductName() : "Product Detail" %> - FashionHub</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="main-container detail-page-container">
        
        <% if (product != null) { 
            String imgUrl = product.getImageUrl();
            if (imgUrl == null || imgUrl.trim().isEmpty()) {
                imgUrl = "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?auto=format&fit=crop&w=600&q=80";
            } else if (!imgUrl.startsWith("http://") && !imgUrl.startsWith("https://")) {
                imgUrl = request.getContextPath() + "/" + imgUrl;
            }
            Category category = (Category) request.getAttribute("category");
            List<ProductSize> sizes = (List<ProductSize>) request.getAttribute("sizes");
        %>
            <div class="breadcrumb">
                <a href="<%= request.getContextPath() %>/home">Home</a> &gt; 
                <a href="<%= request.getContextPath() %>/products">Products</a> &gt;
                <% if (category != null) { %>
                    <a href="<%= request.getContextPath() %>/products?category=<%= category.getCategoryName() %>"><%= category.getCategoryName() %></a> &gt;
                <% } %>
                <span><%= product.getProductName() %></span>
            </div>

            <div class="detail-layout">
                <!-- PRODUCT IMAGE -->
                <div class="detail-gallery">
                    <div class="detail-image-wrapper">
                        <img src="<%= imgUrl %>" alt="<%= product.getProductName() %>">
                    </div>
                </div>

                <!-- PRODUCT ACTIONS -->
                <div class="detail-info">
                    <span class="detail-brand"><%= product.getBrand() %></span>
                    <h1 class="detail-title"><%= product.getProductName() %></h1>
                    
                    <div class="detail-price">Rs. <%= product.getPrice() %></div>
                    
                    <hr class="detail-divider">

                    <p class="detail-description"><%= product.getDescription() %></p>

                    <form action="<%= request.getContextPath() %>/cart/add" method="post" class="add-to-cart-form">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        
                        <!-- SELECT SIZE -->
                        <div class="form-group">
                            <label for="sizeId" class="form-label">Select Size</label>
                            <select name="sizeId" id="sizeId" class="form-control form-select-custom" required>
                                <option value="" disabled selected>Choose a size</option>
                                <% 
                                    if (sizes != null && !sizes.isEmpty()) {
                                        for (ProductSize ps : sizes) {
                                            boolean outOfStock = ps.getStockQuantity() <= 0;
                                %>
                                    <option value="<%= ps.getSizeId() %>" <%= outOfStock ? "disabled" : "" %>>
                                        <%= ps.getSize() %> <%= outOfStock ? "(Out of Stock)" : "(" + ps.getStockQuantity() + " left)" %>
                                    </option>
                                <% 
                                        }
                                    } else {
                                %>
                                    <option value="" disabled>No sizes available</option>
                                <% } %>
                            </select>
                        </div>

                        <!-- SELECT QUANTITY -->
                        <div class="form-group quantity-group">
                            <label for="quantity" class="form-label">Quantity</label>
                            <div class="quantity-input-wrapper">
                                <button type="button" class="qty-btn" onclick="adjustQty(-1)">-</button>
                                <input type="number" name="quantity" id="quantity" class="qty-input" value="1" min="1" max="10" readonly>
                                <button type="button" class="qty-btn" onclick="adjustQty(1)">+</button>
                            </div>
                        </div>

                        <div class="detail-action-buttons">
                            <button type="submit" class="btn btn-primary btn-block btn-add-to-cart">
                                <i class="fa-solid fa-cart-plus"></i> Add to Cart
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        <% } else { %>
            <div class="no-results">
                <h3>Product not found.</h3>
                <a href="<%= request.getContextPath() %>/products" class="btn btn-primary">Back to Catalog</a>
            </div>
        <% } %>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

    <script>
        function adjustQty(amount) {
            var qtyInput = document.getElementById("quantity");
            var currentVal = parseInt(qtyInput.value);
            var newVal = currentVal + amount;
            if (newVal >= 1 && newVal <= 10) {
                qtyInput.value = newVal;
            }
        }
    </script>
</body>
</html>
