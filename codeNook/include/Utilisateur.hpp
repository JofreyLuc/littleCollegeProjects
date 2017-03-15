#ifndef UTILISATEUR
#define UTILISATEUR

//Documentation Main page Doxygen, ici faute de mieux :)

/** \mainpage Documentation Nook
 ** Documentation du projet CPOOA S5 - 
 ** Jofrey Luc, Brice Peters, Quentin Sonrel
 **/

#include <string>
#include <iostream>
#include <vector>

#include "BDD.hpp"
#include "Produit.hpp"
#include "StateUtilisateur.hpp"

// Classe Bdd définie pour éviter les inclusions mutuelles
class Bdd;

/** @brief Classe Utilisateur, gérant toutes les données et les appels aux fonctions de son état.
 ** On stockera des instances de cette classe dans la bdd.
 **/

class Utilisateur {

private:
  Bdd *bdd; /** Base de données attachée à l'utilisateur */
  std::string nom; /** Nom de l'utilisateur */
  std::string prenom; /** Prénom de l'utilisateur */
  std::string pseudonyme; /** Pseudonyme de l'utilisateur */
  std::string mail; /** Adresse mail de l'utilisateur */
  std::string motDePasse; /** Mot de passe de l'utilisateur */
  std::string adresseNum; /** Numéro (adresse) de l'utilisateur */
  std::string adresseCP; /** Code postal de l'utilisateur */
  std::string adresseVille; /** Ville de l'utilisateur */
  std::string numero; /** Numéro (téléphone) de l'utilisateur */

  std::vector<int> histoAchat; /** Liste d'entiers archivant les achats de l'utilisateur */
  std::vector<int> histoVente; /** Liste d'entiers archivant les ventes de l'utilisateur */

  StateUtilisateur* etat; /** Pointeur sur un état (Connecté ou pas) de l'utilisateur */

public:

  /** Crée un utilisateur vide et déconnecté */
  Utilisateur(); 
  /** Constructeur de copie (défaut déconnecté) */
  Utilisateur(const Utilisateur& au); 
  /** Détruit un Utilisateur en libérant le pointeur sur son état */
  ~Utilisateur(); 

  // Getters

  std::string getNom() const;
  std::string getPrenom() const;
  std::string getPseudo() const;
  std::string getMail() const;
  std::string getMotDePasse() const; 
  std::string getAdresseNum() const; 
  std::string getAdresseCP() const; 
  std::string getAdresseVille() const; 
  std::string getNumero() const;

  StateUtilisateur* getEtat(); 
  std::vector<int> getHistoAchat() const; 
  std::vector<int> getHistoVente() const; 
  Bdd* getBdd() const; 

  /**
   ** @brief Utilisée à des fin de réduction de couplage
   ** @return Un produit de la bdd
   **/
  Produit* getProduit(int id); 

  Utilisateur * getUtilisateur(std::string nomUtil,std::string mdp);


  
  // Setters

  void setBdd(Bdd *b);
  void setEtat(StateUtilisateur* s);
  void setNom(std::string n);
  void setPrenom(std::string p);
  void setPseudo(std::string p);
  void setMail(std::string m);
  void setMotDePasse(std::string mdp);
  void setAdresseNum(std::string an);
  void setAdresseCP(std::string acp);
  void setAdresseVille(std::string av);
  void setNumero(std::string n);

  // Methodes

  /** @brief Ajoute un produit dans l'historique d'achat de l'utilisateur
   ** @param id : l'id d'un produit à ajouter
   **/
  void addHistoAchat(int id);
  
  /** @brief Ajoute un produit dans l'historique de vente de l'utilisateur
   ** @param id : l'id d'un produit à ajouter
   **/
  void addHistoVente(int id);

  /** @brief Crée un compte et l'ajoute à la bdd
   ** @param infos : Tableau de strings contenant les infos de compte de l'utilisateur
   **/
  void creerCompte(std::string* infos);

  /** @brief Vide les champs de l'utilisateur (Peut être utilisé pour décrire un utilisateur déconnecté)
   **/
  void vider();

  /** @brief Définit l'utilisateur comme connecté
   ** @param Infos de connexions de l'utilisateur
   **/
  void connexion(std::string pseudo, std::string mdp);
  
  /** @brief Définit l'utilisateur comme déconnecté
   **/
  void deconnexion();

  /** @brief Achète un produit (dans les faits, ajoute le produit dans l'historique d'achats de l'utilisateur et décremente la quantité disponible de ce produit)
   ** @param id : id du produit
   ** @param quantite : nombre de Produit qu'on achète
   **/
  void acheterProduit(int id,int quantite);

  /** @brief Enchérit sur un produit (dans les faits, met à jour le prix du produit)
   ** @param id : id du produit
   ** @param prix : nouveau prix proposé (refusé si trop bas)   
   **/
  void encherir(int id, int prix); 

  /** @brief Ajoute un produit à la vente (Dans la bdd)
   ** @param Les infos du produit à ajouter
   **/
  void vendreProduit(int i, std::string nom, int prix, int quantite, bool enchere);

  /** @brief Permet de rechercher un produit dans la bdd
   ** @param search : terme à rechercher
   ** @return Un vector de Produit
   **/
  std::vector<Produit> rechercherProduit(std::string search);
  
  /** @brief Redéfinit l'opérateur << pour pouvoir afficher simplement les utilisateurs
   **/
  friend std::ostream& operator <<(std::ostream&, const Utilisateur&);
};


#endif
