<%
    com.fashionhub.model.User loggedInUser = (com.fashionhub.model.User) session.getAttribute("loggedInUser");
%>
<nav class="navbar">

    <div class="nav-container">

        <div class="logo">
            <a href="<%= request.getContextPath() %>/home">
                FashionHub
            </a>
        </div>

        <ul class="nav-links">

            <li>
                <a href="<%= request.getContextPath() %>/home">Home</a>
            </li>

            <li>
                <a href="<%= request.getContextPath() %>/products?category=Men">Men</a>
            </li>

            <li>
                <a href="<%= request.getContextPath() %>/products?category=Women">Women</a>
            </li>

            <li>
                <a href="<%= request.getContextPath() %>/products?category=Footwear">Footwear</a>
            </li>

            <li>
                <a href="<%= request.getContextPath() %>/products?category=Accessories">Accessories</a>
            </li>

            <% if (loggedInUser != null) { %>
            <li>
                <a href="<%= request.getContextPath() %>/orders">My Orders</a>
            </li>
            <% } %>

        </ul>

        <div class="nav-actions">

            <% if (loggedInUser != null) { %>
                <span class="user-welcome">Hi, <%= loggedInUser.getFullName() %></span>
                <a href="<%= request.getContextPath() %>/orders" title="My Orders">
                    <i class="fa-solid fa-box"></i>
                </a>
                <a href="<%= request.getContextPath() %>/cart" title="Cart">
                    <i class="fa-solid fa-cart-shopping"></i>
                </a>
                <a href="<%= request.getContextPath() %>/logout" title="Logout">
                    <i class="fa-solid fa-right-from-bracket"></i>
                </a>
            <% } else { %>
                <a href="<%= request.getContextPath() %>/login" title="Login">
                    <i class="fa-solid fa-user"></i>
                </a>
                <a href="<%= request.getContextPath() %>/register" title="Register">
                    <i class="fa-solid fa-user-plus"></i>
                </a>
            <% } %>

        </div>

    </div>

</nav>