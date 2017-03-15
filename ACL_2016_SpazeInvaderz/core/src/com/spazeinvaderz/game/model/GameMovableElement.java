package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Classe pour les éléments jeu qui peuvent se déplacer (joueur et ennemis)
 */
public abstract class GameMovableElement extends GameElement {

    /**
     * Vitesse de déplacement de l'élément
     */
    private int speed;

    /**
     * Enumeration des directions de déplacement possibles
     */
    public enum Direction {
        UP, DOWN, RIGHT, LEFT, NONE
    }

    /**
     * Direction de déplacement de l'élément
     */
    Direction direction;

    /**
     * Contructeur de la classe
     *
     * @param width     largeur
     * @param height    hauteur
     * @param position  position
     * @param speed     vitesse (unité du monde / secondes)
     * @param direction direction
     */
    GameMovableElement(World world, int width, int height, Vector2 position, int speed, Direction direction) {
        super(world, width, height, position);
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * Déplace l'élément vers le haut
     */
    void turnUp() {
        direction = Direction.UP;
    }

    /**
     * Déplace l'élément vers le bas
     */
    void turnDown() {  //FOR WHAT !
        direction = Direction.DOWN;
    }

    /**
     * Déplace l'élément vers la gauche
     */
    public void turnLeft() {
        direction = Direction.LEFT;
    }

    /**
     * Déplace l'élément vers la droite
     */
    public void turnRight() {
        direction = Direction.RIGHT;
    }

    /**
     * Stoppe le déplacement de l'élément
     */
    public void turnNot() {
        direction = Direction.NONE;
    }

    /**
     * Gère le déplacement réel de l'élément en fonction de sa direction
     */
    public void move(float delta) {
        switch (direction) {
            case UP:
                position.y += speed * delta;
                break;
            case DOWN:
                position.y -= speed * delta;
                break;
            case LEFT:
                position.x -= speed * delta;
                break;
            case RIGHT:
                position.x += speed * delta;
                break;
            case NONE:
                break;
        }

        /* Bords (monde) */
        if (position.x < 0) position.x = 0;
        else if (position.x > world.getWidth() - width) position.x = world.getWidth() - width;

        if (position.y < 0) position.y = 0;
        else if (position.y > world.getHeight() - height) position.y = world.getHeight() - height;
    }
}
