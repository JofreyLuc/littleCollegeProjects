#ifndef DISPLAY_H
#define DISPLAY_H

#include "pento.h"
#include "cst.h"

void display_pentomino(pento_p pentomino, SDL_Surface *sprite, SDL_Surface* screen);
void display_goal(int tab_goal [][GOAL_SIZE], SDL_Surface *sprite, SDL_Surface* screen, int X, int Y);
SDL_Surface* load_bmp(char * bmp);
void init_sprite_array(SDL_Surface *sprite_array [20]);
void get_init_pos(int* posx, int* posy, int number);
void update_score(void);

#endif
