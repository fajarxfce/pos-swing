package ui.main;

import model.User;

import javax.swing.*;

public class MainFrame extends JFrame {
    private User currentUser;

    public MainFrame(User user) {
        this.currentUser = user;

        setTitle("POS System - Welcome " + user.getFullName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Welcome to POS System, " + user.getFullName()));
        add(panel);
    }
}