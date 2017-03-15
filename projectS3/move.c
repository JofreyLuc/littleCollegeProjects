/* Jean-Marc Debicki, Jofrey Luc, Thomas Laheurte
   Moving pentominos functions */

#include <SDL/SDL.h>
#include <stdbool.h>
#include "move.h"
#include "display.h"
#include "pento.h"
#include "cst.h"

/*Handle the events*/
void UpdateEvents(input_p in, pento_p* arr, int tab_goal [][GOAL_SIZE])
{
  int wig = which_is_grabbed(arr, 12);
  SDL_Event event;
  while(SDL_PollEvent(&event))
    {
      switch (event.type)
	{
	case SDL_KEYDOWN:
	  switch (event.key.keysym.sym){
	  case SDLK_ESCAPE:
	  case SDLK_q:
	    in->quit = 1;
	    break;
	  }
	  in->key[event.key.keysym.sym] = 1;
	  in->key_click[event.key.keysym.sym] = 1;
	  break;
	case SDL_KEYUP:
	  in->key[event.key.keysym.sym] = 0;
	  if ((in->key_click[SDLK_t] == 1) && (wig >= 0)){
	    turn_left_90(arr[wig], in->mousex, in->mousey);
	  }
	  if ((in->key_click[SDLK_SPACE] == 1) && (wig >= 0)){
	    reverse_pento(arr[wig], in->mousex, in->mousey);
	  }
	  in->key_click[event.key.keysym.sym] = 0;
	  break;
	case SDL_MOUSEMOTION:
	  in->mousex = event.motion.x;
	  in->mousey = event.motion.y;
	  break;
	case SDL_MOUSEBUTTONDOWN:
	  in->mousebuttons[event.button.button] = 1;
	  in->mouse_click[event.button.button] = 1;
	  break;
	case SDL_MOUSEBUTTONUP:
	  in->mousebuttons[event.button.button] = 0;
	  if ((in->mouse_click[SDL_BUTTON_LEFT] == 1) && (((in->mousex >= 760) && (in->mousex <= 880)) && ((in->mousey >= 635) && (in->mousey <= 755)))){
	    return_to_base(arr, 12, tab_goal);
	  }
	  in->mouse_click[event.button.button] = 0;
	  break;
	case SDL_QUIT:
	  in->quit = 1;
	  break;
	}
    }
}

/*Press enter (for title screen purposes)*/
void Press_Enter(input_p in)
{
  SDL_Event event;
  SDL_WaitEvent(&event);
  switch(event.type)
    {
    case SDL_KEYDOWN:
      switch (event.key.keysym.sym){
      case SDLK_RETURN:
	in->quit = 1;
      break;
      }
    }
}

/*Initialize an empty input*/
input_p input_init(void){
  input_p new_input = (input_p)malloc(sizeof(struct input));
  new_input->quit = 0;
  new_input->mousex = 0;
  new_input->mousey = 0;
  int i;
  for(i = 0; i < SDLK_LAST; i++){
    new_input->key[i] = 0;
    new_input->key_click[i] = 0;
  }
  for(i = 0; i < 8; i++){
    new_input->mousebuttons[i] = 0;
    new_input->mouse_click[i] = 0;
  }
  return new_input;
}

/*Tells if the mouse is on the pento*/
bool clickmouse_is_on_pento(input_p in, pento_p pento){
  int lin, col;
  bool res = 0;
  for (lin = 0; lin < 5; lin++){
    for (col = 0; col < 5; col++){
      if (pento->shape[lin][col] != 0){
	if ((((pento->pos_x) + (PART_SIZE * (col))) <= (in->mousex)) && (((pento->pos_x) + (PART_SIZE * (col + 1))) >= (in->mousex))){
	  if ((((pento->pos_y) + (PART_SIZE * (lin))) <= (in->mousey)) && (((pento->pos_y) + (PART_SIZE * (lin + 1))) >= (in->mousey))){
	    res = 1;
	    break;
      	  }
	}
      }
    }
  }
  return res;
}

