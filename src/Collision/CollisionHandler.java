package Collision;
import View.ViewManager;
import WorldObjects.BreakableCube;
import WorldObjects.Player;
import WorldObjects.World;

public class CollisionHandler {

    public void stopMovement() {

    }

    public static void vanish(BreakableCube c) {
        if (c.getHp() > 0) {
            c.decreaseHp();
        } else {
            World.removeFromList(c);
        }
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
