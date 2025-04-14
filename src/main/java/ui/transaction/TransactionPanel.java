package ui.transaction;

import model.Product;
import model.Transaction;
import model.TransactionItem;
import ui.main.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionPanel extends JPanel {
    private MainFrame parentFrame;
    private TransactionController controller;

    // Komponen UI
    private JPanel productSelectionPanel;
    private JPanel cartPanel;

    private JTextField txtCustomerName;
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTextField txtSearchProduct;
    private JButton btnSearch;

    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JTextField txtTotal;
    private JButton btnAddToCart;
    private JButton btnRemoveFromCart;
    private JButton btnCheckout;

    private JSpinner quantitySpinner;

    private Transaction currentTransaction;

    public TransactionPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.controller = new TransactionController();
        this.currentTransaction = new Transaction();
        this.currentTransaction.setTransactionDate(LocalDateTime.now());

        setLayout(new BorderLayout(10, 10));

        createHeaderSection();
        createProductSelectionPanel();
        createCartPanel();

        JPanel mainContent = new JPanel(new GridLayout(1, 2, 10, 0));
        mainContent.add(productSelectionPanel);
        mainContent.add(cartPanel);

        add(mainContent, BorderLayout.CENTER);

        // Load products initially
        loadProducts();
    }

    private void createHeaderSection() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel titleLabel = new JLabel("New Transaction");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));

        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerPanel.add(new JLabel("Customer:"));
        txtCustomerName = new JTextField(20);
        customerPanel.add(txtCustomerName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JLabel dateLabel = new JLabel("Date: " + formatter.format(currentTransaction.getTransactionDate()));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(customerPanel, BorderLayout.CENTER);
        headerPanel.add(dateLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void createProductSelectionPanel() {
        productSelectionPanel = new JPanel(new BorderLayout(0, 10));
        productSelectionPanel.setBorder(BorderFactory.createTitledBorder("Products"));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearchProduct = new JTextField(20);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> searchProducts());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtSearchProduct);
        searchPanel.add(btnSearch);

        // Product table
        String[] productColumns = {"ID", "Code", "Name", "Price", "Stock"};
        productTableModel = new DefaultTableModel(productColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(productTableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Quantity panel
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityPanel.add(new JLabel("Quantity:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantityPanel.add(quantitySpinner);
        btnAddToCart = new JButton("Add to Cart");
        btnAddToCart.addActionListener(e -> {
            try {
                addToCart();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        quantityPanel.add(btnAddToCart);

        productSelectionPanel.add(searchPanel, BorderLayout.NORTH);
        productSelectionPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        productSelectionPanel.add(quantityPanel, BorderLayout.SOUTH);
    }

    private void createCartPanel() {
        cartPanel = new JPanel(new BorderLayout(0, 10));
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));

        // Cart table
        String[] cartColumns = {"Product", "Price", "Quantity", "Subtotal"};
        cartTableModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Total and checkout panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("Total:"));
        txtTotal = new JTextField("0.00");
        txtTotal.setEditable(false);
        txtTotal.setPreferredSize(new Dimension(150, 25));
        totalPanel.add(txtTotal);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRemoveFromCart = new JButton("Remove Selected");
        btnRemoveFromCart.addActionListener(e -> removeFromCart());
        btnCheckout = new JButton("Checkout");
        btnCheckout.addActionListener(e -> checkout());
        buttonPanel.add(btnRemoveFromCart);
        buttonPanel.add(btnCheckout);

        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        cartPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        cartPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() {
        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                return controller.getAllProducts();
            }

            @Override
            protected void done() {
                try {
                    List<Product> products = get();
                    updateProductTable(products);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(TransactionPanel.this,
                            "Error loading products: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void searchProducts() {
        String keyword = txtSearchProduct.getText().trim();

        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                return controller.searchProducts(keyword);
            }

            @Override
            protected void done() {
                try {
                    List<Product> products = get();
                    updateProductTable(products);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(TransactionPanel.this,
                            "Error searching products: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void updateProductTable(List<Product> products) {
        productTableModel.setRowCount(0);
        for (Product product : products) {
            productTableModel.addRow(new Object[]{
                    product.getId(),
                    product.getCode(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
            });
        }
    }

    private void addToCart() throws Exception {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product first!");
            return;
        }

        int productId = (int) productTableModel.getValueAt(selectedRow, 0);
        int quantity = (int) quantitySpinner.getValue();
        int stock = (int) productTableModel.getValueAt(selectedRow, 4);

        if (quantity > stock) {
            JOptionPane.showMessageDialog(this,
                    "Not enough stock! Available: " + stock,
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Product product = controller.getProductById(productId);
        TransactionItem item = new TransactionItem(product, quantity);
        currentTransaction.addItem(item);

        updateCartTable();
        updateTotal();
    }

    private void removeFromCart() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove!");
            return;
        }

        TransactionItem item = currentTransaction.getItems().get(selectedRow);
        currentTransaction.removeItem(item);

        updateCartTable();
        updateTotal();
    }

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (TransactionItem item : currentTransaction.getItems()) {
            cartTableModel.addRow(new Object[]{
                    item.getProduct().getName(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getSubtotal()
            });
        }
    }

    private void updateTotal() {
        txtTotal.setText(String.format("%.2f", currentTransaction.getTotalAmount()));
    }

    private void checkout() {
        if (currentTransaction.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }

        String customerName = txtCustomerName.getText().trim();
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name!");
            return;
        }

        currentTransaction.setCustomerName(customerName);

        // Generate transaction code (e.g., INV-20230501-001)
        String code = "INV-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        currentTransaction.setCode(code);

        int option = JOptionPane.showConfirmDialog(this,
                "Confirm checkout?\nTotal amount: " + String.format("%.2f", currentTransaction.getTotalAmount()),
                "Checkout Confirmation", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            saveTransaction();
        }
    }

    private void saveTransaction() {
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return controller.saveTransaction(currentTransaction);
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(TransactionPanel.this,
                                "Transaction completed successfully!\nTransaction Code: " + currentTransaction.getCode());
                        resetTransaction();
                        loadProducts(); // Reload products to update stock
                    } else {
                        JOptionPane.showMessageDialog(TransactionPanel.this,
                                "Failed to save transaction!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(TransactionPanel.this,
                            "Error during checkout: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void resetTransaction() {
        currentTransaction = new Transaction();
        currentTransaction.setTransactionDate(LocalDateTime.now());

        txtCustomerName.setText("");
        cartTableModel.setRowCount(0);
        txtTotal.setText("0.00");
    }
}