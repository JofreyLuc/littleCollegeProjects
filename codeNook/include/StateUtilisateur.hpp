#ifndef STATEUTIL
#define STATEUTIL

#include <string>
#include "Produit.hpp"

class Utilisateur;

/** @brief Classe abstraite qui sera héritée par les états concrets (Connecté et Déconnecté)
 ** Dans notre implémentation, un Utilisateur connait un Etat à la fois. Cet Etat connait lui aussi l'Utilisateur, 
 ** passé en paramètre du constructeur, pour pouvoir appeler ses fonctions localement. 
 ** Quand l'état actuel va appeler une fonction de changement d'état (connexion ou déconnexion), il va set un nouvel
 ** état à l'Utilisateur avant de se supprimer.
 **/
class StateUtilisateur {

protected :
  
  Utilisateur* _context; /** L'Utilisateur lié à cet état */

  /** @param cont : Un pointeur sur un Utilisateur 
   **/
  StateUtilisateur(Utilisateur* cont) : _context(cont) {};

public :

  /** @brief Permet de connecter un Utilisateur déconnecté
   ** @param nomUtil : nom d'utilisateur, pseudo ou mail ?
   ** @param mdp : mot de passe, en clair
   **/  
  virtual void connexion(std::string pseudo, std::string mdp);
  
  /** @brief Permet de déconnecter un Utilisateur connecté
   **/
  virtual void deconnexion();

  /** @brief Permet de set les infos de l'Utilisateur
   ** @param infos : un tableau de string contenant les infos
   ** du compte (nom, prénom, etc.)
   **/
  virtual void creerCompte(std::string* infos);

  /** @brief Récupère le nom de l'état actuel de l'Utilisateur
   ** @return Un string contenant l'état
   **/
  virtual std::string getNomEtat() const = 0;

  /** @brief Permet à un Utilisateur connecté d'acheter un Produit id
   **/
  virtual void acheterProduit(int id, int quantite) ;

  /** @brief Permet à un Utilisateur connecté d'enchérir sur un Produit id
   **/
  virtual void encherir(int id, int prix);
  
  /** @brief Permet à un Utilisateur connecté de vendre un Produit
   ** @param Les infos du Produit
   **/
  virtual void vendreProduit(int i, std::string nom, int prix, int quantite, bool enchere);
};  

//Redéfiniton de l'opérateur << pour l'affichage
std::ostream& operator <<(std::ostream&, const StateUtilisateur&);

#endif
