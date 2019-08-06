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
import com.jogamp.opengl.util.gl2.GLUT;

import javax.media.opengl.GL2;

public class Sphere implements WorldObject {
    // members
    private Vector position;
    private float radius;

    public Sphere(float radius, Vector pos) {
        this.position = pos;
        this.radius = radius;
    }

    public Sphere(float radius, float x, float y, float z) {
        this.position = new Vector(x, y, z);
        this.radius = radius;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(position.getX(), position.getY(), position.getZ());

        GLUT glut = new GLUT();

        float no_mat[] = {0.0f, 1.0f, 0.0f, 1.0f};
        float mat_diffuse[] = {0.1f, 0.5f, 0.8f, 1.0f};
        float high_shininess[] = {80.0f};
        float mat_specular[] = {0.8f, 0.8f, 0.8f, 1.0f};
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, no_mat, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, mat_specular, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, high_shininess, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, no_mat, 0);
        glut.glutSolidSphere(radius,50,50);

        gl.glFlush();
        gl.glPopMatrix();
    }

    @Override
    public void activateMove(MovementEnum direction) {

    }

    @Override
    public void activateRotate(SteerEnum rotateDirection) {

    }

    @Override
    public Vector getLocation() {
        return position;
    }

    @Override
    public Coordination getCoordination() {
        return null;
    }
}
