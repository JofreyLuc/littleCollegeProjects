package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.univ_lorraine.pacman.view.TextureFactory;

public class Pacman extends GameMoveableElement {

    private int points = 0; //Score

    public Pacman(Vector2 position, World w) {
        super(position, w);
        speed = 6; //Le pacman est plus rapide
        visu = TextureFactory.getInstance().getVisuPacman(w.getPpux() * position.x, w.getPpuy() * position.y);
    }

    public void hasCollidedWith(GameElement ge){
        ge.manageCollision(this);
    }

    public void manageCollision(Ghost g){
        switch (g.getState()){
            case 1 :
                world.setGameOver();
                break;
            case 2 :
                g.setState(3);
                break;
            default :
                break;
        }
    }

    @Override
    public void manageCollision(Pacman p) {/* Il y a un problème */}

    public void addPoints(int p){
        points += p;
    }

    public int getScore(){
        return points;
    }

    @Override
    public TextureRegion getTexture() {
        /* Récupération de la bonne frame d'animation */
        switch (dir) {
            case DOWN:
                TextureFactory.getInstance().updateVisuPacman(4, stateTime, visu);
                break;
            case UP:
                TextureFactory.getInstance().updateVisuPacman(2, stateTime, visu);
                break;
            case RIGHT:
                TextureFactory.getInstance().updateVisuPacman(3, stateTime, visu);
                break;
            case LEFT:
                TextureFactory.getInstance().updateVisuPacman(1, stateTime, visu);
                break;
        }
        return visu.getTexture();
    }

    @Override
    public String toString() {
        return "Pacman x:" + getPosition().x + " y:" + getPosition().y;
    }
}
