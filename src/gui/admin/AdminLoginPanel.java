package gui.admin;

import javax.swing.*;
import java.awt.*;
import auth.AdminAuth;

public class AdminLoginPanel extends JFrame {
    public AdminLoginPanel() {
        // Window Settings
        setTitle("Admin Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);
        JButton loginBtn = new JButton("Login");

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginBtn);

        add(panel);

        // Button Action
        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (AdminAuth.authenticate(username, password)) {
                new AdminDashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show Window
        setVisible(true);
    }
}
