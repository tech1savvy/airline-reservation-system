package gui;

import database.DatabaseManager;
import javax.swing.*;
import java.awt.*;

public class PaymentPanel extends JFrame {

    // Assuming totalPrice is passed to this panel constructor
    public PaymentPanel(int bookingId, String flightNo, String from, String to, String seatType, int passengers, String passengerName, String age, double totalPrice) {
        // Window Settings
        setTitle("Payment");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        JLabel paymentLabel = new JLabel("Total Price: $" + totalPrice);
        JLabel paymentMethodLabel = new JLabel("Select Payment Method:");

        String[] paymentMethods = {"Cash"};  // Only Cash option for simplicity
        JComboBox<String> paymentDropdown = new JComboBox<>(paymentMethods);

        JButton payBtn = new JButton("Confirm Payment");
        JButton cancelBtn = new JButton("Cancel");

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));  // Adding space between components
        panel.add(paymentLabel);
        panel.add(paymentMethodLabel);
        panel.add(paymentDropdown);
        panel.add(payBtn);
        panel.add(cancelBtn);

        add(panel);

        // Button Actions
        payBtn.addActionListener(e -> {
            String selectedPayment = (String) paymentDropdown.getSelectedItem();

            // Assuming payment by cash is always successful for this simple version
            if ("Cash".equals(selectedPayment)) {
                JOptionPane.showMessageDialog(null, "Payment Successful! Total Paid: $" + totalPrice, "Success", JOptionPane.INFORMATION_MESSAGE);

                // Proceed to next panel (for example, ticket panel)
                new TicketPanel(bookingId, flightNo, from, to, seatType, passengers, passengerName, age);
                dispose();  // Close payment panel
            } else {
                JOptionPane.showMessageDialog(null, "Payment failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        // Show Window
        setVisible(true);
    }
}
