package Collision;

import WorldObjects.Player;

public interface Collidable {

    enum Type {
        vanish, stay
    };
    void hit(Player hitter);
}
