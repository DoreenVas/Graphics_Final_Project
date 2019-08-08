package WorldObjects;

import Utils.Vector;

import javax.media.opengl.GL2;

public class BreakableCube extends Cube{
    // members
    private int hp;

    public BreakableCube(Vector v, float l, int lives) {
        super(v, l);
        this.hp = lives;
    }

    public BreakableCube(Vector v, float l, String texturePath, Type t, int lives) {
        super(v, l, texturePath, t);
        this.hp = lives;
    }

    public int getHp() {
        return this.hp;
    }

    public void draw(GL2 gl) {
        super.draw(gl);
    }
}
