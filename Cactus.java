// Kanchan Nannavare
// Topics in Computer Science, Final Project, January 25, 2017
// 3-D image of a saguaro cactus with lightning
// For animation run the program without any arguement and to get the image of lightning run with any arguemnet

import java.awt.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.*;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.geom.*;
import java.awt.event.*;

public class Cactus extends GLJPanel implements GLEventListener {
    
    public static void main(String[] args) {
        JFrame window = new JFrame("CACTUS AND LIGHTNING");
        Cactus cactus = new Cactus();
        window.setContentPane(cactus);
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        
        if (args.length > 0) {
            cactus.time = 0;
        } else {
            final long startTime = System.currentTimeMillis();
            
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    long t = System.currentTimeMillis() - startTime;
                    cactus.time += 1;
                    cactus.repaint();
                }
            };
            Timer animationTimer = new Timer(64, actionListener);
            animationTimer.start();
        }
    }
    
    private int time;
    
    public Cactus() {
        setPreferredSize( new Dimension(1000,700) );
        addGLEventListener(this);
    }
    
    private void right_torus(GL2 gl) {
        double planeX[] = {1.0,  0.0, 0.0, 0.0};
        double planeY[] = {0.0, -1.0, 0.0, 0.0};
        
        gl.glClipPlane(GL2.GL_CLIP_PLANE0, planeX, 0);
        gl.glClipPlane(GL2.GL_CLIP_PLANE1, planeY, 0);
        gl.glEnable(GL2.GL_CLIP_PLANE0);
        gl.glEnable(GL2.GL_CLIP_PLANE1);
        
        GLUT glut = new GLUT();
        glut.glutSolidTorus(0.1, 0.3, 32, 32);
        
        gl.glDisable(GL2.GL_CLIP_PLANE0);
        gl.glDisable(GL2.GL_CLIP_PLANE1);
    }
    
    private void right_cactus(GL2 gl) {
        GLUT glut = new GLUT();
        
        gl.glTranslated(0.0, 0.0, 0.9);
        right_torus(gl);
        
        gl.glLoadIdentity();
        gl.glTranslated(0.3, 0.0, 0.9);
        gl.glRotated(-90.0, 1.0, 0.0, 0.0);
        glut.glutSolidCylinder(0.1, 0.5, 32, 1);
        
        gl.glLoadIdentity();
        gl.glTranslated(0.3, 0.5, 0.9);
        glut.glutSolidSphere(0.1, 32, 32);
        
        gl.glLoadIdentity();
        gl.glTranslated(0.0, -0.3, 0.9);
        glut.glutSolidSphere(0.1, 32, 32);
    }
    
    private void left_cactus(GL2 gl) {
        GLUT glut = new GLUT();
        
        gl.glLoadIdentity();
        gl.glTranslated(-0.1, 0.3, 0.8);
        gl.glRotated(180.0, 0.0, 1.0, 0.0);
        right_torus(gl);
        
        gl.glLoadIdentity();
        gl.glTranslated(-0.4, 0.3, 0.8);
        gl.glRotated(-90.0, 1.0, 0.0, 0.0);
        glut.glutSolidCylinder(0.1, 0.3, 32, 1);
        
        gl.glLoadIdentity();
        gl.glTranslated(-0.4, 0.6, 0.8);
        glut.glutSolidSphere(0.1, 32, 32);
    }
    
    private void cactus(GL2 gl) {
        GLUT glut = new GLUT();
        
        gl.glTranslated(0.0, -0.9, 0.8);
        gl.glRotated(-90.0, 1.0, 0.0, 0.0);
        glut.glutSolidCylinder(0.1, 1.7, 32, 1);
        
        gl.glLoadIdentity();
        gl.glTranslated(0.0, 0.8, 0.8);
        glut.glutSolidSphere(0.1, 32, 32);
        
        gl.glLoadIdentity();
        right_cactus(gl);
        
        gl.glLoadIdentity();
        left_cactus(gl);
    }
    
    private void draw_lightning(double x1, double y1, double x2, double y2, double displace, GL2 gl) {
        gl.glBegin(GL2.GL_LINES);
        
        if(displace < 0.0001) {
            gl.glVertex2d(x1, y1);
            gl.glVertex2d(x2, y2);
            gl.glEnd();
        } else {
            double mid_x = (x2+x1)/2;
            double mid_y = (y2+y1)/2;
            mid_x += (Math.random()-0.5)*displace;
            mid_y += (Math.random()-0.5)*displace;
            draw_lightning (x1, y1, mid_x, mid_y, displace/2, gl);
            draw_lightning (x2, y2, mid_x, mid_y, displace/2, gl);
        }
    }
    
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        double red, green, blue;
        red = 0.0;
        green = 0.0;
        blue = 0.0;
        
        if(time%20 == 0) {
            red = 0.5 + Math.random()*0.5;
            blue = 0.5 + Math.random()*0.5;
            green = 0.5 + Math.random()*0.5;
        }
        
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3d(0.0, 0.0, 0.1);
        gl.glVertex3d(-1.0, -0.3, 0.0);
        
        gl.glColor3d(red, green, blue);
        gl.glVertex3d(-1.0, 1.0, 0.0);
        
        gl.glColor3d(red, green, blue);
        gl.glVertex3d(3.0, 1.0, 0.0);
        
        gl.glColor3d(0.0, 0.0, 0.1);
        gl.glVertex3d(3.0, -0.3, 0.0);
        gl.glEnd();
        
        gl.glColor3d(red, green, blue);
        
        int width;
        double x1, y1, x2, y2, displace;
        
        int iterations = 0;

        if(time%20 == 0) {
            x1 = -1 + Math.random()*4.0;
            y1 = 0.5 + Math.random()*0.5;
            iterations = 2 + (int) (Math.random()*10);
            
            for (int i = 0; i < iterations; i++) {
                width = 1 + (int) (Math.random()*10);
                gl.glLineWidth(width);
                x2 = -1 + Math.random()*4.0;
                y2 = -0.3 + Math.random()*0.8;
                displace = 0.1 + Math.random();
                draw_lightning(x1, y1, x2, y2, displace, gl);
            }
        }
        
        gl.glLoadIdentity();
        gl.glColor3d(0.1, 0.1, 0.1);
        
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(-1.0, -1.2, 1.0);
        
        gl.glColor3d(0.05, 0.0, 0.0);
        gl.glVertex3d(-1.0, -0.3, -1.0);
        gl.glColor3d(0.05, 0.0, 0.0);
        gl.glVertex3d(3.0, -0.3, -1.0);
        
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(3.0, -1.2, 1.0);
        gl.glEnd();
        
        if(time%20 == 0) {
            gl.glColor3d(0.05, 0.06, 0.07);
        } else {
            gl.glColor3d(0.02, 0.02, 0.02);
        }
        cactus(gl);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-6.0, 2.0, -2.0, 2.0, -1.0, 1.0);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        cactus(gl);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0, 3.0, -1.0, 1.0, -1.0, 1.0);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    public void init(GLAutoDrawable drawable) {
        
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(1.0F, 0.0F, 0.0F, 0.0F);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0, 3.0, -1.0, 1.0, -1.0, 1.0);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    public void reshape(GLAutoDrawable drawable,
                        int x, int y, int width, int height) {
    }
    
    public void dispose(GLAutoDrawable drawable) {
    }
}
