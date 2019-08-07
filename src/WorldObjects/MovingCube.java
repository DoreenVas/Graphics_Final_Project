package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Utils.Vector;

import javax.media.opengl.GL2;

public class MovingCube extends Cube implements Collidable {
    // members
//    private Cube cube;
    private float step;
    private Type type = Type.stay;

    public MovingCube(Vector v, float l, String texturePath, Type t, float s) {
        super(v, l, texturePath, t);
        this.step = s;
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
//        collide = CollisionDetector.checkItemsCollisions(nextPos);
//        if (collide) {
//            step = step * -1;
//        }
        this.step = super.moveCube(this.step);
        super.draw(gl);
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
