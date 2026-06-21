<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionhub.model.Product" %>
<%@ page import="com.fashionhub.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <title><%= request.getAttribute("pageTitle") %> - FashionHub</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="main-container catalog-page-container">
        
        <!-- PAGE TITLE -->
        <div class="catalog-header">
            <h1 class="catalog-title"><%= request.getAttribute("pageTitle") %></h1>
            <p class="catalog-subtitle">Discover premium apparel, footwear, and accessories tailored for you.</p>
        </div>

        <div class="catalog-layout">
            
            <!-- SIDEBAR FILTERS -->
            <aside class="catalog-sidebar">
                <div class="filter-group">
                    <h3 class="filter-title">Search Products</h3>
                    <form action="<%= request.getContextPath() %>/products" method="get" class="search-form">
                        <div class="search-input-wrapper">
                            <input type="text" name="search" placeholder="Search brands, names..." value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
                            <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </div>
                    </form>
                </div>

                <div class="filter-group">
                    <h3 class="filter-title">Categories</h3>
                    <ul class="filter-list">
                        <li>
                            <a href="<%= request.getContextPath() %>/products" class="<%= request.getAttribute("currentCategory") == null ? "active" : "" %>">
                                All Collections
                            </a>
                        </li>
                        <% 
                            List<Category> categories = (List<Category>) request.getAttribute("categories");
                            String currentCategory = (String) request.getAttribute("currentCategory");
                            if (categories != null) {
                                for (Category cat : categories) {
                                    boolean isActive = cat.getCategoryName().equalsIgnoreCase(currentCategory);
                        %>
                            <li>
                                <a href="<%= request.getContextPath() %>/products?category=<%= cat.getCategoryName() %>" class="<%= isActive ? "active" : "" %>">
                                    <%= cat.getCategoryName() %>
                                </a>
                            </li>
                        <% 
                                }
                            } 
                        %>
                    </ul>
                </div>
            </aside>

            <!-- PRODUCTS GRID -->
            <main class="catalog-content">
                <div class="product-grid">
                    <% 
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        if (products != null && !products.isEmpty()) {
                            for (Product product : products) {
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
                        <div class="no-results">
                            <i class="fa-solid fa-circle-info"></i>
                            <h3>No products found</h3>
                            <p>Try refining your search keyword or selecting a different category.</p>
                            <a href="<%= request.getContextPath() %>/products" class="btn btn-primary" style="margin-top: 15px; display: inline-block;">Clear Filters</a>
                        </div>
                    <% 
                        } 
                    %>
                </div>
            </main>

        </div>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

</body>
</html>
