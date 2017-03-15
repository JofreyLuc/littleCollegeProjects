package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe pour le monde du jeu
 */
public class World implements Iterable<GameElement>{

    private int width, height, points;
    private double originX, originY;
    private boolean aliensWentDown;
    private int lives;

    /**
     * Instance du vaisseau (joueur) associée au monde
     */
    private Ship ship;

    private ArrayList<Shoot> shoots;

    /**
     * Liste de barrieres
     */
    private ArrayList<Barrier> barriers;
    private ArrayList<Alien> aliens;



    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<Alien> getAliens() {
        return aliens;
    }

    public void update(float delta) {
        ship.move(delta);

        updateAliensMovement();
        for (Alien a : aliens){
            a.move(delta);
        }

        for(int i = shoots.size() - 1; i >= 0; i--) {
            Shoot s = shoots.get(i);
            if(s != null) {
                s.move(delta);

                GameElement o = getInRect(s.getBounds(), s.getSide());

                if (o != null) {
                    o.die();
                    s.die();
                }
            }
        }
    }

    /**
     * Renvoie la limite gauche de la zone de déplacement des aliens
     *
     * @return double
     */
    public double getAliensLeftBound() {
        return width * 0.05;
    }

    /**
     * Renvoie la limite droite de la zone de déplacement des aliens
     *
     * @return double
     */
    public double getAliensRightBound() {
        return width * 0.95;
    }

    /**
     * Renvoie la limite en bas de la zone de déplacement des aliens
     *
     * @return double
     */
    public double getAliensLowerBound() {
        return height * 0.37;
    }

    /**
     * Renvoie la position X préférée pour le score
     * @return double
     */
    public double getScoreX(){
        return width * 0.8;
    }

    /**
     * Renvoie la position Y préférée pour le score
     * @return double
     */
    public double getScoreY(){
        return height * 0.9;
    }

    /**
     * Constructeur de la classe
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        ship = new Ship(this, 50, 50, new Vector2(50,50));
        shoots = new ArrayList<Shoot>();
        barriers = new ArrayList<Barrier>();
        aliens = new ArrayList<Alien>();
        setupAliens(20);
        setupBarriers();
        aliensWentDown = false;
        lives = 3;
    }

    /**
     * Constructeur de la classe qui génère un monde vide pour les tests.
     */
    public World() {
        this.width = 100;
        this.height = 100;
        shoots = new ArrayList<Shoot>();
        barriers = new ArrayList<Barrier>();
        aliens = new ArrayList<Alien>();
        aliensWentDown = false;
        lives = 3;
    }

    /**
     * le joueur perd une vie
     */
    public void loose() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Place des aliens en situation de début de partie
     *
     * @param nbUnits : Nombre d'aliens à placer
     */
    private void setupAliens(int nbUnits) {
        double x = width * 0.1, y = height * 0.75;
        originX = x;
        for (int i = 1; i <= nbUnits; i++) {
            aliens.add(new Alien(this, 15, 15, new Vector2((float) x, (float) y), (int) Math.ceil(Math.random() * 3)));
            x += (width * 0.8) * 0.1;
            if (i % 10 == 0) {
                y -= (height * 0.05);
                x = width * 0.1;
            }
        }
    }

    private void setupBarriers() {
        float h = (float) 0.25 * height, maxWidth = (10 * width) / 11, step = width / 11;
        for(float i = (float) (width/22); i < maxWidth; i += step) {
            barriers.add(new Barrier(this, new Vector2(i, h)));
        }
    }

    private void updateAliensMovement(){
        /**
         * Si pas d'alien, on s'arrête là
         */
        if(aliens.size() == 0) return;

        originX = aliens.get(0).getPosition().x;
        originY = aliens.get(aliens.size()-1).getPosition().y;

        if (originX <= getAliensLeftBound()) {
            for (Alien a : aliens) {
                if (!aliensWentDown && originY > getAliensLowerBound()) {
                    a.turnDown();
                } else {
                    a.turnRight();
                }
            }
            aliensWentDown = true;

        } else if (originX >= (getAliensRightBound() - getWidth() * 0.8)) {
            for (Alien a : aliens) {
                if (!aliensWentDown && originY > getAliensLowerBound()) {
                    a.turnDown();
                } else {
                    a.turnLeft();
                }
            }
            aliensWentDown = true;

        } else {
            aliensWentDown = false;
        }

    }

    /**
     *  Crée un tir (du joueur) aux coordonnées indiquées
     * @param position : position de départ du tir
     */
    void shoot(Vector2 position, GameMovableElement.Direction direction, GameElement.Side side) {
        Shoot shoot = new Shoot(this, position, direction, side);
        shoots.add(shoot);
    }

    /**
     * Ajoute des points au compteur de points
     * @param nb : nombre de points à ajouter
     */
    void addPoints(int nb){
        points += nb;
    }

    void setShip(Ship s){
      ship = s;
    }


    void addAlien(Alien a){
      aliens.add(a);
    }
    /**
     * getNbShoots : renvoie le nombre de tirs
     * @return integer
     */
    public int getNbShoots() {
        return shoots.size();
    }

    /**
     * getNbBarriers : nombre de blocs
     * @return integer
     */
    public int getNbBarriers() { return barriers.size(); }

    /**
     * getShoot : renvoie le tir spécifié (0 à World.getNbShoots())
     * @param i : index du tir
     * @return Shoot
     */
    public Shoot getShoot(int i) {
        return shoots.get(i);
    }

    /**
     * getBarrier : renvoie le bloc spécifié (0 à World.getNbBarriers())
     * @param i : index du bloc
     * @return Barrier
     */
    public Barrier getBarrier(int i) { return barriers.get(i); }

    /**
     * removeShoot : supprime le tir indiqué du monde
     * @param shoot : tir à supprimer
     */
    public void removeShoot(Shoot shoot) {
        shoots.remove(shoot);
    }

    /**
     * removeAlien : supprime l'alien indiqué du monde
     * @param alien : alien à supprimer
     */
    void removeAlien(Alien alien) { aliens.remove(alien); }

    /**
     * removeBarrier : supprime le bloc indiqué
     * @param barrier : bloc à supprimer
     */
    void removeBarrier(Barrier barrier) { barriers.remove(barrier); }

    /**
     * Renvoie le vaisseau du monde
     *
     * @return ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Renvoie le nombre de points
     * @return int
     */
    public int getPoints(){
        return points;
    }

    /**
     * Renvoie l'objet dans le rectangle indiqué
     *
     * @param rect : rectangle dans lequel chercher
     * @param friendSide : côté à ignorer ("tir amis")
     *
     * @return GameElement
     */
    public GameElement getInRect(Rectangle rect, GameElement.Side friendSide) {
        if(friendSide != GameElement.Side.PLAYER) {
            if (ship.getBounds().overlaps(rect)) {
                return ship;
            }
        }

        if(friendSide != GameElement.Side.ALIEN) {
            for (Alien alien : aliens) {
                if (alien.getBounds().overlaps(rect)) {
                    return alien;
                }
            }
        }

        for (Barrier barrier : barriers) {
            if (barrier.getBounds().overlaps(rect)) {
                return barrier;
            }
        }

        return null;
    }

    public Iterator<GameElement> iterator(){
        ArrayList<GameElement> list = new ArrayList<GameElement>();
        list.add(ship);
        for (Alien a : aliens) list.add(a);
        for (Shoot s : shoots) list.add(s);
        for (Barrier b : barriers) list.add(b);
        return list.iterator();
    }
}
