package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.univ_lorraine.pacman.view.TextureFactory;

public class Pellet extends GameElement {

    private Sound sound;

    public Pellet(Vector2 position, World w){
        super(position, w);
        visu = TextureFactory.getInstance().getVisuPellet(w.getPpux() * position.x, w.getPpuy() * position.y);
        sound = Gdx.audio.newSound(Gdx.files.internal("beep.mp3"));
    }

    public void manageCollision(Pacman pac){
        pac.addPoints(10);
        world.pelletEaten(); //Diminution du nombre de pellets
        sound.play(0.2f);
        world.removeElement(pos);
    }

    @Override
    public TextureRegion getTexture() {
        return visu.getTexture();
    }

    public String toString(){
        return "Pellet x:" + getPosition().x + " y:" + getPosition().y;
    }
}
