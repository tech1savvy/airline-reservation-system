package com.airline;

import javax.swing.*;
import gui.MainMenu;
import database.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        // Ensure UI runs on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Initialize Database
            DatabaseManager.connect();
            
            // Launch com.airline.Main Menu UI
            new MainMenu();
        });
    }
}
