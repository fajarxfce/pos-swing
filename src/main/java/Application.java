import dao.UserDAO;
import dao.UserDAOImpl;
import di.ServiceLocator;
import service.AuthService;
import ui.login.LoginController;
import ui.login.LoginFrame;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        // Register services
        UserDAO userDAO = new UserDAOImpl();
        AuthService authService = new AuthService(userDAO);

        ServiceLocator.register(UserDAO.class, userDAO);
        ServiceLocator.register(AuthService.class, authService);

        // Start the application
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            LoginController loginController = new LoginController(loginFrame, authService);
            loginFrame.setVisible(true);
        });
    }
}