package com.spazeinvaderz.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.spazeinvaderz.game.SpazeInvaderzGame;
import com.spazeinvaderz.game.controller.GameListener;
import com.spazeinvaderz.game.model.Alien;
import com.spazeinvaderz.game.model.Barrier;
import com.spazeinvaderz.game.model.Ship;
import com.spazeinvaderz.game.model.Shoot;
import com.spazeinvaderz.game.model.World;
import com.spazeinvaderz.game.factory.TextureFactory;

/**
 * Classe pour l'écran (affichage) du jeu
 */
public class GameScreen extends ScreenAdapter {

    /**
     * L'instance du jeu
     */
    private SpazeInvaderzGame sp;
    /**
     * Le monde du jeu
     */
    private World w;
    /**
     * Afficheur de sprite
     */
    private SpriteBatch batch;
    /**
     * Instance du vaisseau (joueur)
     */
    private Ship ship;
    /**
     * Listener pour les controles clavier
     */
    private GameListener gl;
    /**
     * Police pour l'affichage des points
     */
    private BitmapFont font;

    /**
     * Méthode d'instanciation des éléments (sprites, monde, joueur, etc...)
     *
     * @param spg instance du jeu
     */
    public GameScreen(SpazeInvaderzGame spg) {
        sp = spg;
        batch = new SpriteBatch();
        w = new World(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ship = w.getShip();
        gl = new GameListener(ship);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/8bitwonder.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);
        generator.dispose();

        Gdx.input.setInputProcessor(gl);
    }

    /**
     * Méthode de rendu graphique du jeu
     *
     * @param delta temps écoulé depuis le dernier rafraichissement
     */
    @Override
    public void render(float delta) {
        if(w.getLives() == 0 || w.getAliens().size() == 0) {
            sp.setLeaderBoardScreen(w.getPoints(), w.getWidth(), w.getHeight());
            return;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /**
         * On met à jour tous les éléments du monde
         */
        w.update(delta);

        batch.begin();

        font.draw(batch, "Score " + w.getPoints(), (float) w.getScoreX(), (float) w.getScoreY());

        /**
         * On affiche le vaisseau à l'écran
         */
        batch.draw(TextureFactory.getInstance().getShipTexture(), ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());

        /**
         * On parcourt la liste des aliens pour les affchier
         */
        for (Alien a : w.getAliens()) {
            switch (a.getType()) {
                case 1:
                    batch.draw(TextureFactory.getInstance().getInvader1Texture(), a.getX(), a.getY(), a.getWidth(), a.getHeight());
                case 2:
                    batch.draw(TextureFactory.getInstance().getInvader2Texture(), a.getX(), a.getY(), a.getWidth(), a.getHeight());
                case 3:
                    batch.draw(TextureFactory.getInstance().getInvader3Texture(), a.getX(), a.getY(), a.getWidth(), a.getHeight());
            }
        }

        /**
         * On parcourt les blocs pour les afficher à l'écran
         */
        for (int i = w.getNbBarriers() - 1; i >= 0; i--) {
            Barrier b = w.getBarrier(i);
            batch.draw(TextureFactory.getInstance().getBarrierTexture(b.getLife()), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }

        /**
         * On parcourt tous les tirs pour les afficher à l'écran
         */
        for (int i = w.getNbShoots() - 1; i >= 0; i--) {
            Shoot s = w.getShoot(i);
            batch.draw(TextureFactory.getInstance().getShootTexture(), s.getX(), s.getY(), s.getWidth(), s.getHeight());
        }

        /**
         * Affichage des vies
         */
        int lives = w.getLives();
        if (lives > 0)
            batch.draw(TextureFactory.getInstance().getShieldTexture(), (float) w.getScoreX(), (float) w.getScoreY() + 15, 15, 15);
        if (lives > 1)
            batch.draw(TextureFactory.getInstance().getShieldTexture(), (float) w.getScoreX() + 30, (float) w.getScoreY() + 15, 15, 15);
        if (lives > 2)
            batch.draw(TextureFactory.getInstance().getShieldTexture(), (float) w.getScoreX() + 60, (float) w.getScoreY() + 15, 15, 15);

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
