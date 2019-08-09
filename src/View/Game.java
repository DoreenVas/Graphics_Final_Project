package View;

import Scene.SceneBuilder;
import com.jogamp.opengl.util.Animator;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class Game {

    /***
     * Setting the game window
     */
    protected static void game_window() {
        JFrame game_frame = new JFrame();

        try {
            ViewManager manager = ViewManager.getInstance();
            manager.setGameFrame(game_frame);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GLCanvas canvas = new GLCanvas();
        Animator animator = new Animator(canvas);
        canvas.addGLEventListener(new SceneBuilder());
        game_frame.add(canvas);
        game_frame.setUndecorated(true);
        game_frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        game_frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        game_frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
//        frame.dispose();
    }
}
