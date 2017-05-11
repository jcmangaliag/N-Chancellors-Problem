import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class NChancellorSolver {

	public int[] findBoardCandidates (int[][] referenceBoard, int dimension) {
		int chancellorLocation;
		int[] boardCandidates = new int[dimension];

		for (int i = 0; i < dimension; i++){
			chancellorLocation = 0;
			for (int j = 0; j < dimension; j++){
				if (referenceBoard[i][j] == 1){
					chancellorLocation = j + 1;
					break;
				}
			}
			boardCandidates[i] = chancellorLocation;
		}

		return boardCandidates;
	}

	public boolean checkValidInput(int[][] board, int dimension, int x, int y){
		int i, j;

		// check vertical
		for (i = 0; i < dimension; i++){
			if (i != x && (board[i][y]) == 1){
				return false;
			}
		}

		// check horizontal
		for (j = 0; j < dimension; j++){
			if (j != y && (board[x][j]) == 1){
				return false;
			}
		}

		// check knight's moves in upper left
		if (x > 0 && y > 1 && board[x-1][y-2] == 1){
			return false;
		}
		if (x > 1 && y > 0 && board[x-2][y-1] == 1){
			return false;
		}

		// check knight's moves in upper right
		if (x > 0 && dimension - y > 2 && board[x-1][y+2] == 1){
			return false;
		}
		if (x > 1 && dimension - y > 1 && board[x-2][y+1] == 1){
			return false;
		}

		// check knight's moves in lower left
		if (dimension - x > 1 && y > 1 && board[x+1][y-2] == 1){
			return false;
		}
		if (dimension - x > 2 && y > 0 && board[x+2][y-1] == 1){
			return false;
		}
				
		// check knight's moves in lower right
		if (dimension - x > 1 && dimension - y > 2 && board[x+1][y+2] == 1){
			return false;
		}
		if (dimension - x > 2 && dimension - y > 1 && board[x+2][y+1] == 1){
			return false;
		}

		return true;
	}

	public boolean validateInitialBoard(int[][] referenceBoard, int dimension){
		for (int i = 0; i < dimension; i++){
			for (int j = 0; j < dimension; j++){
				if (referenceBoard[i][j] == 1 && !checkValidInput(referenceBoard, dimension, i, j)){
					return false;
				}
			}
		}

		return true;
	}

	public void writeSolution(BufferedWriter outputWriter, int candidate, int dimension) throws Exception {
		for (int i = 1; i <= dimension; i++){
			outputWriter.write(i == candidate? "1 " : "0 ");
		}

		outputWriter.write("\n");
	}

	public boolean acceptCandidate(int candidate, int move, int dimension, int[][] option, int nopts[]){
		for(int i = move - 1; i >= 1; i--){	 
			// check for same column
			if (candidate == option[i][nopts[i]]){
				return false;
			}

			if (i == move - 1){
				// check for horizontal L going left
				if (candidate > 2 && option[i][nopts[i]] == candidate - 2){
					return false;
				}

				// check for horizontal L going right
				if (dimension - candidate > 1 && option[i][nopts[i]] == candidate + 2){
					return false;
				}
			}

			if (i == move - 2){
				// check for vertical L going left
				if (candidate > 1 && option[i][nopts[i]] == candidate - 1){
					return false;
				}

				// check for vertical L going right
				if (dimension - candidate > 0 && option[i][nopts[i]] == candidate + 1){
					return false;
				}
			}
		}

		return true;
	}


	public void solveNChancellors(int[][] referenceBoard) {
		try {
			BufferedWriter outputWriter = new BufferedWriter(new FileWriter("output.txt"));

			int start, move, dimension = referenceBoard[0].length;
			int[] nopts = new int[dimension+2]; // array of top of stacks
			int[][] option = new int[dimension+2][dimension+2]; // array of stacks of options
			int i, candidate, solutionsCount = 0;
			int[] boardCandidates = findBoardCandidates(referenceBoard, dimension);

			outputWriter.write("size=" + dimension + "\n\n");

			if (validateInitialBoard(referenceBoard, dimension)){
				move = start = 0; 
				nopts[start]= 1;
				
				while (nopts[start] >0){ // while dummy stack is not empty
					if(nopts[move]>0){ 
					
						move++;
						nopts[move]=0; // initialize new move

						if(move==dimension+1){ // solution found!

							solutionsCount++;

							for(i=1;i<move;i++){
								writeSolution(outputWriter, option[i][nopts[i]], dimension);
							}
							outputWriter.write("\n");
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
			}

			outputWriter.write("solutionsCount=" + solutionsCount);
			outputWriter.close();

		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
	}
}