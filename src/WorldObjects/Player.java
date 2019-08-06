package WorldObjects;

import Collision.Collidable;
import Collision.HitListener;
import Coordinations.Cartesian;
import Coordinations.Coordination;
import Utils.Vector;

public class Player implements HitListener{
    // members
    private Vector pos;
    private Vector direction;
    private Vector up;
    private Coordination coordination;
    private float alpha = (float)Math.toRadians(5);
    private static float step = 0.1f;

    public Player(){
        coordination = new Cartesian();
        pos = new Vector(0.0f, 1.0f, 10.0f);
        up = new Vector(coordination.getyAxis().getX(), coordination.getyAxis().getY(), coordination.getyAxis().getZ());
        direction = new Vector(coordination.getzAxis().getX(), coordination.getzAxis().getY(), coordination.getzAxis().getZ());
    }

    public Coordination getCoordination() {
        return coordination;
    }

    public Vector getPos() {
        return pos;
    }

    public Vector getDirection() {
        direction = new Vector(coordination.getzAxis().getX(), coordination.getzAxis().getY(), coordination.getzAxis().getZ());
        return direction;
    }

    public Vector getUp() {
        up = new Vector(coordination.getyAxis().getX(), coordination.getyAxis().getY(), coordination.getyAxis().getZ());
        return up;
    }

    public void setPos(Vector new_pos) {
        pos = new_pos;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public void setUp(Vector up) {
        this.up = up;
    }

    @Override
    public void hitEvent(Collidable beingHit, Player hitter) {
        return;
    }
}
