package Collision;

import WorldObjects.Player;

public interface Collidable {

    enum Type {
        vanish, stay, player
    };
    void hit(Player hitter);
}
