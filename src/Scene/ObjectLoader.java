package Scene;
/**
 * * __ __|_ ___________________________________________________________________________ ___|__ __
 * * // /\ _ /\ \\
 * * //____/ \__ __ _____ _____ _____ _____ _____ | | __ _____ _____ __ __/ \____\\
 * * \ \ / / __| | | __| _ | | _ | | | __| | | __| | /\ \ / /
 * * \____\/_/ | | | | | | | | | | | __| | | | | | | | | | |__ " \_\/____/
 * * /\ \ |_____|_____|_____|__|__|_|_|_|__| | | |_____|_____|_____|_____| _ / /\
 * * / \____\ http://jogamp.org |_| /____/ \
 * * \ / "' _________________________________________________________________________ `" \ /
 * * \/____. .____\/
 * *
 * * Wavefront .obj mesh loader with vertices, face and normal support. Provides a convenience
 * * method to load the whole model as display-list. The code is slightly modified copypasta from
 * * the open source project "jglmark" (https://jglmark.dev.java.net/). Original author is Chris
 * * "Crash0veride007" Brown (crash0veride007@gmail.com). Also added support for compressed mesh
 * * files (.zip).
 * *
 **/

import Coordinations.Cartesian;
import Coordinations.Coordination;
import Utils.Vector;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.media.opengl.GL2;

public class ObjectLoader {

    private ArrayList<float[]> vData; //list of vertex coordinates
    private ArrayList<float[]> vtData; //list of texture coordinates
    private ArrayList<float[]> vnData; //list of normal coordinates
    private ArrayList<int[]> fv; //face vertex indices
    private ArrayList<int[]> ft; //face texture indices
    private ArrayList<int[]> fn; //face normal indices
    private int FaceFormat; //format of the faces triangles or quads
    private int FaceMultiplier; //number of possible coordinates per face
    private int PolyCount = 0; //the model polygon count
    private Material material;
    private Map<String, Material> materials;
    private List<Face> faces;

    private float width;
    private float height;
    private float length;

    public ObjectDisplayer LoadModel(String inModelPath, Vector system, float width, float height,
                                     float length) {
        faces = new ArrayList<>();
        initialize();
        clearData();
        this.width = width;
        this.height = height;
        this.length = length;
        LoadOBJModel(inModelPath);
        return new ObjectDisplayer(system, vData, vtData, vnData, faces, FaceFormat, FaceMultiplier, PolyCount);
    }

    public ObjectDisplayer LoadModel(String inModelPath, Vector system) {
        faces = new ArrayList<>();
        initialize();
        clearData();
        this.width = 1;
        this.height = 1;
        this.length = 1;
        LoadOBJModel(inModelPath);
        return new ObjectDisplayer(system, vData, vtData, vnData, faces, FaceFormat, FaceMultiplier, PolyCount);
    }

    private void initialize() {
        vData = new ArrayList<>();
        vtData = new ArrayList<>();
        vnData = new ArrayList<>();
        fv = new ArrayList<>();
        ft = new ArrayList<>();
        fn = new ArrayList<>();
        materials = new HashMap<>();
        material = null;
    }

    private void clearData() {
        fv = new ArrayList<>();
        ft = new ArrayList<>();
        fn = new ArrayList<>();
    }

