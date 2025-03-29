package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;

public class TicketPanel extends JFrame {
    public TicketPanel(int bookingId, String flightNo, String from, String to, String seatType, int passengers, String passengerName, String age) {
        // Window Settings
        setTitle("Your Ticket");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ticket Information
        JLabel ticketLabel = new JLabel("<html><h2>Booking Confirmed!</h2></html>", SwingConstants.CENTER);
        JLabel flightLabel = new JLabel("Flight No: " + flightNo + " | " + from + " → " + to);
        JLabel seatLabel = new JLabel("Seat Type: " + seatType);
        JLabel passengerLabel = new JLabel("Passengers: " + passengers);
        JLabel nameLabel = new JLabel("Passenger: " + passengerName + " (Age: " + age + ")");

        JButton printBtn = new JButton("Print Ticket");
        JButton exitBtn = new JButton("Exit");

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        panel.add(ticketLabel);
        panel.add(flightLabel);
        panel.add(seatLabel);
        panel.add(passengerLabel);
        panel.add(nameLabel);
        panel.add(printBtn);
        panel.add(exitBtn);

        add(panel);

        // Button Actions
        printBtn.addActionListener(e -> printTicket(flightNo, from, to, seatType, passengers, passengerName, age));
        exitBtn.addActionListener(e -> System.exit(0));

        // Show Window
        setVisible(true);
    }

    // Method to Print Ticket
    private void printTicket(String flightNo, String from, String to, String seatType, int passengers, String passengerName, String age) {
        // Create a PrintJob
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            // Set up the page content to be printed
            graphics.setFont(new Font("Serif", Font.PLAIN, 12));  // Simple font
            graphics.drawString("Airline Ticket Confirmation", 100, 100);
            graphics.drawString("Flight No: " + flightNo, 100, 120);
            graphics.drawString("Route: " + from + " → " + to, 100, 140);
            graphics.drawString("Seat Type: " + seatType, 100, 160);
            graphics.drawString("Passengers: " + passengers, 100, 180);
            graphics.drawString("Passenger Name: " + passengerName + " (Age: " + age + ")", 100, 200);
            graphics.drawString("Thank you for booking with us!", 100, 220);
            return Printable.PAGE_EXISTS;
        });

        // Show Print Dialog
        if (printerJob.printDialog()) {
            try {
                printerJob.print(); // Print the ticket
                JOptionPane.showMessageDialog(null, "Ticket sent to printer.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error printing ticket.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // com.airline.Main method to run the GUI
    public static void main(String[] args) {
        new TicketPanel(12345, "AI-101", "Delhi", "Mumbai", "Economy", 1, "Aman Kumar", "25");
    }
}
