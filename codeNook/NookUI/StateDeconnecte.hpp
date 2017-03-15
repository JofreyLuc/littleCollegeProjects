#ifndef STATEDECON
#define STATEDECON
#include "StateUtilisateur.hpp"
#include "Utilisateur.hpp"

/** @brief Classe concrète de l'état déconnecté des Utilisateurs
 **/

class StateDeconnecte : public StateUtilisateur {
  
public :

  /** @brief Crée une instance d'état déconnecté pour l'Utilisateur cont
   ** @param cont : un pointeur sur un Utilisateur
   **/
  StateDeconnecte(Utilisateur* cont);
  
  virtual void connexion(std::string nomUtil, std::string mdp);

  virtual void creerCompte(std::string* infos);

  virtual std::string getNomEtat() const;

};

#endif
