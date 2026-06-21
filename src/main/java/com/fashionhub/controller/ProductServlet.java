package com.fashionhub.controller;

import java.io.IOException;
import java.util.List;

import com.fashionhub.dao.CategoryDAO;
import com.fashionhub.dao.ProductDAO;
import com.fashionhub.dao.ProductSizeDAO;
import com.fashionhub.dao.impl.CategoryDAOImpl;
import com.fashionhub.dao.impl.ProductDAOImpl;
import com.fashionhub.dao.impl.ProductSizeDAOImpl;
import com.fashionhub.model.Category;
import com.fashionhub.model.Product;
import com.fashionhub.model.ProductSize;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/products", "/product-detail"})
public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private final ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/products".equals(path)) {
            handleProductsList(request, response);
        } else if ("/product-detail".equals(path)) {
            handleProductDetail(request, response);
        }
    }

    private void handleProductsList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String categoryParam = request.getParameter("category");
        String searchParam = request.getParameter("search");
        if (searchParam == null) {
            searchParam = request.getParameter("q");
        }

        List<Product> products;
        String pageTitle = "All Products";

        if (categoryParam != null && !categoryParam.trim().isEmpty()) {
            int categoryId = 0;
            String cat = categoryParam.trim();
            if ("Men".equalsIgnoreCase(cat)) categoryId = 1;
            else if ("Women".equalsIgnoreCase(cat)) categoryId = 2;
            else if ("Footwear".equalsIgnoreCase(cat)) categoryId = 3;
            else if ("Accessories".equalsIgnoreCase(cat)) categoryId = 4;
            
            if (categoryId > 0) {
                products = productDAO.getProductsByCategory(categoryId);
                pageTitle = cat + " Collection";
            } else {
                products = productDAO.getAllProducts();
            }
        } else if (searchParam != null && !searchParam.trim().isEmpty()) {
            products = productDAO.searchProducts(searchParam.trim());
            pageTitle = "Search Results for \"" + searchParam + "\"";
        } else {
            products = productDAO.getAllProducts();
        }

        List<Category> categories = categoryDAO.getAllCategories();

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("pageTitle", pageTitle);
        request.setAttribute("currentCategory", categoryParam);

        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    private void handleProductDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam.trim());
            Product product = productDAO.getProductById(productId);

            if (product != null) {
                List<ProductSize> sizes = productSizeDAO.getSizesByProductId(productId);
                Category category = categoryDAO.getCategoryById(product.getCategoryId());

                request.setAttribute("product", product);
                request.setAttribute("sizes", sizes);
                request.setAttribute("category", category);

                request.getRequestDispatcher("/WEB-INF/views/product-detail.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/products");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/products");
        }
    }
}
