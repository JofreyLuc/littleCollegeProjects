package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.univ_lorraine.pacman.view.TextureFactory;

public abstract class GameMoveableElement extends GameElement {

    protected int speed = 3; //Vitesse de l'élément
    private float lastPositionX, lastPositionY; //Sert au repositionnement de l'élément
    protected float stateTime = 0;
    protected enum Direction {RIGHT, LEFT, UP, DOWN, NOMOV}; //Quatre directions + mouvement immobile
    protected Direction dir = Direction.NOMOV, lastMove = Direction.NOMOV;

    public GameMoveableElement(Vector2 position, World w) {
        super(position, w);
    }

    public void turnLeft(){
        dir = Direction.LEFT;
    }

    public void turnRight(){
        dir = Direction.RIGHT;
    }

    public void turnUp(){
        dir = Direction.UP;
    }

    public void turnDown(){ dir = Direction.DOWN; }

    public void stayStill() {dir = Direction.NOMOV ;}

    public void redirect(){
        setPosition((float)Math.round(lastPositionX), (float)Math.round(lastPositionY)); //Repositionne à l'arrondi entier de la dernière position connue
    }

    /* Retourne l'élément avec lequel on est entré en collision et repositionne légèrement */
    public GameElement prepareCollision(){
        /* Test des collisions avec éléments mobiles */
        for (GameMoveableElement ge : world.getLiving()){
            if (ge != this){
                if (ge.getBB().overlaps(visu.getBB())) return ge;
            }
        }

        /* Test des collisions avec labyrinthe */
        GameElement ge = null, gePlus = null, geMoins = null; /* Sert au repositionnement pour les virages  :
                                                                 correspond aux éléments à droite et à gauche de l'élément collisionné*/
        int x = Math.round(pos.x), y = Math.round(pos.y); // Récupération de l'élément du tableau
        switch (dir){
            case LEFT:
                gePlus = world.getElems()[x - 1][y + 1];
                ge = world.getElems()[x - 1][y];
                geMoins = world.getElems()[x - 1][y - 1];
                break;
            case RIGHT:
                gePlus = world.getElems()[x + 1][y + 1];
                ge = world.getElems()[x + 1][y];
                geMoins = world.getElems()[x + 1][y - 1];
                break;
            case DOWN:
                gePlus = world.getElems()[x - 1][y - 1];
                ge = world.getElems()[x][y - 1];
                geMoins = world.getElems()[x + 1][y - 1];
                break;
            case UP :
                gePlus = world.getElems()[x - 1][y + 1];
                ge = world.getElems()[x][y + 1];
                geMoins = world.getElems()[x + 1][y + 1];
                break;
            case NOMOV:
                gePlus= null;
                ge = null;
                geMoins = null;
        }
        if (gePlus != null && geMoins != null && ge == null && (visu.getBB().overlaps(geMoins.getBB()) || visu.getBB().overlaps(gePlus.getBB()))){
            //Repositionnement virage
            redirect();
        }
        if (ge != null && visu.getBB().overlaps(ge.getBB())){
            //System.out.println("COLLISION");
            return ge;
        }
        return null;
    }

    @Override
    public TextureRegion getTexture() {return null;}

    /* Mise à jour de la position : mouvement*/
    public void update(float delta){
        stateTime += delta;
        float x = getPosition().x, y = getPosition().y;
        lastPositionX = x;
        lastPositionY = y;
        switch (dir) {
            case LEFT :
                setPosition(x - (speed * delta), y);
                lastMove = Direction.LEFT;
                break;
            case RIGHT :
                setPosition(x + (speed * delta), y);
                lastMove = Direction.RIGHT;
                break;
            case UP:
                setPosition(x, y + (speed * delta));
                lastMove = Direction.UP;
                break;
            case DOWN :
                setPosition(x, y - (speed * delta));
                lastMove = Direction.DOWN;
                break;
        }
    }
}
