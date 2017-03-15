package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Classe pour les boucliers du jeu
 */
public class Barrier extends GameElement {
    int life;

    /**
     * Constructeur de la classe
     *
     * @param world    monde
     * @param position position
     */
    public Barrier(World world, Vector2 position) {
        super(world, 35, 35, position);
        life = 100;
    }

    /**
     * Retourne la "vie" du bouclier
     *
     * @return vie du bouclier
     */
    public int getLife() {
        return life;
    }

    /**
     * Surpprime le bouclier du jeu quand il est détruit
     */
    public void die() {
        life -= 10;

        if (life <= 0) {
            world.removeBarrier(this);
        }
    }
}