/*Grabs a pento*/
void grabing_pento(input_p in, pento_p pento, int prev_mx, int prev_my, int tab_goal[][GOAL_SIZE], int goal_x, int goal_y, bool iog){
  regrab_pento(tab_goal, pento);
  if ((in->mousebuttons[SDL_BUTTON_LEFT]) && (pento->grab)){
    pento->pos_x += (in->mousex - prev_mx);
    pento->pos_y += (in->mousey - prev_my);
    highlight(tab_goal, goal_x, goal_y, pento, in);
  } else if ((in->mousebuttons[SDL_BUTTON_LEFT]) && (clickmouse_is_on_pento(in, pento)) && (!iog)){
    pento->grab = 1;
  } else {
    release_pento_on_goal(tab_goal, goal_x, goal_y, pento, in);
  }
}

/*Put the pento on the goal*/
void release_pento_on_goal(int tab_goal [][GOAL_SIZE], int goal_x, int goal_y, pento_p pento, input_p in){
  if (pento->grab){
    pento->grab = 0;
    if (place_for_pento(tab_goal, goal_x, goal_y, pento, in)){
      int lin, col;
      int pento_part_x = pento->pos_x / PART_SIZE - 5;
      int pento_part_y = pento->pos_y / PART_SIZE - 5;
      int mouse_part_x = (in->mousex / PART_SIZE - 5) - which_column_is_under_mouse(pento, in);
      int mouse_part_y = (in->mousey / PART_SIZE - 5) - which_line_is_under_mouse(pento, in);
      int decalage_x = (mouse_part_x - pento_part_x);
      int decalage_y = (mouse_part_y - pento_part_y);
    
      pento->pos_x = goal_x + ((mouse_part_x) * PART_SIZE);
      pento->pos_y = goal_y + ((mouse_part_y) * PART_SIZE);
      pento->released_on_goal = 1;

      victory++;
      score++;

      for(lin = 0; lin <= 4; lin++){
	for(col = 0; col <= 4; col++){
	  if (pento->shape[lin][col] == pento->color){
	    if (tab_goal[pento_part_y + lin + decalage_y][pento_part_x + col + decalage_x] == 0){
	      tab_goal[pento_part_y + lin + decalage_y][pento_part_x + col + decalage_x] = pento->color;
	    }
	  }
	}
      }
    } 
  } 
}

void regrab_pento(int tab_goal[][GOAL_SIZE], pento_p pento){
  if ((!pento->released_on_goal) || (!pento->grab)) return;
  pento->released_on_goal = 0;
  victory--;
  int lin, col;
  for (lin = 0; lin < GOAL_SIZE; lin++){
    for (col = 0; col < GOAL_SIZE; col++){
      if (tab_goal[lin][col] == pento->color){
	tab_goal[lin][col] = 0;
      }
    }
  }
}

/*Handles the pento manipulation*/
void manipulating_all_pento(input_p in, pento_p arr[ARRPENTO_SIZE], SDL_Surface *sprite_array [], int prev_mx, int prev_my, int tab_goal[][GOAL_SIZE], int goal_x, int goal_y){
  int no_pento;
  int wig = which_is_grabbed(arr, 12);
  bool iog = is_one_grabbed(arr, 12);
  for (no_pento = 0; no_pento < 12; no_pento++){
    grabing_pento(in, arr[no_pento], prev_mx, prev_my, tab_goal, goal_x, goal_y, iog);
    if (wig != no_pento){
      display_pentomino(arr[no_pento], sprite_array[no_pento], screen);
    }
    iog = is_one_grabbed(arr, 12);
  }
  display_pentomino(arr[wig], sprite_array[wig], screen);
}

/*Tells if the pento is on the goal*/
bool pento_is_on_goal(int goal_x, int goal_y, input_p in){
  if ((in->mousex >= goal_x) && (in->mousex <= (goal_x + GOAL_SIZE_PIXEL))){
    if ((in->mousey >= goal_y) && (in->mousey <= (goal_y + GOAL_SIZE_PIXEL))){
      return 1;
    }
  }
  return 0;
}

