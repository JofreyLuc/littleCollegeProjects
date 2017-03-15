#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <SDL/SDL.h>
#include "display.h"
#include "pento.h"
#include <string.h>

/*Displays a pentomino */
void display_pentomino(pento_p pentomino, SDL_Surface *sprite, SDL_Surface* screen){
  int X = pentomino->pos_x, Y =pentomino->pos_y;
  int lin, col;
  SDL_Rect position;
  for (lin = 0; lin < 5; lin++){
    for (col = 0; col < 5; col++){
      if (pentomino->shape[lin][col] != 0){
	position.x = X + (PART_SIZE * (col));
	position.y = Y + (PART_SIZE * (lin));
	SDL_BlitSurface(sprite, NULL, screen, &position);
      }
    }
  }
}

/*Displays the goal*/
void display_goal(int tab_goal [][GOAL_SIZE], SDL_Surface *sprite, SDL_Surface* screen, int X, int Y){
  int lin, col;
  SDL_Rect position;
  for (lin = 0; lin < GOAL_SIZE; lin++){
    for (col = 0; col < GOAL_SIZE; col++){
      if (tab_goal[lin][col] != -1){
	position.x = X + (PART_SIZE * (col));
	position.y = Y + (PART_SIZE * (lin));
	SDL_BlitSurface(sprite, NULL, screen, &position);
      }
    }
  }
}

/*Fills an array of sprites with all the possibles pento parts*/
void init_sprite_array(SDL_Surface *sprite_array []){
  sprite_array[0] = SDL_LoadBMP("resources/part_00.bmp");
  sprite_array[1] = SDL_LoadBMP("resources/part_01.bmp");
  sprite_array[2] = SDL_LoadBMP("resources/part_02.bmp");
  sprite_array[3] = SDL_LoadBMP("resources/part_03.bmp");
  sprite_array[4] = SDL_LoadBMP("resources/part_04.bmp");
  sprite_array[5] = SDL_LoadBMP("resources/part_05.bmp");
  sprite_array[6] = SDL_LoadBMP("resources/part_06.bmp");
  sprite_array[7] = SDL_LoadBMP("resources/part_07.bmp");
  sprite_array[8] = SDL_LoadBMP("resources/part_08.bmp");
  sprite_array[9] = SDL_LoadBMP("resources/part_09.bmp");
  sprite_array[10] = SDL_LoadBMP("resources/part_10.bmp");
  sprite_array[11] = SDL_LoadBMP("resources/part_11.bmp");
  sprite_array[12] = SDL_LoadBMP("resources/part_12.bmp");
  sprite_array[13] = SDL_LoadBMP("resources/part_13.bmp");
  sprite_array[14] = SDL_LoadBMP("resources/part_14.bmp");
  sprite_array[15] = SDL_LoadBMP("resources/part_15.bmp");
  sprite_array[16] = SDL_LoadBMP("resources/part_16.bmp");
  sprite_array[17] = SDL_LoadBMP("resources/part_17.bmp");
  sprite_array[18] = SDL_LoadBMP("resources/part_18.bmp");
  sprite_array[19] = SDL_LoadBMP("resources/part_19.bmp");
}

/*Gives the initial position of the pentos*/
void get_init_pos(int* posx, int* posy, int number){
  switch(number%3){
  case 1:
    *posx = 500;
    break;
  case 2:
    *posx = 625;
    break;
  case 0:
    *posx = 750;
    break;
  }
  *posy = 125 + 125 * ((number - 1) / 3);
}

/*Handles the score*/
void update_score(void){
  SDL_Color black = {255, 255, 255, 0};
  SDL_Rect position;
  char scoretxt[50] = "Votre score : ";
  char str_score[5];
  sprintf(str_score, "%03d", score);
  strcat(scoretxt, str_score);
  text = TTF_RenderText_Blended(font, scoretxt, black);
  position.x = 10;
  position.y = 10;
  SDL_BlitSurface(text, NULL, screen, &position);
  SDL_Flip(screen);
}
