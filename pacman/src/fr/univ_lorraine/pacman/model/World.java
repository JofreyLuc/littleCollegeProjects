package fr.univ_lorraine.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import fr.univ_lorraine.pacman.ai.AEtoile;
import fr.univ_lorraine.pacman.ai.GhostBehaviour;

public class World implements Iterable<GameElement>{

    private Pacman pm;
    private Ghost blinky, pinky, inky, clyde;
    private int ppux, ppuy, nbPellets = 0;
    private boolean gameOver = false, victory = true; //Booléens d'tat du jeu
    final GameElement[][] elems; //Tableau d'éléments du jeu
    private int[][] saveLayout; //Sauvegarde du layout pour les stats
    private ArrayList<GameMoveableElement> living; //Pacman + fantômes
    static int WORLD_HEIGHT = 31, WORLD_WIDTH = 27;

    public World(){
        ppux = Gdx.graphics.getWidth() / getWorldWidth() - getWorldWidth() / 6;
        ppuy = Gdx.graphics.getHeight() / getWorldHeight();

        elems = new GameElement[WORLD_WIDTH][WORLD_HEIGHT];
        int[][] layout = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,1,1,2,0,0,0,1,1,1,1,1,3,1,1,1,1,1,0,0,0,0,0,2,0,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,3,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,0,0,0,1,1,0,1,1,1,1,1,3,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,3,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,3,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,3,1,1,1,1,1,1,1,1,0,1,1,1,0,1},
                {1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,3,1,1,1,1,1,1,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,0,0,0,1,1,0,1,1,3,3,3,3,3,3,3,1,1,0,0,0,0,1,1,1,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,1,1,3,1,1,1,1,1,3,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,1,1,3,1,3,3,3,1,3,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,0,0,0,1,1,0,0,0,0,1,1,3,1,3,3,3,1,3,0,0,0,1,1,0,0,0,0,0,1},
                {1,0,1,1,1,1,1,3,1,1,1,1,1,3,1,3,3,3,3,3,1,1,1,1,1,0,1,1,1,1,1},
                {1,0,0,0,0,1,1,0,0,0,0,1,1,3,1,3,3,3,1,3,0,0,0,1,1,0,0,0,0,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,1,1,3,1,3,3,3,1,3,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,1,1,3,1,1,1,1,1,3,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,0,0,0,1,1,0,1,1,3,3,3,3,3,3,3,1,1,0,0,0,0,1,1,1,0,1},
                {1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,3,1,1,1,1,1,1,1,1,0,1,1,1,0,1},
                {1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,3,1,1,1,1,1,1,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,3,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,3,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,0,0,0,1,1,0,1,1,1,1,1,3,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,3,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
                {1,0,0,0,0,1,1,2,0,0,0,1,1,1,1,1,3,1,1,1,1,1,0,0,0,0,0,2,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};


        saveLayout = layout;

        for (int i = 0; i < elems.length; i++){
            for (int j = 0; j < elems[0].length; j++){
                switch (layout[i][j]) {
                    case 1:
                        elems[i][j] = new Block(new Vector2(i, j), this);
                        break;
                    case 2:
                        elems[i][j] = new SuperPellet(new Vector2(i, j), this);
                        nbPellets++;
                        break;
                    case 0:
                        elems[i][j] = new Pellet(new Vector2(i, j), this);
                        nbPellets++;
                        break;
                }
            }
        }
        pm = new Pacman(new Vector2(13, 7), this);
        blinky = new Ghost(new Vector2(13, 17), this, 1, 1);
        pinky = new Ghost(new Vector2(13, 15), this, 2, 1);
        inky = new Ghost(new Vector2(15, 15), this, 3, 1);
        clyde = new Ghost(new Vector2(11, 15), this, 4, 1);

        living = new ArrayList<GameMoveableElement>();
        living.add(pm);
        living.add(blinky);
        living.add(pinky);
        living.add(inky);
        living.add(clyde);
    }

