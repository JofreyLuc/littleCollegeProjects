package com.spazeinvaderz.game.model;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

public class GameMovableElementTest {

    private GameMovableElement element;

    @Before
    public void setUp() {
        World world = new World(100, 100);
        element = new Ship(world, 32, 32, new Vector2(20, 20));
    }

    @Test
    public void TestUp() {
        float init = element.getPosition().y;
        element.turnUp();
        element.move(1);
        float after = element.getPosition().y;
        assert (after > init);
    }

    @Test
    public void TestDown() {
        float init = element.getPosition().y;
        element.turnDown();
        element.move(1);
        float after = element.getPosition().y;
        assert (after < init);
    }

    @Test
    public void TestRight() {
        float init = element.getPosition().x;
        element.turnRight();
        element.move(1);
        float after = element.getPosition().x;
        assert (after > init);
    }

    @Test
    public void TestLeft() {
        float init = element.getPosition().x;
        element.turnLeft();
        element.move(1);
        float after = element.getPosition().x;
        assert (after < init);
    }

    @Test
    public void TestNone() {
        Vector2 init = element.getPosition();
        element.turnNot();
        element.move(1);
        Vector2 after = element.getPosition();
        assert (after.x == init.x);
        assert (after.y == init.y);
    }

    @Test
    public void TestMove() {
        float init = element.getPosition().x;
        element.turnRight();
        element.move(0.05f);
        float after = element.getPosition().x;
        System.out.println(after+"-"+init);
        assert (after - init == 10);
    }

    @Test
    public void TestMoveLimit() {
        element.turnLeft();
        element.move(3);
        Vector2 after = element.getPosition();
        assert (after.x == 0);
    }
}
