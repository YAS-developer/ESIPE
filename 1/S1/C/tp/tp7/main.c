#include <MLV/MLV_all.h>
// #include <MLV/MLV_window.h>

// afficher les chiffres: x:85, y:85+60

int main(){
    MLV_create_window("Frame", NULL, 1200, 800);
    const int SIZE = 9;
    while(1){
        for(int i=1; i<=SIZE+1; i++){
            // for(int j=1; j<=SIZE; j++){
            //     MLV_draw_line(60, 60*i, 120*j, 60*i, MLV_COLOR_ANTIQUE_WHITE);
            //     MLV_draw_line(60, 60, 60, 120, MLV_COLOR_ANTIQUE_WHITE);
            //     MLV_draw_line(120, 60, 120, 120, MLV_COLOR_ANTIQUE_WHITE);
            //     MLV_draw_line(60, 120*i, 120*j, 120*i, MLV_COLOR_ANTIQUE_WHITE);
            // }
            MLV_draw_line(60, 60*i, 600, 60*i, MLV_COLOR_ANTIQUE_WHITE);
            MLV_draw_line(60*i, 60, 60*i, 600, MLV_COLOR_ANTIQUE_WHITE);
        }   

        // /*haut ligne*/      MLV_draw_line(60, 60, 120, 60, MLV_COLOR_ANTIQUE_WHITE);
        // /*gauche ligne*/    MLV_draw_line(60, 60, 60, 120, MLV_COLOR_ANTIQUE_WHITE);
        // /*droite ligne*/    MLV_draw_line(120, 60, 120, 120, MLV_COLOR_ANTIQUE_WHITE);
        // /*sous ligne*/      MLV_draw_line(60, 120, 120, 120, MLV_COLOR_ANTIQUE_WHITE);

        // i va bouger les y 
        // y va bouger les x

        int addX=0, addY=0;
	    int x=0,y=0;
        MLV_wait_mouse(&x, &y);
        printf("x: %d y: %d\n",x, y); 
        
    
        // haut a gauche: 60, 60
        // haut a droite: 120, 60
        // bas a gauche: 60, 120
        // bas a droite: 120, 120 
        // MLV_draw_filled_rectangle(int x, int y, 50, 50, MLV_COLOR_BLACK);
        int aboveLeftX=0, aboveLeftY=0, 
            aboveRightX=0, aboveRightY=0, 
            underLeftX=0, underLeftY=0, 
            underRightX=0, underRightY=0;
            
        addX=0, addY=0;
        int currentX=85, currentY=85;
        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                currentX+=addX;
                currentY+=addY;
                // if(x <= )){

                // }
                // MLV_draw_filled_rectangle(int x, int y, 50, 50, MLV_COLOR_BLACK);
                MLV_draw_text(85+addX, 85+addY,"1", MLV_COLOR_ANTIQUE_WHITE);
                addY+=60; 
            }
            addX+=60;
            addY=0;
            aboveLeftY+=60;
            aboveRightY+=60;
            underRightY+=60;
        } 
        MLV_update_window();  
    }

    return 0;
}   