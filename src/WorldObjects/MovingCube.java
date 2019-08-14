package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Collision.CollisionHandler;
import Enums.MovementEnum;
import Utils.Vector;
import javax.media.opengl.GL2;
import java.util.List;

public class MovingCube extends Cube implements Collidable {
    // members
    private float step;
    private MovementEnum direction;

    public MovingCube(Vector v, float l, String texturePath, Type t, float s, MovementEnum dir) {
        super(v, l, texturePath, t);
        this.step = s;
        this.direction = dir;
    }

    public void draw(GL2 gl) {
        this.step = moveCube(this.direction);
        super.draw(gl);
    }


    public float moveCube(MovementEnum direction) {
        boolean collide = false;
        Vector nextPos = checkNextPos(step, super.getOrigin(), direction);
        // check collision with player
        collide = CollisionDetector.point_cube(Player.getPos(), new Cube(nextPos, length));
        if(collide) {
            World.removeFromList(this);
            CollisionHandler.lose();
            return step;
        }

        collisionWithBoxes();
        if(CollisionDetector.AABB_walls(this)){
            changeStep();
        }

        switch (direction) {
            case DOWN:
            case UP:
                o.setY(o.getY()+step);
                break;
            case LEFT:
            case RIGHT:
                o.setX(o.getX()+step);
                break;
            case FORWARD:
            case BACKWARD:
                o.setZ(o.getZ()+step);
                break;
        }
        return step;
    }

    private void collisionWithBoxes() {
        boolean collide;
        List<Cube> arr = World.getItemsList();
        for (Cube c : arr) {
            if (c == this) { continue;}
            collide = CollisionDetector.AABB_AABB(this, c);
            if (collide) {
                this.changeStep();
                if (c instanceof MovingCube) {
                    ((MovingCube) c).changeStep();
                }
            }
        }
    }

    private Vector checkNextPos(float step, Vector pos, MovementEnum direction) {
        Vector newPos = new Vector(pos);
        switch (direction) {
            case FORWARD:
                newPos.setZ(newPos.getZ() - step);
                break;
            case BACKWARD:
                newPos.setZ(newPos.getZ() + step);
                break;
            case UP:
                newPos.setY(newPos.getY() + step);
                break;
            case DOWN:
                newPos.setY(newPos.getY() - step);
                break;
            case RIGHT:
                newPos.setX(newPos.getX() + step);
                break;
            case LEFT:
                newPos.setX(newPos.getX() - step);
                break;
        }
        return newPos;
    }

    public void changeStep() {
        this.step = this.step * -1;
    }

    @Override
    public void hit(Player hitter) {
        return;
    }
}
