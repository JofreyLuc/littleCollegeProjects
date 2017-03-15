#ifndef CST
#define CST
#include <SDL/SDL_ttf.h>

#include <SDL/SDL.h>

#define SCREEN_WIDTH 1000
#define SCREEN_HEIGHT 875

#define PART_SIZE 25
#define SHAPE_SIZE 125

#define GOAL_EMPTY 0
#define GOAL_UNSET -1
#define GOAL_SIZE 11
#define GOAL_X 125
#define GOAL_Y 125
#define GOAL_SIZE_PIXEL 250
#define BIGSTR_SIZE 1000
#define ARRPENTO_SIZE 20

#define DECALAGE_HIGHLIGHT 12

SDL_Surface *screen, *HL_sprite, *text;
TTF_Font *font;



int quit;
int victory;
int score;









#endif
