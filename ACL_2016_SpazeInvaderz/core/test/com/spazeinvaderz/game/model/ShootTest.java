package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

public class ShootTest {

    private Ship ship;
    private Alien alien;
    private World world;

    @Before
    public void setUp() {
        world = new World();
        ship = new Ship(world, 10, 10, new Vector2(20,20));
        alien = new Alien(world, 10, 10, new Vector2(20,80), 1);
        world.setShip(ship);
        world.addAlien(alien);
    }

    @Test
    public void TestDestroyAlien(){
        ship.shoot();
        alien.turnNot(); // On immobilise l'alien qui par défaut bouge à droite.
        for(int i=0; i<5000; i++)
          world.update(0.015f); //Simule la boucle de jeu
        assert(world.getPoints() == 100);
    }

    @Test
    public void TestDestroyShip(){
        alien.shoot();
        for(int i=0; i<5000; i++)
          world.update(0.015f); //Simule la boucle de jeu
        assert(world.getLives() <= 2);
    }
}
