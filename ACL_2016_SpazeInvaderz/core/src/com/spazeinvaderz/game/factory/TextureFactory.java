package com.spazeinvaderz.game.factory;

import com.badlogic.gdx.graphics.Texture;

/**
 * Singleton servant à instancier les textures des sprites
 **/
public final class TextureFactory {

    /**
     * Textures des différents sprites
     **/
    private Texture ship;
    private Texture shoot;
    private Texture barrier;
    private Texture barrier2;
    private Texture barrier3;
    private Texture barrier4;
    private Texture invader1;
    private Texture invader2;
    private Texture invader3;
    private Texture shield;

    private TextureFactory() {
        ship = new Texture("sprites/ship.png");
        shoot = new Texture("sprites/shoot.png");
        barrier = new Texture("sprites/barrier.png");
        barrier2 = new Texture("sprites/barrier2.png");
        barrier3 = new Texture("sprites/barrier3.png");
        barrier4 = new Texture("sprites/barrier4.png");
        invader1 = new Texture("sprites/Invader1A.png");
        invader2 = new Texture("sprites/Invader2A.png");
        invader3 = new Texture("sprites/Invader3A.png");
        shield = new Texture("sprites/shield.png");
    }

    private static TextureFactory instance = new TextureFactory();

    public static TextureFactory getInstance() {
        return instance;
    }

    public Texture getShipTexture() {
        return ship;
    }

    public Texture getShootTexture() {
        return shoot;
    }

    public Texture getBarrierTexture(int h) {
        if (h > 75) {
            return barrier;
        } else if (h > 50) {
            return barrier2;
        } else if (h > 25) {
            return barrier3;
        }

        return barrier4;
    }

    public Texture getInvader1Texture() {
        return invader1;
    }

    public Texture getInvader2Texture() {
        return invader2;
    }

    public Texture getInvader3Texture() {
        return invader3;
    }

    public Texture getShieldTexture() {
        return shield;
    }

}
