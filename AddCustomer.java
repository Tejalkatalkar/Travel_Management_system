package travel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCustomer extends JFrame {
    JLabel username, idLabel, numberLabel, nameLabel, genderLbl, countryLbl, addressLbl, phoneLbl, emailLbl, imageLabel;
    JTextField usernameField, numberField, nameField, countryField, addressField, phoneField, emailField;
    JComboBox<String> idBox;
    JRadioButton lblMale, lblFemale;
    ButtonGroup genderGroup;
    JButton nextButton, backButton;
    ImageIcon imageIcon;

    public AddCustomer() {
        setTitle("Add Customer - Travel Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Custom Background Panel with Gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 235), 0, getHeight(), Color.WHITE);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setBounds(0, 0, 900, 600);
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

        // Heading Label
        JLabel paymentDetailsLabel = new JLabel("Enter Customer Details:");
        paymentDetailsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        paymentDetailsLabel.setBounds(50, 20, 250, 30);
        backgroundPanel.add(paymentDetailsLabel);

        // Username
        username = new JLabel("Username:");
        username.setBounds(50, 60, 100, 30);
        backgroundPanel.add(username);

        usernameField = new JTextField();
        usernameField.setBounds(150, 60, 200, 30);
        backgroundPanel.add(usernameField);

        // ID Type
        idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 100, 100, 30);
        backgroundPanel.add(idLabel);

        idBox = new JComboBox<>(new String[]{"Passport", "Aadhaar Card", "Pan Card", "Ration Card"});
        idBox.setBounds(150, 100, 200, 30);
        backgroundPanel.add(idBox);

        // ID Number
        numberLabel = new JLabel("Number:");
        numberLabel.setBounds(50, 150, 100, 30);
        backgroundPanel.add(numberLabel);

        numberField = new JTextField();
        numberField.setBounds(150, 150, 200, 30);
        backgroundPanel.add(numberField);

        // Name
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 200, 100, 30);
        backgroundPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 200, 200, 30);
        backgroundPanel.add(nameField);

        // Gender
        genderLbl = new JLabel("Gender:");
        genderLbl.setBounds(50, 250, 100, 30);
        backgroundPanel.add(genderLbl);

        lblMale = new JRadioButton("Male");
        lblMale.setBounds(150, 250, 70, 30);
        lblMale.setBackground(Color.WHITE);
        backgroundPanel.add(lblMale);

        lblFemale = new JRadioButton("Female");
        lblFemale.setBounds(230, 250, 100, 30);
        lblFemale.setBackground(Color.WHITE);
        backgroundPanel.add(lblFemale);

        genderGroup = new ButtonGroup();
        genderGroup.add(lblMale);
        genderGroup.add(lblFemale);

        // Country
        countryLbl = new JLabel("Country:");
        countryLbl.setBounds(50, 300, 100, 30);
        backgroundPanel.add(countryLbl);

        countryField = new JTextField();
        countryField.setBounds(150, 300, 200, 30);
        backgroundPanel.add(countryField);

        // Address
        addressLbl = new JLabel("Address:");
        addressLbl.setBounds(50, 350, 100, 30);
        backgroundPanel.add(addressLbl);

        addressField = new JTextField();
        addressField.setBounds(150, 350, 200, 30);
        backgroundPanel.add(addressField);

        // Phone
        phoneLbl = new JLabel("Phone No:");
        phoneLbl.setBounds(50, 400, 100, 30);
        backgroundPanel.add(phoneLbl);

        phoneField = new JTextField();
        phoneField.setBounds(150, 400, 200, 30);
        backgroundPanel.add(phoneField);

        // Email
        emailLbl = new JLabel("Email ID:");
        emailLbl.setBounds(50, 450, 100, 30);
        backgroundPanel.add(emailLbl);

        emailField = new JTextField();
        emailField.setBounds(150, 450, 200, 30);
        backgroundPanel.add(emailField);

        // Buttons
        nextButton = new JButton("Next");
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBounds(200, 500, 100, 30);
        backgroundPanel.add(nextButton);

        backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(70, 500, 100, 30);
        backgroundPanel.add(backButton);
        
        

        // Image
        try {
            imageIcon = new ImageIcon("C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\Travel_Management_System\\src\\business-travel.png");
            Image img = imageIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(img);
            imageLabel = new JLabel(imageIcon);
            imageLabel.setBounds(500, 97, 300, 300);
            backgroundPanel.add(imageLabel);
        } catch (Exception ex) {
            System.out.println("Image not found: " + ex.getMessage());
        }

        // Action Listeners
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomerToDatabase();
                new UpdateCustomer().setVisible(true);
                dispose();
            }
        });

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void addCustomerToDatabase() {
        try {
            Conn_page connPage = new Conn_page();  // Use the Conn_page class
            try (Connection conn = connPage.getConnection()) {
                String query = "INSERT INTO customers (username, id_type, id_number, name, gender, country, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, usernameField.getText());
                stmt.setString(2, idBox.getSelectedItem().toString());
                stmt.setString(3, numberField.getText());
                stmt.setString(4, nameField.getText());
                stmt.setString(5, lblMale.isSelected() ? "Male" : "Female");
                stmt.setString(6, countryField.getText());
                stmt.setString(7, addressField.getText());
                stmt.setString(8, phoneField.getText());
                stmt.setString(9, emailField.getText());
                
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Customer Added Successfully!");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddCustomer::new);
    }
}
