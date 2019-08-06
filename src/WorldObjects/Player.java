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

    public Player(){
        coordination = new Cartesian();
        pos = new Vector(0.0f, 1.0f, 10.0f);
    }

    public Coordination getCoordination() {
        return coordination;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector new_pos) {
        pos = new_pos;
    }

    @Override
    public void hitEvent(Collidable beingHit, Player hitter) {
        return;
    }
}
