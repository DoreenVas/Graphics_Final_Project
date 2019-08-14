package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Scene.WavefrontObjectLoader_DisplayList;
import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Boss implements WorldObject {
    // members
    private Vector pos;
    private BlockWall AABB;
    private Texture texture;
    private WavefrontObjectLoader_DisplayList model;
    private float step = 0.2f;
    private int hitPoints = 7;

    public Boss(Vector p, String modelPath, String texturePath, Collidable.Type type) {
        this.pos = p;
        try {
            this.texture = TextureIO.newTexture(new File(texturePath), true);
            this.model = new WavefrontObjectLoader_DisplayList(modelPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        float maxHeight, maxWidth, maxDepth;
        maxWidth = 108 / (this.model.getMaxCord()[0]- this.model.getMinCord()[0]);
        maxHeight = 35 / (this.model.getMaxCord()[1]  - this.model.getMinCord()[1]);
        maxDepth = 100 / (this.model.getMaxCord()[2]  - this.model.getMinCord()[2]);
        this.AABB = new BlockWall(new Vector(
                this.pos.getX() - maxWidth/2,
                this.pos.getY(),
                this.pos.getZ() + maxDepth/2),
                maxWidth, maxHeight, maxDepth, null, type);
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslated(this.pos.getX(), this.pos.getY(), this.pos.getZ());
        gl.glRotated(-90f, 1.0f, 0.0f, 0.0f); // for fire guy
        gl.glScaled(0.4,0.4,0.4); // for fire guy

        move();

        this.texture.bind(gl);
        this.model.drawModel(gl);
        gl.glPopMatrix();
    }

    private void move() {
        if (CollisionDetector.AABB_walls(this.AABB)){
            this.step = this.step * -1;
        }
        this.pos.setX(this.pos.getX() + this.step);
        this.AABB.p.setX(this.AABB.p.getX() + this.step*this.AABB.width);
    }

    @Override
    public Vector getLocation() {
        return null;
    }
}
