#include "BDD.hpp"
#include "Utilisateur.hpp"

#include <iostream>

int main(void) {

  //Création BDD et Utilisateur
  Bdd bdd;
  Utilisateur u;
	
  u.setBdd(&bdd);
  u.setNom("Abitbol");
  u.setPrenom("George");
  u.setPseudo("HommeLePlusClasseDuMonde");
  u.setMail("george.abitbol@laclasse.com");
  u.setMotDePasse("mondedemerde");
  u.setAdresseNum("foo");
  u.setAdresseCP("bar");
  u.setAdresseVille("Pom Pom Galli");
  u.setNumero("...");

  // Connexion-Déconnexion de u, via Utilisateur
  u.connexion("HommeLePlusClasseDuMonde", "mondedemerde");

  std::cout << u << std::endl;

  u.deconnexion();

  std::cout << u << std::endl;

  // Sauvegarde et connexion d'un autre utilisateur avec les mêmes identifiants
  u.setBdd(&bdd);
  Utilisateur u2;
  u2.setBdd(&bdd);
  std::cout << u2 << std::endl;
  bdd.sauvegarderCompte(u);

  u2.connexion("HommeLePlusClasseDuMonde", "mondedemerde");

  // Création d'un utilisateur depuis la bdd
  Utilisateur nu = Utilisateur(*bdd.getUtilisateur("HommeLePlusClasseDuMonde", "mondedemerde"));
  std::cout << nu << std::endl;

  // Création et sauvegarde d'un produit
  Produit p1(1, "Jeej", 20, 42, true);
  bdd.sauvegarderProduit(p1);
  std::cout << *bdd.getProduit(1) << std::endl;

  // Achat du produit
  u2.encherir(1,12000000);
  int achat = u2.getHistoAchat()[0];
  std::cout << *bdd.getProduit(achat) << std::endl;

  return 0;
}
