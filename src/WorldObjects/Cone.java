package WorldObjects;

import javax.media.opengl.GL2;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Cone {
    private float x, y, z;
    private float base;
    private float height;

    private int slices = 30;
    private int stacks = 30;

    private boolean fill = true;
    private boolean stroke = true;

    /**
     * Creates a new Cone object using base size and height.
     *
     * @param base The base size of the Cone.
     * @param height The height of the Cone.
     */
    public  Cone(float base, float height, boolean fill, boolean stroke) {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.fill = fill;
        this.stroke = stroke;
        this.base = base;
        this.height = height;
//        this.setThreeD(true);
    }

    /**
     * Creates a new Cone object using base size, height, slices and stacks.
     *
     * @param base The base size of the Cone.
     * @param height The height of the Cone.
     * @param slices The slices of the Cone.
     * @param stacks The stacks of the Cone.
     */
    public  Cone(float base, float height, int slices, int stacks, boolean fill, boolean stroke) {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.base = base;
        this.height = height;
        this.slices = slices;
        this.stacks = stacks;
        this.fill = fill;
        this.stroke = stroke;
//        this.setThreeD(true);
    }

    /**
     * Creates a new Cone object using base size, height and x,y,z-coordinate.
     *
     * @param base The base size of the Cone.
     * @param height The height of the Cone.
     * @param x The x-coordinate of the Cone.
     * @param y The y-coordinate of the Cone.
     * @param z The z-coordinate of the Cone.
     */
    public  Cone(float base, float height, float x, float y, float z, boolean fill, boolean stroke) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.base = base;
        this.height = height;
        this.fill = fill;
        this.stroke = stroke;
//        this.setThreeD(true);
    }

    /**
     * Creates a new Cone object using base size, height, slices, stacks, and x,y,z-coordinate.
     *
     * @param base The base size of the Cone.
     * @param height The height of the Cone.
     * @param x The x-coordinate of the Cone.
     * @param y The y-coordinate of the Cone.
     * @param z The z-coordinate of the Cone.
     * @param slices The slices of the Cone.
     * @param stacks The stacks of the Cone.
     */
    public  Cone(float base, float height, float x, float y, float z, int slices, int stacks, boolean fill, boolean stroke) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.slices = slices;
        this.stacks = stacks;
        this.base = base;
        this.height = height;
        this.fill = fill;
        this.stroke = stroke;
//        this.setThreeD(true);
    }

    public  void draw(GL2 gl, GLU glu) {

        gl.glPushMatrix();
        {
            this.move(gl);

            gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
            gl.glPolygonOffset(1f, 1f);

            gl.glPushMatrix();
            {
                gl.glRotated(90.0, -1.0, 0.0, 0.0);
                if (this.fill) {
                    gl.glColor3f(0.2f, 0.2f, 0.2f);
                    drawSolidCone(glu, base, getHeight(), slices, stacks);
                } else if (this.stroke) {
                    gl.glColor3f(0.2f, 0.2f, 0.2f);
                    drawWireCone(glu, base, getHeight(), slices, stacks);
                }
            }
            gl.glPopMatrix();

            gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);
        }
        gl.glPopMatrix();

//        if ((this.fillColor.getAlpha() < 0.001 || this.strokeColor.getAlpha() < 0.001 || !this.isDepthTest()) &&
//            !this.isThreeD()) {
//            gl.glEnable(GL2.GL_DEPTH_TEST);
//        }
    }

    private GLUquadric quadObj;

    private  void quadObjInit(GLU glu) {
        if (quadObj == null) {
            quadObj = glu.gluNewQuadric();
        }
        if (quadObj == null) {
            throw new GLException("Out of memory");
        }
    }

    protected  void move(GL2 gl) {
        gl.glTranslated(x, y, z);
        gl.glScaled(1, 1, 1);
        gl.glRotated(0,  0.0, 0.0, 1.0);
        gl.glRotated(0, 1.0, 0.0, 0.0);
        gl.glRotated(0, 0.0, 1.0, 0.0);
    }

    private  void drawWireCone(GLU glu, float base, float height, int slices, int stacks) {
        quadObjInit(glu);
        glu.gluQuadricDrawStyle(quadObj, GLU.GLU_LINE);
        glu.gluQuadricNormals(quadObj, GLU.GLU_SMOOTH);
        glu.gluCylinder(quadObj, base, 0.0, height, slices, stacks);
    }

    private  void drawSolidCone(GLU glu, double base, double height, int slices, int stacks) {
        quadObjInit(glu);
        glu.gluQuadricDrawStyle(quadObj, GLU.GLU_FILL);
        glu.gluQuadricNormals(quadObj, GLU.GLU_SMOOTH);
        glu.gluCylinder(quadObj, base, 0.0, height, slices, stacks);
    }

    /**
     * Gets the base size of this Cone.
     *
     * @return The base size of the Cone.
     */
    public  double getBase() {
        return base;
    }

    /**
     * Sets the base size of this Cone.
     *
     * @param base The base size of the Cone.
     */
    public  void setBase(float base) {
        this.base = base;
    }

    /**
     * Gets the height of this Cone.
     *
     * @return The height of the Cone.
     */
    public  float getHeight() {
        return height;
    }

    /**
     * Sets the height of this Cone.
     *
     * @param height The height of the Cone.
     */
    public  void setHeight(float height) {
        this.height = height;
    }

    /**
     * Sets the slices of this Cone.
     *
     * @param slices The slices of the Cone.
     */
    public  void setSlices(int slices) {
        this.slices = slices;
    }

    /**
     * Set the stacks of this Cone.
     *
     * @param stacks The stacks of the Cone.
     */
    public  void setStacks(int stacks) {
        this.stacks = stacks;
    }

    /**
     * Gets the slices of this Cone.
     *
     * @return The slices of the Cone.
     */
    public  int getSlices() {
        return this.slices;
    }

    /**
     * Gets the stacks of this Cone.
     *
     * @return The stacks of the Cone.
     */
    public  int getStacks() {
        return this.stacks;
    }
}