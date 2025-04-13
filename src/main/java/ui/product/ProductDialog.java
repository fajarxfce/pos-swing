package ui.product;


import model.Category;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductDialog extends JDialog {
    private JTextField txtCode;
    private JTextField txtName;
    private JTextArea txtDescription;
    private JTextField txtPrice;
    private JTextField txtStock;
    private JComboBox<Category> cmbCategory;
    private JButton btnSave;
    private JButton btnCancel;

    private ProductController controller;
    private Product product;
    private boolean productSaved = false;

    public ProductDialog(JFrame parent, String title, Product product) {
        super(parent, title, true);
        this.controller = new ProductController();
        this.product = product;

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        layoutComponents();

        if (product != null) {
            populateFields();
        }
    }

    private void initComponents() {
        txtCode = new JTextField(20);
        txtName = new JTextField(20);
        txtDescription = new JTextArea(4, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtPrice = new JTextField(20);
        txtStock = new JTextField(20);

        // Get categories for dropdown
        List<Category> categories = controller.getAllCategories();
        cmbCategory = new JComboBox<>(categories.toArray(new Category[0]));

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> saveProduct());
        btnCancel.addActionListener(e -> dispose());
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Code
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product Code:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtCode, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Product Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtName, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(new JScrollPane(txtDescription), gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtPrice, gbc);

        // Stock
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Stock:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtStock, gbc);

        // Category
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(cmbCategory, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void populateFields() {
        txtCode.setText(product.getCode());
        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtStock.setText(String.valueOf(product.getStock()));

        // Find and select the correct category
        Category productCategory = product.getCategory();
        for (int i = 0; i < cmbCategory.getItemCount(); i++) {
            Category category = cmbCategory.getItemAt(i);
            if (category.getId() == productCategory.getId()) {
                cmbCategory.setSelectedIndex(i);
                break;
            }
        }
    }

    private void saveProduct() {
        // Validate input
        if (txtCode.getText().trim().isEmpty() ||
                txtName.getText().trim().isEmpty() ||
                txtPrice.getText().trim().isEmpty() ||
                txtStock.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            if (price < 0) {
                JOptionPane.showMessageDialog(this,
                        "Price cannot be negative!",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stock < 0) {
                JOptionPane.showMessageDialog(this,
                        "Stock cannot be negative!",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get selected category
            Category selectedCategory = (Category) cmbCategory.getSelectedItem();

            if (product == null) {
                // Create new product
                product = new Product();
            }

            // Update product object
            product.setCode(txtCode.getText().trim());
            product.setName(txtName.getText().trim());
            product.setDescription(txtDescription.getText().trim());
            product.setPrice(price);
            product.setStock(stock);
            product.setCategory(selectedCategory);

            boolean success = controller.saveProduct(product);

            if (success) {
                productSaved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to save product!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Price and stock must be valid numbers!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isProductSaved() {
        return productSaved;
    }
}