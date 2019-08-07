package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Collision.HitListener;
import Coordinations.Cartesian;
import Coordinations.Coordination;
import Enums.MovementEnum;
import Utils.Vector;

import static Enums.MovementEnum.*;

public class Player implements HitListener, Collidable {
    // members
    private static Vector pos;
    private Vector direction;
    private Vector up;
    private Coordination coordination;
//    private float alpha = (float)Math.toRadians(5);
    private Type type = Type.player;
    private static float step = 0.5f;

    public Player(){
        coordination = new Cartesian();
        pos = new Vector(0.0f, 1.0f, 10.0f);
        up = new Vector(coordination.getyAxis().getX(), coordination.getyAxis().getY(), coordination.getyAxis().getZ());
        direction = new Vector(coordination.getzAxis().getX(), coordination.getzAxis().getY(), coordination.getzAxis().getZ());
    }

    public Coordination getCoordination() {
        return coordination;
    }

    public static Vector getPos() {
        return pos;
    }

    public Vector getDirection() {
        direction = new Vector(coordination.getzAxis().getX(), coordination.getzAxis().getY(), coordination.getzAxis().getZ());
        return direction;
    }

    public Vector getUp() {
        up = new Vector(coordination.getyAxis().getX(), coordination.getyAxis().getY(), coordination.getyAxis().getZ());
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
        itemsCollision = CollisionDetector.checkItemsCollisions(nextPos);
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
