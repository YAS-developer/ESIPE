#include <MLV/MLV_window.h>
#include <MLV/MLV_color.h>
#include "board.h"
#include "window.h"
#include "event.h"

board* global_board;
image* global_image;

void init(void)
{
  MLV_create_window("Taquin", "Taquin", 535, 535);
	global_board = initialize_board(4, 4);
	global_image = init_image("Chalet-Suisse.jpg");
  mix_board(global_board);
}

void loop(void)
{
  while ( !is_complete( global_board) )
  {
    MLV_clear_window(MLV_COLOR_BLACK);
    draw_board( global_board, global_image );
    display_ascii_board_on_stdout( global_board );
    MLV_actualise_window();
    manage_events( global_board );
  }
}

void end(void)
{
  MLV_free_window();
}

int main( void )
{
	init();
	loop();
	end();

  return 0;
}