package View;

import WorldObjects.Player;
import com.jogamp.opengl.util.Animator;
import javax.swing.*;

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
     */
    public static ViewManager getInstance() {
        if(viewManager == null) {
            viewManager = new ViewManager();
        }
        return viewManager;
    }

    /**
     * Constructor.
     */
    private ViewManager(){ }

    public void setGameFrame(JFrame frame){
        this.game_frame = frame;
    }

    public void setMenuFrame(JFrame frame){
        this.menu_frame = frame;
    }

    public void lose() {
        showWindow("resources/pics/lose.jpg", "");
    }

    public void win() {
        showWindow("resources/pics/win.jpg", "                 Winner, Great Job!");
    }

    public void mainMenu() {
        showWindow("resources/pics/temple.jpg","Can you escape the temple of doom?");
    }

    private void showWindow(String picPath, String labelText) {
        Menu menu = new Menu();
        menu.menu_window(picPath,labelText);
        if(game_frame != null){
            animator.stop();
            game_frame.dispose();
        }
    }

    public void showInstructions() {
        Instructions instructions = new Instructions();
        instructions.instructions();
    }

    protected void startGame(){
        Game game = new Game();
        game.game_window();
        if(menu_frame != null){
            menu_frame.dispose();
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
