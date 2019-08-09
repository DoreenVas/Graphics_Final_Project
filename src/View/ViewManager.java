package View;

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

    public void closeGameFrame(){
        if(game_frame != null){
            game_frame.dispose();
        }
        else {
            System.out.println("frame is null");
        }
    }

}