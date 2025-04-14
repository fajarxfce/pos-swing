import dao.*;
import di.ServiceLocator;
import service.AuthService;
import service.CategoryService;
import service.ProductService;
import service.TransactionService;
import ui.login.LoginController;
import ui.login.LoginFrame;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();

        // Register Services
        AuthService authService = new AuthService(userDAO);
        ProductService productService = new ProductService(productDAO);
        TransactionService transactionService = new TransactionService(transactionDAO, productService);
        CategoryService categoryService = new CategoryService(categoryDAO);

        ServiceLocator.register(UserDAO.class, userDAO);
        ServiceLocator.register(ProductDAO.class, productDAO);
        ServiceLocator.register(CategoryDAO.class, categoryDAO);
        ServiceLocator.register(AuthService.class, authService);
        ServiceLocator.register(ProductService.class, productService);
        ServiceLocator.register(TransactionService.class, transactionService);
        ServiceLocator.register(CategoryService.class, categoryService);

        ServiceLocator.register(UserDAO.class, userDAO);
        ServiceLocator.register(AuthService.class, authService);

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            LoginController loginController = new LoginController(loginFrame, authService);
            loginFrame.setVisible(true);
        });
    }
}