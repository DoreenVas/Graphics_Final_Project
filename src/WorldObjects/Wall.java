/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;

import Coordinations.Coordination;
import Enums.MovementEnum;
import Enums.SteerEnum;
import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;

public class Wall implements WorldObject {
    // members
    private Texture wallTexture;
    private Vector[] vertexes;

    public Wall(Vector p1, Vector p2, Vector p3, Vector p4, String texture) {
        try {
            vertexes = new Vector[4];
            vertexes[0] = p1;
            vertexes[1] = p2;
            vertexes[2] = p3;
            vertexes[3] = p4;
            wallTexture= TextureIO.newTexture(new File( texture ),true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();

        wallTexture.bind(gl);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(1f, 1.0f);
        gl.glVertex3f(vertexes[0].getX(), vertexes[0].getY(), vertexes[0].getZ());
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(vertexes[1].getX(), vertexes[1].getY(), vertexes[1].getZ());
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(vertexes[2].getX(), vertexes[2].getY(), vertexes[2].getZ());
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(vertexes[3].getX(), vertexes[3].getY(), vertexes[3].getZ());

        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

        gl.glPopMatrix();
        gl.glFlush();
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
