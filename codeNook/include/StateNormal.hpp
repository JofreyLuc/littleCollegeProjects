#ifndef STATENOR
#define STATENOR

#include "StateProduit.hpp"
#include "Produit.hpp"
#include <string>

/** @brief Classe concrète de l'état d'un Produit mis en vente directe
 **/
class StateNormal : public StateProduit {
  
public :
  
  StateNormal(Produit* cont) : StateProduit(cont) {};

  virtual std::string getNomEtat() const;

};

#endif

