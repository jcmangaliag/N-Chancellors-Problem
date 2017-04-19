int **createBoard(FILE *inputFP, int dimension){
	int i, j, **board = (int **) malloc (sizeof(int*) * dimension);

	for (i = 0; i < dimension; i++) {
		board[i] = (int *) malloc (sizeof(int) * dimension);
	}

	for (i = 0; i < dimension; i++){
		for (j = 0; j < dimension; j++){
			fscanf(inputFP, "%d", &board[i][j]);
		}
	}

	return board;
}

void freeBoardMemory(int **board, int dimension){
	int i;

	for (i = 0; i < dimension; i++){
		free(board[i]);
	}

	free(board);
}

void writeSolution(FILE *outputFP, int candidate, int dimension){
	int i;

	for (int i = 1; i <= dimension; i++){
		fprintf(outputFP, i == candidate? "1 " : "0 ");
	}

	fprintf(outputFP, "\n");
}

int solveNChancellors(FILE *outputFP, int **referenceBoard, int dimension){
	int start, move;
	int nopts[dimension+2]; // array of top of stacks
	int option[dimension+2][dimension+2]; // array of stacks of options
	int i, candidate;
	int solutionsCount = 0;

	move = start = 0; 
	nopts[start]= 1;
	
	while (nopts[start] >0){ // while dummy stack is not empty
	
		if(nopts[move]>0){ 
		
			move++;
			nopts[move]=0; // initialize new move

			if(move==dimension+1){ // solution found!

				solutionsCount++;

				for(i=1;i<move;i++){
					writeSolution(outputFP, option[i][nopts[i]], dimension);
				}
				fprintf(outputFP, "\n");
			}
			else {
				for(candidate=dimension;candidate>=1;candidate--){
					for(i=move-1;i>=1;i--){	 
						// check for same column
						if (candidate == option[i][nopts[i]])
							break;

						if (i == move - 1){
							// check for horizontal L going left
							if (candidate > 2 && option[i][nopts[i]] == candidate - 2)
								break;

							// check for horizontal L going right
							if (dimension - candidate > 1 && option[i][nopts[i]] == candidate + 2)
								break;
						}

						if (i == move - 2){
							// check for vertical L going left
							if (candidate > 1 && option[i][nopts[i]] == candidate - 1)
								break;

							// check for vertical L going right
							if (dimension - candidate > 0 && option[i][nopts[i]] == candidate + 1)
								break;
						}
					}

					if(!(i>=1))
						option[move][++nopts[move]] = candidate;
				}
			}
		}
		else {
			move--;
			nopts[move]--;
		}
	}

	return solutionsCount;
}