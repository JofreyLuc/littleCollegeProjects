/* Jean-Marc Debicki, Jofrey Luc, Thomas Laheurte
   Pento functions */

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include "move.h"
#include "pento.h"
#include "display.h"
#include "cst.h"

/*Create a new pento*/
pento_p pento_init(int shape[5][5], int angle, int pos_x, int pos_y, int color){
  pento_p new_pento = (pento_p)malloc(sizeof(struct pento));
  int i, j;
  for (i = 0; i < 5; i++){
    for (j = 0; j < 5; j++){
      new_pento->shape[i][j] = shape[i][j];
    }
  }
  new_pento->angle = angle;
  new_pento->pos_x = pos_x;
  new_pento->pos_y = pos_y;
  new_pento->init_x = pos_x;
  new_pento->init_y = pos_y;
  new_pento->color = color;
  new_pento->grab = 0;
  new_pento->released_on_goal = 0;
  return new_pento;
}

/*Rotates a pento*/
void turn_left_90(pento_p pentomino, int mX, int mY){
  int col, lin;
  int size_tab = 4;
  int temp[5][5];
  int X = pentomino->pos_x + SHAPE_SIZE, Y = pentomino->pos_y;

  pentomino->angle += 90;
  pentomino->angle = ((pentomino->angle == 360) ? 0 : pentomino->angle);  
  for (lin = 0; lin < 5; lin++){
    for (col = 0; col < 5; col++){
      temp[lin][col] = pentomino->shape[col][size_tab - lin];
    }
  } 
  for (lin = 0; lin < 5; lin++){
    for (col = 0; col < 5; col++){
      pentomino->shape[lin][col] = temp[lin][col];
    }
  }
  pentomino->pos_x = mX - mY + Y;
  pentomino->pos_y = mY + mX - X;
  score++;
}

/*Reverses a pento*/
void reverse_pento(pento_p pentomino, int mX, int mY){
  int col, lin;
  int temp[5][5];
  int decalage;
  int div;
  if (pentomino->grab){
    pentomino->reverse = !pentomino->reverse;

    for (lin = 0; lin < 5; lin++){
      for (col = 0; col < 2; col++){
	temp[lin][1 - col] = pentomino->shape[lin][col + 3];
	temp[lin][col + 3] = pentomino->shape[lin][1 - col];
      }
      temp[lin][2] = pentomino->shape[lin][2];
    }
    for (lin = 0; lin < 5; lin++){
      for (col = 0; col < 5; col++){
	pentomino->shape[lin][col] = temp[lin][col];
      }
    }
    decalage = mX - pentomino->pos_x;   
    div = decalage / PART_SIZE;
   
    switch(div){
    case 0:
      pentomino->pos_x -= 4 * PART_SIZE;
      break;
    case 1:
      pentomino->pos_x -= 2 * PART_SIZE;
      break;
    case 3:
      pentomino->pos_x += 2 * PART_SIZE;
      break;
    case 4:
      pentomino->pos_x += 4 * PART_SIZE;
      break;
    }
    score++;
  }
}

/* Tells if a pento is grabbed */
bool is_one_grabbed(pento_p* arr, int nb_pent){
  int i = 0;
  bool iog = 0;
  while (i < nb_pent && !iog){
    iog = arr[i]->grab;
    i++;
  }
  return iog;
}

/* Tells which pento is grabbed */
int which_is_grabbed(pento_p* arr, int nb_pent){
  int i = 0;
  int wig = -1;
  while (i < nb_pent && wig == -1){
    if (arr[i]->grab){
      wig = i;
    }
    i++;
  }
  return wig;
}

/*Put the pento back to their initial state*/
void return_to_base(pento_p* arr, int nb_pent, int tab_goal [][GOAL_SIZE]){
  int i;
  for (i = 0; i < nb_pent; i++){
    int X = arr[i]->pos_x / PART_SIZE - 5;
    int Y = arr[i]->pos_y / PART_SIZE - 5;
    
    while (arr[i]->angle != 0){
      turn_left_90(arr[i], 0, 0);
    }
    if (arr[i]->reverse){
      reverse_pento(arr[i], 0, 0);
    }
    arr[i]->pos_x = arr[i]->init_x;
    arr[i]->pos_y = arr[i]->init_y;
  }

  score = 0;
  int lig, col; 
  
  for (lig = 0; lig <= GOAL_SIZE; lig++){
    for (col = 0; col <= GOAL_SIZE; col++){
      if (tab_goal[lig][col] > 0){
	tab_goal[lig][col] = 0;
      }
    }
  }
}

/*Tells what is the shape, for highlight purposes*/
int what_shape(pento_p pento, int lin, int col, int direction){
  int ws = 0;
  if (direction != 0){
    switch(direction)
      {
      case 1: //bottom neighbourgh of the original square
	lin--;
	break;
      case -1: //top neighbourgh
	lin++;
	break;
      case 2: //right neighbourgh
	col++;
	break;
      case -2: //left neighbourgh
	col--;
	break;
      }	
    if (!(lin < 0) || (lin > 4) || (col < 0) || (col > 4)){
      if (pento->shape[lin][col] == pento->color){
        switch(direction)
	  {
	  case 1:
	    ws = 1000;
	    break;
	  case -1:
	    ws = 100;
	    break;
	  case 2:
	    ws = 10;
	    break;
	  case -2:
	    ws = 1;
	    break;
	  }
      }
    }
  } else {
    ws = what_shape(pento, lin, col, 1) + what_shape(pento, lin, col, -1) + what_shape(pento, lin, col, 2) + what_shape(pento, lin, col, -2);
  }
  return ws;
}

/*Highlight purposes*/
int which_frame_would_it_take(pento_p pento, int lin, int col){
  int wfwit = 0;
  switch(what_shape(pento, lin, col, 0))
    {
    case 1111:
      wfwit = 14;
      break;
    case 1110:
      wfwit = 12;
      break;
    case 1101:
      wfwit = 13;
      break;
    case 1100:
      wfwit = 8;
      break;
    case 1011:
      wfwit = 11;
      break;
    case 1010:
      wfwit = 7;
      break;
    case 1001:
      wfwit = 6;
      break;
    case 1000:
      wfwit = 3;
      break;
    case 111:
      wfwit = 10;
      break;
    case 110:
      wfwit = 4;
      break;
    case 101:
      wfwit = 5;
      break;
    case 100:
      wfwit = 1;
      break;
    case 11:
      wfwit = 9;
      break;
    case 10:
      wfwit = 0;
      break;
    case 1:
      wfwit = 2;
      break;
    }
  return wfwit;
}
      
