package com.spazeinvaderz.game.view;

import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.spazeinvaderz.game.SpazeInvaderzGame;

/**
 * Classe pour l'écran de fin de partie
 */
public class EndScreen extends ScreenAdapter {

    /**
     * L'instance du jeu
     */
    private SpazeInvaderzGame sp;

    private SpriteBatch batch;

    /**
     * Police pour l'affichage des textes
     */
    private BitmapFont font;

    private int score = 0;
    private float w, h;

    /**
     * Méthode d'instanciation des éléments
     *
     * @param spg instance du jeu
     */
    public EndScreen(SpazeInvaderzGame spg) {
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/8bitwonder.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    /**
     * Méthode de rendu
     *
     * @param delta temps écoulé depuis le dernier rafraichissement
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.draw(batch, "Score : " + score, (float) (w * 0.4), (float) (h * 0.5));
        batch.end();
    }

    /**
     * Méthode de gestion des données
     *
     * @param score score du joueur
     * @param w     largeur de l'écran
     * @param h     hauteur de l'écran
     */
    public void setData(int score, float w, float h) {
        this.score = score;
        this.w = w;
        this.h = h;
    }
}
