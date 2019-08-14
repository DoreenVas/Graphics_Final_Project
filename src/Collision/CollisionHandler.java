package Collision;
import Enums.LevelEnum;
import Scene.Sounds;
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
            World.getInstance().removeFromList(c);
        }
    }

    public static void nextLevel(){
        Sounds.makeSound("resources/sounds/portal.wav");
        Player.setLevel(LevelEnum.LEVEL_2);
        ViewManager.getInstance().drawBar();
        World.getInstance().moveToLevel2();
    }

    public static void lose(){
        Player.decreaseLives();
        ViewManager manager = ViewManager.getInstance();
        if (!Player.isAlive()) {
            manager.lose();
        }
        else {
            manager.drawBar();
        }

    }
}
