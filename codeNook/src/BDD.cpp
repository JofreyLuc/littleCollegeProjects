#include "BDD.hpp"
#include "Utilisateur.hpp"
#include "StateDeconnecte.hpp"
#include <string.h>
#include <algorithm> 

void Bdd::sauvegarderCompte(Utilisateur &u) {
  utilisateurs.push_back(u);
}

Utilisateur* Bdd::getUtilisateur(std::string pseudo, std::string motDePasse) {
  unsigned int i;
  Utilisateur *u = NULL;

  for(i = 0; i < utilisateurs.size(); i++) {
    u = &utilisateurs[i];
    if((u->getPseudo().compare(pseudo) == 0) && (u->getMotDePasse().compare(motDePasse) == 0)) {
      return u;
    }
  }

  // u->vider();
  u = NULL;
  return u;
}

void Bdd::sauvegarderProduit(Produit &p){
  produits.push_back(p);
}

void Bdd::supprimerProduit(int id){
  unsigned int i;
  Produit* p = NULL;
  for(i = 0; i < produits.size(); i ++){
    p = &produits[i];
    if(id == p->getId()){
      produits.erase(produits.begin()+i);
    }
  }
}

Produit* Bdd::getProduit(int id){
  unsigned int i;
  Produit* p = NULL;
  for (i = 0; i < produits.size(); i ++){
    p = &produits[i];
    if (id == produits[i].getId()){
      return p ;
    }
  }
  if (i == produits.size()){
    return NULL;
  }
  return p;
}

std::vector<Produit> Bdd::rechercherProduit(std::string search){
  std::vector<Produit>  res;
  unsigned int i;
  Produit p;
  for(i = 0; i < produits.size(); i++){
    p = produits[i];
    if(p.getNom().find(search) != std::string::npos){
      res.push_back(p);
    }
  }
  return res;
}



bool Bdd::comp_prix(Produit p1,Produit p2){
  return p1.getPrix() > p2.getPrix();
}

bool Bdd::desc_prix(Produit p1,Produit p2){
  return p1.getPrix() < p2.getPrix();
}

bool Bdd::comp_alpha(Produit p1,Produit p2){
  return p1.getNom().compare(p2.getNom());
}

std::vector<Produit> Bdd::tri_Prix(){
  std::vector<Produit> copie = produits;
  std::sort(copie.begin(),copie.end(),comp_prix);
  return copie;
}

std::vector<Produit> Bdd::tri_desc_Prix(){
  std::vector<Produit> copie = produits;
  std::sort(copie.begin(), copie.end(), desc_prix);
  return copie;
}

std::vector<Produit> Bdd::tri_alpha(){
  std::vector<Produit> copie = produits;
  std::sort(copie.begin(), copie.end(), comp_alpha);
  return copie;
}
