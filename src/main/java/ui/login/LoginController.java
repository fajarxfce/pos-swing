package ui.login;

import model.Product;
import model.User;
import service.AuthService;
import ui.main.MainFrame;
import javax.swing.*;
import java.util.List;

public class LoginController {
    private final LoginFrame view;
    private final AuthService authService;

    public LoginController(LoginFrame view, AuthService authService) {
        this.view = view;
        this.authService = authService;
        this.view.setController(this);
    }

    public void login() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            view.showError("Username and password cannot be empty");
            return;
        }

        try {
            view.setLoading(true);
            User user = authService.login(username, password);

            if (user != null) {
                openMainApplication(user);
            } else {
                view.showError("Invalid username or password");
                view.clearFields();
            }
        } catch (Exception e) {
            view.showError("An error occurred: " + e.getMessage());
        } finally {
            view.setLoading(false);
        }
    }

    public void cancel() {
        System.exit(0);
    }

    private void openMainApplication(User user) {
        view.dispose();
        MainFrame mainFrame = new MainFrame(user);
        mainFrame.setVisible(true);
    }
}