-- Create flights table (including price column)
CREATE TABLE IF NOT EXISTS flights (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    flight_no TEXT UNIQUE,
    from_city TEXT,
    to_city TEXT,
    departure TEXT,
    seats_available INTEGER,
    price REAL  -- Added price column
);

-- Create bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    flight_no TEXT,
    passenger_name TEXT,
    age INTEGER,
    seat_type TEXT,
    passenger_count INTEGER,
    FOREIGN KEY (flight_no) REFERENCES flights(flight_no)
);

-- Insert dummy data into flights table (including prices)
INSERT INTO flights (flight_no, from_city, to_city, departure, seats_available, price)
VALUES
    ('AI202', 'New Delhi', 'Mumbai', '2025-04-01 06:00:00', 150, 5000.00),
    ('SG102', 'Bangalore', 'Hyderabad', '2025-04-01 10:00:00', 200, 3500.00),
    ('6E125', 'Chennai', 'Ranchi', '2025-04-01 14:00:00', 180, 4000.00),
    ('AI304', 'Kolkata', 'Pune', '2025-04-02 07:00:00', 130, 4500.00),
    -- Additional flights from New Delhi to Mumbai
    ('AI205', 'New Delhi', 'Mumbai', '2025-04-01 08:00:00', 180, 5200.00),
    ('AI206', 'New Delhi', 'Mumbai', '2025-04-01 10:00:00', 150, 5100.00),
    ('SG105', 'New Delhi', 'Mumbai', '2025-04-01 12:00:00', 160, 5000.00),
    ('AI210', 'New Delhi', 'Mumbai', '2025-04-01 16:00:00', 140, 5300.00),
    -- Additional flights from Bangalore to Hyderabad
    ('SG110', 'Bangalore', 'Hyderabad', '2025-04-01 06:30:00', 190, 3600.00),
    ('6E130A', 'Bangalore', 'Hyderabad', '2025-04-01 09:00:00', 200, 3700.00),  -- Changed to unique flight number
    ('AI305', 'Bangalore', 'Hyderabad', '2025-04-01 15:00:00', 180, 3500.00),
    ('SG115', 'Bangalore', 'Hyderabad', '2025-04-01 18:30:00', 170, 3800.00),
    -- Additional flights from Chennai to Ranchi
    ('6E130B', 'Chennai', 'Ranchi', '2025-04-01 07:00:00', 160, 4200.00),  -- Changed to unique flight number
    ('SG200', 'Chennai', 'Ranchi', '2025-04-01 12:00:00', 140, 4100.00),
    ('AI300', 'Chennai', 'Ranchi', '2025-04-01 17:00:00', 150, 4000.00),
    ('6E135', 'Chennai', 'Ranchi', '2025-04-02 08:00:00', 180, 4300.00),
    -- Additional flights from Kolkata to Pune
    ('AI310', 'Kolkata', 'Pune', '2025-04-02 09:00:00', 120, 4600.00),
    ('SG220', 'Kolkata', 'Pune', '2025-04-02 11:00:00', 130, 4700.00),
    ('6E145', 'Kolkata', 'Pune', '2025-04-02 13:00:00', 110, 4400.00),
    ('AI315', 'Kolkata', 'Pune', '2025-04-02 18:00:00', 140, 4800.00);


-- Insert dummy data into bookings table
INSERT INTO bookings (flight_no, passenger_name, age, seat_type, passenger_count)
VALUES
    ('AI202', 'Aman Kumar', 28, 'Economy', 1),
    ('SG102', 'Neha Singh', 35, 'Business', 1),
    ('6E125', 'Rajesh Patel', 40, 'Economy', 2),
    ('AI304', 'Priya Sharma', 30, 'Economy', 1),
    -- Additional bookings
    ('AI205', 'Sonia Gupta', 25, 'Business', 1),
    ('AI206', 'Vinay Singh', 32, 'Economy', 1),
    ('SG105', 'Amit Yadav', 27, 'Economy', 1),
    ('AI210', 'Kriti Mehta', 29, 'Business', 1),
    ('SG110', 'Manoj Verma', 38, 'Economy', 2),
    ('6E130', 'Deepak Kumar', 33, 'Economy', 1),
    ('AI305', 'Shivani Deshmukh', 31, 'Economy', 1),
    ('SG115', 'Ravi Bansal', 41, 'Business', 1),
    ('6E130', 'Harsha Rao', 26, 'Economy', 1),
    ('SG200', 'Pooja Sharma', 30, 'Economy', 1),
    ('AI300', 'Shivendra Singh', 45, 'Business', 2),
    ('6E135', 'Ankit Kapoor', 24, 'Economy', 1),
    ('AI310', 'Maya Desai', 50, 'Economy', 1),
    ('SG220', 'Nitin Patil', 33, 'Economy', 2),
    ('6E145', 'Meera Prasad', 28, 'Business', 1),
    ('AI315', 'Vikash Kumar', 36, 'Economy', 1);
