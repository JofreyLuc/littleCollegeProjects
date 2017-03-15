package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Vector2;

import static com.spazeinvaderz.game.model.GameMovableElement.Direction.*;

/**
 * Classe pour les aliens (ennemis)
 */
public class Alien extends GameMovableElement {

    /**
     * Type de l'alien (1,2,3) déterminant son sprite
     **/
    int type;
    private float timeShoot = 0, timeMove = 0, distanceToMove = 0;
    private boolean isGoingDown;

    /**
     * Constructeur de la classe
     *
     * @param world    monde
     * @param width    largeur
     * @param height   hauteur
     * @param position position
     */
    Alien(World world, int width, int height, Vector2 position, int type) {
        super(world, width, height, position, 20, RIGHT);
        this.type = type;
        side = Side.ALIEN;
    }

    /**
     * Déplace l'alien sur la gauche
     */
    @Override
    public void turnLeft() {
        if (!isGoingDown()) {
            super.turnLeft();
            if (getPosition().x <= world.getAliensLeftBound()) {
                setPosition(new Vector2((float) world.getAliensRightBound(), getPosition().y));
            }
        }
    }

    /**
     * Déplace l'alien sur la droite
     */
    @Override
    public void turnRight() {
        if (!isGoingDown()) {
            super.turnRight();
            if (getPosition().x >= world.getAliensRightBound()) {
                setPosition(new Vector2((float) world.getAliensLeftBound(), getPosition().y));
            }
        }
    }

    /**
     * Déplace l'alien vers le bas
     */
    @Override
    void turnDown() {
        super.turnDown();
        isGoingDown = true;
    }

    /**
     * Gère le mouvement de l'alien
     *
     * @param delta temps écoulé depuis le dernier rafraichissement
     */
    public void move(float delta) {
        timeShoot += delta;
        if (timeShoot > 3) {
            timeShoot -= 3;
            if (Math.random() > 0.9) {
                this.shoot();
            }
        }

        timeMove += delta;
        distanceToMove += delta;
        if (timeMove > 1) {
            super.move(distanceToMove);
            distanceToMove = 0;
            timeMove = 0;
            if (direction == Direction.DOWN) {
                isGoingDown = false;
            }
        }
    }

    /**
     * Déclenche un tir du vaisseau
     */
    public void shoot() {
        Vector2 pos = new Vector2(position);
        /* On décale la position du tir pour qu'il soit en haut et au centre du vaisseau */
        pos.x += getWidth() / 2;
        pos.y += getHeight();
        world.shoot(pos, DOWN, Side.ALIEN);
    }

    /**
     * Supprime l'alien du monde lorsqu'il est tué par le joeur
     */
    public void die() {
        world.addPoints(100);
        world.removeAlien(this);
    }

    public boolean isGoingDown() {
        return isGoingDown;
    }

    /**
     * Renvoie le type de l'alien (déterminant la texture)
     *
     * @return type de l'alien
     */
    public int getType() {
        return type;
    }
}
