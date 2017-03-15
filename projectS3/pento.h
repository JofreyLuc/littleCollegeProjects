/* Jean-Marc Debicki, Jofrey Luc, Thomas Laheurte
   Struct pento and functions prototypes */

#ifndef PENTO_H
#define PENTO_H

#include <stdbool.h>
#include "cst.h"

typedef struct pento{
  int shape[5][5];
  int angle;
  int pos_x;
  int pos_y;
  int init_x;
  int init_y;
  int color;
  bool grab;
  bool released_on_goal;
  bool reverse;
}* pento_p;

pento_p pento_init(int shape[5][5], int angle, int pos_x, int pos_y, int color);
void turn_left_90(pento_p pentomino, int mX, int mY);
void reverse_pento(pento_p pentomino, int mX, int mY);
bool is_one_grabbed(pento_p* arr, int nb_pent);
int which_is_grabbed(pento_p* arr, int nb_pent);
void return_to_base(pento_p* arr, int nb_pent, int tab_goal [][GOAL_SIZE]);
int what_shape(pento_p pento, int lin, int col, int direction);
int which_frame_would_it_take(pento_p pento, int lin, int col);

#endif
