package WorldObjects;

import Collision.Collidable;
import Utils.Vector;

import javax.media.opengl.GL2;

public class Bullet {
    // members
    private Vector direction;
    private Vector p;
    private float width, height, depth;
    private Collidable.Type type;


    public Bullet(Vector leftBottomFront, Vector dir, float width, float height, float depth, String texture, Collidable.Type t) {
        this.p = leftBottomFront;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.type = t;
        this.direction = dir;
    }

    public void draw(GL2 gl) {
        move();
        float angle = this.p.getAngleWithVector(this.direction);
        gl.glPushMatrix();
        gl.glRotated(angle, 0.0f, 0.0f, 1.0f);
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
        gl.glPopMatrix();
    }

    public void move() {
        p.setX(p.getX() - this.direction.getX());
        p.setY(p.getY() + this.direction.getY());
        p.setZ(p.getZ() - this.direction.getZ());
    }
}
