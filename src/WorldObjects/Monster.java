package WorldObjects;

import Collision.Collidable;
import Scene.WavefrontObjectLoader_DisplayList;
import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;

public class Monster implements WorldObject {
    // members
    private Vector pos;
    private BlockWall AABB;
    private Texture texture;
    private WavefrontObjectLoader_DisplayList model;
    private Collidable.Type type;

    public Monster(Vector p, String modelPath, String texturePath, Collidable.Type ty) {
        this.pos = p;
        try {
            this.texture = TextureIO.newTexture(new File(texturePath), true);
            this.model = new WavefrontObjectLoader_DisplayList(modelPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        float maxHeight, maxWidth, maxDepth;
        maxWidth = this.model.getMaxCord()[0] - this.model.getMinCord()[0];
        maxHeight = this.model.getMaxCord()[1] - this.model.getMinCord()[1];
        maxDepth = this.model.getMaxCord()[2] - this.model.getMinCord()[2];
        this.AABB = new BlockWall(p, maxWidth, maxHeight, maxDepth, null, ty);

    }

    @Override
    public void draw(GL2 gl) {

        gl.glTranslated(this.pos.getX(), this.pos.getY(), this.pos.getZ());
        gl.glRotated(-90f, 0.0f, 0.0f, 1.0f);
        gl.glScaled(5,5,5);

        this.texture.bind(gl);
        this.model.drawModel(gl);
    }

    @Override
    public Vector getLocation() {
        return null;
    }
}
