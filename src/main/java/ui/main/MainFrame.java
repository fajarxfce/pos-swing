package ui.main;

import di.ServiceLocator;
import model.User;
import ui.category.CategoryPanel;
import ui.login.LoginController;
import ui.login.LoginFrame;
import ui.product.ProductPanel;
import ui.transaction.TransactionHistoryPanel;
import ui.transaction.TransactionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private User currentUser;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    // Sidebar menu buttons
    private JButton btnProducts;
    private JButton btnCategories;
    private JButton btnReports;
    private JButton btnSettings;
    private JButton btnLogout;
    private JButton btnTransactions;
    private JButton btnTransactionsHistory;

    // Panels
    private ProductPanel productPanel;
    private JPanel categoryPanel;
    private JPanel reportsPanel;
    private JPanel settingsPanel;
    private JPanel transactionPanel;
    private JPanel transactionHistoryPanel;

    public MainFrame(User user) {
        this.currentUser = user;

        setupFrame();
        createMenuBar();
        createSidebar();
        setupMainContentArea();

        // Initially show products panel
        showProductPanel();
    }

    private void setupFrame() {
        setTitle("POS System - Welcome " + currentUser.getFullName());
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Change Password"));
        fileMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Management menu
        JMenu managementMenu = new JMenu("Management");
        JMenuItem productsItem = new JMenuItem("Products");
        productsItem.addActionListener(e -> showProductPanel());

        JMenuItem categoriesItem = new JMenuItem("Categories");
        categoriesItem.addActionListener(e -> showCategoryPanel());

        managementMenu.add(productsItem);
        managementMenu.add(categoriesItem);
        managementMenu.add(new JMenuItem("Users"));

        // Reports menu
        JMenu reportsMenu = new JMenu("Reports");
        reportsMenu.add(new JMenuItem("Sales Report"));
        reportsMenu.add(new JMenuItem("Inventory Report"));

        menuBar.add(fileMenu);
        menuBar.add(managementMenu);
        menuBar.add(reportsMenu);

        setJMenuBar(menuBar);
    }

    private void createSidebar() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBackground(new Color(50, 50, 50));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        // User info panel
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        userPanel.setMaximumSize(new Dimension(200, 80));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        userPanel.setBackground(new Color(70, 70, 70));

        JLabel userIcon = new JLabel("\uD83D\uDC64"); // Unicode user icon
        userIcon.setFont(new Font("Dialog", Font.PLAIN, 24));
        userIcon.setForeground(Color.WHITE);

        JLabel userName = new JLabel(currentUser.getFullName());
        userName.setForeground(Color.WHITE);
        JLabel userRole = new JLabel(currentUser.getRole());
        userRole.setForeground(Color.LIGHT_GRAY);

        JPanel userTextPanel = new JPanel();
        userTextPanel.setLayout(new BoxLayout(userTextPanel, BoxLayout.Y_AXIS));
        userTextPanel.setBackground(new Color(70, 70, 70));
        userTextPanel.add(userName);
        userTextPanel.add(userRole);

        userPanel.add(userIcon, BorderLayout.WEST);
        userPanel.add(userTextPanel, BorderLayout.CENTER);

        // Menu buttons
        btnProducts = createMenuButton("Products");
        btnCategories = createMenuButton("Categories");
        btnReports = createMenuButton("Reports");
        btnTransactions = createMenuButton("Transactions");
        btnTransactionsHistory = createMenuButton("Transaction History");
        btnSettings = createMenuButton("Settings");
        btnLogout = createMenuButton("Logout");

        // Add action listeners
        btnProducts.addActionListener(e -> showProductPanel());
        btnCategories.addActionListener(e -> showCategoryPanel());
        btnReports.addActionListener(e -> showReportsPanel());
        btnTransactions.addActionListener(e -> showTransactionPanel());
        btnTransactionsHistory.addActionListener(e -> showTransactionHistoryPanel());
        btnSettings.addActionListener(e -> showSettingsPanel());
        btnLogout.addActionListener(e -> logout());

        // Add components to sidebar
        sidebarPanel.add(userPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnProducts);
        sidebarPanel.add(btnCategories);
        sidebarPanel.add(btnTransactions);
        sidebarPanel.add(btnTransactionsHistory);
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(btnLogout);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        add(sidebarPanel, BorderLayout.WEST);
    }

    private void showTransactionHistoryPanel() {
        cardLayout.show(contentPanel, "TRANSACTION_HISTORY");
        setActiveButton(btnTransactionsHistory);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.equals(getActiveButton())) {
                    button.setBackground(new Color(50, 50, 50));
                }
            }
        });

        return button;
    }

    private void setupMainContentArea() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(contentPanel, BorderLayout.CENTER);

        // Initialize panels
        productPanel = new ProductPanel(this);

        // Create placeholder panels for now
        categoryPanel = new CategoryPanel(this);
        transactionPanel = new TransactionPanel(this);
        transactionHistoryPanel = new TransactionHistoryPanel(this);
        reportsPanel = createPlaceholderPanel("Reports");
        settingsPanel = createPlaceholderPanel("Settings");

        // Add panels to card layout
        contentPanel.add(productPanel, "PRODUCTS");
        contentPanel.add(categoryPanel, "CATEGORIES");
        contentPanel.add(transactionPanel, "TRANSACTIONS");
        contentPanel.add(transactionHistoryPanel, "TRANSACTION_HISTORY");
    }

    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title + " - Under Construction", SwingConstants.CENTER);
        label.setFont(new Font("Dialog", Font.BOLD, 24));
        label.setForeground(Color.GRAY);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private void showProductPanel() {
        cardLayout.show(contentPanel, "PRODUCTS");
        setActiveButton(btnProducts);
    }

    private void showCategoryPanel() {
        cardLayout.show(contentPanel, "CATEGORIES");
        setActiveButton(btnCategories);
    }

    private void showTransactionPanel() {
        cardLayout.show(contentPanel, "TRANSACTIONS");
        setActiveButton(btnTransactions);
    }

    private void showReportsPanel() {
        cardLayout.show(contentPanel, "REPORTS");
        setActiveButton(btnReports);
    }

    private void showSettingsPanel() {
        cardLayout.show(contentPanel, "SETTINGS");
        setActiveButton(btnSettings);
    }

    private void setActiveButton(JButton activeButton) {
        // Reset all buttons
        btnProducts.setBackground(new Color(50, 50, 50));
        btnCategories.setBackground(new Color(50, 50, 50));
        btnReports.setBackground(new Color(50, 50, 50));
        btnTransactions.setBackground(new Color(50, 50, 50));
        btnSettings.setBackground(new Color(50, 50, 50));
        btnLogout.setBackground(new Color(50, 50, 50));

        // Set active button color
        activeButton.setBackground(new Color(100, 100, 100));
    }

    private JButton getActiveButton() {
        if (btnProducts.getBackground().equals(new Color(100, 100, 100))) return btnProducts;
        if (btnCategories.getBackground().equals(new Color(100, 100, 100))) return btnCategories;
        if (btnReports.getBackground().equals(new Color(100, 100, 100))) return btnReports;
        if (btnSettings.getBackground().equals(new Color(100, 100, 100))) return btnSettings;
        return null;
    }

    private void logout() {
        dispose();

        // Create and show login frame
        SwingUtilities.invokeLater(() -> {
            ui.login.LoginFrame loginFrame = new ui.login.LoginFrame();
            ui.login.LoginController loginController = new ui.login.LoginController(
                    loginFrame,
                    di.ServiceLocator.get(service.AuthService.class)
            );
            loginFrame.setVisible(true);
        });
    }
}