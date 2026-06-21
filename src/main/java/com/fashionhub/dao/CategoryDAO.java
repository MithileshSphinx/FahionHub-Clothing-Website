package com.fashionhub.dao;

import java.util.List;
import com.fashionhub.model.Category;

public interface CategoryDAO {

    boolean addCategory(Category category);

    Category getCategoryById(int categoryId);

    List<Category> getAllCategories();

    boolean updateCategory(Category category);

    boolean deleteCategory(int categoryId);
}