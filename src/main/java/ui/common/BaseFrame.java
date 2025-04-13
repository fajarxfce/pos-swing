package ui.common;

import javax.swing.*;

public abstract class BaseFrame extends JFrame {
    protected void setupFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    protected abstract void initComponents();
    protected abstract void layoutComponents();
    protected abstract void initListeners();
}