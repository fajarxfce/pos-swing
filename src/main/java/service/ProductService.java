package service;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts() throws Exception {
        return productDAO.getAll();
    }

    public Product getProductById(int id) throws Exception {
        return productDAO.getById(id);
    }

    public List<Product> searchProducts(String keyword) throws Exception {
        return productDAO.search(keyword);
    }

    public boolean addProduct(Product product) throws Exception {
        return productDAO.add(product);
    }

    public boolean updateProduct(Product product) throws Exception {
        return productDAO.update(product);
    }

    public boolean deleteProduct(int id) throws Exception {
        return productDAO.delete(id);
    }
}