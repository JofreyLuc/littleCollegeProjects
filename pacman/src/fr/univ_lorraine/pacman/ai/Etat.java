package fr.univ_lorraine.pacman.ai;

/* Classe abstraite représentant un état générique */
public abstract class Etat implements Iterable<Etat>, Comparable<Etat>{
	
    protected static Etat etatFinal;
    protected int g, h;
    protected Etat pere = null;
    protected Action actionPere = null; //Action du père qui a mené au fils
    public enum Action {HAUT, BAS, GAUCHE, DROITE};

    public Etat(){
    }

    public boolean estFinal(){
    	return false;
    }

    public static void setEtatFinal(Etat etat){
    	etatFinal = etat;
    }

    public Etat getEtatFinal(){
    	return etatFinal;
    }
    
    public void setPere(Etat p){
    	pere = p;
    }
    
    public Etat getPere(){
    	return pere;
    }
    
    public void setActionPere(Action a){
    	actionPere = a;
    }
    
    public Action getActionPere(){
    	return actionPere;
    }
    
    public int getf(){
    	return g + h;
    }
    
    public int getg(){
    	return this.g;
    }
    
    public int geth(){
    	return this.h;
    }
    
    public void setg(int newg){
    	g = newg;
    }

    /* Mise à jour du coût */
    public void updateg(){
        if (this.getPere() != null) g = this.getPere().getg() + this.getPere().cout(this);
        else g = 0;
    }

    /* Mise à jour de l'heuristique */
    public void seth(){
    	h = heuristique();
    }
    
    public abstract int heuristique();

    public int cout(Etat t){
    	return 1;
    }

    /* Comparateur nécéssaire pour la file de priorité */
    public int compareTo(Etat e){
    	if (e.getf() > this.getf()) return -1;
        if (e.getf() == this.getf()) return 0;
    	return 1;
    }

    /* Permet de générer une donnée correpondant à cet état */
    abstract public void ecrireStat(String[][] stats, int r, int g, int b);
    
    public String toString(){
    	return null;
    }
}
