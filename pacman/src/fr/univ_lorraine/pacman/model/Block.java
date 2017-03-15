package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import fr.univ_lorraine.pacman.view.TextureFactory;

public class Block extends GameElement {

    public Block(Vector2 position, World w) {
        super(position, w);
        visu = TextureFactory.getInstance().getVisuBlock(position.x * w.getPpux(), position.y * w.getPpuy());
    }

    public void manageCollision(Pacman pac){
        pac.redirect();
    }

    public void manageCollision(Ghost g){
        g.redirect();
    }

    public boolean acceptGhost(){
        return false;
    }

    @Override
    public TextureRegion getTexture() {
        return visu.getTexture();
    }

    public String toString(){
        return "Block x:" + getPosition().x + " y:" + getPosition().y;
    }
}
