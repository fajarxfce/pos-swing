package ui.category;

import model.Category;

import javax.swing.*;
import java.awt.*;

public class CategoryDialog extends JDialog {
    private JTextField txtCode;
    private JTextField txtName;
    private JButton btnSave;
    private JButton btnCancel;

    private CategoryController controller;
    private Category category;
    private boolean categorySaved = false;

    public CategoryDialog(JFrame parent, String title, Category category) {
        super(parent, title, true);
        this.controller = new CategoryController();
        this.category = category;

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        layoutComponents();

        if (category != null) {
            populateFields();
        }
    }

    private void initComponents() {
        txtName = new JTextField(20);

        btnSave = new JButton("Simpan");
        btnCancel = new JButton("Batal");

        btnSave.addActionListener(e -> saveCategory());
        btnCancel.addActionListener(e -> dispose());
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nama Kategori:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtName, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void populateFields() {
        txtName.setText(category.getName());
    }

    private void saveCategory() {
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Mohon isi semua kolom yang diperlukan!",
                    "Error Validasi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (category == null) {
            category = new Category();
        }

        category.setName(txtName.getText().trim());

        boolean success = controller.saveCategory(category);

        if (success) {
            categorySaved = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan kategori!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isCategorySaved() {
        return categorySaved;
    }
}