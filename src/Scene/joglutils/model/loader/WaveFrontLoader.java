/*
 * myWaveFrontLoader.java
 *
 * Created on March 16, 2008, 8:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Scene.joglutils.model.loader;

import Scene.joglutils.model.ModelLoadException;
import Scene.joglutils.model.ResourceRetriever;
import Scene.joglutils.model.geometry.*;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author RodgersGB
 */
public class WaveFrontLoader implements iLoader {
    public static final String VERTEX_DATA = "v ";
    public static final String NORMAL_DATA = "vn ";
    public static final String TEXTURE_DATA = "vt ";
    public static final String FACE_DATA = "f ";
    public static final String SMOOTHING_GROUP = "s ";
    public static final String GROUP = "g ";
    public static final String OBJECT = "o ";
    public static final String COMMENT = "#";
    public static final String EMPTY = "";
    
    int vertexTotal = 0;
    int textureTotal = 0;
    int normalTotal = 0;
    
    private DataInputStream dataInputStream;
    // the model
    private Model model = null;
    /** Bounds of the model */
    private Bounds bounds = new Bounds();
    /** Center of the model */
    private Vec4 center = new Vec4(0.0f, 0.0f, 0.0f);
    private String baseDir = null;

    public List<Vec4> vertices = null;
    public List<Vec4> normals = null;
    public List<TexCoord> texCoords = null;
    public List<Face> faces = null;
    
    // Bounding Sphere helper
    private static double bsRadiusSquare;

    private String line = null;
    private String currentLine = null;

    /** Creates a new instance of myWaveFrontLoader */
    public WaveFrontLoader() {
        vertices = new ArrayList<>();
        normals = new ArrayList<>();
        texCoords = new ArrayList<>();
        faces = new ArrayList<>();
    }

    int numComments = 0;
    public Model load(String path) throws ModelLoadException {
    	bsRadiusSquare = 0;
        model = new Model(path);
        Mesh mesh = null;
        
        baseDir = "";
        String tokens[] = path.split("/");
        for(int i = 0; i < tokens.length - 1; i++) {
            baseDir += tokens[i] + "/";
        }
        
        InputStream stream = null;
        try {
            stream = ResourceRetriever.getResourceAsInputStream(model.getSource());
            if (stream == null) {
                throw new ModelLoadException("Stream is null");
            }
        } catch(IOException e) {
            throw new ModelLoadException("Caught IO exception: " + e);
        }
        
        try {
            // Open a file handle and read the models data
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            currentLine = br.readLine();
            while(currentLine != null) {
                if (lineIs(COMMENT, currentLine)) {
                    // ignore comments
                    numComments++;
                    currentLine = br.readLine();
                    continue;
                }
                
                if (currentLine.length() == 0) {
                    // ignore empty lines
                    currentLine = br.readLine();
                    continue;
                }
                
                if (lineIs(GROUP, currentLine)) {
                    if (mesh == null) {
                        mesh = new Mesh();
                    }
                    
                    mesh.name = parseName(currentLine);
                }
                
                if (lineIs(OBJECT, currentLine)) {
                    
                }
                
                if (lineIs(VERTEX_DATA, currentLine)) {
                    if (mesh == null) {
                        mesh = new Mesh();
                    }
                    
                    vertices.addAll(getPoints(VERTEX_DATA, currentLine, br));
//                    mesh.vertices = vertices;
//                    mesh.numOfVerts = mesh.vertices.length;
                }
                
                if (lineIs(TEXTURE_DATA, currentLine)) {
                    if (mesh == null) {
                        mesh = new Mesh();
                    }
                    
                    texCoords.addAll(getTexCoords(TEXTURE_DATA, currentLine, br));
//                    mesh.texCoords = texCoords;
//                    mesh.hasTexture = true;
//                    mesh.numTexCoords = mesh.texCoords.length;
                }
                
                if (lineIs(NORMAL_DATA, currentLine)) {
                    if (mesh == null)
                        mesh = new Mesh();
                    
                    normals = getPoints(NORMAL_DATA, currentLine, br);
//                    mesh.normals = normals;
                }
                
                if (lineIs(FACE_DATA, currentLine)) {
                    if (mesh == null)
                        mesh = new Mesh();

                    faces.addAll(getFaces(currentLine, mesh, br));
//                    mesh.faces = getFaces(line, mesh, br);
//                    mesh.numOfFaces = mesh.faces.length;

//                    if(vertices != null) {
//                        mesh.vertices = vertices;
//                    }
//                    mesh.numOfVerts = mesh.vertices.length;
//                    if(texCoords != null) {
//                        mesh.texCoords = texCoords;
//                        mesh.hasTexture = true;
//                        mesh.numTexCoords = mesh.texCoords.length;
//                    }
//                    if(normals != null) {
//                        mesh.normals = normals;
//                    }

//                    model.addMesh(mesh);
//                    mesh = null;
                }
                
                if (lineIs("mtllib ", currentLine)){
                    processMaterialLib(currentLine);
                }
                
                if (lineIs("usemtl ", currentLine)) {
                    processMaterialType(currentLine, mesh);
                }

                if(line != null && !currentLine.equals(line)) {
                    currentLine = line;
                    continue;
                }
                currentLine = br.readLine();
            }
        }
        catch (IOException e) {
            throw new ModelLoadException("Failed to find or read OBJ: " + stream);
        }
        if (mesh != null) {
            mesh.faces = faces.toArray(new Face[] {});
            mesh.numOfFaces = mesh.faces.length;

            if(vertices != null) {
                mesh.vertices = vertices.toArray(new Vec4[] {});
            }
            mesh.numOfVerts = mesh.vertices.length;
            if(texCoords != null) {
                mesh.texCoords = texCoords.toArray(new TexCoord[] {});
                mesh.hasTexture = true;
                mesh.numTexCoords = mesh.texCoords.length;
            }
            if(normals != null) {
                mesh.normals = normals.toArray(new Vec4[] {});
            }
        	model.addMesh(mesh);
        }
        mesh = null;
        
//        System.out.println(this.bounds.toString());
        model.setBounds(this.bounds);
        model.setCenterPoint(this.center);
        model.setBsRadiusSquared(bsRadiusSquare);
        
        return model;
    }
    
