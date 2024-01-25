#include <MLV/MLV_all.h>
#include <MLV/MLV_time.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

#define debut_suduko_X WIDTH_WINDOWS_PIX / 20
#define fin_suduko_X (WIDTH_WINDOWS_PIX / 10) * 7
#define pat_suduko_X (fin_suduko_X - debut_suduko_X) / 9

#define debut_suduko_Y HEIGHT_WINDOWS_PIX / 20
#define fin_suduko_Y (HEIGHT_WINDOWS_PIX / 10) * 9
#define pat_suduko_Y (fin_suduko_Y - debut_suduko_Y) / 9

#define debut_num_X WIDTH_WINDOWS_PIX / 20 * 15
#define fin_num_X (WIDTH_WINDOWS_PIX / 20) * 19
#define pat_num_X (fin_num_X - debut_num_X) / 3

#define debut_num_Y HEIGHT_WINDOWS_PIX / 10 * 5
#define fin_num_Y (HEIGHT_WINDOWS_PIX / 10) * 8
#define pat_num_Y (fin_num_Y - debut_num_Y) / 3

#include "interface.h"
int count = 1;
int time_de_jeu;
char *name_jouer;
Board deuxeme, *bb;

void *myThreadFun(void *vargp)
{
  int x, y;
  point mouse;
  while (count)
  {
    usleep(100000);
    affiche_suduko_window(*bb, deuxeme);

    time_de_jeu = MLV_get_time();

    MLV_draw_filled_rectangle(fin_suduko_X + 50, debut_suduko_Y + 50, 150, 50, MLV_rgba(0, 0, 0, 255));

    MLV_draw_text(
        fin_suduko_X + 50, debut_suduko_Y + 50,
        " %d s",
        MLV_COLOR_GREEN, time_de_jeu / 1000);

    MLV_get_mouse_position(&x, &y);
    if (clike_dans_sudoko(x, y))
    {
      mouse = cordnne_de_point_clicke_dans_la_suduko(x, y);
      colore_la_casse(mouse.x, mouse.y);
    }
    actualise_window();
  }
  return NULL;
}

void create_windows(void)
{
  MLV_create_window("Desk Calculator", NULL, WIDTH_WINDOWS_PIX, HEIGHT_WINDOWS_PIX);
}

void rampil_windows(void)
{
  int i;
  /*drow the linges*/
  for (i = 0; i <= 8; i++)
  {
    MLV_draw_line(debut_suduko_X, debut_suduko_Y + (pat_suduko_Y * i), fin_suduko_X, debut_suduko_Y + (pat_suduko_Y * i), MLV_rgba(0, 255, 255, 255));
  }
  MLV_draw_line(debut_suduko_X, fin_suduko_Y, fin_suduko_X, fin_suduko_Y, MLV_rgba(0, 255, 255, 255));

  /*drow the colones*/
  for (i = 0; i <= 8; i++)
  {
    MLV_draw_line(debut_suduko_X + (pat_suduko_X * i), debut_suduko_Y, debut_suduko_X + (pat_suduko_X * i), fin_suduko_Y, MLV_rgba(0, 255, 255, 255));
  }
  MLV_draw_line(fin_suduko_X, debut_suduko_Y, fin_suduko_X, fin_suduko_Y, MLV_rgba(0, 255, 255, 255));
}

