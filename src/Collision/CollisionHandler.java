package Collision;
import View.ViewManager;

public class CollisionHandler {

    public void stopMovement() {

    }

    public void vanish() {

    }

    public static void lose(){
        try {
            ViewManager manager = ViewManager.getInstance();
            manager.lose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
