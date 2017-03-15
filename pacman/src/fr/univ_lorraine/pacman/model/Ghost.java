package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.Position;

import fr.univ_lorraine.pacman.ai.Etat.Action;

import fr.univ_lorraine.pacman.ai.AEtoile;
import fr.univ_lorraine.pacman.ai.GhostBehaviour;
import fr.univ_lorraine.pacman.view.TextureFactory;

/**
 * Created by jyeil on 29/01/16.
 */
public class Ghost extends GameMoveableElement implements Iterable<GameElement>{

    private int name; //Blinky = 1, Pinky = 2, Inky = 3, Clyde = 4
    private int state; //Hunting = 1, hunted = 2, dead = 3
    private int casualties = 0; //Nombre de fantômes mangés
    private VisualChars deadGhost, huntedGhost;
    private ArrayList<GameElement> chemin; //Chemin actuel suivi par le fantôme

    public Ghost(Vector2 position, World w, int n, int s) {
        super(position, w);
        name = n;
        switch (n) {
            case 1:
                visu = TextureFactory.getInstance().getVisuBlinky(w.getPpux() * position.x, w.getPpuy() * position.y);
                break;
            case 2:
                visu = TextureFactory.getInstance().getVisuPinky(w.getPpux() * position.x, w.getPpuy() * position.y);
                break;
            case 3:
                visu = TextureFactory.getInstance().getVisuInky(w.getPpux() * position.x, w.getPpuy() * position.y);
                break;
            default:
                visu = TextureFactory.getInstance().getVisuClyde(w.getPpux() * position.x, w.getPpuy() * position.y);
                break;
        }
        state = s;
        deadGhost = TextureFactory.getInstance().getVisuDeadGhost(w.getPpux() * position.x, w.getPpuy() * position.y);
        huntedGhost = TextureFactory.getInstance().getVisuHuntedGhost(w.getPpux() * position.x, w.getPpuy() * position.y);
        chemin = new ArrayList<GameElement>();
    }

    public Ghost(Ghost g, World w, int changeX, int changeY){
        super(new Vector2(g.getPosition().x + changeX, g.getPosition().y + changeY), w);
        Vector2 newPos = new Vector2(g.getPosition().x + changeX, g.getPosition().y + changeY);
        name = g.getName();
        switch (name) {
            case 1:
                visu = TextureFactory.getInstance().getVisuBlinky(w.getPpux() * newPos.x, w.getPpuy() * newPos.y);
                break;
            case 2:
                visu = TextureFactory.getInstance().getVisuPinky(w.getPpux() * newPos.x, w.getPpuy() * newPos.y);
                break;
            case 3:
                visu = TextureFactory.getInstance().getVisuInky(w.getPpux() * newPos.x, w.getPpuy() * newPos.y);
                break;
            default:
                visu = TextureFactory.getInstance().getVisuClyde(w.getPpux() * newPos.x, w.getPpuy() * newPos.y);
                break;
        }
        setPosition(newPos.x, newPos.y);
        state = g.getState();
        deadGhost = TextureFactory.getInstance().getVisuDeadGhost(w.getPpux() * newPos.x, w.getPpuy() * newPos.y);
        huntedGhost = TextureFactory.getInstance().getVisuHuntedGhost(w.getPpux() * newPos.x, w.getPpuy() * newPos.y);
        chemin = new ArrayList<GameElement>();
    }

    public int getName(){
        return name;
    }

    public void setState(int newState){
        state = newState;
    }

    public void hasCollidedWith(GameElement ge){
        ge.manageCollision(this);
    }

    public void manageCollision(Pacman pac){
        //Selon l'état du fantôme
        switch (state){
            case 1 :
                world.setGameOver();
                break;
            case 2 :
                state = 3;
                break;
            default :
                casualties++;
                pac.addPoints(100 * (int)Math.pow(2, ((double)casualties)));
                break;
        }
    }

    @Override
    public TextureRegion getTexture() {
        switch (state){
            case 1 :
                return visu.getTexture();
            case 2  :
                return huntedGhost.getTexture();
            default :
                return deadGhost.getTexture();
        }
    }

    public int getState(){
        return state;
    }

    /* Mise à jour de la direction du fantome en fonction de l'application d'A* */
    public void updateDir(){
        AEtoile ae = new AEtoile();
        GhostBehaviour gb = new GhostBehaviour(this, world);
        ArrayList<Action> alist = ae.trouverChemin(gb);

        updateChemin(alist);

        if (!alist.isEmpty()){
            Action al = alist.get(0);
            switch (al) {
                case HAUT:
                    turnUp();
                    break;
                    case BAS:
                    turnDown();
                    break;
                case GAUCHE:
                    turnLeft();
                    break;
                case DROITE:
                    turnRight();
                    break;
            }
        }
    }

    /* Mise à jour du chemin du fantôme (représenté par un ensemble de carrés */
    public void updateChemin(ArrayList<Action> act){
        chemin.clear();
        int x = Math.round(pos.x), y = Math.round(pos.y);
        for (Action a : act){
            switch (a){
                case HAUT:
                    y++;
                    break;
                case BAS:
                    y--;
                    break;
                case DROITE:
                    x++;
                    break;
                case GAUCHE:
                    x--;
                    break;
            }
            chemin.add(new Square(new Vector2(x, y), world, name));
        }
    }

    public Iterator<GameElement> iterator(){
        return chemin.iterator();
    }

    public String toString(){
        return "Ghost x:" + getPosition().x + " y:" + getPosition().y;
    }
}
