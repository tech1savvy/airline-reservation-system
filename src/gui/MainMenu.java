package gui;

import database.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.util.List; // Correct import for List
import gui.admin.AdminLoginPanel; // Import the AdminLoginPanel class

public class MainMenu extends JFrame {
    public MainMenu() {
        // Window Settings
        setTitle("Airline Reservation System");
        setSize(400, 350); // Increased the size to accommodate the new button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fetch cities from the database
        List<String> cities = DatabaseManager.getUniqueCities();

        // Components
        JLabel fromLabel = new JLabel("From:");
        JComboBox<String> fromDropdown = new JComboBox<>(cities.toArray(new String[0]));

        JLabel toLabel = new JLabel("To:");
        JComboBox<String> toDropdown = new JComboBox<>(cities.toArray(new String[0]));

        JButton searchFlightsBtn = new JButton("Search Flights");
        JButton adminLoginBtn = new JButton("Admin Login"); // New Admin Login button

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // Adjusted grid layout to fit the new button
        panel.add(fromLabel);
        panel.add(fromDropdown);
        panel.add(toLabel);
        panel.add(toDropdown);
        panel.add(searchFlightsBtn);
        panel.add(adminLoginBtn); // Add the Admin Login button to the panel
        add(panel);

        // Button Action: Open Available Flights Panel
        searchFlightsBtn.addActionListener(e -> {
            String from = (String) fromDropdown.getSelectedItem();
            String to = (String) toDropdown.getSelectedItem();
            new ViewFlightsPanel(from, to);
        });

        // Button Action: Open Admin Login Panel
        adminLoginBtn.addActionListener(e -> {
            new AdminLoginPanel(); // Open the Admin Login window
        });

        // Show Window
        setVisible(true);
    }
}
