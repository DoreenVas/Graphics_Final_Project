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
    private Type type = Type.stay;
    private MovementEnum direction;

    public MovingCube(Vector v, float l, String texturePath, Type t, float s, MovementEnum dir) {
        super(v, l, texturePath, t);
        this.step = s;
        this.direction = dir;
    }

    public void draw(GL2 gl) {
        this.step = moveCube(this.step, this.direction);
        super.draw(gl);
    }


    public float moveCube(float step, MovementEnum direction) {
        boolean collide = false;
        Vector nextPos = checkNextPos(step, super.getOrigin(), direction);
        // check collision with player
        collide = CollisionDetector.point_cube(Player.getPos(), new Cube(nextPos, length));
        if(collide) {
            // game over!
            World.removeFromList(this);
            CollisionHandler.lose();
        }

        collisionWithBoxes();
//        collisionWithWalls();

        switch (direction) {
            case DOWN:
            case UP:
                if (o.getY()-1 < -1 || o.getY()+length+1 > 10) {
                    step = step * -1;
                }
                o.setY(o.getY()+step);
                break;
            case LEFT:
            case RIGHT:
                if (o.getX()-1 < -10 || o.getX()+length+1 > 10) {
                    step = step * -1;
                }
                o.setX(o.getX()+step);
                break;
            case FORWARD:
            case BACKWARD:
                if (o.getZ()-1 < -10 || o.getZ()+length+1 > 10) {
                    step = step * -1;
                }
                o.setZ(o.getZ()+step);
                break;
        }
        return step;
    }

    private void collisionWithWalls() {
        boolean collide;
        List<BlockWall> walls = World.getWalls();
        for (BlockWall w : walls) {
            collide = CollisionDetector.cube_cube(w, this);
            if (collide) {
                this.changeStep();
//                break;
            }
        }
    }

    private void collisionWithBoxes() {
        boolean collide;
        List<Cube> arr = World.getItemsList();
        for (Cube c : arr) {
            if (c == this) { continue;}
            collide = CollisionDetector.cube_cube(this, c);
            if (collide) {
                this.changeStep();
                if (c instanceof MovingCube) {
                    ((MovingCube) c).changeStep();
                }
//                break;
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
