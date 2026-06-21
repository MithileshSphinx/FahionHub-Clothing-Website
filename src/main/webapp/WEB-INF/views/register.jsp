<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <title>Create Account - FashionHub</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="main-container auth-page-container">
        
        <div class="auth-card register-card">
            
            <div class="auth-header">
                <h2>Create Account</h2>
                <p>Join FashionHub for premium apparel collections, trackable shipping, and exclusive offers.</p>
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

            <form action="<%= request.getContextPath() %>/register" method="post" class="auth-form register-form-grid">
                
                <!-- PERSONAL INFO -->
                <div class="form-section-title">Personal Details</div>

                <div class="form-group">
                    <label for="fullName" class="form-label">Full Name *</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-user-tie"></i>
                        <input type="text" name="fullName" id="fullName" class="form-control" placeholder="John Doe" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email" class="form-label">Email Address *</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-envelope"></i>
                        <input type="email" name="email" id="email" class="form-control" placeholder="john@example.com" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone" class="form-label">Phone Number *</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-phone"></i>
                        <input type="tel" name="phone" id="phone" class="form-control" placeholder="9876543210" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="form-label">Password *</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-lock"></i>
                        <input type="password" name="password" id="password" class="form-control" placeholder="Create strong password" required>
                    </div>
                </div>

                <!-- ADDRESS INFO -->
                <div class="form-section-title">Delivery Address</div>

                <div class="form-group full-width-field">
                    <label for="address" class="form-label">Address Line</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-house"></i>
                        <input type="text" name="address" id="address" class="form-control" placeholder="Street name, building number">
                    </div>
                </div>

                <div class="form-group">
                    <label for="city" class="form-label">City</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-city"></i>
                        <input type="text" name="city" id="city" class="form-control" placeholder="City name">
                    </div>
                </div>

                <div class="form-group">
                    <label for="state" class="form-label">State</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-map"></i>
                        <input type="text" name="state" id="state" class="form-control" placeholder="State name">
                    </div>
                </div>

                <div class="form-group">
                    <label for="pincode" class="form-label">Pincode</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-map-pin"></i>
                        <input type="text" name="pincode" id="pincode" class="form-control" placeholder="6-digit pincode">
                    </div>
                </div>

                <div class="full-width-field button-wrapper">
                    <button type="submit" class="btn btn-primary btn-block btn-auth-submit">
                        Register Account
                    </button>
                </div>

            </form>

            <div class="auth-footer">
                <p>Already have an account? <a href="<%= request.getContextPath() %>/login">Sign In Instead</a></p>
            </div>

        </div>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

</body>
</html>
