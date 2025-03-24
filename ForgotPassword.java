package travel_management_system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.InputStream;
import java.sql.*;
import javax.imageio.ImageIO;

public class ForgotPassword extends JFrame {

    private JTextField txtUsername, txtAnswer;
    private JPasswordField txtNewPass, txtConfirmPass;
    private JComboBox<String> comboSecurity;
    private JButton btnSubmit;

    public ForgotPassword() {
        setTitle("Forgot Password");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        GradientPanel leftPanel = new GradientPanel("/authorization.png");
        leftPanel.setBounds(30, 30, 400, 350);
        leftPanel.setLayout(null);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(leftPanel);

        JPanel formPanel = new JPanel();
        formPanel.setBounds(450, 30, 400, 350);
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        add(formPanel);

        addFormFields(formPanel);
        setVisible(true);
    }

    private void addFormFields(JPanel panel) {
        JLabel title = new JLabel("Reset Your Password", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 16));
        title.setBounds(50, 10, 300, 30);
        panel.add(title);

        addLabel(panel, "Username:", 20, 50);
        txtUsername = addTextField(panel, 160, 50);

        addLabel(panel, "Security Question:", 20, 90);
        String[] questions = {"Select a question...", "Your first pet's name?", "Your mother's maiden name?", "Your favorite book?"};
        comboSecurity = new JComboBox<>(questions);
        comboSecurity.setBounds(160, 90, 200, 25);
        panel.add(comboSecurity);

        addLabel(panel, "Answer:", 20, 130);
        txtAnswer = addTextField(panel, 160, 130);

        addLabel(panel, "New Password:", 20, 170);
        txtNewPass = addPasswordField(panel, 160, 170);

        addLabel(panel, "Confirm Password:", 20, 210);
        txtConfirmPass = addPasswordField(panel, 160, 210);

        btnSubmit = new JButton("Reset Password");
        btnSubmit.setBounds(125, 260, 150, 30);
        btnSubmit.setBackground(new Color(0, 123, 255));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSubmit.addActionListener(this::handlePasswordReset);
        panel.add(btnSubmit);
    }

    private void handlePasswordReset(ActionEvent e) {
        String username = txtUsername.getText().trim();
        String securityAns = txtAnswer.getText().trim();
        String newPass = new String(txtNewPass.getPassword());
        String confirmPass = new String(txtConfirmPass.getPassword());

        if (username.isEmpty() || comboSecurity.getSelectedIndex() == 0 || securityAns.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showMessage("Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!newPass.equals(confirmPass)) {
            showMessage("Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            resetPassword(username, securityAns, newPass);
        }
    }

    private void resetPassword(String username, String securityAns, String newPass) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_managementsystem", "root", "root")) {
            String query = "SELECT * FROM account WHERE username = ? AND answer = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, securityAns);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String updateQuery = "UPDATE account SET password = ? WHERE username = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setString(1, newPass);
                            updateStmt.setString(2, username);
                            updateStmt.executeUpdate();
                            showMessage("Password reset successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                    } else {
                        showMessage("Incorrect username or security answer!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            showMessage("Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(label);
    }

    private JTextField addTextField(JPanel panel, int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 200, 25);
        panel.add(textField);
        return textField;
    }

    private JPasswordField addPasswordField(JPanel panel, int x, int y) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(x, y, 200, 25);
        panel.add(passwordField);
        return passwordField;
    }

    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    class GradientPanel extends JPanel {
        private Image img;

        public GradientPanel(String imgPath) {
            try {
                InputStream imageStream = getClass().getResourceAsStream(imgPath);
                if (imageStream != null) {
                    img = ImageIO.read(imageStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 105, 180), getWidth(), getHeight(), new Color(138, 43, 226), true);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            if (img != null) {
                g.drawImage(img, (getWidth() - img.getWidth(this)) / 2, (getHeight() - img.getHeight(this)) / 2, this);
            }
        }
    }

    public static void main(String[] args) {
        new ForgotPassword();
    }
}
