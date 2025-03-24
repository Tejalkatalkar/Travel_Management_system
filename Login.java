package travel_management_system;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private final JTextField usernameField, emailField;
    private final JPasswordField passField;
    private final JButton loginButton, signUpButton;
    private static final Border PANEL_BORDER = BorderFactory.createLineBorder(Color.BLACK, 2);

    public Login() {
        setSize(850, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Left Panel with Logo
        GradientPanel p1 = new GradientPanel();
        p1.setBounds(30, 30, 380, 350);
        p1.setLayout(null);
        p1.setBorder(PANEL_BORDER);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Image_Icon_logo.png"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(65, 50, 250, 250);
        p1.add(image);

        // Right Panel with Login Form
        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBounds(420, 30, 380, 350);
        p2.setBorder(PANEL_BORDER);
        add(p2);

        JLabel signInLabel = new JLabel("Login");
        signInLabel.setBounds(140, 20, 150, 30);
        signInLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 22));
        p2.add(signInLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(40, 60, 100, 20);
        usernameLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        p2.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(40, 85, 300, 30);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        p2.add(usernameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(40, 120, 100, 20);
        emailLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        p2.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(40, 145, 300, 30);
        emailField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        p2.add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(40, 180, 100, 20);
        passLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        p2.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(40, 205, 300, 30);
        passField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        p2.add(passField);

        JLabel forgotPass = new JLabel("Forgot Password?");
        forgotPass.setBounds(40, 240, 150, 20);
        forgotPass.setForeground(Color.BLUE);
        forgotPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p2.add(forgotPass);

        forgotPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ForgotPassword();
            }
        });

        loginButton = new JButton("Login");
        loginButton.setBounds(40, 270, 130, 35);
        loginButton.setBackground(new Color(50, 150, 250));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        loginButton.addActionListener(this);
        p2.add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(210, 270, 130, 35);
        signUpButton.setBackground(new Color(34, 167, 132));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        signUpButton.addActionListener(this);
        p2.add(signUpButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login Window");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            verifyLogin();
        } else if (e.getSource() == signUpButton) {
            setVisible(false);
            new Sign_Up_Page();
        }
    }

    private void verifyLogin() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = new Conn_page().getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM account WHERE username=? AND email=? AND password=?")) {
            
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    new Loading(username).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
