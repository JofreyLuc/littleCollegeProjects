package fr.univ_lorraine.pacman.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import fr.univ_lorraine.pacman.model.World;

public class GameListener implements InputProcessor {
    private World w;
    private enum Keys {UP, DOWN, LEFT, RIGHT}
    private Map<Keys, Boolean> keys;
    private float VERTICAL_INCLINATION = (float)0.7, HORIZONTAL_INCLINATION = (float)1.5; //Gestion de la sensibilité acceléromètre

    public GameListener(World world){
        super();
        w = world;
        keys = new HashMap<Keys, Boolean>();
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                w.getPacman().turnLeft();
                keys.put(Keys.LEFT, true);
                break;
            case Input.Keys.RIGHT:
                w.getPacman().turnRight();
                keys.put(Keys.RIGHT, true);
                break;
            case Input.Keys.UP:
                w.getPacman().turnUp();
                keys.put(Keys.UP, true);
                break;
            case Input.Keys.DOWN:
                w.getPacman().turnDown();
                keys.put(Keys.DOWN, true);
                break;
            case Input.Keys.F5:
                w.generateStats();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                keys.put(Keys.LEFT, false);
                break;
            case Input.Keys.RIGHT:
                keys.put(Keys.RIGHT, false);
                break;
            case Input.Keys.UP:
                keys.put(Keys.UP, false);
                break;
            case Input.Keys.DOWN:
                keys.put(Keys.DOWN, false);
                break;
        }
        if (!keys.containsValue(true)){
            w.getPacman().stayStill();
            w.getBlinky().stayStill();
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

    /* Gestion de l'acceléromètre : retourne faux si indisponible */
    public boolean updateAccelerometer(){
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
            if (Gdx.input.getAccelerometerX() >= HORIZONTAL_INCLINATION + 3.14) {
                w.getPacman().turnDown();
                return true;
            } else if (Gdx.input.getAccelerometerX() <= -(HORIZONTAL_INCLINATION + 3.14)) {
                w.getPacman().turnUp();
                return true;
            }
            if (Gdx.input.getAccelerometerY() >= VERTICAL_INCLINATION) {
                w.getPacman().turnRight();
                return true;
            } else if (Gdx.input.getAccelerometerY() <= -VERTICAL_INCLINATION) {
                w.getPacman().turnLeft();
                return true;
            }
            w.getPacman().stayStill();
            return true;
        }
        return false;
    }
}
