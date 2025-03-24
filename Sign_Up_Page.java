package travel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Sign_Up_Page extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton nextButton, backButton;
    private JTextField usernameField, nameField, answerField;
    private JPasswordField passField;
    private JComboBox<String> securityQuestion;

    public Sign_Up_Page() {
        setTitle("Sign-Up Page");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Left Panel (Gradient Background)
        GradientPanel leftPanel = new GradientPanel();
        leftPanel.setBounds(30, 30, 380, 370);
        leftPanel.setLayout(null);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(leftPanel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Logo.png"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(65, 50, 250, 250);
        leftPanel.add(image);

        JLabel welcomeLabel = new JLabel("Welcome to Travel Management System");
        welcomeLabel.setBounds(20, 310, 340, 20);
        welcomeLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        leftPanel.add(welcomeLabel);

        // Right Panel (Plain Background)
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(420, 30, 380, 370);
        rightPanel.setLayout(null);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        add(rightPanel);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setBounds(140, 10, 200, 30);
        titleLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        rightPanel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 40, 120, 20);
        rightPanel.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setBounds(20, 60, 320, 25);
        rightPanel.add(usernameField);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(20, 90, 120, 20);
        rightPanel.add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(20, 110, 320, 25);
        rightPanel.add(nameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 140, 120, 20);
        rightPanel.add(passLabel);
        passField = new JPasswordField();
        passField.setBounds(20, 160, 320, 25);
        rightPanel.add(passField);

        JLabel secQueLabel = new JLabel("Security Question:");
        secQueLabel.setBounds(20, 190, 160, 20);
        rightPanel.add(secQueLabel);
        String[] questions = {"Your first pet's name?", "Your mother's maiden name?", "Your birth city?"};
        securityQuestion = new JComboBox<>(questions);
        securityQuestion.setBounds(20, 210, 320, 25);
        rightPanel.add(securityQuestion);

        JLabel ansLabel = new JLabel("Answer:");
        ansLabel.setBounds(20, 240, 120, 20);
        rightPanel.add(ansLabel);
        answerField = new JTextField();
        answerField.setBounds(20, 260, 320, 25);
        rightPanel.add(answerField);

        // Buttons
        backButton = new JButton("Back");
        backButton.setBounds(50, 300, 120, 35);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        rightPanel.add(backButton);

        nextButton = new JButton("Next");
        nextButton.setBounds(200, 300, 120, 35);
        nextButton.setBackground(new Color(50, 150, 250));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);
        rightPanel.add(nextButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == nextButton) {
            registerUser();
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Login();
        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String fullName = nameField.getText();
        String password = new String(passField.getPassword());
        String secQuestion = (String) securityQuestion.getSelectedItem();
        String answer = answerField.getText();

        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty() || answer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_managementsystem", "root", "root");
            String query = "INSERT INTO account (username, name, password, security_question, answer) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, fullName);
            pstmt.setString(3, password);
            pstmt.setString(4, secQuestion);
            pstmt.setString(5, answer);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                setVisible(false);
                new Login();
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sign_Up_Page::new);
    }
}

// Gradient Panel Class for Left Panel
class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(255, 105, 180),
            getWidth(), getHeight(), new Color(138, 43, 226),
            true
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
