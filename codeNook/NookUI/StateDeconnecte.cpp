#include "StateDeconnecte.hpp"
#include "StateConnecte.hpp"
#include <string>

StateDeconnecte::StateDeconnecte(Utilisateur* cont) : StateUtilisateur(cont) {};

void StateDeconnecte::creerCompte(std::string* infos){
	_context->setNom(infos[0]);
	_context->setPrenom(infos[1]);
	_context->setPseudo(infos[2]);
	_context->setMail(infos[3]);
	_context->setMotDePasse(infos[4]);
	_context->setAdresseNum(infos[5]);
	_context->setAdresseCP(infos[6]);
	_context->setAdresseVille(infos[7]);
	_context->setNumero(infos[8]);
}

void StateDeconnecte::connexion(std::string nomUtil, std::string mdp) {
  Utilisateur * util = NULL;
if((_context->getUtilisateur(nomUtil,mdp)!=NULL))
    {
      util = _context->getUtilisateur(nomUtil,mdp);
	_context->setEtat(new StateConnecte(_context));
      	_context->setNom(util->getNom());
	_context->setPrenom(util->getPrenom());
	_context->setPseudo(util->getPseudo());
	_context->setMail(util->getMail());
	_context->setMotDePasse(util->getMotDePasse());
	_context->setAdresseNum(util->getAdresseNum());
	_context->setAdresseCP(util->getAdresseCP());
	_context->setAdresseVille(util->getAdresseVille());
	_context->setNumero(util->getNumero());
      delete this;
  }
};

std::string StateDeconnecte::getNomEtat() const{
  return "Déconnecté";
}
