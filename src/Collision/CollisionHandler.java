package Collision;
import View.ViewManager;
import WorldObjects.Player;

public class CollisionHandler {

    public void stopMovement() {

    }

    public void vanish() {

    }

    public static void lose(){
        Player.decreaseLives();
        try {
            ViewManager manager = ViewManager.getInstance();
            if (!Player.isAlive()) {
                manager.lose();
            }
            else {
                manager.drawLivesLabel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
