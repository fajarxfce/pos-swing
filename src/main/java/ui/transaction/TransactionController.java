package ui.transaction;

import di.ServiceLocator;
import model.Product;
import model.Transaction;
import service.ProductService;
import service.TransactionService;

import java.util.List;

public class TransactionController {
    private ProductService productService;
    private TransactionService transactionService;

    public TransactionController() {
        this.productService = ServiceLocator.get(ProductService.class);
        this.transactionService = ServiceLocator.get(TransactionService.class);
    }

    public List<Product> getAllProducts() throws Exception {
        return productService.getAllProducts();
    }

    public List<Product> searchProducts(String keyword) throws Exception {
        return productService.searchProducts(keyword);
    }

    public Product getProductById(int id) throws Exception {
        return productService.getProductById(id);
    }

    public boolean saveTransaction(Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }
}