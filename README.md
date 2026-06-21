# FashionHub - Online Fashion Store

## Project Overview

FashionHub is a full-stack Java-based E-Commerce Web Application developed using the MVC architecture pattern. The application allows users to browse fashion products, filter products by category, add products to cart, place orders, and manage their profiles.

The project is built using Java Servlets, JSP, JDBC, MySQL, Maven, and Apache Tomcat.

---

## Features

### User Module

* User Registration
* User Login
* User Logout
* Session Management
* Profile Management
* Change Password

### Product Module

* View Products
* Product Categories
* Product Details
* Search Products
* Filter Products by Category

### Cart Module

* Add Product to Cart
* Update Quantity
* Remove Product from Cart
* View Cart Summary

### Order Module

* Checkout Process
* Place Order
* Order History
* View Order Details
* Order Status Tracking

### Category Module

* Display Product Categories
* Category-Based Product Listing

---

## Technology Stack

### Frontend

* HTML5
* CSS3
* JSP
* JavaScript

### Backend

* Java 21
* Jakarta Servlet API
* JSP

### Database

* MySQL

### Build Tool

* Maven

### Server

* Apache Tomcat 10.1

### IDE

* Eclipse IDE

---

## Architecture

The project follows the MVC (Model View Controller) Architecture.

### Model

Contains Java Beans representing business entities.

Examples:

* User
* Category
* Product
* ProductSize
* CartItem
* Order
* OrderItem

### View

Contains JSP pages used to display information.

Examples:

* home.jsp
* products.jsp
* login.jsp
* register.jsp
* cart.jsp
* checkout.jsp

### Controller

Contains Servlets responsible for handling requests.

Examples:

* HomeServlet
* LoginServlet
* RegisterServlet
* ProductServlet
* CartServlet
* CheckoutServlet

### DAO Layer

Responsible for all database interactions.

Examples:

* UserDAO
* ProductDAO
* CategoryDAO
* CartDAO
* OrderDAO

---

## Project Structure

FashionHub

src/main/java

com.fashionhub

controller

dao

dao.impl

model

util

src/main/webapp

assets

css

js

images

WEB-INF

views

partials

home.jsp

products.jsp

cart.jsp

login.jsp

register.jsp

header.jsp

navbar.jsp

footer.jsp

web.xml

---

## Database Design

### Tables

#### users

Stores user information.

#### categories

Stores product categories.

#### products

Stores product details.

#### product_sizes

Stores available sizes and stock.

#### cart

Stores user cart.

#### cart_items

Stores products added to cart.

#### orders

Stores order details.

#### order_items

Stores products included in orders.

---

## Database Schema

### users

| Column     | Type         |
| ---------- | ------------ |
| user_id    | INT          |
| full_name  | VARCHAR(100) |
| email      | VARCHAR(100) |
| phone      | VARCHAR(15)  |
| password   | VARCHAR(255) |
| address    | VARCHAR(255) |
| city       | VARCHAR(100) |
| state      | VARCHAR(100) |
| pincode    | VARCHAR(10)  |
| created_at | TIMESTAMP    |

### categories

| Column        | Type        |
| ------------- | ----------- |
| category_id   | INT         |
| category_name | VARCHAR(50) |

### products

| Column       | Type          |
| ------------ | ------------- |
| product_id   | INT           |
| product_name | VARCHAR(200)  |
| brand        | VARCHAR(100)  |
| category_id  | INT           |
| description  | TEXT          |
| price        | DECIMAL(10,2) |
| image_url    | VARCHAR(255)  |
| created_at   | TIMESTAMP     |

### product_sizes

| Column         | Type       |
| -------------- | ---------- |
| size_id        | INT        |
| product_id     | INT        |
| size           | VARCHAR(5) |
| stock_quantity | INT        |

### cart

| Column  | Type |
| ------- | ---- |
| cart_id | INT  |
| user_id | INT  |

### cart_items

| Column       | Type |
| ------------ | ---- |
| cart_item_id | INT  |
| cart_id      | INT  |
| product_id   | INT  |
| size_id      | INT  |
| quantity     | INT  |

### orders

| Column                 | Type          |
| ---------------------- | ------------- |
| order_id               | INT           |
| user_id                | INT           |
| order_date             | TIMESTAMP     |
| total_amount           | DECIMAL(10,2) |
| payment_method         | VARCHAR(50)   |
| order_status           | VARCHAR(50)   |
| delivery_name          | VARCHAR(100)  |
| delivery_phone         | VARCHAR(15)   |
| delivery_address_line1 | VARCHAR(150)  |
| delivery_address_line2 | VARCHAR(150)  |
| delivery_city          | VARCHAR(100)  |
| delivery_state         | VARCHAR(100)  |
| delivery_pincode       | VARCHAR(10)   |

### order_items

| Column        | Type          |
| ------------- | ------------- |
| order_item_id | INT           |
| order_id      | INT           |
| product_id    | INT           |
| size_id       | INT           |
| quantity      | INT           |
| price         | DECIMAL(10,2) |

---

## Setup Instructions

### Clone Project

```bash
git clone <repository-url>
```

### Import Project

1. Open Eclipse
2. Import Existing Maven Project
3. Select FashionHub Project

### Configure Database

Create Database:

```sql
CREATE DATABASE fashion_store;
```

Run all table creation scripts.

### Update Database Credentials

Update:

```java
DBConnection.java
```

```java
private static final String URL =
"jdbc:mysql://localhost:3306/fashion_store";

private static final String USERNAME =
"root";

private static final String PASSWORD =
"your_password";
```

### Build Project

```bash
mvn clean install
```

### Deploy Project

1. Configure Apache Tomcat 10.1
2. Add FashionHub Project to Server
3. Start Server

### Run Application

```text
http://localhost:8080/FashionHub/home
```

---

## Future Enhancements

* Admin Dashboard
* Product Management
* Category Management
* Inventory Management
* Payment Gateway Integration
* Wishlist Feature
* Product Reviews
* Order Tracking
* Email Notifications
* OTP Based Authentication
* Responsive Mobile UI
* REST API Integration

---

## Learning Outcomes

Through this project the following concepts were implemented:

* MVC Architecture
* JDBC Connectivity
* DAO Design Pattern
* JSP and Servlets
* Session Management
* Maven Project Management
* MySQL Database Design
* CRUD Operations
* E-Commerce Workflow
* Java Web Application Development

---


