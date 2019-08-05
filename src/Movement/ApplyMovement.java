/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package Movement;

import Utils.Vector;

public class ApplyMovement {

    private static float step = 0.1f;

    public static Vector chooseDirection(MovementEnum direction, Vector v, float alpha, float beta) {

        switch (direction) {
            case FORWARD:
                return new Vector(v.getX() + step * (float)Math.cos(alpha), v.getY(), v.getZ() + step * (float)Math.sin(alpha));
            case BACKWARD:
                return new Vector(v.getX() - step * (float)Math.cos(alpha), v.getY(), v.getZ() - step * (float)Math.sin(alpha));
            case LEFT:
                return new Vector(v.getX()  - step * (float)Math.cos(alpha + Math.toRadians(90)), v.getY(), v.getZ() - step * (float)Math.sin(alpha + Math.toRadians(90)));
            case RIGHT:
                return new Vector(v.getX() + step * (float)Math.cos(alpha + Math.toRadians(90)), v.getY(), v.getZ() + step * (float)Math.sin(alpha + Math.toRadians(90)));
            case UP:
                return new Vector(v.getX(), v.getY() + step, v.getZ());
            case DOWN:
                return new Vector(v.getX(), v.getY() - step, v.getZ());
        }
        return null;
    }
}