void AFFICHE_NUMERO_window(void)
{
  int i, j;
  char *numbers[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

  for (i = 0; i <= 3; i++)
  {
    MLV_draw_line(debut_num_X, debut_num_Y + (pat_num_Y * i), fin_num_X, debut_num_Y + (pat_num_Y * i), MLV_rgba(255, 255, 255, 255));
  }
  for (i = 0; i <= 3; i++)
  {
    MLV_draw_line(debut_num_X + (pat_num_X * i), debut_num_Y, debut_num_X + (pat_num_X * i), fin_num_Y, MLV_rgba(255, 255, 255, 255));
  }
  int poition_de_x = debut_num_X + pat_num_X / 3;
  int poition_de_Y = debut_num_Y + pat_num_Y / 3;
  for (i = 0; i < 3; i++)
  {
    for (j = 0; j < 3; j++)
    {

      MLV_draw_text(poition_de_x + i * pat_num_X, poition_de_Y + j * pat_num_Y, numbers[j * 3 + i], MLV_rgba(255, 255, 255, 255));
    }
  }
}

void affiche_suduko_window(Board board, Board original)
{
  int i, j;
  MLV_change_default_font("Roboto-Bold.ttf", 30);
  int poition_de_x = debut_suduko_X + pat_suduko_X / 3;
  int poition_de_Y = debut_suduko_Y + pat_suduko_Y / 3;

  char t[3], text[3];
  for (i = 0; i < 9; i++)
  {
    for (j = 0; j < 9; j++)
    {
      sprintf(t, "%d", board[j][i]);
      sprintf(text, "%d", original[j][i]);

      if (board[j][i] == -1)
      {
        vide_la_casse(i, j);
        colore_la_casse(i, j);
        MLV_draw_text(poition_de_x + i * pat_suduko_X, poition_de_Y + j * pat_suduko_Y, "?", MLV_rgba(255, 255, 255, 255));
      }
      else if (*t != '0')
      {
        vide_la_casse(i, j);
        if (*text == '0')
        {
          colore_la_casse(i, j);
        }
        MLV_draw_text(poition_de_x + i * pat_suduko_X, poition_de_Y + j * pat_suduko_Y, t, MLV_rgba(255, 255, 255, 255));
      }
      else
      {
        vide_la_casse(i, j);
        colore_la_casse(i, j);
      }
    }
  }
}
void copy_bord(Board b, Board deuxeme)
{
  int i, j;
  for (i = 0; i < 9; i++)
  {
    for (j = 0; j < 9; j++)
    {
      (deuxeme)[i][j] = b[i][j];
    }
  }
}
void joue(Board b, int *list, char *arr[])
{
  create_windows();
  rampil_windows();
  joue_window(b);
  MLV_free_window();
  create_windows();
  win_the_game(list, arr);
  actualise_window();
  sleep(4);
  MLV_free_window();
}

void win_the_game(int *list, char *arr[])
{
  MLV_change_default_font("Roboto-Bold.ttf", 30);
  MLV_wait_input_box(
      WIDTH_WINDOWS_PIX / 3, 150,
      400, 50,
      MLV_COLOR_GREEN, MLV_COLOR_WHITE, MLV_COLOR_BLACK,
      "name : ",
      &name_jouer);
  drow_table(list, arr);

  MLV_draw_text(
      WIDTH_WINDOWS_PIX / 2, (HEIGHT_WINDOWS_PIX / 3) * 2,
      "%s            %d s",
      MLV_COLOR_BLUE, name_jouer, time_de_jeu / 1000);
  actualise_window();
}
void drow_table(int *list, char *arr[])
{
  int i;
  ModifListDeTOP5(list, arr);
  MLV_draw_text(
      WIDTH_WINDOWS_PIX / 3, HEIGHT_WINDOWS_PIX / 4 - 50,
      " %s",
      MLV_COLOR_GREEN, "the top 5  :");

  for (i = 0; i < 5; i++)
  {
    MLV_draw_text(
        WIDTH_WINDOWS_PIX / 3, HEIGHT_WINDOWS_PIX / 4 + i * 50,
        " %s",
        MLV_COLOR_GREEN, arr[i]);
    MLV_draw_text(
        WIDTH_WINDOWS_PIX / 3 + 150, HEIGHT_WINDOWS_PIX / 4 + i * 50,
        " %d s",
        MLV_COLOR_GREEN, list[i]);
  }
}
void ModifListDeTOP5(int *list, char *arr[])
{
  int i;
  int decal = 0;
  int intermidaire = list[0];
  int intermidaire2;
  char *intermidairechar = arr[0];
  char *intermidaire2char = arr[0];

  for (i = 0; i < 5; i++)
  {
    if (((time_de_jeu / 1000) < list[i]) && (decal == 0))
    {
      decal = 1;
      intermidaire = list[i];
      intermidairechar = arr[i];
      list[i] = (time_de_jeu / 1000);
      arr[i] = name_jouer;

      continue;
    }
    intermidaire2 = list[i];
    intermidaire2char = arr[i];
    list[i] = intermidaire;
    arr[i] = intermidairechar;
    intermidaire = intermidaire2;
    intermidairechar = intermidaire2char;
  }
}
void vide_la_casse(int x, int y)
{
  MLV_draw_filled_rectangle(x * pat_suduko_X + debut_suduko_X + 1, y * pat_suduko_Y + debut_suduko_Y + 1, pat_num_X + 1, pat_suduko_Y - 4, MLV_rgba(0, 0, 0, 255));
}
void colore_la_casse(int x, int y)
{
  MLV_draw_filled_rectangle(x * pat_suduko_X + debut_suduko_X + 1, y * pat_suduko_Y + debut_suduko_Y + 1, pat_num_X + 1, pat_suduko_Y - 4, MLV_rgba(255, 255, 255, 100));
}
void efface_num()
{
  MLV_draw_filled_rectangle(debut_num_X, debut_num_Y, fin_num_X - debut_num_X + pat_num_X / 3, fin_num_Y - debut_num_Y + pat_num_Y / 3, MLV_rgba(0, 0, 0, 255));
}

point cordnne_de_point_clicke_dans_la_suduko(int x, int y)
{
  point p;
  int dev_x = pat_suduko_X;
  int dev_y = pat_suduko_Y;
  p.x = (x - debut_suduko_X) / dev_x;
  p.y = (y - debut_suduko_Y) / dev_y;
  p.x = (p.x > 8) ? 8 : p.x;
  p.y = (p.y > 8) ? 8 : p.y;
  return p;
}

int clike_dans_sudoko(int x, int y)
{
  if ((debut_suduko_X <= x) && (x <= fin_suduko_X) && (debut_suduko_Y <= y) && (y <= fin_suduko_Y))
  {
    return 1;
  }
  return 0;
}
int clike_dans_nume(int x, int y)
{
  if ((debut_num_X <= x) && (x <= fin_num_X) && (debut_num_Y <= y) && (y <= fin_num_Y))
  {
    return 1;
  }
  return 0;
}

int droit_a_change(int x, int y, Board b)
{
  if (b[y][x] == 0)
  {
    return 1;
  }
  return 0;
}
int numero_clicke_dans_le_numbre(int x, int y)
{
  int dev_x = pat_num_X;
  int dev_y = pat_num_Y;
  return ((x - debut_num_X) / dev_x) + 1 + ((y - debut_num_Y) / dev_y) * 3;
}

void actualise_window(void)
{
  MLV_actualise_window();
}



void joue_window(Board b)
{
  
  pthread_t id;
  bb = b;

  int x, y, val;

  copy_bord(b, deuxeme);
  point point_clike, point_selectionne;

  int etat_de_selection = 0;
  actualise_window();
  pthread_create(&id, NULL, myThreadFun, NULL);

  while (1)
  {
    if (verif(b))
    {
      count = 0;
      pthread_join(id, NULL);
      printf("%d", time_de_jeu);
      break;
    }
    MLV_wait_mouse(&x, &y);
    if (clike_dans_sudoko(x, y))
    {

      point_clike = cordnne_de_point_clicke_dans_la_suduko(x, y);

      if ((droit_a_change(point_clike.x, point_clike.y, deuxeme)))
      {
        if (etat_de_selection == 1)
        {
          b[point_selectionne.y][point_selectionne.x] = 0;
          actualise_window();
        }
        point_selectionne.x = point_clike.x;
        point_selectionne.y = point_clike.y;
        b[point_selectionne.y][point_selectionne.x] = -1;
        etat_de_selection = 1;
        AFFICHE_NUMERO_window();
        actualise_window();
      }
    }
    else if (clike_dans_nume(x, y))
    {

      val = numero_clicke_dans_le_numbre(x, y);
      if (etat_de_selection != 0)
      {
        if (est_vrais(b, point_selectionne.x, point_selectionne.y, val))
        {
          b[point_selectionne.y][point_selectionne.x] = val;
          vide_la_casse(point_selectionne.x, point_selectionne.y);
          efface_num();
          actualise_window();
          etat_de_selection = 0;
        }
      }
    }
  }
}
