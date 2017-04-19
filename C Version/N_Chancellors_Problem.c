#include <stdio.h>
#include <stdlib.h>
#include "N_Chancellors_Problem.h"

int main() {
	FILE *inputFP = fopen("input.txt", "r"), *outputFP = fopen("output.txt", "w");
	int noOfPuzzles, dimension, **referenceBoard, i;

	if (inputFP){
		fscanf(inputFP, "%d", &noOfPuzzles);
		for (i = 0; i < noOfPuzzles; i++){
			fscanf(inputFP, "%d", &dimension);
			referenceBoard = createBoard(inputFP, dimension); // board will not be modified
			fprintf(outputFP, "%d solution/s in puzzle %d\n\n", solveNChancellors(outputFP, referenceBoard, dimension), i+1);
			freeBoardMemory(referenceBoard, dimension);
		}

		fclose(inputFP);
		fclose(outputFP);
	} else {
		printf("No input.txt found!\n");
	}
	
	return 0;
}