/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package WorldObjects;

import Collision.Collidable;
import Collision.HitListener;
import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cube extends BlockWall implements Collidable{
    // members
    private List<HitListener> hitListeners;
    protected Texture cubeTexture;
    protected Vector o; //left bottom corner close to view
    protected float length;
    protected Type type;

    private Vector[] arr = new Vector[8];

    public Cube(Vector v, float l) {
        this(v, l , null, null);
    }

    public Cube(Vector v, float l, String texturePath, Type t) {
        super(v, l, l, l, texturePath, t);
        try {
            type = t;
            o = v;
            length = l;
            String cubeTextureFile = texturePath; // the FileName to open
            if (cubeTextureFile != null) {
                cubeTexture = TextureIO.newTexture(new File(cubeTextureFile), true);
            }
            arr[0] = new Vector(o.getX(), o.getY(), o.getZ());
            arr[1] = new Vector(o.getX()+length, o.getY(), o.getZ());
            arr[2] = new Vector(o.getX()+length, o.getY()+length, o.getZ());
            arr[3] = new Vector(o.getX(), o.getY()+length, o.getZ());
            arr[4] = new Vector(o.getX(), o.getY(), o.getZ()-length);
            arr[5] = new Vector(o.getX(), o.getY()+length, o.getZ()-length);
            arr[6] = new Vector(o.getX()+length, o.getY()+length, o.getZ()-length);
            arr[7] = new Vector(o.getX()+length, o.getY(), o.getZ()-length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    public Type getType() {
        return type;
    }

    public void draw(GL2 gl) {
        if(Player.isAlive()) {
            super.draw(gl);
        }
    }

    public Vector getOrigin() {
        return o;
    }

    public float getLength() {
        return this.length;
    }

    public Vector[] getVertexes() {
        return this.arr;
    }

    @Override
    public void hit(Player hitter) {
        this.notifyHit(hitter);
    }

    private void notifyHit(Player hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
