package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe pour les éléments du jeu (joueur, ennemis, etc...)
 */
public abstract class GameElement {

    /**
     * Dimensions de l'élément
     */
    int width, height;
    /**
     * Position de l'élément
     */
    Vector2 position;

    /**
     * Monde du jeu
     */
    World world;

    Side side;

    public enum Side {
        ALIEN, PLAYER
    }

    /**
     * Constructeur de la classe
     *
     * @param width    largeur
     * @param height   hauteur
     * @param position position
     */
    GameElement(World world, int width, int height, Vector2 position) {
        this.world = world;
        this.width = width;
        this.height = height;
        this.position = position;
    }

    /**
     * Renvoie la largeur de l'élément
     *
     * @return largeur
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set la largeur de l'élément
     *
     * @param width largeur
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Renvoie la hauteur de l'élément
     *
     * @return hauteur
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set la hauteur de l'élément
     *
     * @param height hauteur
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Renvoie la position de l'élément
     *
     * @return position
     */
    Vector2 getPosition() {
        return position;
    }

    /**
     * Set la position de l'élément
     *
     * @param position position
     */
    void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Retourne la composante x de la position de l'élément
     *
     * @return position en x
     */
    public float getX() {
        return this.position.x;
    }

    /**
     * Retourne la composante y de la position de l'élément
     *
     * @return position en y
     */
    public float getY() {
        return this.position.y;
    }

    /**
     * Renvoie la boundingBox de l'élément
     * @return Rectangle
     */
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(position.x, position.y, width, height);
        return bounds;
    }

    /**
     * Mort de l'objet dans le jeu
     */
    public abstract void die();

    /**
     * get side : côté de l'objet (alien vs player)
     */
    public Side getSide() { return side; }
}
