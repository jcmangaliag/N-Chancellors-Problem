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

int checkValidInput(int **board, int dimension, int x, int y){
	int i, j;

	// check vertical
	for (i = 0; i < dimension; i++){
		if (i != x && (board[i][y]) == 1){
			
			return 0;
		}
	}

	// check horizontal
	for (j = 0; j < dimension; j++){
		if (j != y && (board[x][j]) == 1){
			return 0;
		}
	}

	// check knight's moves in upper left
	if (x > 0 && y > 1 && board[x-1][y-2] == 1){
		return 0;
	}
	if (x > 1 && y > 0 && board[x-2][y-1] == 1){
		return 0;
	}

	// check knight's moves in upper right
	if (x > 0 && dimension - y > 2 && board[x-1][y+2] == 1){
		return 0;
	}
	if (x > 1 && dimension - y > 1 && board[x-2][y+1] == 1){
		return 0;
	}

	// check knight's moves in lower left
	if (dimension - x > 1 && y > 1 && board[x+1][y-2] == 1){
		return 0;
	}
	if (dimension - x > 2 && y > 0 && board[x+2][y-1] == 1){
		return 0;
	}
			
	// check knight's moves in lower right
	if (dimension - x > 1 && dimension - y > 2 && board[x+1][y+2] == 1){
		return 0;
	}
	if (dimension - x > 2 && dimension - y > 1 && board[x+2][y+1] == 1){
		return 0;
	}

	return 1;
}

void writeSolution(FILE *outputFP, int candidate, int dimension){
	int i;

	for (i = 1; i <= dimension; i++){
		fprintf(outputFP, i == candidate? "1 " : "0 ");
	}

	fprintf(outputFP, "\n");
}

int* findBoardCandidates(int **referenceBoard, int dimension){
	int i, j, chancellorLocation, *boardCandidates = (int *) malloc (sizeof(int) * dimension);

	for (i = 0; i < dimension; i++){
		chancellorLocation = 0;
		for (j = 0; j < dimension; j++){
			if (referenceBoard[i][j] == 1){
				chancellorLocation = j + 1;
				break;
			}
		}
		boardCandidates[i] = chancellorLocation;
	}
	return boardCandidates;
}

int acceptCandidate(int candidate, int move, int dimension, int option[][dimension+2], int nopts[]){
	int i;

	for(i = move - 1; i >= 1; i--){	 
		// check for same column
		if (candidate == option[i][nopts[i]])
			return 0;

		if (i == move - 1){
			// check for horizontal L going left
			if (candidate > 2 && option[i][nopts[i]] == candidate - 2)
				return 0;

			// check for horizontal L going right
			if (dimension - candidate > 1 && option[i][nopts[i]] == candidate + 2)
				return 0;
		}

		if (i == move - 2){
			// check for vertical L going left
			if (candidate > 1 && option[i][nopts[i]] == candidate - 1)
				return 0;

			// check for vertical L going right
			if (dimension - candidate > 0 && option[i][nopts[i]] == candidate + 1)
				return 0;
		}
	}

	return 1;
}

int validateInitialBoard(int **referenceBoard, int dimension){
	int i, j;

	for (i = 0; i < dimension; i++){
		for (j = 0; j < dimension; j++){
			if (referenceBoard[i][j] == 1 && !checkValidInput(referenceBoard, dimension, i, j)){
				return 0;
			}
		}
	}

	return 1;
}

int solveNChancellors(FILE *outputFP, int **referenceBoard, int dimension){
	int start, move;
	int nopts[dimension+2]; // array of top of stacks
	int option[dimension+2][dimension+2]; // array of stacks of options
	int i, candidate, solutionsCount = 0;
	int *boardCandidates = findBoardCandidates(referenceBoard, dimension);

	if (!validateInitialBoard(referenceBoard, dimension))
		return 0;

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
				if (boardCandidates[move - 1] > 0){	// if there's initially placed chancellor, evaluate it
					if(acceptCandidate(boardCandidates[move - 1], move, dimension, option, nopts))
						option[move][++nopts[move]] = boardCandidates[move - 1];
				} else {	// no initially placed chancellor; try all possible candidates
					for(candidate=dimension;candidate>=1;candidate--){
						if(acceptCandidate(candidate, move, dimension, option, nopts))
							option[move][++nopts[move]] = candidate;
					}
				}
			}
		}
		else {
			move--;
			nopts[move]--;
		}
	}

	free(boardCandidates);
	return solutionsCount;
}