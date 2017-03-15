#ifndef PRODUIT
#define PRODUIT

#include <string>
#include <iostream>
#include "StateProduit.hpp"

/** @brief Classe gérant les champs des produits et les appels aux fonctions de leurs états
 **/

class Produit {
private :

  int id; /** Identifiant du produit */
  std::string nom; /** Nom du produit */
  int prix; /** Prix unitaire du produit */
  int quantite; /** Quantité disponible de ce produit */

  StateProduit* etat; /** Etat actuel du produit (En ente directe / Vente aux enchères) */
  
public :

  //Constructeurs
  
  /** @brief Crée un produit par défaut avec ses champs initialisés
   **/
  Produit();
  
  /** @brief Constructeur de copie (Crée un nouvel état pour éviter les fuites de mémoire)
   **/
  Produit(const Produit& pr);

  /** @brief Crée un produit avec ses infos
   ** @param Les infos du produit : enchere (true) => Vente aux enchères
   **/
  Produit(int i, std::string n, int p, int q, bool enchere);

  //Destructeur

  /** @brief Destructeur : Libère le pointeur sur son état
   **/
  ~Produit();

  //Getters

  int getId() const;
  std::string getNom() const;
  int getPrix() const;
  int getQuantite() const;
  StateProduit* getEtat() const;

  //Setters

  void setId(int id);
  void setNom(std::string nom);
  void setPrix(int prix);
  void setQuantite(int quantite);
  void setEtat(StateProduit* s);

  /** @brief Redéfinit l'opérateur << pour l'affichage
   **/
  friend std::ostream& operator <<(std::ostream&, const Produit&);
};


#endif
