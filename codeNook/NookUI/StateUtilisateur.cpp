#include "StateUtilisateur.hpp"

void StateUtilisateur::connexion(std::string pseudo, std::string mdp){}
void StateUtilisateur::deconnexion(){}
void StateUtilisateur::creerCompte(std::string* infos){}
void StateUtilisateur::acheterProduit(int i,int quantite){}
void StateUtilisateur::vendreProduit(int i,std::string nom,int prix,int quantite,bool enchere){}
void StateUtilisateur::encherir(int id,int enchere){}
std::ostream& operator <<(std::ostream &strm, const StateUtilisateur &sd) {
  strm << sd.getNomEtat();
  return strm;
}
