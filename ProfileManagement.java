package travel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileManagement extends JFrame {
    JLabel imageLabel, username, idLabel, numberLabel, nameLabel, genderLbl, countryLbl, addressLbl, phoneLbl, emailLbl;
    JTextField usernameField, numberField, nameField, countryField, addressField, phoneField, emailField;
    JComboBox<String> idBox;
    JRadioButton lblMale, lblFemale;
    ButtonGroup genderGroup;
    JButton back, next;
    ImageIcon imageIcon;

    public ProfileManagement() {
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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
        backgroundPanel.setLayout(null);
        add(backgroundPanel, BorderLayout.CENTER);

        imageIcon = new ImageIcon("C:\\Users\\TEJAL KATALKAR\\Documents\\NetBeansProjects\\Travel_Management_System\\src\\normal.png");
        Image img = imageIcon.getImage().getScaledInstance(900, 250, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(img);

        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, 900, 250);
        backgroundPanel.add(imageLabel);

        int leftX = 50, rightX = 400, startY = 270;
        int width = 200, height = 30, gap = 40;
       
        //  Details Label
        JLabel paymentDetailsLabel = new JLabel("view Details:");
        paymentDetailsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        paymentDetailsLabel.setBounds(50, 30, 250, 30);
        backgroundPanel.add(paymentDetailsLabel);

        username = new JLabel("Username:");
        username.setBounds(leftX, startY, 100, height);
        backgroundPanel.add(username);

        usernameField = new JTextField();
        usernameField.setBounds(leftX + 110, startY, width, height);
        backgroundPanel.add(usernameField);

        idLabel = new JLabel("ID:");
        idLabel.setBounds(leftX, startY + gap, 100, height);
        backgroundPanel.add(idLabel);

        idBox = new JComboBox<>(new String[]{"Passport", "Aadhaar Card", "Pan Card", "Ration Card"});
        idBox.setBounds(leftX + 110, startY + gap, width, height);
        backgroundPanel.add(idBox);

        numberLabel = new JLabel("Number:");
        numberLabel.setBounds(leftX, startY + 2 * gap, 100, height);
        backgroundPanel.add(numberLabel);

        numberField = new JTextField();
        numberField.setBounds(leftX + 110, startY + 2 * gap, width, height);
        backgroundPanel.add(numberField);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(leftX, startY + 3 * gap, 100, height);
        backgroundPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(leftX + 110, startY + 3 * gap, width, height);
        backgroundPanel.add(nameField);

        genderLbl = new JLabel("Gender:");
        genderLbl.setBounds(leftX, startY + 4 * gap, 100, height);
        backgroundPanel.add(genderLbl);

        lblMale = new JRadioButton("Male");
        lblMale.setBounds(leftX + 110, startY + 4 * gap, 70, height);
        lblMale.setOpaque(false);
        backgroundPanel.add(lblMale);

        lblFemale = new JRadioButton("Female");
        lblFemale.setBounds(leftX + 190, startY + 4 * gap, 100, height);
        lblFemale.setOpaque(false);
        backgroundPanel.add(lblFemale);

        genderGroup = new ButtonGroup();
        genderGroup.add(lblMale);
        genderGroup.add(lblFemale);

        countryLbl = new JLabel("Country:");
        countryLbl.setBounds(rightX, startY, 100, height);
        backgroundPanel.add(countryLbl);

        countryField = new JTextField();
        countryField.setBounds(rightX + 110, startY, width, height);
        backgroundPanel.add(countryField);

        addressLbl = new JLabel("Address:");
        addressLbl.setBounds(rightX, startY + gap, 100, height);
        backgroundPanel.add(addressLbl);

        addressField = new JTextField();
        addressField.setBounds(rightX + 110, startY + gap, width, height);
        backgroundPanel.add(addressField);

        phoneLbl = new JLabel("Phone No:");
        phoneLbl.setBounds(rightX, startY + 2 * gap, 100, height);
        backgroundPanel.add(phoneLbl);

        phoneField = new JTextField();
        phoneField.setBounds(rightX + 110, startY + 2 * gap, width, height);
        backgroundPanel.add(phoneField);

        emailLbl = new JLabel("Email ID:");
        emailLbl.setBounds(rightX, startY + 3 * gap, 100, height);
        backgroundPanel.add(emailLbl);

        emailField = new JTextField();
        emailField.setBounds(rightX + 110, startY + 3 * gap, width, height);
        backgroundPanel.add(emailField);

        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(330, startY + 5 * gap, 100, 40);
        backgroundPanel.add(back);

        next = new JButton("Next");
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.setBounds(460, startY + 5 * gap, 100, 40);
        backgroundPanel.add(next);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCustomer();
                dispose();
            }
        });


        setVisible(true);
    }

    public ProfileManagement(String username) {
        this();
        usernameField.setText(username);
    }

    public static void main(String[] args) {
        new ProfileManagement();
    }
}
