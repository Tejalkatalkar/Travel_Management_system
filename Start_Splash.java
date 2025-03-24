package travel_management_system;

import javax.swing.*;
import java.awt.*;

public class Start_Splash extends JFrame implements Runnable {
    Thread thread;
    JLabel imageLabel;
    Image originalImage;
    int screenWidth, screenHeight;

    public Start_Splash() {
        // Get the screen size dynamically
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        // Set full-screen size
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Time_to_travel.png"));
        originalImage = i1.getImage();

        // Create JLabel with the full-screen image
        imageLabel = new JLabel(new ImageIcon(originalImage));
        imageLabel.setBounds(0, 0, screenWidth, screenHeight);
        add(imageLabel);

        setVisible(true);

        // Start animation thread
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        animateZoomOut();
        try {
            Thread.sleep(2000); // Display splash for 2 seconds after animation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispose(); // Close splash screen
        openLoginWindow(); // Open login page
    }

    public void animateZoomOut() {
        // Start with a zoomed-in image
        double scaleFactor = 1.5; // 150% of original size

        for (double scale = scaleFactor; scale > 1.0; scale -= 0.01) {
            int newWidth = (int) (screenWidth * scale);
            int newHeight = (int) (screenHeight * scale);
            int x = (screenWidth - newWidth) / 2;
            int y = (screenHeight - newHeight) / 2;

            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(resizedImage));
            imageLabel.setBounds(x, y, newWidth, newHeight);

            try {
                Thread.sleep(10); // Adjust speed (lower value = faster animation)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void openLoginWindow() {
        new Login(); // Open login page
    }

    public static void main(String[] args) {
        new Start_Splash();
    }
}
