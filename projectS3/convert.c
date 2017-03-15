/* Jean-Marc Debicki, Jofrey Luc, Thomas Laheurte
   Converts text to usable data */

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pento.h"
#include "display.h"
#include "convert.h"
#include "cst.h"

/*Returns the max between two integers*/
int max(int a, int b){
  return (a >= b ? a : b);
}

/*Tests if the file exists and fills the string if so*/
void txt_to_char(FILE* file, char* str){
  file = fopen("1.txt", "r");
  if (file == NULL){
    printf("ERROR - Cannot open file\n");
    exit(0);
  }
  fill_str(str, BIGSTR_SIZE, file);
  fclose(file);
}

/*Returns the size of the goal*/
int goal_size(char* str, int nb_elem){
  int end = 0;
  int size = 0, i = 0;
  while (!end && i < nb_elem){
    if (str[i] == '\n') {
      if (str[i + 1] == '\n'){
	end = 1;
      }
    } else if (str[i] == '#'){
      size++;
    }
  i++;
  }
  return size;
}

/*Returns how many pentos are in the file*/
int nb_pento(char* str, int nb_elem){
  int nb_pento = 0, nb_char = 0; 
  int i = first_pento(str, BIGSTR_SIZE); 
  for(i; i < nb_elem; i++){
    if (str[i] == '#'){
      nb_char++;
    } else if (str[i] == '\n' && str[i + 1] == '\n' && nb_char % 5 == 0){
      nb_pento++;
    }
  }
  return nb_pento;
}

/*Tells if there are enough pentos*/
int pento_ok(char* str, int nb_elem){
  int size = goal_size(str, nb_elem);
  int nb_pen = nb_pento(str, nb_elem);
  return (size/nb_pen == 5 && size % nb_pen == 0);
}

/*Fills a string with the data*/
void fill_str(char* str, int nb_elem, FILE* file){
  int i = 0;
  char tempstr[100] = "";
  /*int ok = full_diese(tempstr, 100, 0);
  if (!ok){
    printf("ERROR - Mauvais encodage\n");
    exit(0);
    }*/
  while (fgets(tempstr, 100, file) != NULL){
    strcat(str,tempstr);
    i++;
  }
}
  
/*Returns the position of the first pento*/
int first_pento(char* str, int nb_elem){
  int i = 0;
  int res = 0;
  while (i < nb_elem && !res){
    if (str[i] == '\n' && str[i + 1] == '\n'){
      res = 1;
    }
    i++;
  }
  return i;
}

/*Fills the shape part of a pento + returns the current position after doing it*/
int pento_array(char* str, int nb_elem, int arr[][5], int current, int number){
  int pos_x, pos_y, stop = 0;
  for (pos_y = 0; pos_y < 5; pos_y++){
    for (pos_x = 0; pos_x < 5; pos_x++){
      arr[pos_y][pos_x] = 0;
    }
  } 
  pos_x = 0;
  pos_y = 0;
  while (stop < 5){
    if (str[current] == ' '){
      pos_y++;
    } else if (str[current] == '#'){
      arr[pos_x][pos_y] = number;
      pos_y++;
      stop++;
    } else {
      pos_x++;
      pos_y = 0;
    }
    current++;
  }
  return current;
}

/*Fills an array with the pentominos of the file*/
void fill_arr_pento(char* str, int nb_elem, pento_p* arr, int nb_pento){
  int number = 1, posx = 0, posy = 0; 
  int pos_current = first_pento(str, nb_elem);
  int temp_arr[5][5];
  while (pos_current < nb_elem && number <= nb_pento){
    if (str[pos_current] == '\n'){
      pos_current++;
    } else {
      pos_current = pento_array(str, nb_elem, temp_arr, pos_current, number);
      get_init_pos(&posx, &posy, number);
      arr[number - 1] = pento_init(temp_arr, 0, posx, posy, number);
      number++;
    }
  }
}

/*Fills an array with the shape of the goal*/
void get_tab_goal(char* str, int nb_elem, int tab_goal[][GOAL_SIZE], int tab_goal_size){
  int current = 0, row = 0, col = 0, nb_sharp = 0, i, j;
  for (i = 0; i < GOAL_SIZE; i++){
    for (j = 0; j < GOAL_SIZE; j++){
      tab_goal[i][j] = -1;
    }
  }
  int size = goal_size(str, nb_elem);
  while (row < GOAL_SIZE && nb_sharp < size){
    col = 0;
    while (nb_sharp<size){
      if (str[current] == '#'){
	tab_goal[row][col] = GOAL_EMPTY;
	nb_sharp++;
      }
      if (str[current++] == '\n'){
	break;
      }
      col++;
    }
    row++;
  }	
}

/*Tests if the file is filled only with '#', '\n' and ' '*/
int full_diese(char* str, int nb_elem, int current){
  if (current == nb_elem){
      return 1;
  } else {
    if (str[current] == '\n' || str[current] == ' ' || str[current] == '#'){
      return full_diese(str, nb_elem, current + 1);
    } else {
      return 0;
    }
  }
}

/*Returns how many characters are in the big string*/
int nb_total_elem(char* str, int nb_elem){
  int current = 0;  
  while (str[current] != '\0'){
    current++;
  }
  return current;
}

/*Tells how many row are in the goal and the size of the longest*/
void goal_specs(char* str, int nb_elem, int* nb_row, int* longest_row){
  int thisrow = 0, i;
  int pos_max = first_pento(str, nb_elem);
  *nb_row = 0;
  for (i = 0; i < pos_max; i++){
    if(str[i] == '\n'){
      *nb_row += 1;
      *longest_row = max(*longest_row, thisrow);
      thisrow = 0;
    } else {
      thisrow++;
    }
  }
}

/*Tests the file*/
void tests(FILE* file, char* str,int nb_elem){
  txt_to_char(file, str);
  int nb_elems = nb_total_elem(str, nb_elem);
  /*int nbpen = nb_pento(str, nb_elems);
  if (nbpen <= 0){
    printf("ERROR - Incorrect file\n");
    exit(0);
    }*/
  int is_ok;
  is_ok = full_diese(str, nb_elems, 0);
  is_ok = pento_ok(str, nb_elems);
  if (!is_ok){
    printf("ERROR - Incorrect file\n");
    exit(0);
  }
}


