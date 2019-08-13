/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;

import Collision.Collidable;
import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;

public class BlockWall implements WorldObject, Collidable {
    // members
    protected Texture wallTexture;
    protected Vector p;
    protected float width, height, depth;
    protected Type type;

    public BlockWall(Vector leftBottomFront, float width, float height, float depth) {
        this(leftBottomFront, width, height, depth,null,null);
    }

    public BlockWall(Vector leftBottomFront, float width, float height, float depth, String texture, Type t) {
        this.p = leftBottomFront;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.type = t;
        if (texture != null) {
            try {
                wallTexture = TextureIO.newTexture(new File(texture), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(GL2 gl) {
//        gl.glPushMatrix();

        wallTexture.bind(gl);
//        gl.glEnable(GL2.GL_TEXTURE_2D);

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
//        gl.glDisable(GL2.GL_TEXTURE_2D);

//        gl.glPopMatrix();
//        gl.glFlush();
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public Vector getLocation() {
        return this.p;
    }

    @Override
    public void hit(Player hitter) {
        return;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDepth() {
        return depth;
    }

    public boolean checkInside(Vector player) {
        float threshold = 1f;
        if (player.getX() >= this.p.getX()-threshold && player.getX() <= this.p.getX()+width+threshold &&
            player.getY() >= this.p.getY()-threshold && player.getY() <= this.p.getY()+height+threshold &&
            player.getZ() <= this.p.getZ()+threshold && player.getZ() >= this.p.getZ()-depth-threshold) {
            return true;
        }
        return false;
    }

    public Vector getCenter() {
        Vector c = new Vector(p.getX() + width/2, p.getY() + height/2, p.getZ() - depth/2);
        return c;
    }
}
