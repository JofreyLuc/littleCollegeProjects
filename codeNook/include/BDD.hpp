#ifndef BDD
#define BDD

#include <vector>

#include "Utilisateur.hpp"
#include "Produit.hpp"

class Utilisateur;

/** @brief Classe simulant la base de données : Elle stocke des instances d'Utilisateur et de Produit
 **/
class Bdd {

private:
  std::vector<Utilisateur> utilisateurs; /** Vector d'Utilisateur stockés */
  std::vector<Produit> produits; /** Vector de Produit stockés */ 

public:

  /** @brief Permet de sauvegarder un Utilisateur dans le vector
   ** @param u : un Utilisateur
   **/
  void sauvegarderCompte(Utilisateur &u);

  /** @brief Permet de récupérer un Utilisateur stocké dans la bdd à partir de ses infos de connexion
   ** @param Infos de connexion 
   ** @return Un pointeur sur un Utilisateur
   **/
  Utilisateur* getUtilisateur(std::string pseudo, std::string motDePasse);

  /** @brief Ajoute un Produit dans le vector de stockage
   ** @param p : un Produit
   **/
  void sauvegarderProduit(Produit &p);

  /** @brief Permet de supprimer un produit de la base de données
   ** @param id : l'id d'un Produit
   **/
  void supprimerProduit(int id);

  /** @brief Permet de récupérer un Produit dans le vector de stockage
   ** @param id : l'id d'un Produit
   ** @return un pointeur sur un Produit
   **/
  Produit* getProduit(int id);

  /** @brief Permet de rechercher un Produit dans le vector
   ** @param search : le nom du produit à rechercher
   ** @return Un vector de Produit correspondant à la recherche
   **/
  std::vector<Produit> rechercherProduit(std::string search);

  /** @brief Comparaison pour tri : ordre croissant
   ** @param Deux produits
   **/
  static bool comp_prix(Produit p1,Produit p2);
  
  /** @brief Comparaison pour tri : ordre décroissant
   ** @param Deux produits
   **/
  static bool desc_prix(Produit p1,Produit p2);
  
  /** @brief Comparaison pour tri : ordre alphabétique
   ** @param Deux produits
   **/
  static bool comp_alpha(Produit p1,Produit p2);

  /** @brief Tri des produits par ordre croissant
   ** @return Une copie du vector de Produit trié
   **/
  std::vector<Produit> tri_Prix();

  /** @brief Tri des produits par ordre décroissant
   ** @return Une copie du vector de Produit trié
   **/
  std::vector<Produit> tri_desc_Prix();

  /** @brief Tri des produits par ordre alphabétique
   ** @return Une copie du vector de Produit trié
   **/
  std::vector<Produit> tri_alpha();
};

#endif
