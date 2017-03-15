package com.spazeinvaderz.game;

import com.badlogic.gdx.Game;
import com.spazeinvaderz.game.view.EndScreen;
import com.spazeinvaderz.game.view.GameScreen;

/**
 * Classe de lancement des écrans du jeu
 */
public class SpazeInvaderzGame extends Game {

    /**
     * Ecran principal de jeu
     */
    private GameScreen gs;
    private EndScreen es;

    /**
     * Méthode de création des écrans et d'assignation du premier écran
     */
    @Override
    public void create() {
        gs = new GameScreen(this);
        es = new EndScreen(this);

        setGameScreen();
    }

    /**
     * Assigne l'écran de jeu en tant qu'écran actif
     */
    private void setGameScreen() {
        setScreen(gs);
    }

    /**
     * Assigne l'écran de fin en tant qu'écran actif
     *
     * @param score score
     * @param w     largeur de l'écran
     * @param h     hauteur de l'écran
     */
    public void setLeaderBoardScreen(int score, float w, float h) {
        es.setData(score, w, h);
        setScreen(es);
    }
}
