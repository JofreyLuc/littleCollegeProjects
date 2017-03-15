package fr.univ_lorraine.pacman.ai;

import java.util.ArrayList;
import fr.univ_lorraine.pacman.ai.Etat.Action;

/**
 * Interface des algorithmes de recherche.
 */
public interface IRecherche {
	ArrayList<Action> trouverChemin(Etat etat);
}
