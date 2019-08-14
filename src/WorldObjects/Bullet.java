package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Utils.Vector;

import javax.media.opengl.GL2;

public class Bullet {
    // members
    private Vector direction;
    private Vector pos;
    private float width, height, depth;
    private Collidable.Type type;


    public Bullet(Vector leftBottomFront, Vector dir, float width, float height, float depth, String texture, Collidable.Type t) {
        this.pos = leftBottomFront;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.type = t;
        this.direction = dir;
    }

    public void draw(GL2 gl) {
        float angle = pos.getAngleWithVector(this.direction);
        gl.glPushMatrix();
        gl.glRotated(angle, 0.0f, 0.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(0.0f, 0.0f, 0.0f);

        // Front Face
        gl.glVertex3f(pos.getX(), pos.getY(), pos.getZ());
        gl.glVertex3f(pos.getX()+width, pos.getY(), pos.getZ());
        gl.glVertex3f(pos.getX()+width, pos.getY()+height, pos.getZ());
        gl.glVertex3f(pos.getX(), pos.getY()+height, pos.getZ());
        // Back Face
        gl.glVertex3f(pos.getX(), pos.getY(), pos.getZ()-depth);
        gl.glVertex3f(pos.getX(), pos.getY()+height, pos.getZ()-depth);
        gl.glVertex3f(pos.getX()+width, pos.getY()+height, pos.getZ()-depth);
        gl.glVertex3f(pos.getX()+width, pos.getY(), pos.getZ()-depth);
        // Top Face
        gl.glVertex3f(pos.getX(), pos.getY()+height, pos.getZ()-depth);
        gl.glVertex3f(pos.getX(), pos.getY()+height, pos.getZ());
        gl.glVertex3f(pos.getX()+width, pos.getY()+height, pos.getZ());
        gl.glVertex3f(pos.getX()+width, pos.getY()+height, pos.getZ()-depth);
        // Bottom Face
        gl.glVertex3f(pos.getX(), pos.getY(), pos.getZ()-depth);
        gl.glVertex3f(pos.getX()+width, pos.getY(), pos.getZ()-depth);
        gl.glVertex3f(pos.getX()+width, pos.getY(), pos.getZ());
        gl.glVertex3f(pos.getX(), pos.getY(), pos.getZ());
        // Right face
        gl.glVertex3f(pos.getX()+width, pos.getY(), pos.getZ()-depth);
        gl.glVertex3f(pos.getX()+width, pos.getY()+height, pos.getZ()-depth);
        gl.glVertex3f(pos.getX()+width, pos.getY()+height, pos.getZ());
        gl.glVertex3f(pos.getX()+width, pos.getY(), pos.getZ());
        // Left Face
        gl.glVertex3f(pos.getX(), pos.getY(), pos.getZ()-depth);
        gl.glVertex3f(pos.getX(), pos.getY(), pos.getZ());
        gl.glVertex3f(pos.getX(), pos.getY()+height, pos.getZ());
        gl.glVertex3f(pos.getX(), pos.getY()+height, pos.getZ()-depth);

        gl.glEnd();
        gl.glPopMatrix();
        move();
    }

    public void move() {
        pos.setX(pos.getX() - this.direction.getX());
        pos.setY(pos.getY() + this.direction.getY());
        pos.setZ(pos.getZ() - this.direction.getZ());
        World world = World.getInstance();

        if(CollisionDetector.point_walls(this.pos)){
            world.deleteBullet(this);
        }
        Boss boss = world.getBoss();
        if(boss.getAABB().checkInside(this.pos)){
            world.deleteBullet(this);
            boss.gotHit();
        }
    }
}
