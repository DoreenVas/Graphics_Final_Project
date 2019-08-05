/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package Coordinations;

import Movement.MovementEnum;
import Steer.SteerEnum;
import Utils.Vector;

public interface Coordination {
    Vector move(MovementEnum direction, Vector pos, float step);
    void rotate(SteerEnum axis, float alpha);
    Vector getxAxis();
    Vector getyAxis();
    Vector getzAxis();
}
