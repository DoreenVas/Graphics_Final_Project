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
        if (!Player.isAlive()) {
            try {
                ViewManager manager = ViewManager.getInstance();
                manager.lose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
