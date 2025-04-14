package ui.transaction;

import model.Transaction;
import ui.main.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TransactionHistoryPanel extends JPanel {
    private MainFrame parentFrame;
    private TransactionHistoryController controller;

    // UI Components
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton viewButton;
    private JButton printButton;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

    public TransactionHistoryPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.controller = new TransactionHistoryController();

        setLayout(new BorderLayout());

        createTopPanel();
        createTable();
        createButtonPanel();

        refreshTransactionList();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> searchTransactions());

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createTable() {
        String[] columns = {"ID", "Code", "Date", "Customer", "Total", "Items"};
        tableModel = new DefaultTableModel(columns, 0);

        transactionTable = new JTable(tableModel);
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionTable.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(5).setPreferredWidth(80);

        // Enable row selection
        ListSelectionModel selectionModel = transactionTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean hasSelection = transactionTable.getSelectedRow() != -1;
            viewButton.setEnabled(hasSelection);
            printButton.setEnabled(hasSelection);
        });

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        viewButton = new JButton("View Details");
        printButton = new JButton("Print Receipt");

        viewButton.setEnabled(false);
        printButton.setEnabled(false);

        viewButton.addActionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow != -1) {
                int transactionId = (Integer) tableModel.getValueAt(selectedRow, 0);
                showTransactionDetails(transactionId);
            }
        });

        printButton.addActionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow != -1) {
                int transactionId = (Integer) tableModel.getValueAt(selectedRow, 0);
                printReceipt(transactionId);
            }
        });

        buttonPanel.add(viewButton);
        buttonPanel.add(printButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void searchTransactions() {
        String keyword = searchField.getText().trim();

        SwingWorker<List<Transaction>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Transaction> doInBackground() throws Exception {
                return controller.searchTransactions(keyword);
            }

            @Override
            protected void done() {
                try {
                    List<Transaction> transactions = get();
                    updateTableData(transactions);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(TransactionHistoryPanel.this,
                            "Error searching transactions: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    public void refreshTransactionList() {
        SwingWorker<List<Transaction>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Transaction> doInBackground() throws Exception {
                return controller.getAllTransactions();
            }

            @Override
            protected void done() {
                try {
                    java.util.List<Transaction> transactions = get();
                    updateTableData(transactions);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(TransactionHistoryPanel.this,
                            "Error loading transactions: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    public void updateTableData(List<Transaction> transactions) {
        tableModel.setRowCount(0);

        for (Transaction transaction : transactions) {
            tableModel.addRow(new Object[]{
                    transaction.getId(),
                    transaction.getCode(),
                    dateFormat.format(transaction.getTransactionDate()),
                    transaction.getCustomerName(),
                    currencyFormat.format(transaction.getTotalAmount()),
                    transaction.getItems().size()
            });
        }
    }

    private void showTransactionDetails(int transactionId) {
        SwingWorker<Transaction, Void> worker = new SwingWorker<>() {
            @Override
            protected Transaction doInBackground() throws Exception {
                return controller.getTransactionById(transactionId);
            }

            @Override
            protected void done() {
                try {
                    Transaction transaction = get();
                    if (transaction != null) {
                        TransactionDetailDialog dialog = new TransactionDetailDialog(parentFrame, transaction);
                        dialog.setVisible(true);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(TransactionHistoryPanel.this,
                            "Error loading transaction details: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void printReceipt(int transactionId) {
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return controller.printReceipt(transactionId);
            }

            @Override
            protected void done() {
                try {
                    Boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(TransactionHistoryPanel.this,
                                "Receipt printed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(TransactionHistoryPanel.this,
                                "Failed to print receipt",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(TransactionHistoryPanel.this,
                            "Error printing receipt: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
}