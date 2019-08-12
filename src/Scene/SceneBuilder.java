/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package Scene;

import Collision.Collidable;
import Collision.CollisionDetector;
import Enums.MovementEnum;
import Enums.SteerEnum;
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
    private World world;
    private Player player;
    private float alpha = (float)Math.toRadians(5);
    private static float step = 0.1f;
//    private WavefrontObjectLoader_DisplayList axe;
    private ObjectLoader objectLoader;
    private ObjectDisplayer objectDisplayer;

    public void display(GLAutoDrawable gLDrawable) {
        Vector direction = player.getDirection();
        Vector up = player.getUp();
        Vector pos = player.getPos();

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

        gl.glColor4f(1f, 1f, 1f, 1f); //NEEDS to be white before drawing, else stuff will tint.

        world.draw(gl);
//        axe.drawModel(gl);
        this.objectDisplayer.draw(glu, gLDrawable);
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        world  = World.getInstance();
        player = new Player();

        gl.glEnable(GL2.GL_LIGHTING);
        if (drawable instanceof Window) {
            Window window = (Window) drawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(this, drawable).addTo(comp);
        }
//        this.axe = new WavefrontObjectLoader_DisplayList("resources/obj/axe.obj");
        this.objectLoader = new ObjectLoader();
        this.objectDisplayer = this.objectLoader.LoadModel("resources/obj/axe.obj", new Vector(0f, 0.5f, 10f), 1,1,1);

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
                player.move(MovementEnum.FORWARD);
                break;
            case 's': // move backward
                player.move(MovementEnum.BACKWARD);
                break;
            case 'd': // move right
                player.move(MovementEnum.RIGHT);
                break;
            case 'a': // move left
                player.move(MovementEnum.LEFT);
                break;
            case 'e': // move up
                player.move(MovementEnum.UP);
                break;
            case 'q': // move down
                player.move(MovementEnum.DOWN);
                break;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_F1) {
            System.out.println("ADD INSTRUCTIONS");
        }
        else if(e.getKeyCode() == KeyEvent.VK_F2) {
            System.out.println("SKIP LEVEL");
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            Player.useWeapon(true);
            Vector nextPos = new Vector(Player.getPos());
            nextPos.setX(Player.getPos().getX() + 3f);
            CollisionDetector.checkPlayerItemsCollisions(nextPos);
            Player.useWeapon(false);
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}
