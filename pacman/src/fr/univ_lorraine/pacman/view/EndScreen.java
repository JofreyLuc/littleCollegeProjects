package fr.univ_lorraine.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Font;

import fr.univ_lorraine.pacman.PacmanGame;

public class EndScreen extends ScreenAdapter {

    private PacmanGame pg;
    private SpriteBatch batch;
    private String msg = "Défaite...\n";
    private int finalScore;
    private BitmapFont font;

    public EndScreen(PacmanGame pacg, boolean v, int score){
        pg = pacg;
        finalScore = score;
        if (v) msg = "Victoire !\n";
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, msg + "Score : " + Integer.toString(finalScore), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
