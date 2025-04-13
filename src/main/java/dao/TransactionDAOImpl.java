package dao;

import model.Category;
import model.Product;
import model.Transaction;
import model.TransactionItem;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDAOImpl implements TransactionDAO {
    private static final Logger LOGGER = Logger.getLogger(TransactionDAOImpl.class.getName());

    @Override
    public boolean saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (code, transaction_date, total_amount, customer_name) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, transaction.getCode());
            stmt.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
            stmt.setDouble(3, transaction.getTotalAmount());
            stmt.setString(4, transaction.getCustomerName());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving transaction", e);
            return false;
        }
    }

    @Override
    public boolean saveTransactionItem(TransactionItem item, int transactionId) {
        String sql = "INSERT INTO transaction_items (transaction_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, transactionId);
            stmt.setInt(2, item.getProduct().getId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getPrice());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        item.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving transaction item", e);
            return false;
        }
    }

    @Override
    public boolean updateProductStock(int productId, int newStock) {
        String sql = "UPDATE products SET stock = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newStock);
            stmt.setInt(2, productId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating product stock", e);
            return false;
        }
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setCode(rs.getString("code"));
                    transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                    transaction.setTotalAmount(rs.getDouble("total_amount"));
                    transaction.setCustomerName(rs.getString("customer_name"));

                    // Load items
                    List<TransactionItem> items = getTransactionItemsByTransactionId(transactionId);
                    transaction.setItems(items);

                    return transaction;
                }
            }
            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting transaction by ID", e);
            return null;
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setCode(rs.getString("code"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setTotalAmount(rs.getDouble("total_amount"));
                transaction.setCustomerName(rs.getString("customer_name"));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all transactions", e);
        }
        return transactions;
    }

    @Override
    public List<TransactionItem> getTransactionItemsByTransactionId(int transactionId) {
        String sql = "SELECT ti.*, p.id as product_id, p.name as product_name, p.code as product_code, " +
                "p.price as product_price, p.stock as product_stock, c.id as category_id, c.name as category_name " +
                "FROM transaction_items ti " +
                "JOIN products p ON ti.product_id = p.id " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "WHERE ti.transaction_id = ?";

        List<TransactionItem> items = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Create category
                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));

                    // Create product
                    Product product = new Product();
                    product.setId(rs.getInt("product_id"));
                    product.setName(rs.getString("product_name"));
                    product.setCode(rs.getString("product_code"));
                    product.setPrice(rs.getDouble("product_price"));
                    product.setStock(rs.getInt("product_stock"));
                    product.setCategory(category);  // Using setCategory instead

                    // Create transaction item
                    TransactionItem item = new TransactionItem(product, rs.getInt("quantity"));
                    item.setId(rs.getInt("id"));
                    item.setPrice(rs.getDouble("price"));

                    items.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting transaction items", e);
        }

        return items;
    }
}