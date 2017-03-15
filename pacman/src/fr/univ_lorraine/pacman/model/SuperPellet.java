package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;

import java.awt.Rectangle;

import fr.univ_lorraine.pacman.view.TextureFactory;

public class SuperPellet extends GameElement {

    public SuperPellet(Vector2 position, World w){
        super(position, w);
        visu = TextureFactory.getInstance().getVisuSPellet(w.getPpux() * position.x, w.getPpuy() * position.y);
    }

    public void manageCollision(Pacman pac){
        pac.addPoints(50);
        world.removeElement(pos);
        world.pelletEaten(); // diminution du nombre de pellets
        Gdx.input.vibrate(500);
        world.startHunt(); //Démarrage de la chasse
    }

    @Override
    public TextureRegion getTexture() {
        return visu.getTexture();
    }

    public String toString(){
        return "SuperPellet x:" + getPosition().x + " y:" + getPosition().y;
    }
}
