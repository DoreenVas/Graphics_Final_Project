package Scene;

import java.util.HashMap;
import java.util.Map;

public class Material {

    private float Ns;
    private float[] Ka;
    private float[] Kd;
    private float[] Ks;
    private float[] Ke;
    private float Ni;
    private float d;
    private Map<String, String> textures;

    public Material() {
        textures = new HashMap<>();
    }

    public void setNs(float ns) {
        Ns = ns;
    }

    public void setKa(float[] ka) {
        Ka = ka;
    }

    public void setKd(float[] kd) {
        Kd = kd;
    }

    public void setKs(float[] ks) {
        Ks = ks;
    }

    public void setKe(float[] ke) {
        Ke = ke;
    }

    public void setNi(float ni) {
        Ni = ni;
    }

    public void setD(float d) {
        this.d = d;
    }

    public void addTexture(String key, String path) {
        textures.put(key, path);
    }

    public float getNs() {
        return Ns;
    }

    public float[] getKa() {
        return Ka;
    }

    public float[] getKd() {
        return Kd;
    }

    public float[] getKs() {
        return Ks;
    }

    public float[] getKe() {
        return Ke;
    }

    public float getNi() {
        return Ni;
    }

    public float getD() {
        return d;
    }

    public Map<String, String> getTextures() {
        return textures;
    }
}
