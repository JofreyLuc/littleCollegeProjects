#ifndef STATECON
#define STATECON
#include "StateUtilisateur.hpp"
#include "Utilisateur.hpp"

/** @brief Classe concrète de l'état connecté des Utilisateurs
 **/

class StateConnecte : public StateUtilisateur {
  
public :

  /** @brief Crée une instance d'état connecté pour l'Utilisateur cont
   ** @param cont : un pointeur sur un Utilisateur
   **/
  StateConnecte(Utilisateur* cont) : StateUtilisateur(cont) {};

  virtual void deconnexion();

  virtual std::string getNomEtat() const;

  virtual void vendreProduit(int i,std::string nom,int prix,int quantite,bool enchere);

  virtual void acheterProduit(int id,int quantite);
  virtual void encherir(int id,int prix);

};

#endif
