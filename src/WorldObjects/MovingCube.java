package WorldObjects;

import Collision.Collidable;
import Utils.Vector;

import javax.media.opengl.GL2;

public class MovingCube extends Cube implements Collidable {
    // members
//    private Cube cube;
    private float step;

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
        this.step = super.moveCube(this.step);
        super.draw(gl);
    }

    @Override
    public void hit(Player hitter) {
        return;
    }
}
