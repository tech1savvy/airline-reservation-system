package gui.admin;

import database.DatabaseManager;
import javax.swing.*;
import java.awt.*;

public class UpdateFlightPanel extends JFrame {
    public UpdateFlightPanel(AdminDashboard dashboard, String flightNo) {
        setTitle("Update Flight");
        setSize(300, 300);
        setLocationRelativeTo(null);

        // Get existing flight details
        String[] flightDetails = DatabaseManager.getFlightDetails(flightNo);
        if (flightDetails == null) {
            JOptionPane.showMessageDialog(null, "Flight not found!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Extract existing flight details
        String from = flightDetails[1];
        String to = flightDetails[2];
        String departure = flightDetails[3];
        int seats = Integer.parseInt(flightDetails[4]);  // Using the seats value from the flight details
        double price = Double.parseDouble(flightDetails[5]);  // Assuming the price is in flightDetails[5]

        // Components for the panel
        JTextField fromField = new JTextField(from);
        JTextField toField = new JTextField(to);
        JTextField departureField = new JTextField(departure);
        JTextField seatsField = new JTextField(String.valueOf(seats));  // Set initial value from flight details
        JTextField priceField = new JTextField(String.valueOf(price));  // Set initial price value
        JButton updateBtn = new JButton("Update");

        // Layout for the panel
        JPanel panel = new JPanel(new GridLayout(6, 2));  // Adjusted to 6 rows
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
        panel.add(updateBtn);

        add(panel);

        // Action for update button
        updateBtn.addActionListener(e -> {
            String fromText = fromField.getText().trim();
            String toText = toField.getText().trim();
            String departureText = departureField.getText().trim();
            String seatsText = seatsField.getText().trim();
            String priceText = priceField.getText().trim();  // Get price input

            // Validate fields
            if (fromText.isEmpty() || toText.isEmpty() || departureText.isEmpty() || seatsText.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int updatedSeats;
            double updatedPrice;
            try {
                updatedSeats = Integer.parseInt(seatsText);
                if (updatedSeats <= 0) {
                    throw new NumberFormatException();
                }
                updatedPrice = Double.parseDouble(priceText);  // Parse the price
                if (updatedPrice <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Seats and price must be positive numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the flight
            if (DatabaseManager.updateFlight(flightNo, fromText, toText, departureText, updatedSeats, updatedPrice)) {
                JOptionPane.showMessageDialog(null, "Flight updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dashboard.refreshFlightTable();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error updating flight!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
