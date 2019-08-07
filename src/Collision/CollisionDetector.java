package Collision;

import Scene.Sounds;
import Utils.Vector;
import WorldObjects.*;

import java.util.ArrayList;

public class CollisionDetector {
    // memebers
    private static float itemsThreshold = 130.0f;
    private static float wallsThreshold = 1.5f;

    /*****
     * Check collision between a cube and a point.
     * @param point a given point.
     * @param cube a given cube.
     * @return returns true if there is a collision, otherwise returns false.
     */
    public static boolean point_cube(Vector point, Cube cube){
        Vector[] arr = cube.getVertexes();
        // check front
        if (point_polygon(point, arr[0], arr[1], arr[2], arr[3], itemsThreshold)) {
            System.out.println("Collision front wall");
            return true;
        }
        // check back
        if (point_polygon(point, arr[4], arr[5], arr[6], arr[6], itemsThreshold)) {
            System.out.println("Collision back wall");
            return true;
        }
        // check left
        if (point_polygon(point, arr[4], arr[0], arr[3], arr[5], itemsThreshold)) {
            System.out.println("Collision left wall");
            return true;
        }
        //check right
        if (point_polygon(point, arr[7], arr[6], arr[2], arr[1], itemsThreshold)) {
            System.out.println("Collision right wall");
            return true;
        }
        //check up
        if (point_polygon(point, arr[5], arr[3], arr[2], arr[6], itemsThreshold)) {
            System.out.println("Collision up wall");
            return true;
        }
        //check down
        if (point_polygon(point, arr[4], arr[7], arr[1], arr[0], itemsThreshold)) {
            System.out.println("Collision down wall");
            return true;
        }
        return false;
    }

    /*****
     * Get the angle between 2 vertexes for collision algorithm.
     * @param v1 first vertex
     * @param v2 second vertex
     * @return The angle (degrees)
     */
    private static float getAngle(Vector v1, Vector v2) {
        float size1 = v1.getVectorSize(), size2 = v2.getVectorSize();
        float angle = (float)Math.acos(v1.dotProduct(v2)/(size1 * size2));
        return (float)Math.toDegrees(angle);
    }

    /*****
     * Sum the angles between the current point and 4 vertexes
     * of the cube face.
     * @param point current point
     * @param p1 first vertex
     * @param p2 second vertex
     * @param p3 third vertex
     * @param p4 fourth vertex
     * @return the sum of angles
     */
    private static float sumAngles(Vector point, Vector p1, Vector p2, Vector p3, Vector p4) {
        float angles = 0;
        angles += getAngle(point.sub(p1), point.sub(p2));
        angles += getAngle(point.sub(p2), point.sub(p3));
        angles += getAngle(point.sub(p3), point.sub(p4));
        angles += getAngle(point.sub(p4), point.sub(p1));
        return angles;
    }

    public static boolean checkItemsCollisions(Vector point) {
        ArrayList<Cube> itemsList = World.getItemsList();
        for(Cube c : itemsList) {
            if(point_cube(point, c)) {
                if(c.getType()== Collidable.Type.vanish) {
                    Sounds.makeSound("resources/sounds/explosion.wav");
                    World.removeFromList(c);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean point_polygon(Vector point, Vector p1, Vector p2, Vector p3, Vector p4, float threshold) {
        float angle = 0;
        // get the sum of angles between the point and all vertexes
        angle = sumAngles(point, p1, p2, p3 ,p4);
        if(angle > 360 - threshold && angle < 360 + threshold) {
            return true;
        }
        return false;
    }

    public static boolean checkCollisionWithWalls(Vector point) {
//        Wall walls[] = World.getWalls();
        BlockWall walls[] = World.getWalls();
//        for (Wall w : walls) {
//            Vector[] vertexes = w.getVertexes();
//            if (point_polygon(point, vertexes[0], vertexes[1], vertexes[2], vertexes[3], wallsThreshold)) {
//                return true;
//            }
//        }
        for (BlockWall w : walls) {
            if (w.checkInside(point)) {
                return true;
            }
        }
        return false;
    }

    public static boolean cube_cube(Cube cube1, Cube cube2) {
        Vector c1, c2, d;
        float dist = (cube1.getLength()+cube2.getLength())/2;
        c1 = cube1.getCenter();
        c2 = cube2.getCenter();
        d = c1.sub(c2);
        if (d.getX() < dist && d.getY() < dist && d.getZ() < dist) {
            return true;
        }
        return false;
    }
}

