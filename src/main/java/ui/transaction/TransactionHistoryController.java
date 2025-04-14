package ui.transaction;

import di.ServiceLocator;
import model.Transaction;
import service.TransactionService;

import java.util.List;

public class TransactionHistoryController {
    private TransactionService transactionService;

    public TransactionHistoryController() {
        this.transactionService = ServiceLocator.get(TransactionService.class);
    }

    public List<Transaction> getAllTransactions() throws Exception {
        return transactionService.getAllTransactions();
    }

    public Transaction getTransactionById(int id) throws Exception {
        return transactionService.getTransactionById(id);
    }

    public List<Transaction> searchTransactions(String keyword) throws Exception {
        return transactionService.searchTransactions(keyword);
    }

    public boolean printReceipt(int transactionId) throws Exception {
        return transactionService.printReceipt(transactionId);
    }
}