    private void LoadOBJModel(String ModelPath) {
        try {
            BufferedReader br = null;
            if (ModelPath.endsWith(".zip")) {
                ZipInputStream tZipInputStream = new ZipInputStream(
                        new BufferedInputStream((new Object()).getClass().getResourceAsStream(ModelPath)));
                ZipEntry tZipEntry;
                tZipEntry = tZipInputStream.getNextEntry();
                String inZipEntryName = tZipEntry.getName();
                if (!tZipEntry.isDirectory()) {
                    br = new BufferedReader(new InputStreamReader(tZipInputStream));
                }
            } else {
                Object obj = new Object();
                br = new BufferedReader(new FileReader(ModelPath));
            }
            String line = null;
            boolean sawFaces = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) { //read any descriptor data in the file
                    // Zzzz ...
                } else if (line.startsWith("mtllib ")) {
                    loadMaterial(line.split(" ")[1]);
                } else if (line.startsWith("usemtl ")) {
                    String[] arr = line.split("\\s+");
                    if (arr[1].contains(":")) {
                        material = materials.get(arr[1].split(":")[1]);
                    } else {
                        material = materials.get(arr[1]);
                    }
                } else if (line.equals("")) {
                    // Ignore whitespace data
                } else if (line.startsWith("v ")) { //read in vertex data
                    if (sawFaces) {
                        saveFace();
                        sawFaces = false;
                    }
                    vData.add(ProcessData(line));
                } else if (line.startsWith("vt ")) { //read texture coordinates
                    vtData.add(ProcessData(line));
                } else if (line.startsWith("vn ")) { //read normal coordinates
                    vnData.add(ProcessData(line));
                } else if (line.startsWith("f ")) { //read face data
                    sawFaces = true;
                    ProcessfData(line);
                }
            }
            saveFace();
            br.close();
        } catch (IOException e) {
        }
    }

    private void loadMaterial(String file) {
        String path = "resources/materialFiles/" + file, line;
        BufferedReader reader = null;
        try {
            Material m = null;
            String material_name = "";
            reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) { //read any descriptor data in the file
                } else if (line.startsWith("newmtl ")) {
                    String[] arr = line.split("\\s+");
                    if (m != null) {
                        materials.put(material_name, m);
                    }
                    material_name = arr[1];
                    m = new Material();
                } else if (line.equals("")) {
                    // Ignore whitespace data
                } else if (line.startsWith("Ka ")) {
                    m.setKa(ProcessData(line));
                } else if (line.startsWith("Kd ")) {
                    m.setKd(ProcessData(line));
                } else if (line.startsWith("Ks ")) {
                    m.setKs(ProcessData(line));
                } else if (line.startsWith("d ")) {
                    m.setD(ProcessData(line)[0]);
                } else if (line.startsWith("Ns ")) {
                    m.setNs(ProcessData(line)[0]);
                } else if (line.startsWith("illum ")) {
                } else if (line.startsWith("map_")) {
                    line = line.replace("map_", "");
                    String[] arr = line.split("\\s+");
                    m.addTexture(arr[0], arr[1]);
                }
            }
            materials.put(material_name, m);
        } catch (Exception e) {
        }
    }

    private void saveFace() {
        SetFaceRenderType();

        Face face = new Face(new Vector(0, 0,0), width, height, length);
        face.setFv(fv);
        face.setFt(ft);
        face.setFn(fn);
        face.setMaterial(material);
        faces.add(face);
        clearData();
    }

    private float[] ProcessData(String read) {
        final String[] s = read.split("\\s+");
        return (ProcessFloatData(s)); //returns an array of processed float data
    }

    private float[] ProcessFloatData(String[] sdata) {
        float[] data = new float[sdata.length - 1];
        for (int loop = 0; loop < data.length; loop++) {
            data[loop] = Float.parseFloat(sdata[loop + 1]);
        }
        return data; //return an array of floats
    }

    private void ProcessfData(String fread) {
        PolyCount++;
        String[] s = fread.split("\\s+");
        if (fread.contains("//")) { //pattern is present if obj has only v and vn in face data
            for (int loop = 1; loop < s.length; loop++) {
                s[loop] = s[loop].replaceAll("//", "/0/"); //insert a zero for missing vt data
            }
        }
        ProcessfIntData(s); //pass in face data
    }

    private void ProcessfIntData(String[] sdata) {
        int[] vdata = new int[sdata.length - 1];
        int[] vtdata = new int[sdata.length - 1];
        int[] vndata = new int[sdata.length - 1];
        for (int loop = 1; loop < sdata.length; loop++) {
            String s = sdata[loop];
            String[] temp = s.split("/");
            vdata[loop - 1] = Integer.valueOf(temp[0]); //always add vertex indices
            if (temp.length > 1) { //we have v and vt data
                vtdata[loop - 1] = Integer.valueOf(temp[1]); //add in vt indices
            } else {
                vtdata[loop - 1] = 0; //if no vt data is present fill in zeros
            }
            if (temp.length > 2) { //we have v, vt, and vn data
                vndata[loop - 1] = Integer.valueOf(temp[2]); //add in vn indices
            } else {
                vndata[loop - 1] = 0; //if no vn data is present fill in zeros
            }
        }
        fv.add(vdata);
        ft.add(vtdata);
        fn.add(vndata);
    }

    private void SetFaceRenderType() {
        final int[] temp = (int[]) fv.get(0);
        if (temp.length == 3) {
            FaceFormat = GL2.GL_TRIANGLES; //the faces come in sets of 3 so we have triangular faces
            FaceMultiplier = 3;
        } else if (temp.length == 4) {
            FaceFormat = GL2.GL_QUADS; //the faces come in sets of 4 so we have quadrilateral faces
            FaceMultiplier = 4;
        } else {
            FaceFormat = GL2.GL_POLYGON; //fall back to render as free form polygons
            FaceMultiplier = temp.length;
        }
    }

    public static int loadWavefrontObjectAsDisplayList(GL2 inGL, String inFileName) {
        int tDisplayListID = inGL.glGenLists(1);
        WavefrontObjectLoader_DisplayList tWaveFrontObjectModel = new WavefrontObjectLoader_DisplayList(inFileName);
        inGL.glNewList(tDisplayListID, GL2.GL_COMPILE);
        tWaveFrontObjectModel.drawModel(inGL);
        inGL.glEndList();
        return tDisplayListID;
    }
}