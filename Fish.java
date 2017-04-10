import java.awt.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.geom.*;
import java.awt.event.*;

public class Fish extends GLJPanel implements GLEventListener {
    
    public static void main(String[] args) {
        JFrame window = new JFrame("Fish");
        Fish fish = new Fish();
        window.setContentPane(fish);
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        
        final long startTime = System.currentTimeMillis();
        
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                long t = System.currentTimeMillis() - startTime;
                fish.x_value = 0.001*t;
//                System.out.println(fish.x_value);
                fish.repaint();
            }
        };
//
        Timer animationTimer = new Timer(16, actionListener);
        animationTimer.start();
        
//        double k = 0;
//        while (k <= 1) {
//            fish.x_value = k;
//            fish.repaint();
//            k +=0.0000000001;
//        }

    }
    
    private double x_value;
    
    public Fish() {
        setPreferredSize( new Dimension(1000,500) );
        addGLEventListener(this);
    }
    
    // ---------------  Methods of the GLEventListener interface -----------
    
    public void display(GLAutoDrawable drawable) {
        // called when the panel needs to be drawn
        GL2 gl = drawable.getGL().getGL2();
        // Add drawing code here!
        
        gl.glClearColor(0.6F, 0.9F, 1.0F, 0.0F);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0, 9.0, -3.0, 3.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glEnable(GL2.GL_LIGHTING);
        
        GLUT glut = new GLUT();
        

//        double k = 0;
//        if(x_value > 200) {
//            x_value = x_value % 200;
//        }
//        
//        if(x_value > 0 && x_value < 100)
//        {
//            k = - 1 + x_value/100;
//                            System.out.println("first condition");
//
//        } else {
//            k = (x_value-100)/100;
//                            System.out.println("second condition"); // forward
//
//        }
//        
//        gl.glTranslated(0.1*k, 0, 0);
        
        if (x_value >= 9) {
            while (x_value >= 9) {
                x_value -= 10;
            }
            if(x_value >= 9) {
            gl.glTranslated(-11.0 + x_value, 0, 0);
            }
        }

        gl.glTranslated(x_value, 0, 0);

  
     
//        gl.glRotated(-20.0, 0.0, 0.0, 1.0);

        double radius = 0.5; // 0.5 for ortho

        gl.glColor3d(0.8, 0.6, 0.0);

        // body
        glut.glutSolidSphere(radius, 32, 16);
//        gl.glDisable(GL2.GL_LIGHTING);


        gl.glColor3d(0.8, 0.6, 0.0);

//        gl.glEnable(GL2.GL_LIGHTING);

        // tail
        double x = radius * Math.cos(3*Math.PI/4);

        double[][] vertexList =
        {
            {x,-x,-x},
            {x,-x,x},
            {x, -x, 0},
            {x - radius/2, - x + radius/2, 0},// up
            {x - radius/4, 0 , 0},
            {-radius, 0, 0},
            {x - radius/2, x - radius/2 , 0}, // 5 bottom
            {x, x, -x},
            {x, x, x},
            {x, x, 0}
        };
        
        int[][] faceList =
        {  {1,2,3}, {0, 2, 3}, {1, 3, 4, 5}, {0, 3, 4, 5},
            {6, 7, 4, 5}, {6,7,9}, {6, 9, 8}, {8, 6, 4, 5}};
        
        for (int i = 0; i < faceList.length; i++) {
            gl.glBegin(GL2.GL_TRIANGLE_FAN);
            for (int j = 0; j < faceList[i].length; j++) {
                int vertexNum = faceList[i][j];  // Index for vertex j of face i.
                double[] vertexCoords = vertexList[vertexNum];  // The vertex itself.
                gl.glVertex3dv( vertexCoords, 0 );
            }
            gl.glEnd();
        }
        gl.glDisable(GL2.GL_LIGHTING);
        
        // eye
        gl.glColor3d(1.0, 1.0, 1.0);
        gl.glTranslated(0.3, 0.1, 1.0);
        glut.glutSolidSphere(0.04, 100, 40);
        gl.glColor3d(0, 0, 0);
        glut.glutSolidSphere(0.02, 100, 40);
        
        
        gl.glTranslated(-0.3, -0.1, -1.0);
//                gl.glRotated(20.0, 0.0, 0.0, 1.0);


        

    }
    
    public void init(GLAutoDrawable drawable) {
        // called when the panel is created
//        GL2 gl = drawable.getGL().getGL2();

//        // Add initialization code here!
        
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0, 9.0, -3.0, 3.0, -2.0, 2.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0.0, -0.1, 0.0);
        gl.glRotated(-20.0, 1.0, 0.0, 0.0);
        
        gl.glEnable(GL2.GL_DEPTH_TEST);
        
        float lightPosition[]   = {0.7F, 1.0F, -0.5F, 0.0F};
        float whiteLight[]      = {1.0F, 1.0F, 1.0F, 1.0F};
        float ambientMaterial[] = {0.0F, 0.0F, 0.4F, 1.0F};
        float diffuseMaterial[] = {0.0F, 0.0F, 0.5F, 1.0F};
        
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,  whiteLight, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE,  whiteLight, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whiteLight, 0);
        
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,  ambientMaterial, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE,  diffuseMaterial, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteLight, 0);
        gl.glMaterialf( GL2.GL_FRONT, GL2.GL_SHININESS, 100.0F);
        
        gl.glEnable(GL2.GL_LIGHT0);
    }
    
    public void reshape(GLAutoDrawable drawable,
                        int x, int y, int width, int height) {
        // called when user resizes the window
    }
    
    public void dispose(GLAutoDrawable drawable) {
        // called when the panel is being disposed
    }
    
}
