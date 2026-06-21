package com.fashionhub.controller;

import java.io.IOException;
import java.util.List;

import com.fashionhub.dao.CategoryDAO;
import com.fashionhub.dao.ProductDAO;
import com.fashionhub.dao.impl.CategoryDAOImpl;
import com.fashionhub.dao.impl.ProductDAOImpl;
import com.fashionhub.model.Category;
import com.fashionhub.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Category> categories = categoryDAO.getAllCategories();
        List<Product> latestProducts = productDAO.getLatestProducts();

        request.setAttribute("categories", categories);
        request.setAttribute("latestProducts", latestProducts);

        request.getRequestDispatcher("/WEB-INF/views/home.jsp")
               .forward(request, response);
    }
}