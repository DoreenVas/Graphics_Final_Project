package WorldObjects;

import Utils.Vector;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;

public class Circle {
    // members
    private Vector center;
    private float radius;
    private Texture texture;

    public Circle(Vector c, float r, String texturePath) {
        this.center = c;
        this.radius = r;
        try {
            texture = TextureIO.newTexture(new File(texturePath), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawCircle(GL2 gl)
    {
        float angle, radian, x, y, xcos, ysin, tx, ty;       // values needed by drawCircleOutline

        gl.glPushMatrix();

        gl.glEnable(GL2.GL_TEXTURE_2D);
        texture.bind(gl);
//        gl.glBindTexture(GL2.GL_TEXTURE_2D, this.texture);

        gl.glBegin(GL2.GL_POLYGON);

        for (angle=0.0f; angle<360.0f; angle+=2.0f)
        {
            radian = angle * ((float)Math.PI/180.0f);

            xcos = (float)Math.cos(radian);
            ysin = (float)Math.sin(radian);
            x = xcos * this.radius  + this.center.getX();
            y = ysin * this.radius  + this.center.getY();
            tx = xcos * 0.5f + 0.5f;
            ty = ysin * 0.5f + 0.5f;

            gl.glTexCoord2f(tx, ty);
            gl.glVertex2f(x, y);
        }

        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

        gl.glPopMatrix();
        gl.glFlush();
    }
}
