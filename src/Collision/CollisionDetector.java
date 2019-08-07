package Collision;

import Scene.Sounds;
import Utils.Vector;
import WorldObjects.Cube;
import WorldObjects.Wall;
import WorldObjects.World;
import java.util.ArrayList;

public class CollisionDetector {
    /*****
     * Check collision between a cube and a point.
     * @param point a given point.
     * @param cube a given cube.
     * @return returns true if there is a collision, otherwise returns false.
     */
    public static boolean point_cube(Vector point, Cube cube){
        Vector p1, p2, p3, p4;
        float threshold = 130.0f;
        Vector[] arr = cube.getVertexes();
        float angle = 0;
        // check front
        p1 = arr[0];
        p2 = arr[1];
        p3 = arr[2];
        p4 = arr[3];
        // get the sum of angles between the point and all vertexes
        angle = sumAngles(point, p1, p2, p3 ,p4);
        if(angle > 360 - threshold && angle < 360 + threshold) {
            System.out.println("Collision front wall");
            return true;
        }
        // check back
        p1 = arr[4];
        p2 = arr[5];
        p3 = arr[6];
        p4 = arr[7];
        // get the sum of angles between the point and all vertexes
        angle = sumAngles(point, p1, p2, p3 ,p4);
        if(angle > 360 - threshold && angle < 360 + threshold) {
            System.out.println("Collision back wall");
            return true;
        }
        // check left
        p1 = arr[4];
        p2 = arr[0];
        p3 = arr[3];
        p4 = arr[5];
        // get the sum of angles between the point and all vertexes
        angle = sumAngles(point, p1, p2, p3 ,p4);
        if(angle > 360 - threshold && angle < 360 + threshold) {
            System.out.println("Collision left wall");
            return true;
        }
        //check right
        p1 = arr[7];
        p2 = arr[6];
        p3 = arr[2];
        p4 = arr[1];
        // get the sum of angles between the point and all vertexes
        angle = sumAngles(point, p1, p2, p3 ,p4);
        if(angle > 360 - threshold && angle < 360 + threshold) {
            System.out.println("Collision right wall");
            return true;
        }
        //check up
        p1 = arr[5];
        p2 = arr[3];
        p3 = arr[2];
        p4 = arr[6];
        // get the sum of angles between the point and all vertexes
        angle = sumAngles(point, p1, p2, p3 ,p4);
        if(angle > 360 - threshold && angle < 360 + threshold) {
            System.out.println("Collision up wall");
            return true;
        }
        //check down
        p1 = arr[4];
        p2 = arr[7];
        p3 = arr[1];
        p4 = arr[0];
        // get the sum of angles between the point and all vertexes
        angle = sumAngles(point, p1, p2, p3 ,p4);
        if(angle > 360 - threshold && angle < 360 + threshold) {
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

    public static boolean checkCollisions(Vector point) {
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

