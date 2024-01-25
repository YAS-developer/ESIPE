#include <stdio.h>
#include <unistd.h>
#include <MLV/MLV_all.h>
#include "in_out.h"

int fread_board(const char* file, Board board){
	FILE* f;
	int i,j;
	int entry;

	f = fopen(file, "r");
	if (f == NULL){
		fprintf(stderr, "Erreur d'ouverture du fichier %s\n", file);
		return 0;
	}

	for (i=0 ; i<9 ; i++){
		for (j=0 ; j<9 ; j++){
			fscanf(f, "%d", &entry);
			board[i][j] = entry;
		}
	}
	return 1;
}

void check_existing_records(char* filename) {
	if (access(filename, F_OK) != 0) {
		FILE *file = fopen(filename, "w+");
		fprintf(file, "5999 5999 5999 5999 5999 5999 5999 5999 5999 5999");
		fclose(file);
	}
}

void update_records(char* filename, int newTime, char* timeBuffer) {
	int times[10];
	char fileContent[50];
	char* token;
	int temp;
	FILE *file = fopen(filename, "r");
	fread(fileContent, 1, 50, file);
	token = strtok(fileContent, " ");
	for (int i=0; i<10; i++) {
		times[i] = atoi(token);
		if (i!=10) token = strtok(NULL, " ");
	}
	fclose(file);
	if (times[9] > newTime) times[9] = newTime;
	for (int i=9; i!=0; i--) if (times[i-1] > times[i]) {
		temp = times[i-1];
		times[i-1] = times[i];
		times[i] = temp;
	}
	FILE *file2 = fopen(filename, "w");
	for (int i=0; i<10; i++) {
		sprintf(timeBuffer, "%02d:%02d", times[i]/60, times[i]%60);
		MLV_draw_text(620, 60+i*30, timeBuffer, MLV_COLOR_WHITE);
		fprintf(file2, "%d%s", times[i], i!=9 ? " " : "");
	}
	fclose(file2);
}
