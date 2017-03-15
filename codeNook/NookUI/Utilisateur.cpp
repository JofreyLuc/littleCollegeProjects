#include "Utilisateur.hpp"
#include "StateDeconnecte.hpp"

//Constructeur

Utilisateur::Utilisateur() : etat(new StateDeconnecte(this)){}

Utilisateur::Utilisateur(const Utilisateur& au) : bdd(au.getBdd()), nom(au.getNom()), prenom(au.getPrenom()), pseudonyme(au.getPseudo()), mail(au.getMail()), motDePasse(au.getMotDePasse()), adresseNum(au.getAdresseNum()), adresseCP(au.getAdresseCP()), adresseVille(au.getAdresseVille()), numero(au.getNumero()), histoAchat(au.getHistoAchat()), histoVente(au.getHistoVente()), etat(new StateDeconnecte(this)) {}

Utilisateur::~Utilisateur() {
  delete etat;
}

// Getters

std::string Utilisateur::getNom() const {
	return nom;
}

std::string Utilisateur::getPrenom() const {
	return prenom;
}

std::string Utilisateur::getPseudo() const {
	return pseudonyme;
}

std::string Utilisateur::getMail() const {
	return mail;
}

std::string Utilisateur::getMotDePasse() const {
	return motDePasse;
}

std::string Utilisateur::getAdresseNum() const {
	return adresseNum;
}

std::string Utilisateur::getAdresseCP() const {
	return adresseCP;
}

std::string Utilisateur::getAdresseVille() const {
	return adresseVille;
}

std::string Utilisateur::getNumero() const {
	return numero;
}

std::vector<int> Utilisateur::getHistoAchat() const {
  return histoAchat;
}

std::vector<int> Utilisateur::getHistoVente() const {
  return histoVente;
}

StateUtilisateur* Utilisateur::getEtat() {
  return etat;
}

Bdd* Utilisateur::getBdd() const {
  return bdd;
}

Produit* Utilisateur::getProduit(int id){
  return bdd->getProduit(id);
}

Utilisateur* Utilisateur::getUtilisateur(std::string nomUtil,std::string mdp){
  return bdd->getUtilisateur(nomUtil,mdp);
}

// Setters

void Utilisateur::setBdd(Bdd *b) {
	bdd = b;
}

void Utilisateur::setEtat(StateUtilisateur* s){
  etat = s;
}

void Utilisateur::setNom(std::string n) {
	nom = n;
}

void Utilisateur::setPrenom(std::string p) {
	prenom = p;
}

void Utilisateur::setPseudo(std::string p) {
	pseudonyme = p;
}

void Utilisateur::setMail(std::string m) {
	mail = m;
}

void Utilisateur::setMotDePasse(std::string mdp) {
	motDePasse = mdp;
}

void Utilisateur::setAdresseNum(std::string an) {
	adresseNum = an;
}

void Utilisateur::setAdresseCP(std::string acp) {
	adresseCP = acp;
}

void Utilisateur::setAdresseVille(std::string av) {
	adresseVille = av;
}

void Utilisateur::setNumero(std::string n) {
	numero = n;
}

// Méthodes

void Utilisateur::addHistoAchat(int id){
  histoAchat.push_back(id);
}

void Utilisateur::addHistoVente(int id){
  histoVente.push_back(id);
}
 

void Utilisateur::creerCompte(std::string* infos){
  etat->creerCompte(infos);
  bdd->sauvegarderCompte(*this);
}

void Utilisateur::connexion(std::string pseudo, std::string mdp){
  etat->connexion(pseudo, mdp);
}

void Utilisateur::deconnexion(){
  etat->deconnexion();
}

void Utilisateur::acheterProduit(int id,int quantite){
  etat->acheterProduit(id,quantite);
}

void Utilisateur::encherir(int id,int prix){
  etat->encherir(id,prix);
}

void Utilisateur::vendreProduit(int i,std::string nom,int prix,int quantite,bool enchere){
  etat->vendreProduit(i,nom,prix,quantite,enchere);
}

void Utilisateur::vider() {
	nom = "";
	prenom = "";
	pseudonyme = "";
	mail = "";
	motDePasse = "";
	adresseNum = "";
	adresseCP = "";
	adresseVille = "";
	numero = "";
}

std::vector<Produit> Utilisateur::rechercherProduit(std::string search){
  return bdd->rechercherProduit(search);
}

std::ostream& operator <<(std::ostream &strm, const Utilisateur &u) {
	strm << "- Utilisateur -";
	strm << "\nNom : " << u.nom;
	strm << "\nPrenom : " << u.prenom;
	strm << "\nPseudonyme : " << u.pseudonyme;
	strm << "\nMail : " << u.mail;
	strm << "\nMot de passe (lol) : " << u.motDePasse;
	strm << "\nAdresse (numéro et rue) : " << u.adresseNum;
	strm << "\nAdresse (Code postal) : " << u.adresseCP;
	strm << "\nAdresse (Ville) : " << u.adresseVille;
	strm << "\nNumero : " << u.numero;

	strm << "\nEtat : " << *(u.etat);

	return strm;
}
