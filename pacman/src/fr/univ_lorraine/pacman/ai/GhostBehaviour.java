package fr.univ_lorraine.pacman.ai;

import java.util.ArrayList;
import java.util.Iterator;

import fr.univ_lorraine.pacman.model.Ghost;
import fr.univ_lorraine.pacman.model.World;

/**
 * Classe représentant un état d'un fantôme
 */
public class GhostBehaviour extends Etat {

    private Ghost g;
    private World w;

    public GhostBehaviour(Ghost gh, World wo){
        g = gh;
        w = wo;
    }

    /* Heuristique : distance directe au pacman */
    public int heuristique() {
        double pacX = (double)w.getPacman().getPosition().x, pacY = (double)w.getPacman().getPosition().y;
        double x = (double)g.getPosition().x, y = g.getPosition().y;
        return (int)Math.sqrt(Math.pow(pacX - x, 2) + Math.pow(pacY - y, 2));
    }

    /* Génere du html */
    public void ecrireStat(String[][] stats, int r, int g, int b) {
        stats[Math.round(this.g.getPosition().x)][Math.round(this.g.getPosition().y)] =  "<span style=\"background:rgb(" + r + "," + g + "," + b +")\">&#x2003</span>";
    }

    @Override
    public Iterator<Etat> iterator() {
        ArrayList<Etat> enfants = new ArrayList<Etat>();
        int x = Math.round(g.getPosition().x), y = Math.round(g.getPosition().y);
        try {
            if (x + 1 < w.getElems().length && (w.getElems()[x + 1][y] == null || w.getElems()[x + 1][y].acceptGhost())) {
                GhostBehaviour gb = new GhostBehaviour(new Ghost(g, w, 1, 0), w);
                gb.setActionPere(Action.DROITE);
                enfants.add(gb);
            }
            if (x - 1 >= 0 && (w.getElems()[x - 1][y] == null || w.getElems()[x - 1][y].acceptGhost())) {
                GhostBehaviour gb = new GhostBehaviour(new Ghost(g, w, -1, 0), w);
                gb.setActionPere(Action.GAUCHE);
                enfants.add(gb);
            }
            if (y + 1 < w.getElems()[0].length && (w.getElems()[x][y + 1] == null || w.getElems()[x][y + 1].acceptGhost())) {
                GhostBehaviour gb = new GhostBehaviour(new Ghost(g, w, 0, 1), w);
                gb.setActionPere(Action.HAUT);
                enfants.add(gb);
            }
            if (y - 1 >= 0 && (w.getElems()[x][y - 1] == null || w.getElems()[x][y - 1].acceptGhost())) {
                GhostBehaviour gb = new GhostBehaviour(new Ghost(g, w, 0, -1), w);
                gb.setActionPere(Action.BAS);
                enfants.add(gb);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Array out of bounds : Iterator : " + x + "  "  + y);
        }
        return enfants.iterator();
    }

    public Ghost getgh(){
        return g;
    }

    /* Redéfinition du equals nécéssaire pour comparer les états */
    public boolean equals(Object obj) {
        if (g.getPosition().x == ((GhostBehaviour)obj).getgh().getPosition().x && g.getPosition().y == ((GhostBehaviour)obj).getgh().getPosition().y){
            return true;
        }
        return false;
     }

    @Override
    public boolean estFinal() {
        return g.getBB().overlaps(w.getPacman().getBB());
    }

    public String toString(){
        return "EtatFantome";
    }
}
