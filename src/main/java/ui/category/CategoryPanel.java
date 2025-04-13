package ui.category;

import model.Category;
import ui.main.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoryPanel extends JPanel implements ActionListener {
    private MainFrame parentFrame;
    private CategoryController controller;

    // Komponen UI
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;

    public CategoryPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.controller = new CategoryController(this);

        setLayout(new BorderLayout());

        createTopPanel();
        createTable();
        createButtonPanel();

        // Muat data kategori awal
        refreshCategoryList();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Judul
        JLabel titleLabel = new JLabel("Manajemen Kategori");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Panel pencarian
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Cari");
        refreshButton = new JButton("Segarkan");

        searchButton.addActionListener(this);
        refreshButton.addActionListener(this);

        searchPanel.add(new JLabel("Pencarian:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createTable() {
        String[] columns = {"ID", "Kode", "Nama"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        categoryTable = new JTable(tableModel);
        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryTable.getTableHeader().setReorderingAllowed(false);

        // Atur lebar kolom
        categoryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        categoryTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        categoryTable.getColumnModel().getColumn(2).setPreferredWidth(300);

        // Aktifkan pemilihan baris
        ListSelectionModel selectionModel = categoryTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean hasSelection = categoryTable.getSelectedRow() != -1;
            editButton.setEnabled(hasSelection);
            deleteButton.setEnabled(hasSelection);
        });

        JScrollPane scrollPane = new JScrollPane(categoryTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addButton = new JButton("Tambah Kategori");
        editButton = new JButton("Edit Kategori");
        deleteButton = new JButton("Hapus Kategori");

        // Nonaktifkan tombol edit dan hapus di awal
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Tambahkan action listeners
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void refreshCategoryList() {
        java.util.List<Category> categories = controller.getAllCategories();
        updateTableData(categories);
    }

    public void updateTableData(java.util.List<Category> categories) {
        tableModel.setRowCount(0);

        for (Category category : categories) {
            tableModel.addRow(new Object[]{
                    category.getId(),
                    category.getCode(),
                    category.getName()
            });
        }
    }

    private void showAddCategoryDialog() {
        CategoryDialog dialog = new CategoryDialog(parentFrame, "Tambah Kategori Baru", null);
        dialog.setVisible(true);

        if (dialog.isCategorySaved()) {
            refreshCategoryList();
        }
    }

    private void showEditCategoryDialog(int categoryId) {
        Category category = controller.getCategoryById(categoryId);
        if (category != null) {
            CategoryDialog dialog = new CategoryDialog(parentFrame, "Edit Kategori", category);
            dialog.setVisible(true);

            if (dialog.isCategorySaved()) {
                refreshCategoryList();
            }
        }
    }

    private void confirmDeleteCategory(int categoryId) {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus kategori ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            boolean success = controller.deleteCategory(categoryId);
            if (success) {
                refreshCategoryList();
                JOptionPane.showMessageDialog(this, "Kategori berhasil dihapus!");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus kategori!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            List<Category> results = controller.searchCategories(searchField.getText());
            updateTableData(results);
        } else if (e.getSource() == refreshButton) {
            refreshCategoryList();
        } else if (e.getSource() == addButton) {
            showAddCategoryDialog();
        } else if (e.getSource() == editButton) {
            int selectedRow = categoryTable.getSelectedRow();
            if (selectedRow != -1) {
                int categoryId = (Integer) tableModel.getValueAt(selectedRow, 0);
                showEditCategoryDialog(categoryId);
            }
        } else if (e.getSource() == deleteButton) {
            int selectedRow = categoryTable.getSelectedRow();
            if (selectedRow != -1) {
                int categoryId = (Integer) tableModel.getValueAt(selectedRow, 0);
                confirmDeleteCategory(categoryId);
            }
        }
    }
}