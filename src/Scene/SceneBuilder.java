/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package Scene;

import Collision.CollisionDetector;
import Movement.MovementEnum;
import Steer.SteerEnum;
import WorldObjects.*;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;

import Utils.Vector;

public class SceneBuilder extends KeyAdapter implements GLEventListener {

    private static GLU glu = new GLU();
    private WorldObject world;
    //private Box box1;
    private Cube cube;
    private Sphere sphere;
    private Player player;
    private Vector pos;
    private Vector direction;
    private Vector up;
    private float alpha = (float)Math.toRadians(5);
    private static float step = 0.1f;

    public void display(GLAutoDrawable gLDrawable) {
        direction = new Vector(player.getCoordination().getzAxis().getX(), player.getCoordination().getzAxis().getY(), player.getCoordination().getzAxis().getZ());
        up = new Vector(player.getCoordination().getyAxis().getX(), player.getCoordination().getyAxis().getY(), player.getCoordination().getyAxis().getZ());
        pos = player.getPos();

        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();  // Reset The View
        glu.gluLookAt(pos.getX(), pos.getY(), pos.getZ(),
                pos.getX() - direction.getX(),
                pos.getY() - direction.getY(),
                pos.getZ() - direction.getZ(),
                up.getX(),
                up.getY(),
                up.getZ());

        CollisionDetector.point_cube(pos, cube);
        gl.glColor4f(1f, 1f, 1f, 1f); //NEEDS to be white before drawing, else stuff will tint.
        world.draw(gl);
        //box1.draw(gl);
        cube.draw(gl);
        sphere.draw(gl);
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.5f);    // White Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        //box1 = new Box();
        cube = new Cube(new Vector(-1,-1,1),2,"resources/box.jpg");
        world = new World();
        player = new Player();
        sphere = new Sphere(1, -5, 2, 0);

        gl.glEnable(GL2.GL_LIGHTING);
        if (drawable instanceof Window) {
            Window window = (Window) drawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(this, drawable).addTo(comp);
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void reshape(GLAutoDrawable drawable, int x,
                        int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if(height <= 0) {
            height = 1;
        }
        float h = (float)width / (float)height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 'i': // rotate up (x axis)
                player.getCoordination().rotate(SteerEnum.UP_X, -alpha);
                break;
            case 'k': // rotate down (x axis)
                player.getCoordination().rotate(SteerEnum.DOWN_X, alpha);
                break;
            case 'l': // rotate right (y axis)
                player.getCoordination().rotate(SteerEnum.RIGHT_Y, -alpha);
                break;
            case 'j': // rotate left (y axis)
                player.getCoordination().rotate(SteerEnum.LEFT_Y, alpha);
                break;
            case 'o': // rotate right (z axis)
                player.getCoordination().rotate(SteerEnum.RIGHT_Z, alpha);
                break;
            case 'u': // rotate left (z axis)
                player.getCoordination().rotate(SteerEnum.LEFT_Z, -alpha);
                break;
            case 'w': // move forward
                player.setPos(player.getCoordination().move(MovementEnum.FORWARD ,pos, step));
                break;
            case 's': // move backward
                player.setPos(player.getCoordination().move(MovementEnum.BACKWARD ,pos , step));
                break;
            case 'd': // move right
                player.setPos(player.getCoordination().move(MovementEnum.RIGHT ,pos , step));
                break;
            case 'a': // move left
                player.setPos(player.getCoordination().move(MovementEnum.LEFT ,pos , step));
                break;
            case 'e': // move up
                player.setPos(pos = player.getCoordination().move(MovementEnum.UP ,pos , step));
                break;
            case 'q': // move down
                player.setPos(player.getCoordination().move(MovementEnum.DOWN ,pos , step));
                break;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}
