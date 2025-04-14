package ui.transaction;

import model.Transaction;
import model.TransactionItem;
import ui.main.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

public class TransactionDetailDialog extends JDialog {
    private Transaction transaction;
    private JTable itemsTable;
    private DefaultTableModel tableModel;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public TransactionDetailDialog(MainFrame parent, Transaction transaction) {
        super(parent, "Transaction Details", true);
        this.transaction = transaction;

        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        createHeaderPanel();
        createItemsTable();
        createTotalsPanel();
        createButtonPanel();
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(4, 2, 10, 5));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Transaction Information")
        ));

        headerPanel.add(new JLabel("Transaction ID:"));
        headerPanel.add(new JLabel(String.valueOf(transaction.getId())));

        headerPanel.add(new JLabel("Transaction Code:"));
        headerPanel.add(new JLabel(transaction.getCode()));

        headerPanel.add(new JLabel("Date:"));
        headerPanel.add(new JLabel(dateFormat.format(transaction.getTransactionDate())));

        headerPanel.add(new JLabel("Customer:"));
        headerPanel.add(new JLabel(transaction.getCustomerName()));

        add(headerPanel, BorderLayout.NORTH);
    }

    private void createItemsTable() {
        String[] columns = {"Product", "Price", "Quantity", "Subtotal"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemsTable = new JTable(tableModel);

        for (TransactionItem item : transaction.getItems()) {
            tableModel.addRow(new Object[]{
                    item.getProduct().getName(),
                    currencyFormat.format(item.getPrice()),
                    item.getQuantity(),
                    currencyFormat.format(item.getSubtotal())
            });
        }

        JScrollPane scrollPane = new JScrollPane(itemsTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createTotalsPanel() {
        JPanel totalsPanel = new JPanel(new GridLayout(1, 2, 10, 5));
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel totalLabel = new JLabel("TOTAL:");
        totalLabel.setFont(new Font(totalLabel.getFont().getName(), Font.BOLD, 14));

        JLabel totalValueLabel = new JLabel(currencyFormat.format(transaction.getTotalAmount()), SwingConstants.RIGHT);
        totalValueLabel.setFont(new Font(totalValueLabel.getFont().getName(), Font.BOLD, 14));

        totalsPanel.add(totalLabel);
        totalsPanel.add(totalValueLabel);

        add(totalsPanel, BorderLayout.SOUTH);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JButton printButton = new JButton("Print Receipt");
        printButton.addActionListener(e -> {
            TransactionHistoryController controller = new TransactionHistoryController();
            try {
                boolean success = controller.printReceipt(transaction.getId());
                if (success) {
                    JOptionPane.showMessageDialog(this, "Receipt printed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to print receipt",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error printing receipt: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}