    private boolean lineIs(String type, String line) {
        return line.startsWith(type);
    }
    
    private List<Vec4> getPoints(String prefix, String currLine, BufferedReader br) throws IOException {
        List<Vec4> points = new ArrayList<>();
        boolean isVertices = prefix.equals(VERTEX_DATA);
        
        // we've already read in the first line (currLine)
        // so go ahead and parse it
        points.add(parsePoint(currLine));
        
        // parse through the rest of the points
        while((line = br.readLine()) != null) {
            if (!lineIs(prefix, line))
                break;
            
            Vec4 point = parsePoint(line);
            if (isVertices) {
                // Calculate the bounds for the entire model
                bounds.calc(point);
                
                // Compute the new bounding sphere radius
                double currRadius = Math.pow(point.x, 2) + 
									Math.pow(point.y, 2) + 
									Math.pow(point.z, 2);
				if (currRadius > bsRadiusSquare) {
					bsRadiusSquare = currRadius;
				}
            }
            points.add(point);
        }
        
        if (isVertices) {
            // Calculate the center of the model
            center.x = 0.5f * (bounds.max.x + bounds.min.x);
            center.y = 0.5f * (bounds.max.y + bounds.min.y);
            center.z = 0.5f * (bounds.max.z + bounds.min.z);
        }
        
        // return the points
//        Vec4 values[] = new Vec4[points.size()];
//        return points.toArray(values);
        return points;
    }
    
    private List<TexCoord> getTexCoords(String prefix, String currLine, BufferedReader br) throws IOException {
        List<TexCoord> texCoords = new ArrayList<>();
        
        String s[] = currLine.split("\\s+");
        TexCoord texCoord = new TexCoord();
        texCoord.u = Float.parseFloat(s[1]);
        texCoord.v = Float.parseFloat(s[2]);
            
        texCoords.add(texCoord);
        
        // parse through the rest of the points
        while((line = br.readLine()) != null) {
            if (!lineIs(prefix, line))
                break;
            
            s = line.split("\\s+");
        
            texCoord = new TexCoord();
            texCoord.u = Float.parseFloat(s[1]);
            texCoord.v = Float.parseFloat(s[2]);
            
            texCoords.add(texCoord);
        }
        
        // return the texture coordinates
//        TexCoord values[] = new TexCoord[texCoords.size()];
//        return texCoords.toArray(values);
        return texCoords;
    }
    
    private List<Face> getFaces(String currLine, Mesh mesh, BufferedReader br) throws IOException {
        List<Face> faces = new ArrayList<>();
        
        faces.add(parseFace(currLine));
        
        // parse through the rest of the faces
        while((line = br.readLine()) != null) {
            if (lineIs(SMOOTHING_GROUP, line)) {
                continue;
            }
            else if (lineIs("usemtl ", line)) {
                processMaterialType(line, mesh);
            }
            
            else if (lineIs(FACE_DATA, line)) {
                faces.add(parseFace(line));
            }
            
            else
                break;
        }        
        
        // return the faces
//        Face values[] = new Face[faces.size()];
        return faces;
    }
    
