package service;

import dao.UserDAO;
import model.User;

import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean authenticate(String username, String password) {
        try {
            return userDAO.authenticate(username, password);
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            return false;
        }
    }

    public User login(String username, String password) {
        try {
            return userDAO.getUserByCredentials(username, password);
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            return null;
        }
    }
}