/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package Coordinates;

import Enums.MovementEnum;
import Enums.SteerEnum;
import Utils.Vector;

public interface Coordinates {
    Vector move(MovementEnum direction, Vector pos, float step);
    void rotate(SteerEnum axis, float alpha);
    Vector getxAxis();
    Vector getyAxis();
    Vector getzAxis();
}
