# Airline Reservation System - Project Outline

## **1. Project Overview**
A **Standalone Java-based Airline Reservation System** where customers can book flight tickets via a machine. The project uses:
- **Java (Standalone)** – Core logic and UI.
- **Swing (GUI)** – To provide a graphical user interface.
- **SQLite** – File-based database for storing flights, users, and tickets.
- **Launch4j** – To generate a `.exe` file for Windows.
- **Inno Setup** – To create an installer with a bundled JRE, uninstaller, and custom UI.
- **Git/GitHub** – For source code management and collaboration.
- **JUnit** – For unit testing.

## **2. Project Structure**
```
airline-reservation/
│── src/                        # Source code directory
│   ├── models/                 # Data models
│   │   ├── Flight.java
│   │   ├── Ticket.java
│   │   ├── User.java
│   ├── services/               # Business logic
│   │   ├── FlightService.java
│   │   ├── TicketService.java
│   │   ├── UserService.java
│   ├── database/               # Database connection
│   │   ├── DatabaseConnection.java
│   ├── gui/                    # Swing UI (Final Version)
│   │   ├── MainMenu.java
│   │   ├── BookingPanel.java
│   │   ├── PaymentPanel.java
│   │   ├── ConfirmationPanel.java
│   ├── com.airline.Main.java                # Entry point
│── test/                        # Unit Tests (JUnit)
│   ├── FlightServiceTest.java
│   ├── TicketServiceTest.java
│── lib/                         # External JARs (SQLite JDBC)
│── dist/                        # Compiled files & final output
│   ├── airline-reservation.exe  # Generated executable
│   ├── sqlite/                  # SQLite database files
│   ├── jre/                     # Bundled JRE
│   ├── README.txt
│   ├── setup.iss                # Inno Setup script for installer
│── launch4j-config.xml          # Config for creating .exe
│── README.md
```

## **3. Key Features & Implementation**
### **Backend Implementation**
✅ Uses **SQLite** as the database (lightweight, embedded).  
✅ `DatabaseConnection.java` handles SQLite CRUD operations.  
✅ `FlightService`, `TicketService`, `UserService` manage business logic.  
✅ `JUnit` tests ensure data consistency and functionality.  

### **Frontend (GUI) Implementation** *(Added later if needed)*
✅ **Swing UI** with panels for booking, payment, and confirmation.  
✅ **Event-driven programming** for user interactions.  
✅ **JTable** for displaying flights and ticket details.  

### **Executable & Installer**
✅ **Launch4j** generates a `.exe` file for Windows users.  
✅ **Inno Setup** creates a custom installer with:
   - **Bundled JRE** (Users don't need to install Java manually).
   - **Custom UI** (Logo, license agreement, modern wizard style).
   - **Uninstaller** for easy removal.  

## **4. Future Enhancements**
📌 **Enhance UI** – Improve Swing design for a better user experience.  
📌 **Multi-language support** – Add localization for different regions.  
📌 **Data Encryption** – Secure sensitive user and payment data.  
📌 **Auto-Update Feature** – Enable downloading newer versions from a server.  

---
### **Final Steps to Build the Project**
1️⃣ **Develop backend logic (SQLite, services, and models).**  
2️⃣ **Implement GUI using Swing (if required).**  
3️⃣ **Compile Java to JAR and create .exe with Launch4j.**  
4️⃣ **Use Inno Setup to bundle everything into a Windows installer.**  

