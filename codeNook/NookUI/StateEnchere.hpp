#ifndef STATEENCH
#define STATEENCH

#include "StateProduit.hpp"
#include "Produit.hpp"
#include <string>

/** @brief Classe concrète de l'état d'un Produit mis aux enchères 
 **/
class StateEnchere : public StateProduit {
  
public :
  
  StateEnchere(Produit* cont) : StateProduit(cont) {};

  virtual std::string getNomEtat() const;

};

#endif

