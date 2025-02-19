import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuSolverClass implements SudokuSolver {
	private int[][] board;
	
	/**
	 * Contructor that initializes the attribute (int[][] board) with 9 x 9 elements. 
	 */
	public SudokuSolverClass() {
		board = new int[9][9];
	}
	
	@Override
	public void setNumber(int r, int c, int nbr) {
		if(r < getDimension() && c < getDimension() && c > -1 && r > -1 
				&& nbr < 10 && nbr >= 0) {
			board[r][c] = nbr;
		} else {
			throw new IllegalArgumentException("Wrong dimensions on row or column");
		}
	}

	@Override
	public int getNumber(int r, int c) {
		if(r < getDimension() && c < getDimension() && c > -1 && r > -1) {
			return board[r][c];
		} else {
			throw new IllegalArgumentException("Wrong dimensions on row or column");
		}
	}

	@Override
	public void clearNumber(int r, int c) {
		if(r < getDimension() && c < getDimension() && c > -1 && r > -1) {
			board[r][c] = 0;
		} else {
			throw new IllegalArgumentException("Wrong dimensions on row or column");
		}
	}

	@Override
	public boolean isValid(int r, int c, int nbr) {
		return validRow(r, c, nbr) && validColumn(r, c, nbr) && validSquare(r, c, nbr);
	}
	
	private boolean validRow(int r, int c, int nbr) {
		for (int i = 0; i < getDimension(); i++) {
			if ( board[i][c] == nbr && i != r) {
				return false; 
			}
		}
		return true; 
	}
	
	private boolean validColumn(int r, int c, int nbr) {
		for (int i = 0; i < getDimension(); i++) {
			if ( board[r][i] == nbr && i != c) {
				return false; 
			}
		}
		return true; 
	}
	
	private boolean validSquare(int r, int c, int nbr) {
		int r0 = (int) Math.floor(r/3) * 3;
		int c0 = (int) Math.floor(c/3) * 3;
		for (int a = r0; a < r0+3; a++) {
			for (int b = c0; b < c0+3; b++) {
				if(board[a][b] == nbr && (a != r && b != c)) {
					return false; 
				}
			}
		}
		return true; 
	}

	@Override
	public boolean isAllValid() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if(board[r][c] != 0 && !isValid(r, c, getNumber(r, c))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean solve() {
		return magicSolver(0, 0);
	}
	
	private boolean magicSolver(int startR, int startC) {
		for (int r = startR; r < 9; r++) {
			for (int c = startC; c < 9; c++) {
				if(board[r][c] == 0) {
					for (int n = 1; n <= 9; n++) {
						if(isValid(r, c, n)) {
							setNumber(r, c, n);
							if(magicSolver(r, c)) {
								return true; 
							}
							clearNumber(r, c); 
						}
					}
					return false; 
				}
			}
			startC = 0; 
		}
		return true;
	}

	@Override
	public void clear() {
		for (int r = 0; r < getDimension(); r++) {
			for (int c = 0; c < getDimension(); c++) {
				board[r][c] = 0;
			}
		}
	}

	@Override
	public int[][] getMatrix() {
		return board.clone(); 
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		board = nbrs; 
	}
}
