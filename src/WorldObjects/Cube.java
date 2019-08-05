/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;

import Coordinations.Coordination;
import Movement.MovementEnum;
import Steer.SteerEnum;
import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;

public class Cube implements WorldObject {
    // members
    private Texture cubeTexture;
    private Vector o; //left bottom corner close to view
    private float length;
    float step = 0.1f;

    public Cube(Vector v, float l, String texturePath) {
        try {
            o = v;
            length = l;
            String cubeTextureFile = texturePath; // the FileName to open
            cubeTexture= TextureIO.newTexture(new File( cubeTextureFile ),true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(GL2 gl) {
        // update position
        //moveCube();

        gl.glPushMatrix();

        cubeTexture.bind(gl);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ());
        gl.glTexCoord2f(2f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ());
        gl.glTexCoord2f(2f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ());
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ());
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ()-length);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ()-length);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ()-length);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ()-length);
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ()-length);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ());
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ());
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ()-length);
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ()-length);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ()-length);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ());
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ());
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ()-length);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ()-length);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ());
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ());
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ()-length);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ());
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ());
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ()-length);

        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

        gl.glPopMatrix();
        gl.glFlush();
    }

    private void moveCube() {
        if (o.getX() < -20 || o.getX() > 20) {
            step = step * -1;
        }
        o.setX(o.getX()+step);
    }

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
}
