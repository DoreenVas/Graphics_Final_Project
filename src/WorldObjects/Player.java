package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Collision.CollisionHandler;
import Coordinates.Cartesian;
import Coordinates.Coordinates;
import Enums.LevelEnum;
import Enums.MovementEnum;
import Utils.Vector;
import View.ViewManager;

import static Enums.MovementEnum.*;

public class Player implements Collidable {
    // members
    private static Vector pos;
    private Vector direction;
    private Vector up;
    private static Coordinates Coordinates;
    private Type type = Type.player;
    private static float step = 0.5f;
    private static int lives;
    private static boolean weaponUse = false;
    private static LevelEnum level;

    public Player(){
        Coordinates = new Cartesian();
        pos = new Vector(0f, 0.5f, 10f);
        up = getUp();
        direction = getDirection();
        lives = 2;
        level = LevelEnum.LEVEL_1;
        ViewManager manager = ViewManager.getInstance();
        manager.drawBar();
    }

    public static int getLevel() {
        return level.ordinal()+1;
    }

    public static void setLevel(LevelEnum level) {
        Player.level = level;
    }

    public static boolean isAlive() {
        if (lives > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public static int getLives() {
        return lives;
    }

    public static void decreaseLives() {
        lives = lives -1;
    }

    public Coordinates getCoordinates() {
        return Coordinates;
    }

    public static Vector getPos() {
        return pos;
    }

    public static void resetCoordinates() {
        Coordinates = new Cartesian();
//        Coordinates.rotate(SteerEnum.UP_X, -0.2f);
    }

    public Vector getDirection() {
        Vector zAxis = Coordinates.getzAxis();
        direction = new Vector(zAxis.getX(), zAxis.getY(), zAxis.getZ());
        return direction;
    }

    public Vector getUp() {
        Vector yAxis = Coordinates.getyAxis();
        up = new Vector(yAxis.getX(), yAxis.getY(), yAxis.getZ());
        return up;
    }

    public float getStep() {
        return step;
    }

    public static void setPos(Vector new_pos) {
        pos = new_pos;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public void setUp(Vector up) {
        this.up = up;
    }

    public void move(MovementEnum direction) {
        boolean itemsCollision, wallsCollision;
        Vector nextPos = checkNextPos(pos, direction);
        itemsCollision = CollisionDetector.checkPlayerItemsCollisions(nextPos);
        wallsCollision = CollisionDetector.point_walls(nextPos);
        //player touches boss
        if(World.getInstance().getBoss().getAABB_collision().checkInside(nextPos)){
            CollisionHandler.lose();
        }
        if (!wallsCollision && !itemsCollision) {
            switch(direction) {
                case FORWARD: // move forward
                    Coordinates.move(FORWARD, pos, step);
                    break;
                case BACKWARD: // move backward
                    Coordinates.move(BACKWARD, pos, step);
                    break;
                case RIGHT: // move right
                    Coordinates.move(RIGHT, pos, step);
                    break;
                case LEFT: // move left
                    Coordinates.move(LEFT, pos, step);
                    break;
                case UP: // move up
                    Coordinates.move(UP, pos, step);
                    break;
                case DOWN: // move down
                    Coordinates.move(DOWN, pos, step);
                    break;
            }
        }
    }

    private Vector checkNextPos(Vector pos, MovementEnum direction) {
        Vector nextPos = Coordinates.move(direction, new Vector(pos), step);
        return nextPos;
    }

    public static void useWeapon(boolean use) {
        weaponUse = use;
    }

    public static boolean isWeaponUsed() {
        return weaponUse;
    }
}
