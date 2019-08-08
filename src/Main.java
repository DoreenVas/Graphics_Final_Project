/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import Scene.SceneBuilder;
import Scene.Sounds;
import com.jogamp.opengl.util.Animator;

public class Main extends JFrame {

    public static void main(String[] args) {
        menu_window();
//        game_window();
    }

    public static void menu_window(){
        JFrame frame = new JFrame();
        frame.setSize(800, 600);

        //Create Image and set in the given frame
        CreateImage.createImage("resources/pics/temple1.jpg", frame);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game");
        frame.setResizable(false);

        //setting button with icon
        ImageIcon icon = new ImageIcon("resources/pics/start.png");
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 150, 50,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon(newimg);
        JButton button = new JButton(icon);
        button.setBounds(325, 275, 150, 50);

        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                game_window();
            }
        });

        frame.add(button);
        Sounds.makeSound("resources/sounds/intro.wav");
        frame.validate();
        frame.setLayout(null);
        frame.setVisible(true);
    }

    /***
     * Setting the game window
     */
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

    /***
     * In charge of drawing the image
     */
    static class ImagePanel extends JComponent {
        private Image image;
        private int width;
        private int height;
        public ImagePanel(Image image, int width, int height) {
            this.image = image;
            this.width = width;
            this.height = height;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0,width, height, this);
        }
    }

    /***
     * Creating the image based on the given path.
     */
    static class CreateImage{
        private static  BufferedImage createImage(String path, JFrame frame){
            BufferedImage myImage = null;
            try {
                myImage = ImageIO.read(new File(path));
                frame.setContentPane(new ImagePanel(myImage, frame.getWidth(), frame.getHeight()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return myImage;
        }
    }
}
