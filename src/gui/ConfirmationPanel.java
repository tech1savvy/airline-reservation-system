package gui;

import database.DatabaseManager;
import javax.swing.*;
import java.awt.*;

public class ConfirmationPanel extends JFrame {
    public ConfirmationPanel(String flightNo, String from, String to, String seatType, int passengers, String passengerName, String age) {
        // Window Settings
        setTitle("Confirm Booking");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        JLabel flightLabel = new JLabel("Flight No: " + flightNo + " | " + from + " → " + to);
        JLabel seatLabel = new JLabel("Seat Type: " + seatType);
        JLabel passengerLabel = new JLabel("Passengers: " + passengers);
        JLabel nameLabel = new JLabel("Passenger: " + passengerName + " (Age: " + age + ")");

        JButton confirmBtn = new JButton("Confirm & Pay");
        JButton cancelBtn = new JButton("Cancel");

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        panel.add(flightLabel);
        panel.add(seatLabel);
        panel.add(passengerLabel);
        panel.add(nameLabel);
        panel.add(confirmBtn);
        panel.add(cancelBtn);

        add(panel);

        // Button Actions
        confirmBtn.addActionListener(e -> {
            try {
                // Calculate the total price based on seatType and passengers (dummy calculation here)
                double totalPrice = calculateTotalPrice(seatType, passengers); // You can customize this function

                int bookingId = DatabaseManager.addBooking(flightNo, passengerName, Integer.parseInt(age), seatType, passengers);

                if (bookingId != -1) {
                    // Pass totalPrice along with other arguments
                    new PaymentPanel(bookingId, flightNo, from, to, seatType, passengers, passengerName, age, totalPrice);
                    dispose();  // Close confirmation panel
                } else {
                    JOptionPane.showMessageDialog(null, "Booking failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid age format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        // Show Window
        setVisible(true);
    }

    // Dummy function to calculate total price, you can customize it based on seatType, passengers, etc.
    private double calculateTotalPrice(String seatType, int passengers) {
        double pricePerPassenger = 100.0; // Dummy price
        if ("Business".equalsIgnoreCase(seatType)) {
            pricePerPassenger = 200.0;
        }
        return pricePerPassenger * passengers;
    }
}
