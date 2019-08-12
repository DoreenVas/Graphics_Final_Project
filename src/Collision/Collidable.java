package Collision;

import WorldObjects.Player;

public interface Collidable {

    enum Type {
        tnt, stay, player, breakable, portal
    };
    void hit(Player hitter);
}
