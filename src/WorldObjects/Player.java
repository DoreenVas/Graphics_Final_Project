package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Collision.HitListener;
import Coordinations.Cartesian;
import Coordinations.Coordination;
import Enums.MovementEnum;
import Utils.Vector;
import View.ViewManager;

import static Enums.MovementEnum.*;

public class Player implements HitListener, Collidable {
    // members
    private static Vector pos;
    private Vector direction;
    private Vector up;
    private Coordination coordination;
    private Type type = Type.player;
    private static float step = 0.5f;
    private static int lives;

    public Player(){
        coordination = new Cartesian();
        pos = new Vector(0.0f, 1.0f, 10.0f);
        up = getUp();
        direction = getDirection();
        lives = 2;
        try {
            ViewManager manager = ViewManager.getInstance();
            manager.drawLivesLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Coordination getCoordination() {
        return coordination;
    }

    public static Vector getPos() {
        return pos;
    }

    public Vector getDirection() {
        Vector zAxis = coordination.getzAxis();
        direction = new Vector(zAxis.getX(), zAxis.getY(), zAxis.getZ());
        return direction;
    }

    public Vector getUp() {
        Vector yAxis = coordination.getyAxis();
        up = new Vector(yAxis.getX(), yAxis.getY(), yAxis.getZ());
        return up;
    }

    public void setPos(Vector new_pos) {
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
        wallsCollision = CollisionDetector.checkCollisionWithWalls(nextPos);
        if (!wallsCollision && !itemsCollision) {
            switch(direction) {
                case FORWARD: // move forward
                    coordination.move(FORWARD, pos, step);
                    break;
                case BACKWARD: // move backward
                    coordination.move(BACKWARD, pos, step);
                    break;
                case RIGHT: // move right
                    coordination.move(RIGHT, pos, step);
                    break;
                case LEFT: // move left
                    coordination.move(LEFT, pos, step);
                    break;
                case UP: // move up
                    coordination.move(UP, pos, step);
                    break;
                case DOWN: // move down
                    coordination.move(DOWN, pos, step);
                    break;
            }
        }
    }

    private Vector checkNextPos(Vector pos, MovementEnum direction) {
        Vector nextPos = coordination.move(direction, new Vector(pos), step);
        return nextPos;
    }

    @Override
    public void hitEvent(Collidable beingHit, Player hitter) {
        return;
    }

    @Override
    public void hit(Player hitter) {
        return;
    }
}
