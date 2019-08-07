/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;

import Collision.Collidable;
import Coordinations.Coordination;
import Enums.MovementEnum;
import Enums.SteerEnum;
import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;

public class BlockWall implements WorldObject, Collidable {
    // members
    private Texture wallTexture;
//    private Vector[] vertexes;
    private Vector p;
    private float width, height, depth;
    private Type type;

//    public BlockWall(Vector p1, Vector p2, Vector p3, Vector p4, Vector p5, Vector p6, Vector p7, Vector p8, String texture, Type t) {
//        try {
//            vertexes = new Vector[8];
//            vertexes[0] = p1;
//            vertexes[1] = p2;
//            vertexes[2] = p3;
//            vertexes[3] = p4;
//            vertexes[4] = p5;
//            vertexes[5] = p6;
//            vertexes[6] = p7;
//            vertexes[7] = p8;
//            wallTexture= TextureIO.newTexture(new File( texture ),true);
//            type = t;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    public BlockWall(Vector leftBottomFront, float width, float height, float depth, String texture, Type t) {
        this.p = leftBottomFront;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.type = t;
        try {
            wallTexture= TextureIO.newTexture(new File( texture ),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();

        wallTexture.bind(gl);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(p.getX(), p.getY(), p.getZ());
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ());
        gl.glTexCoord2f(1f, 1.0f);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ());
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ());
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(p.getX(), p.getY(), p.getZ()-depth);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ()-depth);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ()-depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ()-depth);
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ()-depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ());
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ());
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ()-depth);
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(p.getX(), p.getY(), p.getZ()-depth);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ()-depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ());
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(p.getX(), p.getY(), p.getZ());
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ()-depth);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ()-depth);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(p.getX()+width, p.getY()+height, p.getZ());
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(p.getX()+width, p.getY(), p.getZ());
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(p.getX(), p.getY(), p.getZ()-depth);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(p.getX(), p.getY(), p.getZ());
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ());
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(p.getX(), p.getY()+height, p.getZ()-depth);

        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

        gl.glPopMatrix();
        gl.glFlush();
    }

    public Type getType() {
        return this.type;
    }

//    public Vector[] getVertexes() {
//        return vertexes;
//    }

    @Override
    public void activateMove(MovementEnum direction) {

    }

    @Override
    public void activateRotate(SteerEnum rotateDirection) {

    }

    @Override
    public Vector getLocation() {
        return null;
    }

    @Override
    public Coordination getCoordination() {
        return null;
    }

    @Override
    public void hit(Player hitter) {
        return;
    }

//    public Vector getCenter() {
//        Vector c;
//        c = new Vector(vertexes[0].add(vertexes[2].multByScalar(0.5f)));
//        return c;
//    }


    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDepth() {
        return depth;
    }

    public boolean checkInside(Vector other) {
        if (this.p.getX() < other.getX() && this.p.getX() + width > other.getX() &&
            this.p.getY() < other.getY() && this.p.getY() + height > other.getY() &&
            this.p.getZ() > other.getZ() && this.p.getZ() - depth < other.getZ()) {
            return true;
        }
        return false;
    }
}
