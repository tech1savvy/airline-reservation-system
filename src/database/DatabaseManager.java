package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.net.URISyntaxException;

public class DatabaseManager {

    // Declare the exePath and baseDir variables
    private static String exePath;
    private static String baseDir;

    static {
        try {
            // This block initializes the paths, catching any potential URISyntaxException
            exePath = new File(DatabaseManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            baseDir = new File(exePath).getParent(); // Path to the directory where the .exe file is located
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Dynamically calculate the path to the database file
    private static final String databasePath = baseDir + File.separator + "airline.db"; // Path to the database file

    // Use the dynamically computed path to connect to the database
    private static final String DB_URL = "jdbc:sqlite:" + databasePath;

    // Connect to Database
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create Tables
    public static void createTables() {
        String flightsTable = "CREATE TABLE IF NOT EXISTS flights (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "flight_no TEXT UNIQUE," +
                "from_city TEXT," +
                "to_city TEXT," +
                "departure TEXT," +
                "seats_available INTEGER," +
                "price REAL" + // Added price column
                ");";

        String bookingsTable = "CREATE TABLE IF NOT EXISTS bookings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "flight_no TEXT," +
                "passenger_name TEXT," +
                "age INTEGER," +
                "seat_type TEXT," +
                "passenger_count INTEGER," +
                "FOREIGN KEY (flight_no) REFERENCES flights(flight_no)" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(flightsTable);
            stmt.execute(bookingsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Flight (now includes price)
    public static boolean addFlight(String flightNo, String from, String to, String departure, int seats, double price) {
        String query = "INSERT INTO flights (flight_no, from_city, to_city, departure, seats_available, price) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, flightNo);
            pstmt.setString(2, from);
            pstmt.setString(3, to);
            pstmt.setString(4, departure);
            pstmt.setInt(5, seats);
            pstmt.setDouble(6, price);  // Added price parameter
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get All Flights (now includes price)
    public static List<String[]> getFlights() {
        List<String[]> flights = new ArrayList<>();
        String query = "SELECT * FROM flights";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                flights.add(new String[]{
                        rs.getString("flight_no"),
                        rs.getString("from_city"),
                        rs.getString("to_city"),
                        rs.getString("departure"),
                        String.valueOf(rs.getInt("seats_available")),
                        String.valueOf(rs.getDouble("price"))  // Added price column
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    // Get Flight Details by Flight Number (now includes price)
    public static String[] getFlightDetails(String flightNo) {
        String query = "SELECT * FROM flights WHERE flight_no = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, flightNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new String[]{
                        rs.getString("flight_no"),
                        rs.getString("from_city"),
                        rs.getString("to_city"),
                        rs.getString("departure"),
                        String.valueOf(rs.getInt("seats_available")),
                        String.valueOf(rs.getDouble("price"))  // Added price column
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if no flight found
    }

    // Update Flight (now includes price)
    public static boolean updateFlight(String flightNo, String from, String to, String departure, int seats, double price) {
        String query = "UPDATE flights SET from_city = ?, to_city = ?, departure = ?, seats_available = ?, price = ? WHERE flight_no = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, from);
            pstmt.setString(2, to);
            pstmt.setString(3, departure);
            pstmt.setInt(4, seats);
            pstmt.setDouble(5, price);  // Added price parameter
            pstmt.setString(6, flightNo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Flight
    public static boolean deleteFlight(String flightNo) {
        String query = "DELETE FROM flights WHERE flight_no = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, flightNo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add Booking
    public static int addBooking(String flightNo, String passengerName, int age, String seatType, int passengerCount) {
        String query = "INSERT INTO bookings (flight_no, passenger_name, age, seat_type, passenger_count) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, flightNo);
            pstmt.setString(2, passengerName);
            pstmt.setInt(3, age);
            pstmt.setString(4, seatType);
            pstmt.setInt(5, passengerCount);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  // Return generated booking ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails
    }

    // Get All Bookings
    public static List<String[]> getBookings() {
        List<String[]> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                bookings.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("flight_no"),
                        rs.getString("passenger_name"),
                        String.valueOf(rs.getInt("age")),
                        rs.getString("seat_type"),
                        String.valueOf(rs.getInt("passenger_count"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Delete Booking
    public static boolean deleteBooking(int bookingId) {
        String query = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Main Menu
    public static List<String> getUniqueCities() {
        List<String> cities = new ArrayList<>();
        String query = "SELECT DISTINCT from_city FROM flights UNION SELECT DISTINCT to_city FROM flights";

        // Debugging: Check the query being executed
        System.out.println("Executing query: " + query);

        try (Connection conn = connect();  // Ensure this returns a valid connection
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Debugging: Check if ResultSet contains data
            if (!rs.next()) {
                System.out.println("No cities found in the database.");
            } else {
                do {
                    String city = rs.getString(1);
                    if (!cities.contains(city)) {
                        cities.add(city);
                        System.out.println("Added city: " + city);  // Debugging: output each city added
                    }
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Debugging: Check the final list of cities
        System.out.println("Cities fetched: " + cities);
        return cities;
    }

}
