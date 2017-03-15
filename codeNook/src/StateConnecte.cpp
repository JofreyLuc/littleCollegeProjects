#include "StateConnecte.hpp"
#include "StateDeconnecte.hpp"
#include <string>

void StateConnecte::deconnexion(){
  _context->setEtat(new StateDeconnecte(_context));
  delete this;
};

std::string StateConnecte::getNomEtat() const{
  return "Connecté";
}

void StateConnecte::acheterProduit(int id,int quantite){
  Produit *p;
  p = _context->getProduit(id);
  if( (p->getQuantite() >= quantite) && (p->getEtat()->getNomEtat().compare("Vente directe") == 0)){
     p->setQuantite(p->getQuantite() - quantite);
    _context->addHistoAchat(p->getId());
    }
   //_context->getBdd()->supprimerProduit(id);
}

void StateConnecte::encherir(int id,int prix){
  Produit* p;
  p = _context->getProduit(id);
  if((p->getEtat()->getNomEtat().compare("Vente aux enchères") == 0) &&( p->getPrix()<prix)){
    p->setPrix(prix);
    _context->addHistoAchat(p->getId());
  }
  //_context->getBdd()->supprimerProduit(id);
}

void StateConnecte::vendreProduit(int i,std::string nom,int prix,int quantite,bool enchere){
  Produit p = Produit(i,nom,prix,quantite,enchere);
  _context->getBdd()->sauvegarderProduit(p);
  _context->addHistoVente(p.getId());
}
