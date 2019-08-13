package WorldObjects;

import Collision.Collidable;
import Coordinates.Coordinates;
import Utils.Vector;

import javax.media.opengl.GL2;

public class Bullet {
    // members
    private Coordinates coordinates;
    private Vector p;
    private float width, height, depth;
    private Collidable.Type type;


    public Bullet(Vector leftBottomFront, float width, float height, float depth, String texture, Collidable.Type t, Coordinates c) {
        this.p = leftBottomFront;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.type = t;
        this.coordinates = c;
    }

    public void draw(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(0.0f, 0.0f, 0.0f);

        // Front Face
        gl.glVertex3f(p.getX(), p.getY(), p.getZ());
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ());
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ());
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ());
        // Back Face
        gl.glVertex3f(p.getX(), p.getY(), p.getZ()-depth);
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ()-depth);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ()-depth);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ()-depth);
        // Top Face
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ()-depth);
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ());
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ());
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ()-depth);
        // Bottom Face
        gl.glVertex3f(p.getX(), p.getY(), p.getZ()-depth);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ()-depth);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ());
        gl.glVertex3f(p.getX(), p.getY(), p.getZ());
        // Right face
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ()-depth);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ()-depth);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ());
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ());
        // Left Face
        gl.glVertex3f(p.getX(), p.getY(), p.getZ()-depth);
        gl.glVertex3f(p.getX(), p.getY(), p.getZ());
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ());
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ()-depth);

        gl.glEnd();
    }

    public void move() {
        Vector pos = this.p;
        pos.setX(pos.getX() + this.coordinates.getxAxis().getX());
        pos.setY(pos.getY() + this.coordinates.getxAxis().getY());
        pos.setZ(pos.getZ() + this.coordinates.getxAxis().getZ());
        this.p = pos;
    }
}
