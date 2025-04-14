package dao;

import model.Transaction;
import model.TransactionItem;

import java.util.List;

public interface TransactionDAO {
    boolean saveTransaction(Transaction transaction);

    boolean saveTransactionItem(TransactionItem item, int transactionId);

    boolean updateProductStock(int productId, int newStock);

    Transaction getTransactionById(int transactionId);

    List<Transaction> getAllTransactions();

    List<TransactionItem> getTransactionItemsByTransactionId(int transactionId);

    List<Transaction> searchTransactions(String keyword);
}