    private Face parseFace(String line) {
        String s[] = line.split("\\s+");
        if (line.contains("//")) { // Pattern is present if obj has no texture
            for (int loop=1; loop < s.length; loop++) {
                s[loop] = s[loop].replaceAll("//","/-1/"); //insert -1 for missing vt data
            }
        }
        
        int vdata[] = new int[s.length-1];
        int vtdata[] = new int[s.length-1];
        int vndata[] = new int[s.length-1];
        Face face = new Face(s.length - 1);
        
        for (int loop = 1; loop < s.length; loop++) {
            String s1 = s[loop];
            String[] temp = s1.split("/");
            
            if (temp.length > 0) { // we have vertex data
                if (Integer.valueOf(temp[0]) < 0) {
                    //TODO handle relative vertex data
                }
                else {
                    face.vertIndex[loop-1] = Integer.valueOf(temp[0]) - 1;// - this.vertexTotal;
                    //System.out.println("found vertex index: " + face.vertIndex[loop-1]);
                }
            }
            
            if (temp.length > 1) { // we have texture data
                if(Integer.valueOf(temp[1]) < 0) {
                    face.coordIndex[loop - 1] = 0;
                }
                else {
                    face.coordIndex[loop - 1] = Integer.valueOf(temp[1]) - 1;// - this.textureTotal;
                    //System.out.println("found texture index: " + face.coordIndex[loop-1]);
                }
            }
            
            if (temp.length > 2) { // we have normal data
                face.normalIndex[loop-1] = Integer.valueOf(temp[2]) - 1;// - this.normalTotal;
                //System.out.println("found normal index: " + face.normalIndex[loop-1]);
            }
        }
        
        return face;
    }
    
    private Vec4 parsePoint(String line) {
        Vec4 point = new Vec4();
        
        final String s[] = line.split("\\s+");
        
        point.x = Float.parseFloat(s[1]);
        point.y = Float.parseFloat(s[2]);
        point.z = Float.parseFloat(s[3]);
        
        return point;
    }
    
    private String parseName(String line) {
        String name;
        
        final String s[] = line.split("\\s+");
        
        name = s[1];
        
        return name;
    }
    
    private void processMaterialLib(String mtlData) {
        String s[] = mtlData.split("\\s+");
        
        Material mat = new Material();
        InputStream stream = null;
        try {
            stream = ResourceRetriever.getResourceAsInputStream(baseDir + s[1]);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        if(stream == null) {
            try {
                stream = new FileInputStream(baseDir + s[1]);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }
        }
        loadMaterialFile(stream);
    }
    
    private void processMaterialType(String line, Mesh mesh) {
        String s[] = line.split("\\s+");
        
        int materialID = -1;
        boolean hasTexture = false;
        
        for(int i = 0; i < model.getNumberOfMaterials(); i++){
            Material mat = model.getMaterial(i);
            
            if(mat.strName.equals(s[1])){
                materialID = i;
                if(mat.strFile != null)
                    hasTexture = true;
                else
                    hasTexture = false;
                break;
            }
        }
        
        if(materialID != -1)
            mesh.materialID = materialID;
    }
    
    public Material loadMaterialFile(InputStream stream) {
        Material mat = null;
        int texId = 0;
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;

            while((line = br.readLine()) != null){
                
                String parts[] = line.trim().split("\\s+");
                parts[0] = parts[0].toLowerCase();
                
                if(parts[0].equals("newmtl")){
                    if(mat != null)
                        model.addMaterial(mat);
                    
                    mat = new Material();
                    mat.strName = parts[1];
                    mat.textureId = texId++;
                    
                } else if(parts[0].equals("ks"))
                    mat.specularColor = parseColor(line);
                
                else if(parts[0].equals("ns")) {
                    if (parts.length > 1)
                        mat.shininess = Float.valueOf(parts[1]);
                }
                else if(parts[0].equals("d"))
                    ;
                else if(parts[0].equals("illum"))
                    ;
                else if(parts[0].equals("ka"))
                    mat.ambientColor = parseColor(line);
                else if(parts[0].equals("kd"))
                    mat.diffuseColor = parseColor(line);
                else if(parts[0].equals("map_kd")) {
                    if (parts.length > 1)
                        mat.strFile = /*baseDir + */parts[1];
                }
                
                else if(parts[0].equals("map_ka")) {
                    if (parts.length > 1)
                        mat.strFile = /*baseDir + */parts[1];
                }
            }
            
            br.close();
            model.addMaterial(mat);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return mat;
    }
    
    private Color parseColor(String line) {
        String parts[] = line.trim().split("\\s+");
        
        Color color = new Color(Float.valueOf(parts[1]),
                Float.valueOf(parts[2]),Float.valueOf(parts[3]));
        
        return color;
    }
}
