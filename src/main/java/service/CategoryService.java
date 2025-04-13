package service;


import dao.CategoryDAO;
import model.Category;

import java.util.List;

public class CategoryService {
    private CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<Category> getAllCategories() throws Exception {
        return categoryDAO.getAll();
    }

    public Category getCategoryById(int id) throws Exception {
        return categoryDAO.getById(id);
    }

    public boolean addCategory(Category category) throws Exception {
        return categoryDAO.add(category);
    }

    public boolean updateCategory(Category category) throws Exception {
        return categoryDAO.update(category);
    }

    public boolean deleteCategory(int id) throws Exception {
        return categoryDAO.delete(id);
    }
}