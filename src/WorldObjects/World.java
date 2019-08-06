/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class World {
    // members
    private Texture floorTexture;
    private Texture wallTexture;
    private Texture ceilingTexture;
    private static ArrayList<Cube> itemsList;

    public World() {
        itemsList = new ArrayList<>();
        // define textures
        try {
            String floorTextureFile = "resources/floor.jpeg"; // the FileName to open
            floorTexture = TextureIO.newTexture(new File(floorTextureFile), true);
            String wallTextureFile = "resources/wall.jpg"; // the FileName to open
            wallTexture = TextureIO.newTexture(new File(wallTextureFile), true);
            String ceilingTextureFile = "resources/sky.jpg"; // the FileName to open
            ceilingTexture = TextureIO.newTexture(new File(ceilingTextureFile), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void draw(GL2 gl) {
        gl.glPushMatrix();

        addLight(gl);
        gl.glTexParameteri ( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT );
        gl.glTexParameteri( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT );

        float mat_ambient[] = {1f, 1f, 1f, 1.0f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);


        floorTexture.bind(gl);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        gl.glBegin(GL2.GL_QUADS);
        // floor
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-25f,-1f,-25f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(25f,-1f,-25f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(25f,-1f,25f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-25f,-1f,25f);

        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

        ceilingTexture.bind(gl);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glColor4f(1f, 1f, 1f, 1f);
        gl.glBegin(GL2.GL_QUADS);
        // Ceiling
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-25,40,-25);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(25,40,-25);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(25,40,25);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-25,40,25);

        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

        // Walls
        wallTexture.bind(gl);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glColor4f(1f, 1f, 1f, 1f); //NEEDS to be white before drawing, else stuff will tint.
        gl.glBegin(GL2.GL_QUADS);

        // front wall
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-25,-1,25);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(25,-1,25);
        gl.glTexCoord2f(1f, 1.0f);
        gl.glVertex3f(25,40,25);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-25,40,25);

        // back wall
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-25,-1,-25);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(25,-1,-25);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(25,40,-25);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-25,40,-25);

        // right wall
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(25,40,25);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(25,-1,25);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(25,-1,-25);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(25,40,-25);

        // left wall
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-25,40,25);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-25,-1,25);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-25,-1,-25);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-25,40,-25);

        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

        gl.glPopMatrix();
        gl.glFlush();

        for (Cube c : itemsList) {
            c.draw(gl);
        }
    }

    private void addLight(GL2 gl) {

        // define diffusive purple light
        float diffuse0[] = {1f, 0f, 1f,1.0f};
        float position0[] = {0f, 3f, 3f, 1.0f};
        float direction0[] = {0f, -1f, 1f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position0, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse0, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, diffuse0, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPOT_DIRECTION, direction0, 0);
        gl.glEnable(GL2.GL_LIGHT0);

        // define ambient white light
        float ambient2[] = {0.6f, .6f, 0.6f,1.0f};
        float position2[] = {0f, 200f, 0f, 1.0f};
        float direction2[] = {0f, -1f, 0f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, ambient2, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, position2, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPOT_DIRECTION, direction2, 0);
        gl.glEnable(GL2.GL_LIGHT2);

        gl.glEnable(GL2.GL_NORMALIZE);
    }

    public static ArrayList<Cube> getItemsList() {
        return itemsList;
    }

    public static void addToList(Cube c) {
        itemsList.add(c);
    }

    public static void removeFromList(Cube c) {
        itemsList.remove(c);
    }
}
