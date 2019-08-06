/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;

import Coordinations.Coordination;
import Enums.MovementEnum;
import Enums.SteerEnum;
import Utils.Vector;

import javax.media.opengl.GL2;
import java.util.ArrayList;

public interface WorldObject {
    ArrayList<Cube> itemsList = new ArrayList<>();

    void draw(GL2 gl);
    void activateMove(MovementEnum direction);
    void activateRotate(SteerEnum rotateDirection);
    Vector getLocation();
    Coordination getCoordination();

}
