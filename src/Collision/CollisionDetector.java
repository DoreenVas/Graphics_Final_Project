package Collision;

import Utils.Vector;
import WorldObjects.Cube;

public class CollisionDetector {

    public boolean point_cube(Vector point, Cube cube){
        // check front

        // check back

        // check left

        //check right

        //check up

        //check down
        return false;
    }


    private float getAngle(Vector v1, Vector v2) {
        float size1 = v1.getVectorSize(), size2 = v2.getVectorSize();
        float angle = (float)Math.acos(v1.dotProduct(v2)/(size1 * size2));
        return angle;
    }

}
