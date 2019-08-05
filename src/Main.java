/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
import java.awt.Frame;
import javax.media.opengl.awt.GLCanvas;

import Scene.SceneBuilder;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.opengl.util.Animator;

public class Main extends KeyAdapter {

    static GLCanvas canvas = new GLCanvas();
    static Frame frame = new Frame("Jogl 3D Room");
    static Animator animator = new Animator(canvas);

    public static void exit(){
        animator.stop();
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        canvas.addGLEventListener(new SceneBuilder());
        frame.add(canvas);
        frame.setSize(800, 600);
		frame.setUndecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }
}
