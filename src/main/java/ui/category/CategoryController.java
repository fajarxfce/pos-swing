package ui.category;

import di.ServiceLocator;
import model.Category;
import service.CategoryService;

import java.util.List;

public class CategoryController {
    private CategoryPanel view;
    private CategoryService categoryService;

    public CategoryController(CategoryPanel view) {
        this.view = view;
        this.categoryService = ServiceLocator.get(CategoryService.class);
    }

    // Constructor tanpa argumen untuk dialog
    public CategoryController() {
        this.categoryService = ServiceLocator.get(CategoryService.class);
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public Category getCategoryById(int id) {
        return categoryService.getCategoryById(id);
    }

    public List<Category> searchCategories(String keyword) {
        return categoryService.searchCategories(keyword);
    }

    public boolean saveCategory(Category category) {
        if (category.getId() == 0) {
            // Kategori baru
            return categoryService.addCategory(category);
        } else {
            // Kategori yang sudah ada
            return categoryService.updateCategory(category);
        }
    }

    public boolean deleteCategory(int id) {
        return categoryService.deleteCategory(id);
    }
}
