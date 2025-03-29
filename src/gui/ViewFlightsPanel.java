package gui;

import database.DatabaseManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ViewFlightsPanel extends JFrame {
    private JTextField fromDateField;
    private JTextField toDateField;
    private JTextField minPriceField;
    private JTextField maxPriceField;

    public ViewFlightsPanel(String from, String to) {
        // Window Settings
        setTitle("Available Flights from " + from + " to " + to);
        setSize(700, 400);  // Increased size to accommodate filters and the table
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for filters
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Date filter setup
        filterPanel.add(new JLabel("From Date (YYYY-MM-DD):"));
        fromDateField = new JTextField(10);
        filterPanel.add(fromDateField);

        filterPanel.add(new JLabel("To Date (YYYY-MM-DD):"));
        toDateField = new JTextField(10);
        filterPanel.add(toDateField);

        // Price filter setup
        filterPanel.add(new JLabel("Min Price:"));
        minPriceField = new JTextField(5);
        filterPanel.add(minPriceField);

        filterPanel.add(new JLabel("Max Price:"));
        maxPriceField = new JTextField(5);
        filterPanel.add(maxPriceField);

        // Filter Button
        JButton filterButton = new JButton("Filter Flights");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterFlights(from, to);  // Re-fetch and display flights with filters applied
            }
        });
        filterPanel.add(filterButton);

        // Add the filter panel at the top
        add(filterPanel, BorderLayout.NORTH);

        // Initially display all flights without filtering
        filterFlights(from, to);
    }

    // Method to filter and display the flights based on the input filters
    private void filterFlights(String from, String to) {
        // Fetch Flights Data from Database
        List<String[]> allFlights = DatabaseManager.getFlights();

        // Filter the flights based on the 'from' and 'to' cities
        List<String[]> filteredFlights = new ArrayList<>();
        for (String[] flight : allFlights) {
            String flightFrom = flight[1]; // Assuming column 1 is the 'From' city
            String flightTo = flight[2];   // Assuming column 2 is the 'To' city

            // Check if the flight matches the selected 'from' and 'to' cities
            if (flightFrom.equals(from) && flightTo.equals(to)) {
                boolean matchesDateRange = true;
                boolean matchesPriceRange = true;

                // Check if date filters are applied
                String departureDate = flight[3]; // Assuming column 3 is the 'Departure' date
                if (!fromDateField.getText().isEmpty()) {
                    try {
                        Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDateField.getText());
                        Date flightDate = new SimpleDateFormat("yyyy-MM-dd").parse(departureDate.split(" ")[0]);
                        if (flightDate.before(fromDate)) {
                            matchesDateRange = false;
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Invalid from date format.");
                        return;
                    }
                }
                if (!toDateField.getText().isEmpty()) {
                    try {
                        Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDateField.getText());
                        Date flightDate = new SimpleDateFormat("yyyy-MM-dd").parse(departureDate.split(" ")[0]);
                        if (flightDate.after(toDate)) {
                            matchesDateRange = false;
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Invalid to date format.");
                        return;
                    }
                }

                // Check if price filters are applied
                double price = Double.parseDouble(flight[5]); // Assuming column 5 is the 'Price'
                if (!minPriceField.getText().isEmpty()) {
                    double minPrice = Double.parseDouble(minPriceField.getText());
                    if (price < minPrice) {
                        matchesPriceRange = false;
                    }
                }
                if (!maxPriceField.getText().isEmpty()) {
                    double maxPrice = Double.parseDouble(maxPriceField.getText());
                    if (price > maxPrice) {
                        matchesPriceRange = false;
                    }
                }

                // If the flight matches all filters, add to filteredFlights list
                if (matchesDateRange && matchesPriceRange) {
                    filteredFlights.add(flight);
                }
            }
        }

        // Table Model
        String[] columnNames = {"Flight No", "From", "To", "Departure", "Seats Available", "Price"};
        DefaultTableModel model = new DefaultTableModel(filteredFlights.toArray(new String[0][]), columnNames);
        JTable flightTable = new JTable(model);

        // Click Event - Select Flight
        flightTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = flightTable.getSelectedRow();
                if (row != -1) {
                    String flightNo = model.getValueAt(row, 0).toString();
                    new BookingPanel(flightNo, from, to);  // Assuming BookingPanel constructor is properly updated
                    dispose();  // Close this panel after selection
                }
            }
        });

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(flightTable);
        add(scrollPane, BorderLayout.CENTER);

        // Refresh Window
        setVisible(true);
    }
}
