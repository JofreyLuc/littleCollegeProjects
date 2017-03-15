package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Classe pour les projectiles tirés par les ennemis et le joueur
 */
public class Shoot extends GameMovableElement {

    /**
     * Constructeur de la classe
     *
     * @param world    monde
     * @param position position
     * @param dir      direction
     * @param side     côté
     */
    Shoot(World world, Vector2 position, Direction dir, Side side) {
        super(world, 5, 5, position, 300, dir);
        this.side = side;
    }

    /**
     * Prise en charge du mouvement (déjà fait par GameMovableElement), gestion supplémentaire
     * de la destruction du tir lorsqu'il sors de l'écran
     *
     * @param delta : temps écoulé depuis la dernière image
     */
    public void move(float delta) {
        super.move(delta);

        /* On supprime le tir s'il sors du monde */
        if (position.y + height >= world.getHeight()) {
            world.removeShoot(this);
        } else if (position.y - height <= 0) {
            world.removeShoot(this);
        }

    }

    /**
     * Supprime le projectile du monde
     */
    public void die() {
        world.removeShoot(this);
    }
}
