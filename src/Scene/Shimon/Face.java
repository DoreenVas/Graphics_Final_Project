package Scene.Shimon;

import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Face {

    private ArrayList<int[]> fv; //face vertex indices
    private ArrayList<int[]> ft; //face texture indices
    private ArrayList<int[]> fn; //face normal indices
    private int FaceFormat; //format of the faces triangles or quads
    private int FaceMultiplier; //number of possible coordinates per face
    private int PolyCount; //the model polygon count
    //private FloatBuffer modeldata; //buffer which will contain vertice data
    private Texture texture;
    private Material material;

    private boolean init = true;

    private float width;
    private float height;
    private float length;
    private Vector pos;

    private int item;

    /**
     * Default constructor.
     *
     * @param system the object's position
     */
    public Face(Vector system, float width, float height, float length) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.pos = system;
    }

    public void setFv(ArrayList<int[]> fv) {
        this.fv = fv;
    }

    public void setFt(ArrayList<int[]> ft) {
        this.ft = ft;
    }

    public void setFn(ArrayList<int[]> fn) {
        this.fn = fn;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

//    public void setTexturePath(String texturePath) {
//        this.texturePath = texturePath;
//    }

    public void setPolyCount(int polyCount) {
        PolyCount = polyCount;
    }

    public void setFaceMultiplier(int faceMultiplier) {
        FaceMultiplier = faceMultiplier;
    }

    public void setFaceFormat(int faceFormat) {
        FaceFormat = faceFormat;
    }

    private void ConstructInterleavedArray(GL2 inGL, List<float[]> vData, List<float[]> vtData, List<float[]> vnData) {
        final int tv[] = (int[]) fv.get(0);
        final int tt[] = (int[]) ft.get(0);
        final int tn[] = (int[]) fn.get(0);
        //if a value of zero is found that it tells us we don't have that type of data
        if ((tv[0] != 0) && (tt[0] != 0) && (tn[0] != 0)) {
            ConstructTNV(inGL, vData, vtData, vnData); //we have vertex, 2D texture, and normal Data
            //inGL.glInterleavedArrays(GL2.GL_T2F_N3F_V3F, 0, modeldata);
        } else if ((tv[0] != 0) && (tt[0] != 0) && (tn[0] == 0)) {
            ConstructTV(inGL, vData, vtData); //we have just vertex and 2D texture Data
            //inGL.glInterleavedArrays(GL2.GL_T2F_V3F, 0, modeldata);
        } else if ((tv[0] != 0) && (tt[0] == 0) && (tn[0] != 0)) {
            ConstructNV(inGL, vData, vnData); //we have just vertex and normal Data
            //inGL.glInterleavedArrays(GL2.GL_N3F_V3F, 0, modeldata);
        } else if ((tv[0] != 0) && (tt[0] == 0) && (tn[0] == 0)) {
            ConstructV(inGL, vData);
            //inGL.glInterleavedArrays(GL2.GL_V3F, 0, modeldata);
        }
    }

    private void ConstructTNV(GL2 inGL, List<float[]> vData, List<float[]> vtData, List<float[]> vnData) {
        int[] v, t, n;
        float tcoords[] = new float[2]; //only T2F is supported in interLeavedArrays!!
        float coords[] = new float[3];
        int fbSize = PolyCount * (FaceMultiplier * 8); //3v per poly, 2vt per poly, 3vn per poly
        //modeldata = Buffers.newDirectFloatBuffer(fbSize);
        //modeldata.position(0);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            t = (int[]) (ft.get(oloop));
            n = (int[]) (fn.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the texture coordinate data
                for (int tloop = 0; tloop < tcoords.length; tloop++) {
                    //only T2F is supported in interleavedarrays!!
                    tcoords[tloop] = ((float[]) vtData.get(t[iloop] - 1))[tloop];
                }
                //modeldata.put(tcoords);
                inGL.glTexCoord2f(tcoords[0], tcoords[1]);
                //fill in the normal coordinate data
                for (int vnloop = 0; vnloop < coords.length; vnloop++) {
                    coords[vnloop] = ((float[]) vnData.get(n[iloop] - 1))[vnloop];
                }
                //modeldata.put(coords);
                inGL.glNormal3f(coords[0], coords[1], coords[2]);
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++) {
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                }
                //modeldata.put(coords);
                inGL.glVertex3f(coords[0], coords[1], coords[2]);
            }
        }
        //modeldata.position(0);
    }

    private void ConstructTV(GL2 inGL, List<float[]> vData, List<float[]> vtData) {
        int[] v, t;
        float tcoords[] = new float[2]; //only T2F is supported in interLeavedArrays!!
        float coords[] = new float[3];
        int fbSize = PolyCount * (FaceMultiplier * 5); //3v per poly, 2vt per poly
        //modeldata = Buffers.newDirectFloatBuffer(fbSize);
        //modeldata.position(0);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            t = (int[]) (ft.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the texture coordinate data
                for (int tloop = 0; tloop < tcoords.length; tloop++) {
                    //only T2F is supported in interleavedarrays!!
                    tcoords[tloop] = ((float[]) vtData.get(t[iloop] - 1))[tloop];
                }
                //modeldata.put(tcoords);
                inGL.glTexCoord2f(tcoords[0], tcoords[1]);
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++) {
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                }
                //modeldata.put(coords);
                inGL.glVertex3f(coords[0], coords[1], coords[2]);
            }
        }
        //modeldata.position(0);
    }

    private void ConstructNV(GL2 inGL, List<float[]> vData, List<float[]> vnData) {
        int[] v, n;
        float coords[] = new float[3];
        int fbSize = PolyCount * (FaceMultiplier * 6); //3v per poly, 3vn per poly
        //modeldata = Buffers.newDirectFloatBuffer(fbSize);
        //modeldata.position(0);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            n = (int[]) (fn.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the normal coordinate data
                for (int vnloop = 0; vnloop < coords.length; vnloop++) {
                    coords[vnloop] = ((float[]) vnData.get(n[iloop] - 1))[vnloop];
                }
                //modeldata.put(coords);
                inGL.glNormal3f(coords[0], coords[1], coords[2]);
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++) {
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                }
                //modeldata.put(coords);
                inGL.glVertex3f(coords[0], coords[1], coords[2]);
            }
        }
        //modeldata.position(0);
    }

    private void ConstructV(GL2 inGL, List<float[]> vData) {
        int[] v;
        float coords[] = new float[3];
        int fbSize = PolyCount * (FaceMultiplier * 3); //3v per poly
        //modeldata = Buffers.newDirectFloatBuffer(fbSize);
        //modeldata.position(0);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++) {
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                }
                //modeldata.put(coords);
                inGL.glVertex3f(coords[0], coords[1], coords[2]);
            }
        }
        //modeldata.position(0);
    }

    private void cleanup() {
        fv.clear();
        ft.clear();
        fn.clear();
    }

    public void initialize(GL2 gl, List<float[]> vData, List<float[]> vtData, List<float[]> vnData) {
        item = gl.glGenLists(1);
        gl.glNewList(item, GL2.GL_COMPILE);
        gl.glBegin(GL2.GL_QUAD_STRIP);

        if(material != null) {
            if (material.getKa() != null) {
                gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, new float[]{
                        material.getKa()[0],
                        material.getKa()[1],
                        material.getKa()[2],
                        1
                }, 0);
            }

            if (material.getKd() != null) {
                gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, new float[]{
                        material.getKd()[0],
                        material.getKd()[1],
                        material.getKd()[2],
                        1
                }, 0);
            }

            if (material.getKs() != null) {
                gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[]{
                        material.getKs()[0],
                        material.getKs()[1],
                        material.getKs()[2],
                        1
                }, 0);
            }
        }

