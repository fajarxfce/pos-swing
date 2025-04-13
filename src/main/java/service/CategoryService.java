package service;


import dao.CategoryDAO;
import model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryService {
    private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());
    private CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<Category> getAllCategories() {
        try {
            return categoryDAO.getAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error mendapatkan semua kategori", e);
            return new ArrayList<>();
        }
    }

    public Category getCategoryById(int id) {
        try {
            return categoryDAO.getById(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error mendapatkan kategori dengan ID: " + id, e);
            return null;
        }
    }

    public boolean addCategory(Category category) {
        try {
            return categoryDAO.add(category);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error menambah kategori: " + category.getName(), e);
            return false;
        }
    }

    public boolean updateCategory(Category category) {
        try {
            return categoryDAO.update(category);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error memperbarui kategori: " + category.getName(), e);
            return false;
        }
    }

    public boolean deleteCategory(int id) {
        try {
            return categoryDAO.delete(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error menghapus kategori dengan ID: " + id, e);
            return false;
        }
    }

    public List<Category> searchCategories(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllCategories();
            }

            List<Category> allCategories = categoryDAO.getAll();
            List<Category> result = new ArrayList<>();

            String lowercaseKeyword = keyword.toLowerCase();
            for (Category category : allCategories) {
                if (category.getName().toLowerCase().contains(lowercaseKeyword) ||
                        (category.getCode() != null && category.getCode().toLowerCase().contains(lowercaseKeyword))) {
                    result.add(category);
                }
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error mencari kategori dengan kata kunci: " + keyword, e);
            return new ArrayList<>();
        }
    }
}