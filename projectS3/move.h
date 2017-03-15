/* Jean-Marc Debicki, Jofrey Luc, Thomas Laheurte
   Display functions prototypes */

#ifndef MOVE_H
#define MOVE_H

#include <SDL/SDL.h>
#include <stdbool.h>
#include "pento.h"
#include "cst.h"

typedef struct input{
  char key[SDLK_LAST];
  char key_click[SDLK_LAST];
  int mousex,mousey;
  char mousebuttons[8];
  char mouse_click[8];
  int quit;
}* input_p;

input_p input_init(void);
void UpdateEvents(input_p in, pento_p* arr, int goal_tab [][GOAL_SIZE]);
void Press_Enter(input_p in);
bool clickmouse_is_on_pento(input_p in, pento_p pento);
void grabing_pento(input_p in, pento_p pento, int prev_mx, int prev_my, int tab_goal[][GOAL_SIZE], int goal_x, int goal_y, bool iog);
void release_pento_on_goal(int tab_goal [][GOAL_SIZE], int goal_x, int goal_y, pento_p pento, input_p in);
void regrab_pento(int tab_goal[][GOAL_SIZE], pento_p pento);
void manipulating_all_pento(input_p in, pento_p arr[ARRPENTO_SIZE], SDL_Surface *sprite_array [], int prev_mx, int prev_my, int tab_goal[][GOAL_SIZE], int goal_x, int goal_y);
bool pento_is_on_goal(int goal_x, int goal_y, input_p in);
bool place_for_pento(int tab_goal [][GOAL_SIZE], int goal_x, int goal_y, pento_p pento, input_p in);
int which_line_is_under_mouse(pento_p pento, input_p in);
int which_column_is_under_mouse(pento_p pento, input_p in);
void highlight(int tab_goal [][GOAL_SIZE], int goal_x, int goal_y, pento_p pento, input_p in);

#endif
