import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class Pendulum extends JPanel {
    
    public static void main(String[] args) {
        JFrame window = new JFrame("Pendulum animation"); // create frame
        final Pendulum pendulum = new Pendulum();
        window.setContentPane(pendulum);  // add the pendulum frame to the content page
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close
        window.pack();
        window.setVisible(true);
        
        if (args.length > 0)
        {
            try {
            pendulum.theta = Double.parseDouble(args[0]);
            }
            catch (java.lang.NumberFormatException exception) {
                double t = 0.0, omega = 0.3;
                while (true) {
                    pendulum.theta = (Math.PI / 6) * Math.cos(omega * t);
                    pendulum.repaint();
                    t += 1.0;
                    
                    System.out.print("PRESS ENTER to continue");
                    
                    Scanner scanner = new Scanner(System.in);
                    scanner.nextLine();
                }
            }
        }
        else
        {
            final long startTime = System.currentTimeMillis();
            
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed (ActionEvent arg0) {
                    long t = System.currentTimeMillis() - startTime;
                    final double OMEGA = 2.0e-3;
                    pendulum.theta = (Math.PI / 6) * Math.cos(OMEGA * t);
                    pendulum.repaint();
                }
            };
            Timer animationTimer = new Timer(16, actionListener);
            animationTimer.start();
        }
    }
    
    private double theta;
    
    public Pendulum() {
        setPreferredSize(new Dimension(600, 600));
    }
    
    public void paintComponent(Graphics g) {
        final double LEFT = -0.7, RIGHT = 0.7, BOTTOM = -1.3, TOP = 0.1;
        final double SUPPORT_HEIGHT = 0.2, LENGTH = 1.0, WIDTH = 0.04, RADIUS = 0.15;
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        applyWorldToViewportTransform(g2, LEFT, RIGHT, BOTTOM, TOP);

        
        g2.setPaint(Color.GRAY);
        g2.fill(new Rectangle2D.Double(LEFT, TOP-SUPPORT_HEIGHT, RIGHT - LEFT, SUPPORT_HEIGHT));
        
        g2.rotate(theta);

        g2.setPaint(Color.YELLOW);
        g2.fill(new Rectangle2D.Double(-WIDTH / 2, -LENGTH, WIDTH, LENGTH+WIDTH / 2));
        
        
        g2.setPaint(Color.BLUE);
        g2.fill(new Ellipse2D.Double(- WIDTH / 4, -WIDTH / 4, WIDTH / 2, WIDTH / 2));
        
        g2.translate(0, -LENGTH);
        g2.setPaint(Color.RED);
        g2.fill(new Ellipse2D.Double(-RADIUS, -RADIUS, 2*RADIUS, 2*RADIUS));

        
    }
    
    public void applyWorldToViewportTransform(Graphics2D g2, double left, double right, double bottom, double top) {
        
        int viewportWidth = this.getWidth();
        int viewportHeight = this.getHeight();
        
        double worldWidth = right - left;
        double worldHeight = top - bottom;
        
        g2.scale(viewportWidth / worldWidth, - viewportHeight / worldHeight);
        g2.translate(-left, -top);
    }
    
}
