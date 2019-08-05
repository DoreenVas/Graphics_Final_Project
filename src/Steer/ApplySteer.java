/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package Steer;

import Utils.Vector;

public class ApplySteer {

    private static float rotStep = (float)Math.toRadians(10);

    public static Vector chooseDirection(SteerEnum direction, Vector v, float alpha, float beta) {

        switch (direction) {
            case UP_X:
            case DOWN_X:
                return new Vector((float)(Math.cos(beta + Math.toRadians(90) * Math.cos(alpha))),
                        (float) (Math.sin(beta + Math.toRadians(90))),
                        (float) (Math.cos(beta + Math.toRadians(90) * Math.sin(alpha))));
            case LEFT_Y:
                return new Vector(v.getX(), v.getY() + rotStep, v.getZ());
            case RIGHT_Y:
                return new Vector(v.getX(), v.getY() - rotStep, v.getZ());
            case LEFT_Z:
                return new Vector(v.getX(), v.getY(), v.getZ() + rotStep);
            case RIGHT_Z:
                return new Vector(v.getX(), v.getY(), v.getZ() - rotStep);
        }
        return null;
    }

    public static float stepAngle(float angle, int sign) {
        return angle + (sign * rotStep);
    }
}
