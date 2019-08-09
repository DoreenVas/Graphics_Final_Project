package View; /******
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
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import Scene.SceneBuilder;
import Scene.Sounds;
import com.jogamp.opengl.util.Animator;

public class Main extends JFrame {

    public static void main(String[] args) throws IOException, SQLException {
        Menu menu = new Menu();
        menu.menu_window("resources/pics/temple.jpg","Can you escape the temple of doom?");
//        Game game = new Game();
//        game.game_window();
    }

    /***
     * In charge of drawing the image
     */
    static class ImagePanel extends JComponent {
        private Image image;
        private int width;
        private int height;

        protected ImagePanel(Image image, int width, int height) {
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
        protected static  BufferedImage createImage(String path, JFrame frame){
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
