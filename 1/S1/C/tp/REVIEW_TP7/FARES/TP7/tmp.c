// MLV_create_window("Sudoku", NULL, 1200, 800);


    // while(1){
    //     MLV_update_window();

    //     int i, j;

    //     for(i = 1; i <= SIZE+1; i++)
    //     {
    //         MLV_draw_line(60,60*i,60*(SIZE+1),60*i, MLV_COLOR_WHITE);
    //         MLV_draw_line(60*i, 60, 60*i, 60*(SIZE+1), MLV_COLOR_WHITE);
    //     }

    //     int x,y;

    //     
        
    //     for(i = 0; i < SIZE; i++){
    //         // MLV_draw_text((90-3)+60*i, 90-8, "1", MLV_COLOR_WHITE);

            

    //         for(j = 0; j < SIZE; j++){
    //             sprintf(text, "%d", grid[i][j]);
    //             if(strcmp(text, "0")){
    //                 MLV_draw_text((90-3)+60*i, (90-8)+60*j, text, MLV_COLOR_WHITE);
    //             }    
    //         }
    //     }

    //     MLV_wait_mouse(&x,&y);

    //     printf("x : %d     y : %d\n", x,y);
    //     int caseX = 0;
    //     int caseY = 0;

    //     if((x >= 60 && x <= 600) && ( y >= 60 && y <= 600)){

    //       caseX = x;
    //       caseY = y;

    //       printf("X2 = %d, Y2 = %d\n", x, y);

    //       int minX = 750;
    //       int maxX = 750+(60*SIZE2);
    //       int minY = 200;
    //       int maxY = 200+(60*SIZE2);

    //       for(i = 0; i <= SIZE2 ; i++){
    //       MLV_draw_line(minX,minY+(case*i),maxX,minY+(case*i), MLV_COLOR_WHITE);
    //       MLV_draw_line(minX+(case*i), minY, minX+(case*i), maxY, MLV_COLOR_WHITE);
    //       }

    //       MLV_draw_line(minX,maxY,minX,maxY+case, MLV_COLOR_WHITE); // ligne gauche x

    //       MLV_draw_line(minX,maxY+case,maxX,maxY+case, MLV_COLOR_WHITE); // ligne bas Y

    //       MLV_draw_line(maxX,maxY,maxX,maxY+case, MLV_COLOR_WHITE); // ligne droite X

    //       MLV_draw_text((minX+(maxX-minX)/2)-20, ((maxY+case)-(case/2))-8, "Valider", MLV_COLOR_WHITE);

    //       printf("max Y + case : %d\n", maxY+case);

    //       printf("case/2 : %d\n", case/2);

    //       printf("blabla: %d\n", (maxY+case)-(case/2));

    //       int a = 1;

    //       for(i = 0; i < 3; i++)
    //       {
    //         for(j = 0; j < 3; j++){
    //           sprintf(text, "%d", a);
    //             if(strcmp(text, "0")){
    //                 MLV_draw_text(((minX+case*j)+case/2)-3, ((minY+case*i)+case/2)-6, text, MLV_COLOR_WHITE);
    //             }  
    //             a++;
    //         }
    //       }

    //       // for(j = 0; j < 9; j++){
                  
    //       //   }
    //     }else if((x >= 750 && x <= 930) && (y >= 200 && y <= 380)){
    //       printf("sdqs\n");
    //       printf("CaseX : %d && CaseY : %d\n", caseX, caseY);
    //       if(caseX != 0 && caseY != 0){
    //         MLV_draw_text(caseX, caseY, "x", MLV_COLOR_WHITE);
            
    //       }
          
    //     }
        

        

    //     // for(j = 1; j <= SIZE+1; j++){
    //     //         
    //     // }
    // }

    // MLV_free_window();