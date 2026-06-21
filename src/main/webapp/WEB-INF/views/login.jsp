<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/header.jsp" />
    <title>Login - FashionHub</title>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="main-container auth-page-container">
        
        <div class="auth-card">
            
            <div class="auth-header">
                <h2>Welcome Back</h2>
                <p>Sign in to your FashionHub account to access your cart and saved orders.</p>
            </div>

            <!-- ALERTS -->
            <% 
                String error = (String) request.getAttribute("error");
                String success = (String) request.getAttribute("success");
                if (error != null) {
            %>
                <div class="alert alert-danger">
                    <i class="fa-solid fa-circle-exclamation"></i> <%= error %>
                </div>
            <% } %>

            <% if (success != null) { %>
                <div class="alert alert-success">
                    <i class="fa-solid fa-circle-check"></i> <%= success %>
                </div>
            <% } %>

            <form action="<%= request.getContextPath() %>/login" method="post" class="auth-form">
                
                <div class="form-group">
                    <label for="email" class="form-label">Email Address</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-envelope"></i>
                        <input type="email" name="email" id="email" class="form-control" placeholder="name@example.com" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="form-label">Password</label>
                    <div class="input-icon-wrapper">
                        <i class="fa-solid fa-lock"></i>
                        <input type="password" name="password" id="password" class="form-control" placeholder="Enter your password" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-block btn-auth-submit">
                    Sign In
                </button>

            </form>

            <div class="auth-footer">
                <p>Don't have an account? <a href="<%= request.getContextPath() %>/register">Create Account</a></p>
            </div>

        </div>

    </div>

    <!-- FOOTER -->
    <jsp:include page="partials/footer.jsp" />

</body>
</html>