/*Tells if there is enough space on the goal fro the grabbed pento*/
bool place_for_pento(int tab_goal [][GOAL_SIZE], int goal_x, int goal_y, pento_p pento, input_p in){
  int count = 0;
  if (pento_is_on_goal(goal_x, goal_y, in)){
    int lin, col;
    int pento_part_x = pento->pos_x / PART_SIZE - 5;
    int pento_part_y = pento->pos_y / PART_SIZE - 5;
    int mouse_part_x = (in->mousex / PART_SIZE - 5) - which_column_is_under_mouse(pento, in);
    int mouse_part_y = (in->mousey / PART_SIZE - 5) - which_line_is_under_mouse(pento, in);    
    int decalage_x = (mouse_part_x - pento_part_x);
    int decalage_y = (mouse_part_y - pento_part_y);

    for(lin = 0; lin <= 4; lin++){
      for(col = 0; col <= 4; col++){
	if ((pento->shape[lin][col] == pento->color)){
	  if (!(((pento_part_x + col + decalage_x < 0) || (pento_part_x + col + decalage_x >= 10)) || ((pento_part_y + lin + decalage_y< 0) || (pento_part_y + lin + decalage_y >= 10)))){
	    if (tab_goal[pento_part_y + lin + decalage_y][pento_part_x + col + decalage_x] == 0){
	      count++;
	    }
	  }
	}
      }
    }
  }
  return (count == 5);
}

/*Which line of the goal under mouse*/
int which_line_is_under_mouse(pento_p pento, input_p in){
  int line;
  for (line = 0; line < 5; line++){
    if ((in->mousey >= pento->pos_y + (line * PART_SIZE)) && (in->mousey < pento->pos_y + ((line + 1) * PART_SIZE))){
      return line;
    }
  }
}

/*Which column of the goal under mouse*/
int which_column_is_under_mouse(pento_p pento, input_p in){
  int column;
  for (column = 0; column < 5; column++){
    if ((in->mousex >= pento->pos_x + (column * PART_SIZE)) && (in->mousex < pento->pos_x + ((column + 1) * PART_SIZE))){
      return column;
    }
  }
}

/*Highlight the place where the pento will be put on the goal*/
void highlight(int tab_goal [][GOAL_SIZE], int goal_x, int goal_y, pento_p pento, input_p in){
  if (place_for_pento(tab_goal, goal_x, goal_y, pento, in)){
    int lin, col;
    int pento_part_x = pento->pos_x / PART_SIZE - 5;
    int pento_part_y = pento->pos_y / PART_SIZE - 5;
    int mouse_part_x = (in->mousex / PART_SIZE - 5) - which_column_is_under_mouse(pento, in);
    int mouse_part_y = (in->mousey / PART_SIZE - 5) - which_line_is_under_mouse(pento, in);   
    int decalage_x = (mouse_part_x - pento_part_x);
    int decalage_y = (mouse_part_y - pento_part_y);
    SDL_Rect position;
    SDL_Rect decoup; 
    decoup.x = 0;
    decoup.y = 0;
    decoup.w = 49;
    decoup.h = 49;
    
    for(lin = 0; lin <= 4; lin++){
      for(col = 0; col <= 4; col++){
	if (pento->shape[lin][col] == pento->color){
	  if (tab_goal[pento_part_y + lin + decalage_y][pento_part_x + col + decalage_x] == 0){
	    position.x = goal_x + (PART_SIZE * (col + pento_part_x + decalage_x)) - DECALAGE_HIGHLIGHT;
	    position.y = goal_y + (PART_SIZE * (lin + pento_part_y + decalage_y)) - DECALAGE_HIGHLIGHT;
	    decoup.x = (decoup.w * which_frame_would_it_take(pento, lin, col));
	    SDL_BlitSurface(HL_sprite, &decoup, screen, &position);
	  }
	}
      }
    }
  }
}
