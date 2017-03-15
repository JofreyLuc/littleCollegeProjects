#ifndef STATEPRO
#define STATEPRO

#include <string>

// Fausse classe Produit pour éviter les inclusions mutuelles
class Produit;

/** @brief Classe abstraite qui sera héritée par les états concrets (Normal et Enchères)
 ** Dans notre implémentation, un Produit connait un Etat à la fois. Cet Etat connait lui aussi le Produit, 
 ** passé en paramètre du constructeur, pour pouvoir appeler ses fonctions localement. 
 ** L'état étant propre à une instance de Produit, c'est Produit qui se chargera de le détruire.
 **/
class StateProduit {

protected :

  Produit* _context; /** Pointeur sur le Produit qui sera associé à cet état */

  /** @brief Crée un état sur un Produit
   ** @param cont : Un pointeur sur un état
   **/  
  StateProduit(Produit* cont) : _context(cont) {};

public :

  /** @brief Permet de récupérer le nom d'un état
   ** @param : le nom de l'état
   **/
  virtual std::string getNomEtat() const = 0;

}; 

//Redéfinition de l'opérateur << pour les affichages
std::ostream& operator <<(std::ostream&, const StateProduit&);

#endif
