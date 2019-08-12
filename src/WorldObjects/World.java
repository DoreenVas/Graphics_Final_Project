/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;
import Collision.Collidable;
import Enums.MovementEnum;
import Utils.Vector;
import View.ViewManager;
import javax.media.opengl.GL2;
import javax.media.opengl.GLContext;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/***
 * singleton
 */
public class World {
    // members
    private static World world;
    private static ArrayList<Cube> itemsList;
    private static ArrayList<BlockWall> walls;
    private static ArrayList<BreakableCube> breakWall;

    /**
     * Returns the classes instance.
     * @return the classes current instance
     * @throws IOException thrown from inner function
     * @throws SQLException thrown from inner function
     */
    public static World getInstance(){
        if(world == null) {
            world = new World();
        }
        return world;
    }

    private World() {
        itemsList = new ArrayList<>();
        walls = new ArrayList<>();
        breakWall = new ArrayList<>();
        createLevel1();
        createLevel2();
    }

    public void draw(GL2 gl) {
        gl.glPushMatrix();

        addLightLevel1(gl);
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

        for (int i = 0; i<itemsList.size(); i++){
            itemsList.get(i).draw(gl);
        }
    }

    private void createLevel1() {
        createWallsLevel1();
//        createItemsLevel1();
    }

    private void createWallsLevel1() {
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
        walls.add(new BlockWall(new Vector(-11,-2,25),
                22,13,1,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));
        // back wall
        walls.add(new BlockWall(new Vector(-11,-2,-75),
                22,13,1,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));
        // right wall
        walls.add(new BlockWall(new Vector(10,-2,25),
                1,13,90,
                "resources/pics/steel-box.jpg",
                Collidable.Type.stay));
        // left wall
        walls.add(new BlockWall(new Vector(-11,-2,25),
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
                40,1,10,
                "resources/pics/cave.jpg",
                Collidable.Type.stay));
        // back wall
        walls.add(new BlockWall(new Vector(50,-2,-65),
                1,13,11,
                "resources/pics/portal3.jpg",
                Collidable.Type.portal));
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

    private void createItemsLevel1() {
        Cube tnt1 = new Cube(new Vector(-9,-1,1),2,"resources/pics/tnt.jpg", Collidable.Type.tnt);
        Cube tnt2 = new Cube(new Vector(-1,-1,1),2,"resources/pics/tnt.jpg", Collidable.Type.tnt);
        Cube tnt3 = new Cube(new Vector(7,-1,1),2,"resources/pics/tnt.jpg", Collidable.Type.tnt);

        MovingCube movingCube1 = new MovingCube(new Vector(-9, -1, -30), 2,
                "resources/pics/moving_box.png", Collidable.Type.stay, 0.1f, MovementEnum.RIGHT);
        MovingCube movingCube2 = new MovingCube(new Vector(7, -1, -30), 2,
                "resources/pics/moving_box.png", Collidable.Type.stay, -0.1f, MovementEnum.LEFT);
        MovingCube movingCube3 = new MovingCube(new Vector(-9, -1, -20), 2,
                "resources/pics/moving_box.png", Collidable.Type.stay, 0.1f, MovementEnum.RIGHT);
        MovingCube movingCube4 = new MovingCube(new Vector(7, -1, -10), 2,
                "resources/pics/moving_box.png", Collidable.Type.stay, -0.1f, MovementEnum.LEFT);

        itemsList.add(tnt1);
        itemsList.add(tnt2);
        itemsList.add(tnt3);
        itemsList.add(movingCube1);
        itemsList.add(movingCube2);
        itemsList.add(movingCube3);
        itemsList.add(movingCube4);
        for (int i = -10; i < 10; i+=2) {
            itemsList.add(new MovingCube(new Vector(i, 7, -45), 2,
                    "resources/pics/moving_box.png", Collidable.Type.stay, 0.05f, MovementEnum.DOWN));
        }

        Vector p;
        for (int z = -65; z > -75; z-=2) {
            for (int y = -1; y < 9; y+=2) {
                p = new Vector(10, y, z);
                itemsList.add(new BreakableCube(p, 2, 3, "resources/pics/box.jpg", Collidable.Type.breakable));
            }
        }
    }

    private void createLevel2() {
        createWallsLevel2();
    }

    private void createWallsLevel2() {
        // floor
        walls.add(new BlockWall(new Vector(-45,-2f,-80f),
                90,1,90,
                "resources/pics/dark_floor.jpg",
                Collidable.Type.stay));
        // ceiling
        walls.add(new BlockWall(new Vector(-45,50,-80f),
                90,1,90,
                "resources/pics/stars.jpg",
                Collidable.Type.stay));
        // front wall
        walls.add(new BlockWall(new Vector(-45,-2,-80f),
                90,52,1,
                "resources/pics/fire_texture.jpg",
                Collidable.Type.stay));
        // back wall
        walls.add(new BlockWall(new Vector(-45,-2,-170),
                90,52,1,
                "resources/pics/fire_texture.jpg",
                Collidable.Type.stay));
        // right wall
        walls.add(new BlockWall(new Vector(44,-2,-80),
                1,52,90,
                "resources/pics/fire_texture.jpg",
                Collidable.Type.stay));
        // left wall
        walls.add(new BlockWall(new Vector(-44,-2,-80),
                1,52,90,
                "resources/pics/fire_texture.jpg",
                Collidable.Type.stay));

    }

    private void addLightLevel1(GL2 gl) {

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

    public static void removeFromList(Cube c) {
        itemsList.remove(c);
    }

    public static ArrayList<BlockWall> getWalls() {
        return walls;
    }

    public void moveToLevel2() {
        Player.setPos(new Vector(0f, 0.5f, -85f));
        Player.resetCoordiantions();
    }
}
