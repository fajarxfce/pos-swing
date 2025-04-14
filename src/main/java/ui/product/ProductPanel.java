package ui.product;

import model.Product;
import ui.main.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductPanel extends JPanel {
    private MainFrame parentFrame;
    private ProductController controller;

    // UI Components
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

    public ProductPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.controller = new ProductController(this);

        setLayout(new BorderLayout());

        createTopPanel();
        createTable();
        createButtonPanel();

        refreshProductList();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel titleLabel = new JLabel("Product Management");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> controller.searchProducts(searchField.getText()));

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createTable() {
        String[] columns = {"ID", "Code", "Name", "Price", "Stock", "Category"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        productTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        productTable.getColumnModel().getColumn(5).setPreferredWidth(150);

        // Enable row selection
        ListSelectionModel selectionModel = productTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean hasSelection = productTable.getSelectedRow() != -1;
            editButton.setEnabled(hasSelection);
            deleteButton.setEnabled(hasSelection);
        });

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addButton = new JButton("Add Product");
        editButton = new JButton("Edit Product");
        deleteButton = new JButton("Delete Product");

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        addButton.addActionListener(e -> showAddProductDialog());
        editButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                int productId = (Integer) tableModel.getValueAt(selectedRow, 0);
                showEditProductDialog(productId);
            }
        });
        deleteButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                int productId = (Integer) tableModel.getValueAt(selectedRow, 0);
                confirmDeleteProduct(productId);
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void refreshProductList() {
        List<Product> products = controller.getAllProducts();
        updateTableData(products);
    }

    public void updateTableData(List<Product> products) {
        tableModel.setRowCount(0);

        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getCode(),
                    product.getName(),
                    currencyFormat.format(product.getPrice()),
                    product.getStock(),
                    product.getCategoryName()
            });
        }
    }

    private void showAddProductDialog() {
        ProductDialog dialog = new ProductDialog(parentFrame, "Add New Product", null);
        dialog.setVisible(true);

        if (dialog.isProductSaved()) {
            refreshProductList();
        }
    }

    private void showEditProductDialog(int productId) {
        Product product = controller.getProductById(productId);
        if (product != null) {
            ProductDialog dialog = new ProductDialog(parentFrame, "Edit Product", product);
            dialog.setVisible(true);

            if (dialog.isProductSaved()) {
                refreshProductList();
            }
        }
    }

    private void confirmDeleteProduct(int productId) {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this product?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            boolean success = controller.deleteProduct(productId);
            if (success) {
                refreshProductList();
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete product!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}