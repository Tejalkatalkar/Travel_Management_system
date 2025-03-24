package travel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Payment extends JFrame implements ActionListener {

    private JButton payButton, backButton;
    private JTextField nameField, cardNumberField, expiryField, cvvField, amountField;
    private JComboBox<String> paymentMethod;
    private JLabel imageLabel;
    private ImageIcon imageIcon;

    public Payment() {
        setTitle("Payment");
        setBounds(350, 150, 800, 600);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBounds(0, 0, 800, 600);
        backgroundPanel.setLayout(null);
        backgroundPanel.setBackground(Color.WHITE);
        add(backgroundPanel);

        // Adding Image
        imageIcon = new ImageIcon("C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\Travel_Management_System\\src\\payment.png");
        Image img = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(img);

        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(450, 100, 300, 300);
        backgroundPanel.add(imageLabel);

        // Payment Details Label
        JLabel paymentDetailsLabel = new JLabel("Enter Payment Details:");
        paymentDetailsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        paymentDetailsLabel.setBounds(50, 30, 250, 30);
        backgroundPanel.add(paymentDetailsLabel);

        // Name on Card
        JLabel nameLabel = new JLabel("Name on Card:");
        nameLabel.setBounds(50, 80, 200, 25);
        backgroundPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(50, 105, 300, 30);
        backgroundPanel.add(nameField);

        // Card Number
        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setBounds(50, 140, 200, 25);
        backgroundPanel.add(cardLabel);

        cardNumberField = new JTextField();
        cardNumberField.setBounds(50, 165, 300, 30);
        backgroundPanel.add(cardNumberField);

        // Expiry Date
        JLabel expiryLabel = new JLabel("Expiry Date (MM/YY):");
        expiryLabel.setBounds(50, 200, 200, 25);
        backgroundPanel.add(expiryLabel);

        expiryField = new JTextField();
        expiryField.setBounds(50, 225, 140, 30);
        backgroundPanel.add(expiryField);

        // CVV
        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(220, 200, 100, 25);
        backgroundPanel.add(cvvLabel);

        cvvField = new JTextField();
        cvvField.setBounds(220, 225, 130, 30);
        backgroundPanel.add(cvvField);

        // Amount Field
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(50, 260, 200, 25);
        backgroundPanel.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(50, 285, 300, 30);
        backgroundPanel.add(amountField);

        // Payment Method Selection
        JLabel methodLabel = new JLabel("Payment Method:");
        methodLabel.setBounds(50, 320, 200, 25);
        backgroundPanel.add(methodLabel);

        String[] methods = {"Credit Card", "Debit Card", "UPI", "Net Banking"};
        paymentMethod = new JComboBox<>(methods);
        paymentMethod.setBounds(50, 345, 300, 30);
        backgroundPanel.add(paymentMethod);

        // Pay Button
        payButton = new JButton("Pay");
        payButton.setBounds(250, 450, 120, 40);
        payButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        payButton.setBackground(Color.BLUE);
        payButton.setForeground(Color.WHITE);
        payButton.addActionListener(this);
        backgroundPanel.add(payButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(400, 450, 120, 40);
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        backgroundPanel.add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == payButton) {
            String name = nameField.getText();
            String cardNumber = cardNumberField.getText();
            String expiry = expiryField.getText();
            String cvv = cvvField.getText();
            String method = (String) paymentMethod.getSelectedItem();
            String amount = amountField.getText();

            if (name.isEmpty() || cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty() || amount.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all payment details!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                savePaymentToDatabase(name, cardNumber, expiry, cvv, method, amount);
            }
        } else if (ae.getSource() == backButton) {
            this.setVisible(false);
        }
    }

    private void savePaymentToDatabase(String name, String cardNumber, String expiry, String cvv, String method, String amount) {
        String url = "jdbc:mysql://localhost:3306/travel_managementsystem";
        String user = "root";
        String password = "root";

        String query = "INSERT INTO Payments (name_on_card, card_number, expiry_date, cvv, payment_method, amount, payment_status, payment_date) VALUES (?, ?, ?, ?, ?, ?, 'Success', NOW())";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, cardNumber);
            pst.setString(3, expiry);
            pst.setString(4, cvv);
            pst.setString(5, method);
            pst.setDouble(6, Double.parseDouble(amount));

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Payment Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Payment::new);
    }
}
