package gui;

import javax.swing.*;
import java.awt.*;

public class BookingPanel extends JFrame {
    public BookingPanel(String flightNo, String from, String to) {
        // Window Settings
        setTitle("Book a Ticket - Flight " + flightNo);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        JLabel flightLabel = new JLabel("Flight No: " + flightNo + " | " + from + " → " + to);
        
        JLabel seatTypeLabel = new JLabel("Seat Type:");
        String[] seatTypes = {"Economy", "Business", "First Class"};
        JComboBox<String> seatTypeDropdown = new JComboBox<>(seatTypes);

        JLabel passengerCountLabel = new JLabel("Number of Passengers:");
        JSpinner passengerCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        JLabel nameLabel = new JLabel("Passenger Name:");
        JTextField nameField = new JTextField(20);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(3);

        JButton nextBtn = new JButton("Next");
        JButton cancelBtn = new JButton("Cancel");

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        panel.add(flightLabel);
        panel.add(new JLabel(""));  // Empty cell
        panel.add(seatTypeLabel);
        panel.add(seatTypeDropdown);
        panel.add(passengerCountLabel);
        panel.add(passengerCountSpinner);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(nextBtn);
        panel.add(cancelBtn);

        add(panel);

        // Button Actions
        nextBtn.addActionListener(e -> {
            String seatType = (String) seatTypeDropdown.getSelectedItem();
            int passengers = (int) passengerCountSpinner.getValue();
            String passengerName = nameField.getText();
            String age = ageField.getText();

            if (passengerName.isEmpty() || age.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all details.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                new ConfirmationPanel(flightNo, from, to, seatType, passengers, passengerName, age);
                dispose();  // Close booking panel
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        // Show Window
        setVisible(true);
    }
}