    public GameElement[][] getElems() {
        return elems;
    }

    public Pacman getPacman() {
        return pm;
    }

    public Ghost getBlinky() {
        return blinky;
    }

    public Ghost getPinky() {
        return pinky;
    }

    public Ghost getInky() {
        return inky;
    }

    public Ghost getClyde() { return clyde; }

    /* Permet de générer les statistiques A* de l'état actuel de la partie */
    public void generateStats() {
        /* Dessin des murs */
        String[][] stat = new String[saveLayout.length][saveLayout[0].length];
        for (int i = 0; i < saveLayout.length; i++){
            for (int j = 0; j < saveLayout[0].length; j++){
                if (saveLayout[i][j] == 1) stat[i][j] = "<span style=\"background:rgb(0,0,0)\">&#x2003</span>";
                else stat[i][j] = "<span style=\"background:rgb(255,255,255)\">&#x2003</span>";
            }
        }

        AEtoile aemodifie = new AEtoile();
        GhostBehaviour gh = (GhostBehaviour)aemodifie.trouverCheminPourStats(new GhostBehaviour(blinky, this), stat);

        /* Desin pacman + blinky */
        stat[Math.round(blinky.getPosition().x)][Math.round(blinky.getPosition().y)] = "<span style=\"background:rgb(255,0,0)\">&#x2003</span>";
        stat[Math.round(gh.getgh().getPosition().x)][Math.round(gh.getgh().getPosition().y)] = "<span style=\"background:rgb(255,255,0)\">&#x2003</span>";

        /* Calcul du temps et du nombre d'états */
        long debut = java.lang.System.currentTimeMillis();
        int nb = aemodifie.trouverCheminNbEtat(new GhostBehaviour(blinky, this));
        long fin = java.lang.System.currentTimeMillis();

        /* Ecriture du fichier */
        try {
            FileWriter fw = new FileWriter("./stats.html");
            BufferedWriter buf = new BufferedWriter(fw);
            for (int i = stat[0].length - 1; i >= 0; i--) {
                for (int j = stat.length - 1; j >= 0; j--){
                    buf.write(stat[j][i]);
                }
                buf.write("<br>");
                buf.newLine();
            }
            buf.write("<br><br>");
            buf.write("Temps d'execution : " + (fin - debut) + " ms<br>" + nb + " etats construits");
            buf.flush();
            buf.close();
            fw.close();
        } catch (IOException e){
            System.out.println("Exception écriture fichier");
        }
    }

    public static int getWorldHeight() {
        return WORLD_HEIGHT;
    }

    public static int getWorldWidth() {
        return WORLD_WIDTH;
    }

    public void removeElement(Vector2 position){
        elems[(int)position.x][(int)position.y] = null;
    }

    public void startHunt(){
        //Passe l'état de tous les fantômes à "chassé"
        blinky.setState(2);
        pinky.setState(2);
        inky.setState(2);
        clyde.setState(2);
    }

    public int getPpuy() {
        return ppuy;
    }

    public int getPpux() {
        return ppux;
    }

    public ArrayList<GameMoveableElement> getLiving() {
        return living;
    }

    public void pelletEaten(){
        nbPellets--;
    }

    public void setGameOver(){
        //Appelé si un fantôme touche pacman
        gameOver = true;
        victory = false;
    }

    public boolean isFinished(){
        return gameOver || (nbPellets == 0);
    }

    public boolean pacmanWon(){
        return victory;
    }

    @Override
    public Iterator<GameElement> iterator() {
        ArrayList<GameElement> arr = new ArrayList<GameElement>();
        for (int i = 0; i < elems.length; i++){
            for (int j = 0; j < elems[0].length; j++){
                if (elems[i][j] != null && elems[i][j] != pm){
                    arr.add(elems[i][j]);
                }
            }
        }
        for (GameElement ge : living){
            arr.add(ge);
        }
        return arr.iterator();
    }
}
