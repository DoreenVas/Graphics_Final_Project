package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Enums.MovementEnum;
import Utils.Vector;

import javax.media.opengl.GL2;

public class MovingCube extends Cube implements Collidable {
    // members
//    private Cube cube;
    private float step;
    private Type type = Type.stay;
    private MovementEnum direction;

    public MovingCube(Vector v, float l, String texturePath, Type t, float s, MovementEnum dir) {
        super(v, l, texturePath, t);
        this.step = s;
        this.direction = dir;
    }

//    public MovingCube(Cube c) {
//        this.cube = c;
//    }
//
//    public void draw(GL2 gl) {
//        this.step = this.cube.moveCube(this.step);
//        this.cube.draw(gl);
//    }
//
//    public Cube getCube() {
//        return this.cube;
//    }

    public void draw(GL2 gl) {
        boolean collide;
//        Vector nextPos = checkNextPos(step, super.getOrigin());
//        collide = CollisionDetector.cube_cube(this);
//        if (collide) {
//            step = step * -1;
//        }
        this.step = moveCube(this.step, this.direction);
        super.draw(gl);
    }


    public float moveCube(float step, MovementEnum direction) {
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


    private Vector checkNextPos(float step, Vector pos) {
        Vector newPos = new Vector(pos);
        newPos.setX(newPos.getX() + step);
        return newPos;
    }

    @Override
    public void hit(Player hitter) {
        return;
    }
}