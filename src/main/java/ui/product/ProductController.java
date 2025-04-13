package ui.product;

import di.ServiceLocator;
import model.Category;
import model.Product;
import service.CategoryService;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private ProductPanel view;
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductPanel view) {
        this.view = view;
        this.productService = ServiceLocator.get(ProductService.class);
        this.categoryService = ServiceLocator.get(CategoryService.class);
    }

    // No-arg constructor for dialogs
    public ProductController() {
        this.productService = ServiceLocator.get(ProductService.class);
        this.categoryService = ServiceLocator.get(CategoryService.class);
    }

    public List<Product> getAllProducts() {
        try {
            return productService.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Product getProductById(int id) {
        try {
            return productService.getProductById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> searchProducts(String keyword) {
        try {
            List<Product> results = productService.searchProducts(keyword);
            if (view != null) {
                view.updateTableData(results);
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean saveProduct(Product product) {
        try {
            if (product.getId() == 0) {
                // New product
                return productService.addProduct(product);
            } else {
                // Existing product
                return productService.updateProduct(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        try {
            return productService.deleteProduct(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Category> getAllCategories() {
        try {
            return categoryService.getAllCategories();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}