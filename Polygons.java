// Written by Kanchan Nannavare on January 18, 2017

// This program creates a polygon of a size that user passes through arguement. Default size is 3. Polygon have different colors at the vertices. When the size is large, it looks like a circle with RGB colors that vary smoothly

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;

public class Polygons extends JPanel implements GLEventListener {
    
    public static void main(String[] args) {
        JFrame window = new JFrame("Polygons");
        Polygons polygons = new Polygons();
        window.setContentPane(polygons);
        window.pack();
        window.setLocation(50, 50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        
        if (args.length > 0)
        {
            polygons.side = Integer.parseInt(args[0]);  // set size
        } else
        {
            polygons.side = 3;  // default size
        }
    }
    
    private int side;
    
    public Polygons() {
        GLJPanel gljPanel = new GLJPanel(new GLCapabilities(null));
        gljPanel.setPreferredSize(new Dimension(400,400));
        gljPanel.addGLEventListener(this);
        add(gljPanel);
    }
    
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        double angle = 2 * Math.PI / side;      // angle of each side from the center
        
        double start = Math.PI + (Math.PI / 2) - (angle/2);    // makes side at the bottom of the polygon, parallel to the bottom of the viewport.
        
        double red, blue, green, center;
        double x, y;
        
        int r = side/3;     // Number of sides that have similar RGB color
        
        red = 0.0;
        blue = 0.0;
        green = 1.0;
        center = 1.0;
        
        double rr = 2*green/r;
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.05, 1.05, -1.05, 1.05, -1.0, 1.0);
        
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3d(center, center, center);       // polygon has joined triangles. so that the color in the center can be made white.
        gl.glVertex2d(0.0, 0.0);
        
        // Green
        for(int i = 0; i < r; i++) {
            x = Math.cos(start);
            y = Math.sin(start);
            gl.glColor3d(red, green, blue);
            gl.glVertex2d(x, y);
            if(i != 0) {
                gl.glEnd();
                gl.glBegin(GL2.GL_POLYGON);
                gl.glColor3d(center, center, center);
                gl.glVertex2d(0.0, 0.0);
                gl.glColor3d(red, green, blue);
                gl.glVertex2d(x, y);
            }
            
            if( i <= (r/2)) {
                red = red + rr;     // more red for the next vertex
            } else {
                green = green - rr;     // less green for the next vertex
            }
            start += angle;
        }
        
        // red
        red = 1.0;
        green = 0.0;
        
        if(side % 3 == 2) {
            r += 1;
        }
        
        rr = 2*red/r;
        
        for(int i = 0; i < r; i++) {
            x = Math.cos(start);
            y = Math.sin(start);
            
            gl.glColor3d(red, green, blue);
            gl.glVertex2d(x, y);
            gl.glEnd();
            gl.glBegin(GL2.GL_POLYGON);
            gl.glColor3d(center, center, center);
            gl.glVertex2d(0.0, 0.0);
            gl.glColor3d(red, green, blue);
            gl.glVertex2d(x, y);
            
            if( i <= (r/2)) {
                blue = blue + rr;
            } else {
                red = red - rr;
            }
            start += angle;
        }
        
        // blue
        
        red = 0.0;
        blue = 1.0;
        
        if(side%3 == 1) {
            r += 1;
        }
        rr = 2*blue/r;
        for(int i = 0; i < r; i++) {
            x = Math.cos(start);
            y = Math.sin(start);
            
            gl.glColor3d(red, green, blue);
            gl.glVertex2d(x, y);
            gl.glEnd();
            gl.glBegin(GL2.GL_POLYGON);
            gl.glColor3d(center, center, center);
            gl.glVertex2d(0.0, 0.0);
            gl.glColor3d(red, green, blue);
            gl.glVertex2d(x, y);
            
            if( i <= (r/2)) {
                green = green + rr;
            } else {
                blue = blue - rr;
            }
            start += angle;
            
            if(i == (r-1)) {
                System.out.println("End");
                gl.glColor3d(0.0, 1.0, 0.0);
                gl.glVertex2d(Math.cos(start), Math.sin(start));
                gl.glEnd();
            }
        }
    }
    
    public void init(GLAutoDrawable drawable) {}
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
    public void dispose(GLAutoDrawable drawable) {}
}
