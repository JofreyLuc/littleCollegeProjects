package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Classe pour le vaisseau (joueur)
 */
public class Ship extends GameMovableElement {

    /**
     * Score du joueur
     */
    private int score;

    /**
     * Constructeur de la classe
     *
     * @param world    monde
     * @param width    largeur
     * @param height   hauteur
     * @param position position
     */
    Ship(World world, int width, int height, Vector2 position) {
        super(world, width, height, position, 200, GameMovableElement.Direction.NONE);

        score = 0;
        side = Side.PLAYER;
    }

    /**
     * Déclenche un tir du vaisseau
     */
    public void shoot() {
        Vector2 pos = new Vector2(position);
        /* On décale la position du tir pour qu'il soit en haut et au centre du vaisseau */
        pos.x += getWidth() / 2 - 3;
        pos.y += getHeight();
        world.shoot(pos, Direction.UP, Side.PLAYER);
    }

    /**
     * Déclenche la fin de partie (échec)
     */
    public void die() {
        world.loose();
    }
}
