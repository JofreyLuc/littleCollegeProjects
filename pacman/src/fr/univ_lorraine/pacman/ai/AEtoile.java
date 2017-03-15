package fr.univ_lorraine.pacman.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

import fr.univ_lorraine.pacman.ai.Etat.Action;
import fr.univ_lorraine.pacman.model.World;

public class AEtoile implements IRecherche {

	private PriorityQueue<Etat> OUVERTS;
	private LinkedList<Etat> FERMES;
	
	public AEtoile() {
	}
	
	/* Fonction principale qui génére la liste des actions à partir de l'état renvoyé par la fonction secondaire */
	public ArrayList<Action> trouverChemin(Etat etat) {
		Etat actuel = trouverCheminSecond(etat);
		ArrayList<Action> actionsPeres = new ArrayList<Action>();
		while (actuel.getPere() != null){
			actionsPeres.add(actuel.getActionPere());
			actuel = actuel.getPere();
		}
		Collections.reverse(actionsPeres);
		return actionsPeres;
	}

    /* Renvoie le dernier état de l'application d'A* */
	private Etat trouverCheminSecond(Etat etat){
		Etat s;
		OUVERTS = new PriorityQueue<Etat>();
		FERMES = new LinkedList<Etat>();
		OUVERTS.add(etat);
		etat.setg(0);
		s = etat;
		while (!OUVERTS.isEmpty() && !OUVERTS.peek().estFinal()){
			OUVERTS.remove(s);
			FERMES.add(s);
			for (Etat suivant : s){
				suivant.updateg();
				suivant.seth();
				if ((!OUVERTS.contains(suivant) && !FERMES.contains(suivant)) || suivant.getg() > (s.getg() + s.cout(suivant))){
					int nouveauG = s.getg() + s.cout(suivant);
                    suivant.setg(nouveauG);
					suivant.setPere(s);
					OUVERTS.offer(suivant);
					if (FERMES.contains(suivant)){
						FERMES.remove(suivant);
					}
				}
			}
			if (!OUVERTS.isEmpty()) s = OUVERTS.peek();

		}
		if (OUVERTS.isEmpty()) return null;

		return s;
	}

    /* Application d'A* qui remplit un tableau de statistiques au cours de l'appel */
    public Etat trouverCheminPourStats(Etat etat, String[][] stats){
        Etat s;
        float r = 160, g = 255, b = 0; //Couleurs d'affichage des états
        OUVERTS = new PriorityQueue<Etat>();
        FERMES = new LinkedList<Etat>();
        OUVERTS.add(etat);
        etat.setg(0);
        s = etat;
        while (!OUVERTS.isEmpty() && !OUVERTS.peek().estFinal()){
            OUVERTS.remove(s);
            FERMES.add(s);
            for (Etat suivant : s) {
                suivant.updateg();
                suivant.seth();

                /* Appel de l'écriture de l'état avec une teinte calculée en fonction de son heuristique */
                float h = suivant.geth();
                if (suivant.getf() != 0)
                    suivant.ecrireStat(stats, Math.round(r), Math.round(g - (h / (World.getWorldHeight() - 2)) * 255), Math.round(b + (h / (World.getWorldHeight() - 2)) * 255));
                else
                    suivant.ecrireStat(stats, Math.round(r), Math.round(g), Math.round(b));


                if ((!OUVERTS.contains(suivant) && !FERMES.contains(suivant)) || suivant.getg() > (s.getg() + s.cout(suivant))){
                    int nouveauG = s.getg() + s.cout(suivant);
                    suivant.setg(nouveauG);
                    suivant.setPere(s);
                    OUVERTS.offer(suivant);
                    if (FERMES.contains(suivant)){
                        FERMES.remove(suivant);
                    }
                }
            }
            if (!OUVERTS.isEmpty()){
                s = OUVERTS.peek();
            }
        }
        if (OUVERTS.isEmpty()) {

            return null;
        }
        return s;
    }

    /* Applique A* mais renvoie le nombre d'états construit plutôt que l'état final */
    public int trouverCheminNbEtat(Etat etat){
        Etat s;
        int counter = 0;
        OUVERTS = new PriorityQueue<Etat>();
        FERMES = new LinkedList<Etat>();
        OUVERTS.add(etat);
        etat.setg(0);
        s = etat;
        while (!OUVERTS.isEmpty() && !OUVERTS.peek().estFinal()){
            OUVERTS.remove(s);
            FERMES.add(s);
            for (Etat suivant : s){
                suivant.updateg();
                suivant.seth();
                if ((!OUVERTS.contains(suivant) && !FERMES.contains(suivant)) || suivant.getg() > (s.getg() + s.cout(suivant))){
                    counter++;
                    int nouveauG = s.getg() + s.cout(suivant);
                    suivant.setg(nouveauG);
                    suivant.setPere(s);
                    OUVERTS.offer(suivant);
                    if (FERMES.contains(suivant)){
                        FERMES.remove(suivant);
                    }
                }
            }
            if (!OUVERTS.isEmpty()){
                s = OUVERTS.peek();
            }
        }
        return counter;
    }
}