//        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, material.getKa(), 0);
//        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, material.getKd(), 0);
//        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, material.getKs(), 0);
        ConstructInterleavedArray(gl, vData, vtData, vnData);
        cleanup();

        gl.glEnd();
        gl.glEndList();

//        if(material.getTextures().size() != 0) {
//            texture = material.getTextures().get("Ks");
//        }
    }

    public void draw(GLU glu, GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        if (material != null) {
            for (Map.Entry<String, String> entry : material.getTextures().entrySet()) {
                try {
                    texture  = TextureIO.newTexture(new File(entry.getValue()), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (texture != null) {
                    texture.bind(gl);
                }
            }
            if(material.getTextures().size() == 0) {
                gl.glBindTexture(GL2.GL_TEXTURE_2D, 1);
            }
        }

        gl.glPushMatrix();
        gl.glScalef(width, height, length);
        gl.glTranslatef((float) getCenter().getX(), (float) getCenter().getY(), (float) getCenter().getZ());

        gl.glCallList(item);
        gl.glTranslatef((float) -getCenter().getX(), (float) -getCenter().getY(), (float) -getCenter().getZ());
        gl.glPopMatrix();
    }

    public Vector getCenter() {
        return new Vector(pos.getX(), pos.getY(), pos.getZ());
    }

    public Vector getDimensions() {
        return new Vector(width, height, length);
    }
}
