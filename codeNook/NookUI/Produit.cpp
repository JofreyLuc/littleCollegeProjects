#include "Produit.hpp"
#include "StateNormal.hpp"
#include "StateEnchere.hpp"

#include <iostream>

//Constructeur

Produit::Produit(int i, std::string n, int p, int q, bool enchere) : id(i), nom(n), prix(p), quantite(q) {
  if (enchere) etat = new StateEnchere(this);
  else etat = new StateNormal(this);
}

Produit::Produit(const Produit& pr) : id(pr.getId()), nom(pr.getNom()), prix(pr.getPrix()), quantite(pr.getQuantite()) {
  if (pr.getEtat()->getNomEtat().compare("Vente directe") == 0) etat = new StateNormal(this);
  else etat = new StateEnchere(this);
}

Produit::Produit() : id(0), nom("default"), prix(0), quantite(0), etat(new StateNormal(this)) {}

Produit::~Produit(){
  delete etat;
}

//Getters
int Produit::getId() const {
  return id;
}

std::string Produit::getNom() const {
  return nom;
}
int Produit::getPrix() const {
  return prix;
}
int Produit::getQuantite() const {
  return quantite;
}

StateProduit* Produit::getEtat() const{
  return etat;
}

  //Setters

void Produit::setId(int id){
  this->id = id;
}

void Produit::setNom(std::string nom){
  this->nom = nom;
}

void Produit::setPrix(int prix){
  this->prix = prix;
}

void Produit::setQuantite(int quantite){
  this->quantite = quantite;
}

void Produit::setEtat(StateProduit* s){
  etat = s;
}

std::ostream& operator <<(std::ostream &strm, const Produit &p) {
	strm << "- Produit -";
	strm << "\nId : " << p.id; 
	strm << "\nNom : " << p.nom;
	strm << "\nPrix : " << p.prix;
	strm << "\nQuantite : " << p.quantite;

      	strm << "\nEtat : " << *(p.etat);

	return strm;
}
