#include <SDL/SDL.h>
#include <SDL/SDL_mixer.h>
#include <math.h>
#include <time.h>
#include <stdbool.h>
#include "pento.h"
#include "convert.h"
#include "move.h"
#include "display.h"
#include "cst.h"
#include <SDL/SDL_ttf.h>

int quit;
SDL_Surface *part_of_piece, *to_fill;
SDL_Surface *sprite_part_array [20];

int main(int argc, char* argv[])
{ 
  FILE* file = NULL;
  char str[BIGSTR_SIZE] = "";
  tests(file, str, BIGSTR_SIZE);
  int nb_elem = nb_total_elem(str, BIGSTR_SIZE);
  int nbpen = nb_pento(str, nb_elem);
  pento_p arr[ARRPENTO_SIZE];
  fill_arr_pento(str, BIGSTR_SIZE, arr, nbpen);
  int tab_goal[GOAL_SIZE][GOAL_SIZE];
  get_tab_goal(str, BIGSTR_SIZE, tab_goal, GOAL_SIZE);
  SDL_Surface * background;
  SDL_Surface * title;
  input_p input = input_init();

  /* initialize SDL */
  SDL_Init(SDL_INIT_VIDEO);

  /* initialize sprite array */
  init_sprite_array(sprite_part_array);
  
  /* set the title bar */
  SDL_WM_SetCaption("SUPER EGGSECUTABLE EXTRA Deluxe All-Stars Game Of The Year Edition .:*'Skidrow_Team'*:.", "SDL Animation");
  
  /* create window */
  screen = SDL_SetVideoMode(SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0);
  
  /* set keyboard repeat */
  SDL_EnableKeyRepeat(70, 70);

  /* set the title screen */
  title = SDL_LoadBMP("resources/Press_Enter.bmp");

  /* Title Screen */
  while (!input->quit){
    SDL_BlitSurface(title, NULL, screen, NULL);

    SDL_UpdateRect(screen, 0, 0, 0, 0);

    Press_Enter(input);
  }

  /* set the background and load a .bmp */
  background = SDL_LoadBMP("resources/bg_04.bmp");

  /* load the to_fill.bmp */
  to_fill = SDL_LoadBMP("resources/to_fill.bmp");

  /* load the highlight HL_sprite.bmp with transparency */
  HL_sprite = SDL_LoadBMP("resources/HL_sprite.bmp");
  int colorkey = SDL_MapRGB(screen->format, 0, 255, 0);
  SDL_SetColorKey(HL_sprite, SDL_SRCCOLORKEY, colorkey);

  /* getting mouse specification at the beginning of the program */
  UpdateEvents(input, arr, tab_goal);

  /* music */
  if (!Mix_OpenAudio(MIX_DEFAULT_FREQUENCY, MIX_DEFAULT_FORMAT, MIX_DEFAULT_CHANNELS, 1024) == 0){
    printf("ERREUR - Music cannot be opened\n");
  }
  Mix_Music * Music = NULL;
  Music = Mix_LoadMUS("resources/eggsecutable_OST.mp3");
  Mix_PlayMusic(Music, -1);

  input->quit = 0;
  victory = 0;
  score = 0;
  TTF_Init();
  text = NULL;
  font = NULL;
  font = TTF_OpenFont("resources/PIXEAB__.TTF", 20);
  update_score();

  while ((input->quit != 1) && (victory != 12)){
    /* looking for events */
    int x_mouse = input->mousex, y_mouse = input->mousey;
    int i;
    
    UpdateEvents(input, arr, tab_goal);
    
    /* draw the background */
    SDL_BlitSurface(background, NULL, screen, NULL);
    
    /* draw goal */
    display_goal(tab_goal, to_fill, screen, 125, 125);

    manipulating_all_pento(input, arr, sprite_part_array, x_mouse, y_mouse, tab_goal, 125, 125);

    update_score();

    /* update the screen */
    SDL_UpdateRect(screen, 0, 0, 0, 0);
  }  
  
  /* clean up music */
  Mix_FreeMusic(Music);

  /* clean up */

  TTF_CloseFont(font);
  TTF_Quit();
  int i;
  for (i = 0; i < nbpen; i++)
    {
      free(arr[i]);
    }
  free(input);
  SDL_FreeSurface(text);
  SDL_FreeSurface(screen);
  SDL_FreeSurface(part_of_piece);
  SDL_FreeSurface(title);
  SDL_FreeSurface(to_fill);
  SDL_FreeSurface(background);
  SDL_Quit();
  return 0;
}
