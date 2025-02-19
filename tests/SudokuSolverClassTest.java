import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuSolverClassTest {
	private SudokuSolverClass board;
	
	@BeforeEach
	void setUp() throws Exception {
		board = new SudokuSolverClass();
	}

	@AfterEach
	void tearDown() throws Exception {
		board.clear();
	}

	@Test
	void testBoardCreation() {
		assertEquals(9, board.getMatrix().length);
		assertEquals(9, board.getMatrix()[1].length);
		assertEquals(9, board.getDimension());
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				assertEquals(0, board.getMatrix()[r][c], "board creation failed - not empty");
			}
		}
	}
	
	@Test
	void testSetNumber() {
		board.setNumber(1,1,7);
		assertEquals(7, board.getMatrix()[1][1], "failed to set number");
		board.setNumber(1,1,8);
		assertEquals(8, board.getMatrix()[1][1], "failed to re-set number");
		
		assertThrows(IllegalArgumentException.class, () -> board.setNumber(1000, 3, 4), "too big row");
		assertThrows(IllegalArgumentException.class, () -> board.setNumber(3, -3, 4), "negative col");
		assertThrows(IllegalArgumentException.class, () -> board.setNumber(3, 3, -4), "negative nbr");
		assertThrows(IllegalArgumentException.class, () -> board.setNumber(3, 3, 40), "too big nbr");
	}
	
	@Test
	void testGetNumber() {
		int[][] nbrs = {
		  { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
		  { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
		  { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
		  { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
		  { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
		  { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
		  { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
		  { 0, 9, 0, 0, 0, 0, 4, 0, 0 } 
		};
		
//		int[][] solution = {
//			{8, 1, 2, 7, 5, 3, 6, 4, 9}, 
//			{9, 4, 3, 6, 8, 2, 1, 7, 5}, 
//			{6, 7, 5, 4, 9, 1, 2, 8, 3}, 
//			{1, 5, 4, 2, 3, 7, 8, 9, 6}, 
//			{3, 6, 9, 8, 4, 5, 7, 2, 1}, 
//			{2, 8, 7, 1, 6, 9, 5, 3, 4}, 
//			{5, 2, 1, 9, 7, 4, 3, 6, 8}, 
//			{4, 3, 8, 5, 2, 6, 9, 1, 7}, 
//			{7, 9, 6, 3, 1, 8, 4, 5, 2}
//		};
		
		board.setMatrix(nbrs);
		assertEquals(8, board.getNumber(0, 0), "retrieved wrong numba");
		board.solve(); 
		assertEquals(2, board.getNumber(8, 8), "retrieved wrong numba");
	}
	
	@Test
	void testClearNumber() {
		int[][] nbrs = {
		  { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
		  { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
		  { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
		  { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
		  { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
		  { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
		  { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
		  { 0, 9, 0, 0, 0, 0, 4, 0, 0 } 
		};
		
		board.setMatrix(nbrs);
		assertEquals(8, board.getNumber(0, 0), "retrieved wrong numba before clear");
		board.clearNumber(0, 0);
		assertEquals(0, board.getNumber(0, 0), "clear failed");
	}
	
	@Test 
	void testIsValid() {
		board.setNumber(1,1,7);
		//Samma rad
		assertFalse(board.isValid(1, 2, 7), "can insert same number on same row");
		assertTrue(board.isValid(1, 2, 8), "can not insert different number on same row");
		//Samma col
		assertFalse(board.isValid(2, 1, 7), "can insert same number on same column");
		assertTrue(board.isValid(2, 1, 8), " can not insert different number on same col");
		//Samma ruta
		assertFalse(board.isValid(0, 0, 7), "can insert same number in same square");
		assertTrue(board.isValid(0, 0, 8), "can not insert different number in same square");
		//Annan ruta + rad + col 
		assertTrue(board.isValid(7,7, 7), "can not insert same number in valid position");
	}
	
	@Test
	void testIsAllValid() {	
		//Testboard 
		int[][] nbrs = {
		  { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
		  { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
		  { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
		  { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
		  { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
		  { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
		  { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
		  { 0, 9, 0, 0, 0, 0, 4, 0, 0 } 
		};
		board.setMatrix(nbrs);
		assertTrue(board.isAllValid(), "full board failed when should have passed");
		
		//Test col
		board.setNumber(0, 1, 8);
		assertFalse(board.isAllValid(), "passed when should have failed, col duplicate");
		board.clearNumber(0, 1);
		
		//Test row
		board.setNumber(1, 0, 8);
		assertFalse(board.isAllValid(), "passed when should have failed, row duplicate");
		board.clearNumber(1, 0);
		
		//Test square
		board.setNumber(1, 1, 8);
		assertFalse(board.isAllValid(), "passed when should have failed, square duplicate");
		board.clearNumber(1, 1);
	}
	
	@Test 
	void testSolverWithSolution() {
		int[][] nbrs = {
		  { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
		  { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
		  { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
		  { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
		  { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
		  { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
		  { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
		  { 0, 9, 0, 0, 0, 0, 4, 0, 0 } 
		};
		
		int[][] solution = {
			{8, 1, 2, 7, 5, 3, 6, 4, 9}, 
			{9, 4, 3, 6, 8, 2, 1, 7, 5}, 
			{6, 7, 5, 4, 9, 1, 2, 8, 3}, 
			{1, 5, 4, 2, 3, 7, 8, 9, 6}, 
			{3, 6, 9, 8, 4, 5, 7, 2, 1}, 
			{2, 8, 7, 1, 6, 9, 5, 3, 4}, 
			{5, 2, 1, 9, 7, 4, 3, 6, 8}, 
			{4, 3, 8, 5, 2, 6, 9, 1, 7}, 
			{7, 9, 6, 3, 1, 8, 4, 5, 2}
		};
		
		board.setMatrix(nbrs);
		assertTrue(board.solve(), "Failed to solve solvable sudoku");
		
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				assertEquals(solution[r][c], board.getMatrix()[r][c], "Not the right solution");
			}
		}
		assertTrue(board.isAllValid(), "is not valid - should be." );
	}
	@Test 
	void testSolverImpossible() {
		int[][] nbrs = {
		  { 8, 3, 2, 7, 0, 0, 0, 0, 0 },
		  { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
		  { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
		  { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
		  { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
		  { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
		  { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
		  { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
		  { 0, 9, 0, 0, 0, 0, 4, 0, 0 } 
		};
		
		board.setMatrix(nbrs);
		assertFalse(board.solve(), "Failed to fail solution");
	}
	
	@Test
	void testSolveEmpty() {
		board.solve();
		printBoard();
	}
	
	@Test
	void testSolveFig1() {
		int[][] nbrs = {
		  { 0, 0, 8, 0, 0, 9, 0, 6, 2 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
		  { 1, 0, 2, 5, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 2, 1, 0, 0, 9, 0 },
		  { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
		  { 6, 0, 0, 0, 0, 0, 0, 2, 8 },
		  { 4, 1, 0, 6, 0, 8, 0, 0, 0 },
		  { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 4, 0, 0 } 
		};
		
		int [][] solution = {
			{5, 4, 8, 1, 7, 9, 3, 6, 2},
			{3, 7, 6, 8, 2, 4, 9, 1, 5},
			{1, 9, 2, 5, 6, 3, 8, 7, 4},
			{7, 8, 4, 2, 1, 6, 5, 9, 3},
			{2, 5, 9, 3, 8, 7, 6, 4, 1},
			{6, 3, 1, 9, 4, 5, 7, 2, 8},
			{4, 1, 5, 6, 9, 8, 2, 3, 7},
			{8, 6, 7, 4, 3, 2, 1, 5, 9},
			{9, 2, 3, 7, 5, 1, 4, 8, 6}
		};
		board.setMatrix(nbrs);
		board.solve();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				assertEquals(solution[r][c], board.getNumber(r,c), "board fig1 solution failed - not same");
			}
		}
	}
	
	@Test
	void testClear() {
		for (int r = 0; r < 9; r++) {
			board.setNumber(r, 0, r+1);
		}
		board.clear();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				assertEquals(0, board.getNumber(r,c), "board clear failed - not empty");
			}
		}
	}
	
	@Test
	void testGetMatrix() {
		int[][] nbrs = {
		  { 0, 0, 8, 0, 0, 9, 0, 6, 2 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
		  { 1, 0, 2, 5, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 2, 1, 0, 0, 9, 0 },
		  { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
		  { 6, 0, 0, 0, 0, 0, 0, 2, 8 },
		  { 4, 1, 0, 6, 0, 8, 0, 0, 0 },
		  { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 4, 0, 0 } 
		};
		
		int [][] solution = {
			{5, 4, 8, 1, 7, 9, 3, 6, 2},
			{3, 7, 6, 8, 2, 4, 9, 1, 5},
			{1, 9, 2, 5, 6, 3, 8, 7, 4},
			{7, 8, 4, 2, 1, 6, 5, 9, 3},
			{2, 5, 9, 3, 8, 7, 6, 4, 1},
			{6, 3, 1, 9, 4, 5, 7, 2, 8},
			{4, 1, 5, 6, 9, 8, 2, 3, 7},
			{8, 6, 7, 4, 3, 2, 1, 5, 9},
			{9, 2, 3, 7, 5, 1, 4, 8, 6}
		};
		
		board.setMatrix(nbrs);
		board.solve();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				assertEquals(solution[r][c], board.getMatrix()[r][c], "getMatrix() did not match solution");
			}
		}
	}
	
	@Test
	void testSetMatrix() {
		int[][] nbrs = {
		  { 0, 0, 8, 0, 0, 9, 0, 6, 2 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
		  { 1, 0, 2, 5, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 2, 1, 0, 0, 9, 0 },
		  { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
		  { 6, 0, 0, 0, 0, 0, 0, 2, 8 },
		  { 4, 1, 0, 6, 0, 8, 0, 0, 0 },
		  { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 4, 0, 0 } 
		};
		
		board.setMatrix(nbrs);
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				assertEquals(nbrs[r][c], board.getMatrix()[r][c], "setMatrix() did not create the correct board");
			}
		}
	}
		
		
	private void printBoard() {
		System.out.println("--------------------------");
		for(int i = 0; i<9; i++) {
			System.out.println(Arrays.toString(board.getMatrix()[i]));
		}
	}
}
