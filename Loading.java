package travel_management_system;

import javax.swing.*;
import java.awt.*;

public class Loading extends JFrame implements Runnable {
    JProgressBar pb;
    Thread t;
    String username;

    public void run() {
        try {
            for (int i = 1; i <= 100; i++) {
                final int progress = i;
                SwingUtilities.invokeLater(() -> pb.setValue(progress));
                Thread.sleep(50);
            }
            dispose();
            new Dashboard(username).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Loading(String user) {
        this.username = user;
        t = new Thread(this);

        setBounds(450, 200, 650, 400);
        setUndecorated(true);
        setLayout(null);

        GradientPanel panel = new GradientPanel();
        panel.setBounds(0, 0, 650, 400);
        panel.setLayout(null);
        add(panel);

        JLabel title = new JLabel("Travel Management System", SwingConstants.CENTER);
        title.setBounds(50, 20, 550, 40);
        title.setFont(new Font("Raleway", Font.BOLD, 32));
        title.setForeground(Color.BLUE);
        panel.add(title);

        // Load Image
        ImageIcon imgIcon = new ImageIcon(ClassLoader.getSystemResource("Daco.png"));
        Image img = imgIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setBounds(250, 80, 150, 150);
        panel.add(imgLabel);

        JLabel subtitle = new JLabel("Please wait while we load your experience...", SwingConstants.CENTER);
        subtitle.setBounds(50, 50, 550, 30);
        subtitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        subtitle.setForeground(Color.DARK_GRAY);
        panel.add(subtitle);

        pb = new JProgressBar();
        pb.setBounds(150, 250, 350, 25);
        pb.setForeground(Color.BLUE);
        pb.setBackground(new Color(255, 255, 255, 150));
        pb.setStringPainted(true);
        panel.add(pb);

        JLabel loadingText = new JLabel("Loading...", SwingConstants.CENTER);
        loadingText.setBounds(250, 280, 150, 25);
        loadingText.setFont(new Font("Tahoma", Font.BOLD, 16));
        loadingText.setForeground(Color.BLUE);
        panel.add(loadingText);

        JLabel welcome = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcome.setBounds(100, 330, 450, 25);
        welcome.setFont(new Font("Tahoma", Font.BOLD, 16));
        welcome.setForeground(Color.BLUE);
        panel.add(welcome);

        t.start();
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE,
                    getWidth(), getHeight(), new Color(173, 216, 230));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        new Loading("User").setVisible(true);
    }
}