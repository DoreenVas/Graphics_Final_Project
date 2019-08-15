package WorldObjects;

import Collision.Collidable;
import Collision.CollisionDetector;
import Scene.WavefrontObjectLoader_DisplayList;
import Utils.Vector;
import View.ViewManager;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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

        float height, width, depth;
        width = 0.5f*(this.model.getMaxCord()[0]- this.model.getMinCord()[0]);
        height =(this.model.getMaxCord()[1]  - this.model.getMinCord()[1]);
        depth = 0.4f*(this.model.getMaxCord()[2]  - this.model.getMinCord()[2]);
        this.AABB = new BlockWall(new Vector(
                this.pos.getX() - width/2,
                this.pos.getY(),
                this.pos.getZ() + depth/2),
                width, height, 0.5f, texturePath, type);
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

        // return gl to origin
        gl.glScaled(1.4,1.4,1.4);
        gl.glRotated(90f, 1.0f, 0.0f, 0.0f);
        gl.glTranslated(-this.pos.getX(), -this.pos.getY(), -this.pos.getZ());

        this.AABB.draw(gl);
        gl.glPopMatrix();
    }

    private void move() {
        if (CollisionDetector.AABB_walls(this.AABB)){
            this.step = this.step * -1;
        }
        pos.setX(pos.getX() + step);
        AABB.p.setX(AABB.p.getX() + step);
    }

    @Override
    public Vector getLocation() {
        return null;
    }

    public BlockWall getAABB() {
        return AABB;
    }

    public void gotHit() {
        hitPoints = hitPoints -1;
        System.out.println("got hit, "+hitPoints +" left");
        if (hitPoints == 0){
            World.getInstance().resetBulletsList();
            ViewManager.getInstance().win();
        }
    }
}
