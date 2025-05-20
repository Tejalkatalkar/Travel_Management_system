package travel_management_system;

import javax.swing.*;
import java.awt.*;

public class DestinationsUI extends JFrame {
    public DestinationsUI() {
        setTitle("Top Sights in India");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel with Gradient Background
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, getWidth(), 0, new Color(135, 206, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Top Sights in India", JLabel.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        JPanel gridPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        gridPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        String[][] destinations = {
            {"Gateway Of India", "C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\travel_management_system\\src\\travel_management_system\\download (6).jpg", "4.6 ⭐ (3.8L)", "Historical landmark", "Free"},
            {"Taj Mahal", "C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\travel_management_system\\src\\travel_management_system\\download (4).jpg", "4.6 ⭐ (2.4L)", "Monument", "₹1,100.00"},
            {"Amber Palace", "C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\travel_management_system\\src\\travel_management_system\\download (5).jpg", "4.6 ⭐ (1.6L)", "Castle", "₹500.00"},
            {"Somnath Temple", "C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\travel_management_system\\src\\travel_management_system\\Taj mahal palace.jpg", "4.5 ⭐ (3.2L)", "Locality", ""},
            {"Mehrangarh Fort", "C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\travel_management_system\\src\\travel_management_system\\mehrangarh-fort.jpg", "4.6 ⭐ (69K)", "Historical landmark", ""},
            {"Sri Harmandir Sahib", "C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\travel_management_system\\src\\travel_management_system\\download (5).jpg", "4.9 ⭐ (2L)", "Gurudwara", ""},
            {"Ellora Caves", "C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\travel_management_system\\src\\travel_management_system\\mehrangarh-fort.jpg", "4.7 ⭐ (53K)", "Historical landmark", ""},
            {"Goa", "C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\travel_management_system\\src\\travel_management_system\\download.jpg", "4.8 ⭐ (1.9L)", "Beach", ""}
        };

        for (String[] destination : destinations) {
            gridPanel.add(createDestinationCard(destination));
        }

        // Navigation Panel for Back and Next buttons at the bottom
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            // Open HomePage and dispose of this frame
            new HomePage().setVisible(true);
            dispose();
        });
        JButton nextButton = new JButton("Next");
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> {
            // Open HotelSearch page (ensure HotelSearch.java exists in your package)
            new HotelSearch().setVisible(true);
            dispose();
        });
        navPanel.add(backButton);
        navPanel.add(nextButton);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(navPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createDestinationCard(String[] destination) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        cardPanel.setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(destination[1]);
        Image img = icon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel nameLabel = new JLabel(destination[0], JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel ratingLabel = new JLabel(destination[2], JLabel.CENTER);
        JLabel categoryLabel = new JLabel(destination[3], JLabel.CENTER);
        JLabel priceLabel = new JLabel(destination[4].isEmpty() ? "Free Entry" : "Entry: " + destination[4], JLabel.CENTER);
        priceLabel.setForeground(Color.RED);

        JButton bookButton = new JButton("Book Now");
        bookButton.setPreferredSize(new Dimension(100, 30));
        bookButton.addActionListener(e -> new BookingPage(destination[0]));

        JPanel textPanel = new JPanel(new GridLayout(4, 1));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(nameLabel);
        textPanel.add(ratingLabel);
        textPanel.add(categoryLabel);
        textPanel.add(priceLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(bookButton);

        cardPanel.add(imageLabel, BorderLayout.NORTH);
        cardPanel.add(textPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        return cardPanel;
    }

    private static class HomePage {

        public HomePage() {
        }
    }

    // BookingPage class to handle booking functionality
    class BookingPage extends JFrame {
        public BookingPage(String destination) {
            setTitle("Book Your Trip to " + destination);
            setSize(600, 450);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            getContentPane().setBackground(Color.WHITE);
            setLayout(new BorderLayout());

            // Title Panel with Gradient Background
            JPanel titlePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, getWidth(), 0, new Color(135, 206, 250));
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            titlePanel.setLayout(new BorderLayout());
            titlePanel.setPreferredSize(new Dimension(600, 50));
            titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel titleLabel = new JLabel("Book Your Trip", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titlePanel.add(titleLabel, BorderLayout.CENTER);

            // Form Panel using absolute positioning for the form fields
            JPanel formPanel = new JPanel();
            formPanel.setLayout(null);
            formPanel.setBackground(Color.WHITE);
            formPanel.setPreferredSize(new Dimension(600, 300));

            // Destination
            JLabel destinationLabel = new JLabel("Destination:");
            destinationLabel.setBounds(20, 20, 120, 25);
            formPanel.add(destinationLabel);

            JTextField destinationField = new JTextField(destination);
            destinationField.setBounds(160, 20, 250, 25);
            destinationField.setEditable(false);
            formPanel.add(destinationField);

            // Full Name
            JLabel nameLabel = new JLabel("Full Name:");
            nameLabel.setBounds(20, 60, 120, 25);
            formPanel.add(nameLabel);

            JTextField nameField = new JTextField();
            nameField.setBounds(160, 60, 250, 25);
            formPanel.add(nameField);

            // Date
            JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
            dateLabel.setBounds(20, 100, 120, 25);
            formPanel.add(dateLabel);

            JTextField dateField = new JTextField();
            dateField.setBounds(160, 100, 250, 25);
            formPanel.add(dateField);

            // Contact Number
            JLabel contactLabel = new JLabel("Contact Number:");
            contactLabel.setBounds(20, 140, 120, 25);
            formPanel.add(contactLabel);

            JTextField contactField = new JTextField();
            contactField.setBounds(160, 140, 250, 25);
            formPanel.add(contactField);

            // Confirm Button
            JButton confirmButton = new JButton("Confirm Booking");
            confirmButton.setBounds(180, 200, 200, 30);
            confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
            confirmButton.addActionListener(e -> {
                String name = nameField.getText().trim();
                String date = dateField.getText().trim();
                String contact = contactField.getText().trim();

                if (name.isEmpty() || date.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(BookingPage.this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(BookingPage.this,
                            "Booking Confirmed!\nName: " + name +
                            "\nDestination: " + destination +
                            "\nDate: " + date +
                            "\nContact: " + contact,
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the booking window after confirmation
                }
            });
            formPanel.add(confirmButton);

            // Navigation Panel for Back and Next buttons (if needed on BookingPage)
            JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            JButton backButton = new JButton("Back");
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.addActionListener(e -> dispose());
            
            JButton nextButton = new JButton("Next");
            nextButton.setBackground(Color.BLACK);
            nextButton.setForeground(Color.WHITE);
            // Add further action for nextButton here if needed
            
            navPanel.add(backButton);
            navPanel.add(nextButton);

            // Add panels to the frame
            add(titlePanel, BorderLayout.NORTH);
            add(formPanel, BorderLayout.CENTER);
            add(navPanel, BorderLayout.SOUTH);

            revalidate();
            repaint();
            setVisible(true);
        }
    }

    public static void main(String[] args) {
        new DestinationsUI();
    }
} 