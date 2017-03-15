package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public abstract class GameElement {

    protected Vector2 pos; //Position
    protected World world;
    protected int width, height; //Taille : le plus souvent à 1
    protected VisualChars visu; //Caractéristiques visuelles (Texture + BoundingBox)

    public GameElement(Vector2 position, World w){
        pos = position;
        world = w;
        width = 1;
        height = 1;
    }

    public boolean acceptGhost(){
        return true;
    }

    public Vector2 getPosition() {
        return pos;
    }

    /* Mise à jour de la position de l'élément et de sa BoundingBox */
    public void setPosition(float newx, float newy){
        pos.set(newx, newy);
        visu.getBB().setPosition(newx * world.getPpux(), newy * world.getPpuy());
    }

    /* Met à jour la taille de la boundingbox (au cas où, pour de futurs repositionnements */
    public void updateBB(float x, float y){
        visu.getBB().setSize(width * x, height * y);
    }

    /* Gestion des collisions, pour les éléments mobiles */
    public void manageCollision(Ghost g){};
    public void manageCollision(Pacman p){};

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBB() { return visu.getBB(); }

    public abstract TextureRegion getTexture();

    public abstract String toString();
}
