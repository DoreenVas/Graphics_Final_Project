package Collision;

import WorldObjects.Player;

/**
 * A HitListener Interface.
 * this is the interface that is responsible of hitting event.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the player that's doing the hitting.
     * @param beingHit the object being hit
     * @param hitter the hitting player
     */
    void hitEvent(Collidable beingHit, Player hitter);
}