import java.awt.*;        // original java graphics package.
import javax.swing.*;     // newer java graphics package.
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.geom.*;
import java.awt.event.*;


public class WiredTeapot extends JPanel implements GLEventListener {
    public static void main(String[] args) {
        JFrame window = new JFrame("Wired Tea Pot");
        WiredTeapot teapot = new WiredTeapot();
        window.setContentPane(teapot);
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        
        final long startTime = System.currentTimeMillis();
        
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed (ActionEvent arg0) {
                long t = System.currentTimeMillis() - startTime;
                teapot.theta = t * 0.01;
                teapot.repaint();
            }
        };
        Timer animationTimer = new Timer(16, actionListener);
        animationTimer.start();
    }

    private double theta;
    
    public WiredTeapot() {
        GLJPanel gljPanel = new GLJPanel(new GLCapabilities(null));
        gljPanel.setPreferredSize(new Dimension(400, 400));
        gljPanel.addGLEventListener(this);
        add(gljPanel);
    }

// Methods of the GLEventListener interface

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(1.0F, 0.0F, 0.0F, 0.0F);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    
        gl.glColor3d(1.0, 1.0, 1.0);
        gl.glLineWidth(1.0F);
    
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // called before ortho2D
        gl.glOrtho(-1.0, 3.0, -1.0, 3, -1.0, 1.0);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // called before ortho2D

        // Front left
        GLUT glut = new GLUT();
        
        //cube
        gl.glColor3d(0.0, 0.0, 0.0);
        glut.glutWireCube(1.7f);
        
        // teapot
        gl.glColor3d(1.0, 1.0, 1.0);
        gl.glRotated(theta, 0.0, 1.0, 0.0);
        glut.glutWireTeapot(0.5);

        
        // front right
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-3.0, 1.0, -1.0, 3.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        //cube
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glRotated(-90.0, 0.0, 1.0, 0.0);
        glut.glutWireCube(1.7f);
        // teapot
        gl.glRotated(theta, 0.0, 1.0, 0.0);
        gl.glColor3d(1.0, 1.0, 1.0);
        glut.glutWireTeapot(0.5);
        
        // top left
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0, 3.0, -3.0, 1.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glRotated(270.0, 1.0, 0.0, 0.0);
        gl.glColor3d(0.0, 0.0, 0.0);
        glut.glutWireCube(1.7f);
        
        gl.glRotated(theta, 0.0, 1.0, 0.0);
        gl.glColor3d(1.0, 1.0, 1.0);
        glut.glutWireTeapot(0.5);
    
        // top right
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-3.0, 1.0, -3.0, 1.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        // cube
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glRotated(-30.0, 1.0, 0.0, 0.0);
        gl.glRotated(-45.0, 0.0, 0.0, 1.0);
        glut.glutWireCube(1.4f);
        gl.glRotated(30.0, 1.0, 0.0, 0.0);
        gl.glRotated(45.0, 0.0, 0.0, 1.0);
        // teapot
        gl.glRotated(-30.0, 1.0, 0.0, 0.0);
        gl.glRotated(theta, 0.0, 1.0, 0.0);
        gl.glColor3d(1.0, 1.0, 1.0);
        glut.glutWireTeapot(0.5);

}

    public void init(GLAutoDrawable drawable) {

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void dispose(GLAutoDrawable drawable) {

    }
}
