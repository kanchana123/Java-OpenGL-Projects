// This program creates a daisy using OpenGL
// Written by Kanchan Nannavare on January 15, 2017


import java.awt.*;        // original java graphics package.
import java.awt.geom.*;   // geometric objects such as ellipse and rectangle.
import java.awt.event.*;  // needed for animation.
import javax.swing.*;     // newer java graphics package.
import java.util.Scanner; // needed for keyboard input from the console.

public class Daisy extends JPanel {

    public static void main(String[] args) {
        JFrame window = new JFrame("Daisy");
        window.setContentPane(new Daisy());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }

    public Daisy() {
        setPreferredSize(new Dimension(600, 500));
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setPaint(Color.GREEN);                     // green background
        g2.fillRect(0, 0, getWidth(), getHeight());
    
        final double R = 50;                        // radius of inner circle
    
        final double RR = 300;                      // radius for petals
        double theta = Math.PI/50;                  // angle for petals
        Path2D petals = new Path2D.Double();
    
        final double center_x = 300;
        final double center_y = 250;

        petals.moveTo(center_x + R * Math.cos(0), center_y + R*Math.sin(0));
    
        // angles for petals
        double r1 = 2;
        double r2 = 5;
        for(int i = 0; i < 50; i++) {
            petals.quadTo(center_x + RR*Math.cos(r1 * theta), center_y + RR*Math.sin(r1 * theta), center_x + R *Math.cos(r2*theta), center_y + R*Math.sin(r2*theta));
            r1 += 5;
            r2 += 5;
        }
    
        petals.closePath();
    
        g2.setPaint(Color.WHITE);
        g2.fill(petals);
    
        g2.setPaint(Color.YELLOW);
        g2.fill(new Ellipse2D.Double(250, 200, 2 * R, 2 * R));
    }
}
