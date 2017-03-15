package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.univ_lorraine.pacman.view.TextureFactory;

/**
 * Carrés qui permettent de représenter visuellement le chemin d'un fantôme
 */
public class Square extends GameElement {
    private int color; //1 = red, 2 = pink, 3 = blue, 4 = orange

    public Square(Vector2 position, World w, int c){
        super(position, w);
        color = c;
        switch (c){
            case 1:
                visu = TextureFactory.getInstance().getVisuRed(w.getPpux() * position.x, w.getPpuy() * position.y);
                break;
            case 2 :
                visu = TextureFactory.getInstance().getVisuPink(w.getPpux() * position.x, w.getPpuy() * position.y);
                break;
            case 3:
                visu = TextureFactory.getInstance().getVisuBlue(w.getPpux() * position.x, w.getPpuy() * position.y);
                break;
            default:
                visu = TextureFactory.getInstance().getVisuOrange(w.getPpux() * position.x, w.getPpuy() * position.y);
                break;
        }

    }

    @Override
    public TextureRegion getTexture() {
        return visu.getTexture();
    }

    @Override
    public String toString() {
        switch (color){
            case 1: return "RedSquare " + pos.x + " " + pos.y;
            case 2 : return "PinkSquare " + pos.x + " " + pos.y;
            case 3 : return "BlueSquare " + pos.x + " " + pos.y;
            default : return "OrangeSquare " + pos.x + " " + pos.y;
        }
    }
}
