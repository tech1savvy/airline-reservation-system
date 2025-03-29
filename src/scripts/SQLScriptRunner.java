package scripts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class SQLScriptRunner {

    // Database URL (same as in your DatabaseManager)
    private static final String DB_URL = "jdbc:sqlite:airline.db";

    public static void main(String[] args) {
        // Specify the relative path to your SQL script (same folder as the Java file)
        String sqlScriptPath = "script.sql";

        // Execute the SQL script
        executeSQLScript(sqlScriptPath);
    }

    public static void executeSQLScript(String scriptPath) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             BufferedReader reader = new BufferedReader(new FileReader(scriptPath))) {

            // Read the entire SQL script
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            // Execute the SQL script
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql.toString());
                System.out.println("SQL script executed successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading the SQL script.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL execution failed.");
        }
    }
}
