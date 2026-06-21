<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionhub.model.Product" %>
<%@ page import="com.fashionhub.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <title>FashionHub - Premium Fashion Store</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <!-- HERO SECTION -->
    <section class="hero-section">
        <div class="hero-overlay"></div>
        <div class="hero-content">
            <span class="hero-tagline">New Season Arrivals</span>
            <h1 class="hero-title">Elevate Your Daily Style</h1>
            <p class="hero-description">Explore our curated collections featuring premium quality, modern aesthetics, and ultimate comfort.</p>
            <div class="hero-actions">
                <a href="<%= request.getContextPath() %>/products" class="btn btn-primary">Shop Collection <i class="fa-solid fa-arrow-right"></i></a>
                <a href="#categories" class="btn btn-secondary">Browse Categories</a>
            </div>
        </div>
    </section>

    <div class="main-container">

        <!-- QUICK NAVIGATION DASHBOARD -->
        <section class="section quick-dashboard-section">
            <div class="dashboard-grid">
                <a href="<%= request.getContextPath() %>/products" class="dashboard-card">
                    <div class="dashboard-card-icon"><i class="fa-solid fa-store"></i></div>
                    <div class="dashboard-card-info">
                        <h4>Shop Collection</h4>
                        <p>Browse our premium apparel</p>
                    </div>
                </a>
                <a href="<%= request.getContextPath() %>/cart" class="dashboard-card">
                    <div class="dashboard-card-icon"><i class="fa-solid fa-cart-shopping"></i></div>
                    <div class="dashboard-card-info">
                        <h4>My Cart</h4>
                        <p>View items & checkout</p>
                    </div>
                </a>
                <a href="<%= request.getContextPath() %>/orders" class="dashboard-card">
                    <div class="dashboard-card-icon"><i class="fa-solid fa-box"></i></div>
                    <div class="dashboard-card-info">
                        <h4>My Orders</h4>
                        <p>Track & manage orders</p>
                    </div>
                </a>
                <% if (session.getAttribute("loggedInUser") == null) { %>
                    <a href="<%= request.getContextPath() %>/login" class="dashboard-card">
                        <div class="dashboard-card-icon"><i class="fa-solid fa-user-lock"></i></div>
                        <div class="dashboard-card-info">
                            <h4>Login / Register</h4>
                            <p>Access personalized shopping</p>
                        </div>
                    </a>
                <% } else { %>
                    <a href="<%= request.getContextPath() %>/logout" class="dashboard-card">
                        <div class="dashboard-card-icon"><i class="fa-solid fa-right-from-bracket"></i></div>
                        <div class="dashboard-card-info">
                            <h4>Sign Out</h4>
                            <p>Safely secure your session</p>
                        </div>
                    </a>
                <% } %>
            </div>
        </section>

        <!-- CATEGORIES SECTION -->
        <section id="categories" class="section">
            <div class="section-header">
                <h2 class="section-title">Shop by Category</h2>
                <div class="section-line"></div>
            </div>
            
            <div class="category-grid">
                <% 
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    if (categories != null) {
                        for (Category cat : categories) {
                            String iconClass = "fa-tag";
                            String desc = "Discover the latest trends";
                            if ("Men".equalsIgnoreCase(cat.getCategoryName())) {
                                iconClass = "fa-person";
                                desc = "Sleek and classic menswear";
                            } else if ("Women".equalsIgnoreCase(cat.getCategoryName())) {
                                iconClass = "fa-person-dress";
                                desc = "Elegant and modern styles";
                            } else if ("Footwear".equalsIgnoreCase(cat.getCategoryName())) {
                                iconClass = "fa-shoe-prints";
                                desc = "Comfort meets premium craftsmanship";
                            } else if ("Accessories".equalsIgnoreCase(cat.getCategoryName())) {
                                iconClass = "fa-gem";
                                desc = "Details that complete your look";
                            }
                %>
                    <a href="<%= request.getContextPath() %>/products?category=<%= cat.getCategoryName() %>" class="category-card">
                        <span class="category-icon">
                            <i class="fa-solid <%= iconClass %>"></i>
                        </span>
                        <span class="category-name" style="display:block;"><%= cat.getCategoryName() %></span>
                        <span class="category-desc" style="display:block;"><%= desc %></span>
                        <span class="category-link">Explore <i class="fa-solid fa-chevron-right"></i></span>
                    </a>
                <% 
                        }
                    } 
                %>
            </div>
        </section>

        <!-- LATEST PRODUCTS SECTION -->
        <section class="section">
            <div class="section-header">
                <h2 class="section-title">New Arrivals</h2>
                <div class="section-line"></div>
            </div>

            <div class="product-grid">
                <% 
                    List<Product> latestProducts = (List<Product>) request.getAttribute("latestProducts");
                    if (latestProducts != null && !latestProducts.isEmpty()) {
                        for (Product product : latestProducts) {
                            // Determine placeholder image if image_url is empty
                            String imgUrl = product.getImageUrl();
                            if (imgUrl == null || imgUrl.trim().isEmpty()) {
                                imgUrl = "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?auto=format&fit=crop&w=600&q=80";
                            } else if (!imgUrl.startsWith("http://") && !imgUrl.startsWith("https://")) {
                                imgUrl = request.getContextPath() + "/" + imgUrl;
                            }
                %>
                    <div class="product-card">
                        <div class="product-image-container">
                            <img class="product-image" src="<%= imgUrl %>" alt="<%= product.getProductName() %>">
                            <div class="product-badge">NEW</div>
                            <div class="product-card-overlay">
                                <a href="<%= request.getContextPath() %>/product-detail?id=<%= product.getProductId() %>" class="btn-view-detail">View Details</a>
                            </div>
                        </div>
                        <div class="product-info">
                            <span class="product-brand"><%= product.getBrand() %></span>
                            <h3 class="product-name">
                                <a href="<%= request.getContextPath() %>/product-detail?id=<%= product.getProductId() %>"><%= product.getProductName() %></a>
                            </h3>
                            <div class="product-price">Rs. <%= product.getPrice() %></div>
                        </div>
                    </div>
                <% 
                        }
                    } else { 
                %>
                    <p class="no-items">No new products available at the moment.</p>
                <% 
                    } 
                %>
            </div>
        </section>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

</body>
</html>