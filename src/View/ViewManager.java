package View;

import WorldObjects.Player;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

/***
 * Singleton that manages the view windows.
 */
public class ViewManager {
    private static ViewManager viewManager;
    private JFrame game_frame;
    private JFrame menu_frame;
    private Animator animator;
    private JLabel LivesLabel;

    /**
     * Returns the classes instance.
     * @return the classes current instance
     * @throws IOException thrown from inner function
     * @throws SQLException thrown from inner function
     */
    public static ViewManager getInstance() throws IOException, SQLException {
        if(viewManager == null) {
            viewManager = new ViewManager();
        }
        return viewManager;
    }

    /**
     * Constructor.
     * @throws IOException thrown from inner function
     * @throws SQLException thrown from inner function
     */
    private ViewManager(){ }

    public void setGameFrame(JFrame frame){
        this.game_frame = frame;
    }

    public void setMenuFrame(JFrame frame){
        this.menu_frame = frame;
    }

    public void lose() throws IOException, SQLException {
        showWindow("resources/pics/lose.jpg", "The temple remains unbeatable");
    }

    public void win() throws IOException, SQLException {
        showWindow("resources/pics/win.jpg", "Winner, Great Job!");
    }

    public void mainMenu() throws IOException, SQLException {
        showWindow("resources/pics/temple.jpg","Can you escape the temple of doom?");
    }

    private void showWindow(String picPath, String labelText) throws IOException, SQLException {
        Menu menu = new Menu();
        menu.menu_window(picPath,labelText);
        if(game_frame != null){
            animator.stop();
            game_frame.dispose();
        }
        else {
            System.out.println("game_frame is null");
        }
    }

    protected void startGame() throws IOException, SQLException {
        Game game = new Game();
        game.game_window();
        if(menu_frame != null){
            menu_frame.dispose();
        }
        else {
            System.out.println("menu_frame is null");
        }
    }

    protected void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public void setLivesLabel(JLabel label) {
        this.LivesLabel = label;
    }

    public void drawBar(){
        if (!this.LivesLabel.isVisible()) {
            this.LivesLabel.setVisible(true);
        }
        this.LivesLabel.setText("Level: "+Player.getLevel()+", Lives: " + Player.getLives());
    }
}
