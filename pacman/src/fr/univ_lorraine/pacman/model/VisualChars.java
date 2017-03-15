package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class VisualChars {

    /* Classe servant à contenir les infos visuelles d'un élément : sa texture et sa boundingbox */

    private Rectangle boundingbox;
    private TextureRegion texture;
    private float baseBBWidth, baseBBHeight;

    public VisualChars(Rectangle bb, TextureRegion t){
        boundingbox = bb;
        texture = t;
        baseBBHeight = bb.getHeight();
        baseBBWidth = bb.getWidth();
    }

    public Rectangle getBB() {
        return boundingbox;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion tex) { texture = tex; }

    public float getBaseBBWidth() {
        return baseBBWidth;
    }

    public float getBaseBBHeight() {
        return baseBBHeight;
    }
}
