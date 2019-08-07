/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import Scene.SceneBuilder;
import com.jogamp.opengl.util.Animator;

public class Main extends JFrame {

    public static void main(String[] args) {
        menu_window();
//        game_window();
    }

    public static void menu_window(){
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(800,700));
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game");
        frame.setResizable(false);
        JButton button = new JButton("START PLAYING");
        panel.add(button);
        frame.setVisible(true);

        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                game_window();
            }
        });
    }

    private static void game_window(){
        JFrame game_frame = new JFrame();
        GLCanvas canvas = new GLCanvas();
        Animator animator = new Animator(canvas);
        canvas.addGLEventListener(new SceneBuilder());
        game_frame.add(canvas);
        game_frame.setUndecorated(true);
        game_frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        game_frame.addWindowListener(new java.awt.event.WindowAdapter() {
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
        game_frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
//        frame.dispose();
    }
}
