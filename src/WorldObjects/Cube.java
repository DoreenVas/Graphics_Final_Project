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
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Cube implements Collidable{
    // members
    private List<HitListener> hitListeners;
    private Texture cubeTexture;
    private Vector o; //left bottom corner close to view
    private float length;

    private Vector[] arr = new Vector[8];
    private Type type;

    public Cube(Vector v, float l, String texturePath, Type t) {
        try {
            type = t;
            o = v;
            length = l;
            String cubeTextureFile = texturePath; // the FileName to open
            cubeTexture= TextureIO.newTexture(new File( cubeTextureFile ),true);
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
        // update position
        //moveCube();

        gl.glPushMatrix();

        cubeTexture.bind(gl);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ());
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ());
        gl.glTexCoord2f(1f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ());
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ());
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ()-length);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ()-length);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ()-length);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ()-length);
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ()-length);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ());
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ());
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ()-length);
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ()-length);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ()-length);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ());
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ());
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ()-length);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ()-length);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX()+length, o.getY()+length, o.getZ());
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX()+length, o.getY(), o.getZ());
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ()-length);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(o.getX(), o.getY(), o.getZ());
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ());
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(o.getX(), o.getY()+length, o.getZ()-length);

        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

        gl.glPopMatrix();
        gl.glFlush();
    }

    public float moveCube(float step) {
        if (o.getX()-1 < -10 || o.getX()+length+1 > 10) {
            step = step * -1;
        }
        o.setX(o.getX()+step);
        return step;
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

    public void makeSound() {
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    File f = new File("resources/sounds/shot.wav");
//                    Clip clip = AudioSystem.getClip();
//                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
//                    clip.open(inputStream);
//                    clip.start();
//                } catch (Exception e) {
//                    System.err.println(e.getMessage());
//                }
//            }
//        }).start();

        File file = new File("resources/sounds/shot.wav");
        URL url = null;
        if (file.canRead()) {
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(url);
        AudioClip clip = Applet.newAudioClip(url);
        clip.play();
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
