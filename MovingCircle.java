// Circle moves back and forth and changes color when the direction changes
// Written by Kanchan Nannavare on January 12, 2017

import java.awt.*;        // original java graphics package.
import java.awt.geom.*;   // geometric objects such as ellipse and rectangle.
import java.awt.event.*;  // needed for animation.
import javax.swing.*;     // newer java graphics package.
import java.util.Scanner; // needed for keyboard input from the console.

public class MovingCircle extends JPanel {
    
    public static void main(String[] args) {
        JFrame window = new JFrame("Moving Circle");
        final MovingCircle circle = new MovingCircle();
        window.setContentPane(circle);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        
        final long startTime = System.currentTimeMillis();
        
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                long t = System.currentTimeMillis() - startTime;
                circle.x_value = 0.1*t;
                circle.repaint();
            }
        };
        
        Timer animationTimer = new Timer(16, actionListener);
        animationTimer.start();
    }
    
    private double x_value;

    public MovingCircle() {
        setPreferredSize(new Dimension(500, 50));
    }
    
    public void paintComponent(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setPaint(Color.RED);
        
        // Make x_value between 0 and 900
        if(x_value >= 900) {
            x_value = x_value%900;
        }
        
        // left to right
        if(x_value <= 450) {
            g2.translate(-x_value, 0);
            g2.setPaint(Color.RED);
        } else if (x_value <= 900)  {       // right to left
            g2.translate(x_value-900, 0);
            g2.setPaint(Color.YELLOW);
        }

        g2.fill(new Ellipse2D.Double(450, 0, 50, 50));

    }
}
