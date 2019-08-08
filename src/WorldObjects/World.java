/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;
import Collision.Collidable;
import Utils.Vector;
import javax.media.opengl.GL2;
import java.util.ArrayList;

public class World {
    // members
    private static ArrayList<Cube> itemsList;
    private static ArrayList<BlockWall> walls;
    private static ArrayList<BreakableCube> breakWall;

    public World() {
        itemsList = new ArrayList<>();
        walls = new ArrayList<>();
        breakWall = new ArrayList<>();
        createWalls();
    }

    public void draw(GL2 gl) {
        gl.glPushMatrix();

        addLight(gl);
        gl.glTexParameteri ( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT );
        gl.glTexParameteri( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT );

        float mat_ambient[] = {1f, 1f, 1f, 1.0f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);

        for (BlockWall w : walls) {
            w.draw(gl);
        }

        for (BreakableCube b : breakWall) {
            b.draw(gl);
        }

        for (Cube c : itemsList) {
            c.draw(gl);
        }
    }

    private void createWalls() {
        // floor
        walls.add(new BlockWall(new Vector(-11f,-2f,25f),
                22,1,100,
                "resources/pics/floor.jpeg",
                Collidable.Type.stay));
        // ceiling
        walls.add(new BlockWall(new Vector(-11f,10f,25f),
                22,1,100,
                "resources/pics/cave.jpg",
                Collidable.Type.stay));
        // front wall
        walls.add(new BlockWall(new Vector(-10,-2,25),
                20,13,1,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));
        // back wall
        walls.add(new BlockWall(new Vector(-10,-2,-75),
                20,13,1,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));
        // right wall
        walls.add(new BlockWall(new Vector(10,-2,25),
                1,13,100,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));
        // left wall
        walls.add(new BlockWall(new Vector(-10,-2,25),
                1,13,100,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));

        // after turn
        // floor
        walls.add(new BlockWall(new Vector(10f,-2f,-65f),
                40,1,20,
                "resources/pics/floor.jpeg",
                Collidable.Type.stay));
        // ceiling
        walls.add(new BlockWall(new Vector(10f,11f,-65f),
                40,1,20,
                "resources/pics/cave.jpg",
                Collidable.Type.stay));
        // back wall
        walls.add(new BlockWall(new Vector(50,-2,-65),
                1,13,20,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));
        // right wall
        walls.add(new BlockWall(new Vector(10,-2,-65),
                40,13,1,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));
        // left wall
        walls.add(new BlockWall(new Vector(10,-2,-75),
                40,13,1,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));

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

    public static void addToItemsList(Cube c) {
        itemsList.add(c);
    }

    public static void removeFromList(Cube c) {
        itemsList.remove(c);
    }

    public static ArrayList<BlockWall> getWalls() {
        return walls;
    }
}
