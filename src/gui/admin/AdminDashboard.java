package gui.admin;

import database.DatabaseManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTable flightsTable, bookingsTable;
    private DefaultTableModel flightTableModel, bookingTableModel;

    public AdminDashboard() {
        // Window Settings
        setTitle("Admin Dashboard");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Flight Table Setup (Now includes Price)
        String[] flightColumnNames = {"Flight No", "From", "To", "Departure", "Seats", "Price"};
        flightTableModel = new DefaultTableModel(flightColumnNames, 0);
        flightsTable = new JTable(flightTableModel);
        JScrollPane flightScrollPane = new JScrollPane(flightsTable);
        refreshFlightTable();  // Populate the table with flights

        // Booking Table Setup
        String[] bookingColumnNames = {"ID", "Flight No", "Passenger", "Age", "Seat Type", "Passengers"};
        bookingTableModel = new DefaultTableModel(bookingColumnNames, 0);
        bookingsTable = new JTable(bookingTableModel);
        JScrollPane bookingScrollPane = new JScrollPane(bookingsTable);
        refreshBookingTable();  // Populate the table with bookings

        // Buttons
        JButton addFlightBtn = new JButton("Add Flight");
        JButton updateFlightBtn = new JButton("Update Flight");
        JButton deleteFlightBtn = new JButton("Delete Flight");
        JButton cancelBookingBtn = new JButton("Cancel Booking");

        // Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addFlightBtn);
        buttonPanel.add(updateFlightBtn);
        buttonPanel.add(deleteFlightBtn);
        buttonPanel.add(cancelBookingBtn);

        JPanel tablePanel = new JPanel(new GridLayout(2, 1));
        tablePanel.add(flightScrollPane);
        tablePanel.add(bookingScrollPane);

        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addFlightBtn.addActionListener(e -> new AddFlightPanel(this));
        updateFlightBtn.addActionListener(e -> updateFlight());
        deleteFlightBtn.addActionListener(e -> deleteFlight());
        cancelBookingBtn.addActionListener(e -> cancelBooking());

        // Show Window
        setVisible(true);
    }

    // Refresh Flight Table Data (Including Price)
    public void refreshFlightTable() {
        flightTableModel.setRowCount(0);  // Clear existing rows
        List<String[]> flights = DatabaseManager.getFlights();  // Fetch flight data from DB
        for (String[] flight : flights) {
            flightTableModel.addRow(flight);  // Add each flight row (with price) to the table
        }
    }

    // Refresh Booking Table Data
    public void refreshBookingTable() {
        bookingTableModel.setRowCount(0);  // Clear existing rows
        List<String[]> bookings = DatabaseManager.getBookings();  // Fetch booking data from DB
        for (String[] booking : bookings) {
            bookingTableModel.addRow(booking);  // Add each booking row to the table
        }
    }

    // Update Flight
    private void updateFlight() {
        int row = flightsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a flight to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String flightNo = (String) flightTableModel.getValueAt(row, 0);
        new UpdateFlightPanel(this, flightNo);
    }

    // Delete Flight
    private void deleteFlight() {
        int row = flightsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a flight to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String flightNo = (String) flightTableModel.getValueAt(row, 0);
        if (DatabaseManager.deleteFlight(flightNo)) {
            JOptionPane.showMessageDialog(null, "Flight deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshFlightTable();
        } else {
            JOptionPane.showMessageDialog(null, "Error deleting flight!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cancel Booking
    private void cancelBooking() {
        int row = bookingsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a booking to cancel!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int bookingId = Integer.parseInt((String) bookingTableModel.getValueAt(row, 0));
        if (DatabaseManager.deleteBooking(bookingId)) {
            JOptionPane.showMessageDialog(null, "Booking canceled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshBookingTable();
        } else {
            JOptionPane.showMessageDialog(null, "Error canceling booking!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
