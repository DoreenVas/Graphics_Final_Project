package Scene;

import Utils.Vector;
import com.jogamp.common.nio.Buffers;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ObjectDisplayer  {
    private List<float[]> vData; //list of vertex coordinates
    private List<float[]> vtData; //list of texture coordinates
    private List<float[]> vnData; //list of normal coordinates
    private List<int[]> fv; //face vertex indices
    private List<int[]> ft; //face texture indices
    private List<int[]> fn; //face normal indices
    private FloatBuffer modeldata; //buffer which will contain vertice data
    private int FaceFormat; //format of the faces triangles or quads
    private int FaceMultiplier; //number of possible coordinates per face
    private int PolyCount; //the model polygon count
    private Material material;
    private boolean init = true;
//    private Texture texture;
    private float centerX;
    private float centerY;
    private float centerZ;
    private float xrot;        // X Rotation
    private float yrot;        // Y Rotation
    private float zrot;        // Z Rotation
    private float xAddRot;        // amount to add to X Rotation
    private float yAddRot;        // amount to add to X Y Rotation
    private float zAddRot;        // amount to add to X Z Rotation

    private float minX;
    private float minY;
    private float minZ;
    private float maxX;
    private float maxY;
    private float maxZ;

    private Vector pos;

    private List<Face> faces;

    public ObjectDisplayer(Vector system, List<float[]> vData, List<float[]> vtData, List<float[]> vnData,
                           List<Face> faces, int FaceFormat, int FaceMultiplier, int PolyCount) {

        this.vData = new ArrayList<>(vData);
        this.vtData = new ArrayList<>(vtData);
        this.vnData = new ArrayList<>(vnData);
        this.faces = new ArrayList<>(faces);
        this.FaceFormat = FaceFormat;
        this.FaceMultiplier = FaceMultiplier;
        this.PolyCount = PolyCount;
        this.pos = system;

        centerX = calcMid(vData, 0);
        centerY = calcMid(vData, 1);
        centerZ = calcMid(vData, 2);

        minX = calcMin(vData, 0);
        minY = calcMin(vData, 1);
        minZ = calcMin(vData, 2);
        minX = calcMax(vData, 0);
        minY = calcMax(vData, 1);
        minZ = calcMax(vData, 2);
    }

    private void addToArr(ArrayList<float[]> data, float toAdd) {
        for (float[] arr : data) {
            float[] newArr = new float[arr.length];
            for (int i = 0; i < arr.length; i++) {
                newArr[i] = arr[i] + toAdd;
            }
        }
    }

    private float calcMid(List<float[]> data, int dimIndex) {
        float sum = 0, count = 0;
        for (float[] arr : data) {
            sum += arr[dimIndex];
            count++;
        }
        return sum / count;
    }

    private float calcMin(List<float[]> data, int index) {
        float min = data.get(0)[index];
        for (float[] arr : data) {
            if(min > arr[index]) {
                min = arr[index];
            }
        }
        return min;
    }

    private float calcMax(List<float[]> data, int index) {
        float max = data.get(0)[index];
        for (float[] arr : data) {
            if(max < arr[index]) {
                max = arr[index];
            }
        }
        return max;
    }

    private void ConstructInterleavedArray(GL2 inGL) {
        final int tv[] = (int[]) fv.get(0);
        final int tt[] = (int[]) ft.get(0);
        final int tn[] = (int[]) fn.get(0);
        //if a value of zero is found that it tells us we don't have that type of data
        if ((tv[0] != 0) && (tt[0] != 0) && (tn[0] != 0)) {
            ConstructTNV(); //we have vertex, 2D texture, and normal Data
            inGL.glInterleavedArrays(GL2.GL_T2F_N3F_V3F, 0, modeldata);
        } else if ((tv[0] != 0) && (tt[0] != 0) && (tn[0] == 0)) {
            ConstructTV(); //we have just vertex and 2D texture Data
            inGL.glInterleavedArrays(GL2.GL_T2F_V3F, 0, modeldata);
        } else if ((tv[0] != 0) && (tt[0] == 0) && (tn[0] != 0)) {
            ConstructNV(); //we have just vertex and normal Data
            inGL.glInterleavedArrays(GL2.GL_N3F_V3F, 0, modeldata);
        } else if ((tv[0] != 0) && (tt[0] == 0) && (tn[0] == 0)) {
            ConstructV();
            inGL.glInterleavedArrays(GL2.GL_V3F, 0, modeldata);
        }
    }

    private void ConstructTNV() {
        int[] v, t, n;
        float tcoords[] = new float[2]; //only T2F is supported in interLeavedArrays!!
        float coords[] = new float[3];
        int fbSize = PolyCount * (FaceMultiplier * 8); //3v per poly, 2vt per poly, 3vn per poly
        modeldata = Buffers.newDirectFloatBuffer(fbSize);
        modeldata.position(0);
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
                modeldata.put(tcoords);
                //fill in the normal coordinate data
                for (int vnloop = 0; vnloop < coords.length; vnloop++) {
                    coords[vnloop] = ((float[]) vnData.get(n[iloop] - 1))[vnloop];
                }
                modeldata.put(coords);
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++) {
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                }
                modeldata.put(coords);
            }
        }
        modeldata.position(0);
    }

    private void ConstructTV() {
        int[] v, t;
        float tcoords[] = new float[2]; //only T2F is supported in interLeavedArrays!!
        float coords[] = new float[3];
        int fbSize = PolyCount * (FaceMultiplier * 5); //3v per poly, 2vt per poly
        modeldata = Buffers.newDirectFloatBuffer(fbSize);
        modeldata.position(0);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            t = (int[]) (ft.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the texture coordinate data
                for (int tloop = 0; tloop < tcoords.length; tloop++) {
                    //only T2F is supported in interleavedarrays!!
                    tcoords[tloop] = ((float[]) vtData.get(t[iloop] - 1))[tloop];
                }
                modeldata.put(tcoords);
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++) {
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                }
                modeldata.put(coords);
            }
        }
        modeldata.position(0);
    }

    private void ConstructNV() {
        int[] v, n;
        float coords[] = new float[3];
        int fbSize = PolyCount * (FaceMultiplier * 6); //3v per poly, 3vn per poly
        modeldata = Buffers.newDirectFloatBuffer(fbSize);
        modeldata.position(0);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            n = (int[]) (fn.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the normal coordinate data
                for (int vnloop = 0; vnloop < coords.length; vnloop++) {
                    coords[vnloop] = ((float[]) vnData.get(n[iloop] - 1))[vnloop];
                }
                modeldata.put(coords);
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++) {
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                }
                modeldata.put(coords);
            }
        }
        modeldata.position(0);
    }

    private void ConstructV() {
        int[] v;
        float coords[] = new float[3];
        int fbSize = PolyCount * (FaceMultiplier * 3); //3v per poly
        modeldata = Buffers.newDirectFloatBuffer(fbSize);
        modeldata.position(0);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++) {
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                }
                modeldata.put(coords);
            }
        }
        modeldata.position(0);
    }

    private void cleanup() {
        fv.clear();
        ft.clear();
        fn.clear();
        material = null;
    }

    public void draw(GLU glu, GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

//        gl.glDisable(GL.GL_TEXTURE_2D);

        gl.glPushMatrix();

        gl.glTranslatef((float) getCenter().getX(), (float) getCenter().getY(), (float) getCenter().getZ());

        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

//        if(material != null) {
//            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, material.getKa(), 0);
//            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, material.getKd(), 0);
//            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, material.getKs(), 0);
//        }

//        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);

        if (init) {
            for (Face face : faces) {
//                loadFaceData(face);
//                ConstructInterleavedArray(gl);
//                cleanup();
                face.setPolyCount(PolyCount);
                face.setFaceMultiplier(FaceMultiplier);
                face.setFaceFormat(FaceFormat);
                face.initialize(gl, vData, vtData, vnData);
            }
            init = false;
        }

        for (Face face : faces) {
            face.draw(glu, glAutoDrawable);
        }

//        gl.glDrawArrays(FaceFormat, 0, PolyCount * FaceMultiplier);

        gl.glTranslatef((float) -getCenter().getX(), (float) -getCenter().getY(), (float) -getCenter().getZ());

        gl.glPopMatrix();

//        gl.glEnable(GL.GL_TEXTURE_2D);

        xrot += xAddRot;
        yrot += yAddRot;
        zrot += zAddRot;


//        gl.glEnable(GL2.GL_TEXTURE_2D);
//        gl.glBindTexture(GL2.GL_TEXTURE_2D, textId);    // Bind The Texture
//
//        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, verticeAttributesID);
//
//        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
//        gl.glVertexPointer(3, GL2.GL_FLOAT, 8 * 4, 0);
//
//        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
//        gl.glNormalPointer(GL2.GL_FLOAT, 8 * 4, 3 * 4);
//
//        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
//        gl.glTexCoordPointer(2, GL2.GL_FLOAT, 8 * 4, (3 + 3) * 4);
//
//        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, indicesID);
//        gl.glDrawElements(GL2.GL_TRIANGLES, indicesCount, GL2.GL_UNSIGNED_INT, 0);
//
//        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
//        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
//        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
//        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    private void loadFaceData(Face face) {
        //texture = TextureManager.getInstance().getTexture(TextureKind.axe, "resources/images/axe.jpg");

//        this.fv = face.getFv();
//        this.ft = face.getFt();
//        this.fn = face.getFn();
//        this.material = face.getMaterial();
    }

    public Vector getCenter() {
        return new Vector(this.pos.getX() + this.centerX, this.pos.getY() + this.centerY,
                this.pos.getZ() + this.centerZ);
    }

    public Vector getDimensions() {
        return new Vector(maxX - minX, maxY - minY, maxZ - minZ);
    }

    public void setRotation(float xrot, float yrot, float zrot) {
        this.xAddRot = xrot;
        this.yAddRot = yrot;
        this.zAddRot = zrot;
    }
}
