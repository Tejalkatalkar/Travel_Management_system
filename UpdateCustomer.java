package travel_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCustomer extends JFrame {
    JLabel usernameLbl, idLabel, numberLabel, nameLabel, genderLbl, countryLbl, addressLbl, phoneLbl, emailLbl;
    JTextField usernameField, numberField, nameField, countryField, addressField, phoneField, emailField;
    JComboBox<String> idBox;
    JRadioButton lblMale, lblFemale;
    ButtonGroup genderGroup;
    JButton updateBtn, backBtn, skipBtn;
    private final Component Component;

    public UpdateCustomer() {
        setTitle("Update Customer - Travel Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

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

        JLabel titleLabel = new JLabel("Update Customer Details:");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setBounds(50, 20, 250, 30);
        backgroundPanel.add(titleLabel);

        usernameLbl = new JLabel("Username:");
        usernameLbl.setBounds(50, 60, 100, 30);
        backgroundPanel.add(usernameLbl);

        usernameField = new JTextField();
        usernameField.setBounds(150, 60, 200, 30);
        backgroundPanel.add(usernameField);

        idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 100, 100, 30);
        backgroundPanel.add(idLabel);

        idBox = new JComboBox<>(new String[]{"Passport", "Aadhaar Card", "Pan Card", "Ration Card"});
        idBox.setBounds(150, 100, 200, 30);
        backgroundPanel.add(idBox);

        numberLabel = new JLabel("Number:");
        numberLabel.setBounds(50, 150, 100, 30);
        backgroundPanel.add(numberLabel);

        numberField = new JTextField();
        numberField.setBounds(150, 150, 200, 30);
        backgroundPanel.add(numberField);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 200, 100, 30);
        backgroundPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 200, 200, 30);
        backgroundPanel.add(nameField);

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

        countryLbl = new JLabel("Country:");
        countryLbl.setBounds(50, 300, 100, 30);
        backgroundPanel.add(countryLbl);

        countryField = new JTextField();
        countryField.setBounds(150, 300, 200, 30);
        backgroundPanel.add(countryField);

        addressLbl = new JLabel("Address:");
        addressLbl.setBounds(50, 350, 100, 30);
        backgroundPanel.add(addressLbl);

        addressField = new JTextField();
        addressField.setBounds(150, 350, 200, 30);
        backgroundPanel.add(addressField);

        phoneLbl = new JLabel("Phone No:");
        phoneLbl.setBounds(50, 400, 100, 30);
        backgroundPanel.add(phoneLbl);

        phoneField = new JTextField();
        phoneField.setBounds(150, 400, 200, 30);
        Component add = backgroundPanel.add(phoneField);

        emailLbl = new JLabel("Email ID:");
        emailLbl.setBounds(50, 450, 100, 30);
        Component /*add1*/ = backgroundPanel.add(emailLbl);

        emailField = new JTextField();
        emailField.setBounds(150, 450, 200, 30);
        backgroundPanel.add(emailField);

        backBtn = new JButton("Back");
        backBtn.setBounds(70, 500, 100, 30);
        backgroundPanel.add(backBtn);

        skipBtn = new JButton("Skip");
        skipBtn.setBounds(185, 500, 100, 30);
        backgroundPanel.add(skipBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(300, 500, 100, 30);
        backgroundPanel.add(updateBtn);
        

        updateBtn.addActionListener(e -> updateCustomer());

        backBtn.addActionListener(e -> {
            new AddCustomer().setVisible(true);
            dispose();
        });

        skipBtn.addActionListener(e -> {
            String username = usernameField.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username is required to proceed!");
            } else {
                new ProfileManagement(username).setVisible(true);
                dispose();
            }
        });
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\Travel_Management_System\\src\\business-travel.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(500, 97, 300, 300);
        backgroundPanel.add(imageLabel);

        setVisible(true);
    }

    private void updateCustomer() {
        String username = usernameField.getText();
        if (username.isEmpty() || numberField.getText().isEmpty() || nameField.getText().isEmpty() ||
            countryField.getText().isEmpty() || addressField.getText().isEmpty() || phoneField.getText().isEmpty() ||
            emailField.getText().isEmpty() || genderGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "All fields are required!");
            return;
        }

        try {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_db", "root", "root")) {
                String query = "UPDATE customers SET id_type=?, id_number=?, name=?, gender=?, country=?, address=?, phone=?, email=? WHERE username=?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, idBox.getSelectedItem().toString());
                stmt.setString(2, numberField.getText());
                stmt.setString(3, nameField.getText());
                stmt.setString(4, lblMale.isSelected() ? "Male" : "Female");
                stmt.setString(5, countryField.getText());
                stmt.setString(6, addressField.getText());
                stmt.setString(7, phoneField.getText());
                stmt.setString(8, emailField.getText());
                stmt.setString(9, username);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Customer Updated Successfully!");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdateCustomer::new);
    }
}
