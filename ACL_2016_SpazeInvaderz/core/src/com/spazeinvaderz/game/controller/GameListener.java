package com.spazeinvaderz.game.controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input;
import com.spazeinvaderz.game.model.Ship;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour le listener d'input (controles clavier)
 */
public class GameListener implements InputProcessor {

    /**
     * Instance de vaisseau à controler
     */
    private Ship ship;

    /**
     * Enum représentant les touches qui sont pressées
     **/
    private enum Keys {
        LEFT, RIGHT
    }

    private Map<Keys, Boolean> keys;

    /**
     * Constructeur de la classe
     *
     * @param ship vaisseau
     */
    public GameListener(Ship ship) {
        super();
        this.ship = ship;
        keys = new HashMap<Keys, Boolean>();
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
    }

    /**
     * Gère l'appui sur une touche
     *
     * @param keycode Code de la touche
     * @return true quand appuyé
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                ship.turnLeft();
                keys.put(Keys.LEFT, true);
                break;
            case Input.Keys.RIGHT:
                ship.turnRight();
                keys.put(Keys.RIGHT, true);
                break;
        }

        return true;
    }

    /**
     * Gère le relachement d'une touche
     *
     * @param keycode Code de la touche
     * @return true quand relaché
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                keys.put(Keys.LEFT, false);
                ship.turnRight();
                break;
            case Input.Keys.RIGHT:
                keys.put(Keys.RIGHT, false);
                ship.turnLeft();
                break;
            case Input.Keys.SPACE:
                ship.shoot();
                break;
        }

        if (!keys.containsValue(true)) {
            ship.turnNot();
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
