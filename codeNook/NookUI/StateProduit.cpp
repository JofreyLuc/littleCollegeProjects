#include "StateProduit.hpp"

std::ostream& operator <<(std::ostream &strm, const StateProduit &sd) {
  strm << sd.getNomEtat();
  return strm;
}
