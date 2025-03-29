package gui.admin;

import database.DatabaseManager;
import javax.swing.*;
import java.awt.*;

public class AddFlightPanel extends JFrame {
    public AddFlightPanel(AdminDashboard dashboard) {
        setTitle("Add Flight");
        setSize(300, 300);
        setLocationRelativeTo(null);

        // Components
        JTextField flightNoField = new JTextField(10);
        JTextField fromField = new JTextField(10);
        JTextField toField = new JTextField(10);
        JTextField departureField = new JTextField(10);
        JTextField seatsField = new JTextField(5);
        JTextField priceField = new JTextField(5);  // New field for price
        JButton addBtn = new JButton("Add");

        // Layout
        JPanel panel = new JPanel(new GridLayout(7, 2));  // Adjusted to 7 rows
        panel.add(new JLabel("Flight No:"));
        panel.add(flightNoField);
        panel.add(new JLabel("From:"));
        panel.add(fromField);
        panel.add(new JLabel("To:"));
        panel.add(toField);
        panel.add(new JLabel("Departure:"));
        panel.add(departureField);
        panel.add(new JLabel("Seats:"));
        panel.add(seatsField);
        panel.add(new JLabel("Price:"));  // Label for price
        panel.add(priceField);  // Field for price
        panel.add(new JLabel(""));  // Empty space
        panel.add(addBtn);

        add(panel);

        // Action
        addBtn.addActionListener(e -> {
            String flightNo = flightNoField.getText().trim();
            String from = fromField.getText().trim();
            String to = toField.getText().trim();
            String departure = departureField.getText().trim();
            String seatsText = seatsField.getText().trim();
            String priceText = priceField.getText().trim();  // Get price input

            if (flightNo.isEmpty() || from.isEmpty() || to.isEmpty() || departure.isEmpty() || seatsText.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int seats;
            double price;
            try {
                seats = Integer.parseInt(seatsText);
                if (seats <= 0) {
                    throw new NumberFormatException();
                }
                price = Double.parseDouble(priceText);  // Parse the price
                if (price <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Seats and price must be positive numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (DatabaseManager.addFlight(flightNo, from, to, departure, seats, price)) {
                JOptionPane.showMessageDialog(null, "Flight added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dashboard.refreshFlightTable();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error adding flight!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
