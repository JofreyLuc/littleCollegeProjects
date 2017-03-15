package fr.univ_lorraine.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.univ_lorraine.pacman.PacmanGame;

public class SplashScreen extends ScreenAdapter {

    private static int WELCOME_TIME = 1000;
    private PacmanGame pg;
    private SpriteBatch batch;
    private Texture img;
    private long debut;

    public SplashScreen(PacmanGame pacg){
        pg = pacg;
        batch = new SpriteBatch();
        img = new Texture("logo_UL_DI.jpg");
        debut = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (System.currentTimeMillis() - debut > WELCOME_TIME){
            pg.setGameScreen();
        } else {
            batch.begin();
            batch.draw(img, 0, 0);
            batch.end();
        }
    }
}
