package travel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FlightBookingUI extends JFrame {
    private JTextField fromField, toField, dateField;
    private JComboBox<String> passengerBox, classBox;
    private JRadioButton oneWayButton, roundTripButton;

    // Database Credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/travel_management_system";
    private static final String DB_USER = "root";  // Change if needed
    private static final String DB_PASSWORD = "root";  // Change if needed

    public FlightBookingUI() {
        setTitle("Flight Booking");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = Color.WHITE;
                Color color2 = new Color(135, 206, 250); // Sky Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        headerPanel.setBounds(0, 0, 500, 70);

        JLabel titleLabel = new JLabel("Search Flights", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBounds(50, 80, 400, 300);
        formPanel.setBackground(Color.WHITE);

        JLabel fromLabel = new JLabel("From:");
        fromField = new JTextField();
        JLabel toLabel = new JLabel("To:");
        toField = new JTextField();
        
        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField();
        dateField.setEditable(true); // Manual input allowed

        JLabel passengerLabel = new JLabel("Passengers:");
        passengerBox = new JComboBox<>(new String[]{"1", "2", "3", "4+"});
        JLabel classLabel = new JLabel("Class:");
        classBox = new JComboBox<>(new String[]{"Economy", "Business", "First Class"});

        // Flight Type Toggle
        JLabel typeLabel = new JLabel("Trip Type:");
        oneWayButton = new JRadioButton("One-Way");
        roundTripButton = new JRadioButton("Round Trip");
        ButtonGroup tripGroup = new ButtonGroup();
        tripGroup.add(oneWayButton);
        tripGroup.add(roundTripButton);
        oneWayButton.setSelected(true);

        JPanel tripTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tripTypePanel.setBackground(Color.WHITE);
        tripTypePanel.add(oneWayButton);
        tripTypePanel.add(roundTripButton);

        formPanel.add(fromLabel);
        formPanel.add(fromField);
        formPanel.add(toLabel);
        formPanel.add(toField);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(passengerLabel);
        formPanel.add(passengerBox);
        formPanel.add(classLabel);
        formPanel.add(classBox);
        formPanel.add(typeLabel);
        formPanel.add(tripTypePanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(150, 400, 200, 50);
        buttonPanel.setBackground(Color.WHITE);

        JButton searchButton = new JButton("Search Flights");
        searchButton.setBackground(new Color(255, 140, 0));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFlights();
            }
        });

        buttonPanel.add(searchButton);

        // Adding components to frame
        add(headerPanel);
        add(formPanel);
        add(buttonPanel);

        setVisible(true);
    }

    private void searchFlights() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        String date = dateField.getText().trim();
        String flightClass = classBox.getSelectedItem().toString();

        if (from.isEmpty() || to.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT airline, flight_number, departure_time, arrival_time, price " +
                     "FROM flights WHERE source = ? AND destination = ? AND class_type = ?")) {
            
            stmt.setString(1, from);
            stmt.setString(2, to);
            stmt.setString(3, flightClass);

            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append("Airline: ").append(rs.getString("airline")).append("\n")
                      .append("Flight No: ").append(rs.getString("flight_number")).append("\n")
                      .append("Departure: ").append(rs.getString("departure_time")).append("\n")
                      .append("Arrival: ").append(rs.getString("arrival_time")).append("\n")
                      .append("Price: ₹").append(rs.getDouble("price")).append("\n\n");
            }

            if (result.length() == 0) {
                JOptionPane.showMessageDialog(this, "No flights found!", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, result.toString(), "Available Flights", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlightBookingUI::new);
    }
}
