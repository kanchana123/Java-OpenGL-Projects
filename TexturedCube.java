// A java program to demonstrate texture mapping.
// Written on January 23, 2017.

import java.awt.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.*;           // for a sphere
import com.jogamp.opengl.util.gl2.GLUT;   // for a teapot

public class TexturedCube extends GLJPanel implements GLEventListener {

  static final int SIZE = 512;   // Should be a power of 2.

// ----------------- Main program -----------------

  public static void main(String[] args) {
    JFrame window = new JFrame("TexturedCube.java");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setContentPane(new TexturedCube());
    window.pack();
    window.setVisible(true);
  }

// ----------------- Constructor -----------------

  public TexturedCube() {
    setPreferredSize(new Dimension(SIZE, SIZE));
    addGLEventListener(this);
  }

// -----------------A Methods to create Texture -----------------
    
    private void createTexture(GL2 gl) {
        gl.glClearColor(1.0F, 1.0F, 0.3F, 0.0F);    // yellow background
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-2.0, 2.0, -2.0, 2.0, -2.0, 2.0);
        
        gl.glDisable(GL2.GL_TEXTURE_2D);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glBegin(GL2.GL_TRIANGLES);
            gl.glColor3d(1.0, 0.0, 0.0);
            gl.glVertex2d(0.0, 1.0);
            gl.glColor3d(0.0, 1.0, 0.0);
            gl.glVertex2d(-1.0, -1.0);
            gl.glColor3d(0.0, 0.0, 1.0);
            gl.glVertex2d(1.0, -1.0);
        gl.glEnd();
        
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        gl.glCopyTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, 0, 0, 2*SIZE, 2*SIZE, 0);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
        
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

        gl.glEnable(GL2.GL_TEXTURE_2D);
    }
    

// ----------------- Methods of the GLEventListener interface -----------------

  public void init(GLAutoDrawable drawable) {
  }

  public void display(GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();
      
      createTexture(gl);
      
      // clear the screen
      
      gl.glClearColor(1.0F, 0.0F, 0.0F, 0.0F);
      gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
      
      // Map the texture onto a square
      
      gl.glTranslated(1.3, 1.3, 0.0);
      gl.glScaled(0.6, 0.6, 1.0);
//
      gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex2d(0.0, 0.0);
      
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex2d(0.0, 1.0);

        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex2d(1.0, 1.0);
      
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex2d(0.0, 1.0);
      gl.glEnd();
      
  
    
    // Map the texture onto the front, right side, and top of a cube
    
      gl.glLoadIdentity();
      gl.glRotated(30.0, 1.0, 0.0, 0.0);
      gl.glRotated(-45.0, 0.0, 1.0, 0.0);

    double[] [] vertices = {{-1, -1, 1}, {-1, 1, 1}, {1, 1, 1}, {1, -1, 1},
                            {-1, -1, -1}, {-1, 1, -1}, {1, 1, -1}, {1, -1, -1}};
    
    int[][] indices = {{0, 3, 2, 1}, {3, 7, 6, 2}, {2, 6, 5, 1}};
    
    double[][] textureCoordinates = {{0,0}, {1,0}, {1,1}, {0, 1}};
    
    gl.glBegin(GL2.GL_QUADS);
    
      for(int face = 0; face < 3; face++) {
          for(int vertx = 0; vertx < 4; vertx++) {
              int index = indices[face][vertx];
              gl.glTexCoord2dv(textureCoordinates[vertx], 0);
              gl.glVertex3dv(vertices[index], 0);
          }
      }
    gl.glEnd();
      
      gl.glDisable(GL2.GL_TEXTURE_2D);

      gl.glLineWidth(5);

      gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(-1, 1, 1);
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glVertex3d(1, 1, 1);
      
        gl.glColor3d(0.0, 1.0, 0.0);
        gl.glVertex3d(1, 1, 1);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(1, 1, -1);
      
        gl.glColor3d(0.0, 0.0, 1.0);
        gl.glVertex3d(1, 1, 1);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(1, -1, 1);
      gl.glEnd();

      gl.glEnable(GL2.GL_TEXTURE_2D);

    // Map the texture onto a glut teapot
      gl.glLoadIdentity();
      gl.glTranslated(-1.3, -1.6, 0.0);
      
      GLUT glut = new GLUT();
      glut.glutSolidTeapot(0.4);
      
      // Map the texture onto a glu sphere.
      
      gl.glLoadIdentity();
      gl.glTranslated(1.4, -1.5, 0.0);
      gl.glRotated(160.0, 0.30, 0.50, 1.0);
      
      GLU glu = new GLU();
      GLUquadric quadric = glu.gluNewQuadric();
      glu.gluQuadricTexture(quadric, true);
      glu.gluSphere(quadric, 0.40, 24, 24);
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
  }

  public void dispose(GLAutoDrawable drawable) {
  }
}
