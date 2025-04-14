package service;

import dao.TransactionDAO;
import model.Product;
import model.Transaction;
import model.TransactionItem;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());

    private TransactionDAO transactionDAO;
    private ProductService productService;

    public TransactionService(TransactionDAO transactionDAO, ProductService productService) {
        this.transactionDAO = transactionDAO;
        this.productService = productService;
    }

    public boolean saveTransaction(Transaction transaction) {
        try {
            // 1. Save transaction header
            boolean success = transactionDAO.saveTransaction(transaction);

            if (!success) {
                return false;
            }

            // 2. Save transaction items
            for (TransactionItem item : transaction.getItems()) {
                success = transactionDAO.saveTransactionItem(item, transaction.getId());

                if (!success) {
                    // Handle partial failure (could implement rollback)
                    LOGGER.log(Level.SEVERE, "Failed to save transaction item");
                    return false;
                }

                // 3. Update product stock
                Product product = item.getProduct();
                product.setStock(product.getStock() - item.getQuantity());
                success = productService.updateProduct(product);

                if (!success) {
                    LOGGER.log(Level.SEVERE, "Failed to update product stock");
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving transaction", e);
            return false;
        }
    }

    public List<Transaction> getAllTransactions() throws Exception {
        return transactionDAO.getAllTransactions();
    }

    public Transaction getTransactionById(int id) throws Exception {
        return transactionDAO.getTransactionById(id);
    }

    public List<Transaction> searchTransactions(String keyword) throws Exception {
        return transactionDAO.searchTransactions(keyword);
    }

    public boolean printReceipt(int transactionId) throws Exception {
        Transaction transaction = getTransactionById(transactionId);
        if (transaction == null) {
            return false;
        }
        return true;
    }
}