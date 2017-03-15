/* Jean-Marc Debicki, Jofrey Luc et Thomas Laheurte
   Definition of convert functions */

#ifndef CONVERT_H
#define CONVERT_H

#include "pento.h"
#include <stdio.h>

int goal_size(char* str, int nb_elem);
void fill_str(char* str, int nb_elem, FILE* file);
int pento_array(char* str, int nb_elem, int arr[][5], int pos, int number);
int first_pento(char* str, int nb_elem);
void fill_arr_pento(char* str, int nb_elem, pento_p* arr, int nb_pento);
int nb_pento(char* str, int nb_elem);
int pento_ok(char* str, int nb_elem);
void get_tab_goal(char* str,int nb_elem, int tab_goal[][GOAL_SIZE], int tab_goal_size);
int full_diese(char* str, int nb_elem, int current);
int nb_total_elem(char* str, int nb_elem);
void goal_specs(char* str, int nb_elem, int* nb_row, int* longest_row);
int max(int a, int b);
void txt_to_char(FILE* file, char* str);
void tests(FILE* file, char* str,int nb_elem);

#endif
