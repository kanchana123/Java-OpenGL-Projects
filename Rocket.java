// Rocket Animation
// Written by Kanchan Nannavare on January 10, 2017
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Rocket extends JPanel {
    public static void main(String[] args) {
        JFrame window = new JFrame("Rocket");
        final Rocket rocket = new Rocket();
        window.setContentPane(rocket);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        
        final long startTime = System.currentTimeMillis();
        
        ActionListener ActionListener = new ActionListener() {
            public void actionPerformed (ActionEvent arg0) {
                long t = System.currentTimeMillis() - startTime;
                rocket.y_value = -0.1 * t;
                rocket.repaint();
            }
        };
        Timer animationTimer = new Timer(16, ActionListener);
        animationTimer.start();
    }
    
    private double y_value;
    
    public Rocket() {
        setPreferredSize(new Dimension(700, 500));
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        // Ground
        g2.setPaint(new Color(0, 150, 0));
        QuadCurve2D q = new QuadCurve2D.Float();
        q.setCurve(0, 350, 350, 300, 700, 350);
        g2.fill(q);
        g2.fillRect(0, 350, 700, 150);
        
        // Moon
        g2.setPaint(Color.WHITE);
        g2.fill(new Ellipse2D.Double(400, 50, 150, 150));
        
        // Stars
        int star_size = 5;
        g2.fill(new Ellipse2D.Double(80, 250, star_size, star_size));
        g2.fill(new Ellipse2D.Double(150, 50, star_size, star_size));
        g2.fill(new Ellipse2D.Double(275, 70, star_size, star_size));
        g2.fill(new Ellipse2D.Double(600, 50, star_size, star_size));
        star_size = 7;
        g2.fill(new Ellipse2D.Double(350, 260, star_size, star_size));
        g2.fill(new Ellipse2D.Double(650, 200, star_size, star_size));
        g2.fill(new Ellipse2D.Double(180, 260, star_size, star_size));
        g2.fill(new Ellipse2D.Double(500, 280, star_size, star_size));
        star_size = 9;
        g2.fill(new Ellipse2D.Double(170, 160, star_size, star_size));
        g2.fill(new Ellipse2D.Double(300, 180, star_size, star_size));
        g2.fill(new Ellipse2D.Double(380, 30, star_size, star_size));
        g2.fill(new Ellipse2D.Double(50, 100, star_size, star_size));

        // Rocket
        g2.translate(50, y_value);
        g2.fillRect(50, 320, 30, 70);
        
        Path2D triangle = new Path2D.Double();
        triangle.moveTo(50, 320);
        triangle.lineTo(65, 290);
        triangle.lineTo(80, 320);
        triangle.closePath();
        
        g2.setPaint(Color.RED);
        g2.fill(triangle);
        
        Path2D rocket_side1 = new Path2D.Double();
        rocket_side1.moveTo(80, 360);
        rocket_side1.lineTo(90, 370);
        rocket_side1.lineTo(90, 400);
        rocket_side1.lineTo(80, 390);
        rocket_side1.closePath();
        g2.fill(rocket_side1);
        
        Path2D rocket_side2 = new Path2D.Double();
        rocket_side2.moveTo(50, 360);
        rocket_side2.lineTo(40, 370);
        rocket_side2.lineTo(40, 400);
        rocket_side2.lineTo(50, 390);
        rocket_side2.closePath();
        g2.fill(rocket_side2);
        
        g2.fillRect(50, 390, 30, 5);
        
        Path2D fire = new Path2D.Double();
        fire.moveTo(50, 395);
        fire.quadTo(35, 435, 57, 410);
        fire.quadTo(65, 450, 72, 410);
        fire.quadTo(95, 435, 80, 395);
        fire.closePath();
        
        Color orange = new Color(255, 100, 0);
        Color yellow = new Color(255, 255, 0);

        GradientPaint orangetoyellow = new GradientPaint(65, 395, orange, 65, 450, yellow);
        g2.setPaint(orangetoyellow);
        g2.fill(fire);
    }
}
