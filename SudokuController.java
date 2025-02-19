import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SudokuController {
    public SudokuController() {
        SwingUtilities.invokeLater(() -> createWindow(new SudokuSolverClass(), "Sudoku", 800, 600));
    }
    
    public void createWindow(SudokuSolverClass solver, String title, int width, int height) {
    	JFrame frame = new JFrame(title);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	Container pane = frame.getContentPane();
    	
    	
    	//Boxes 
    	JPanel boxgrid = new JPanel();
    	boxgrid.setLayout(new GridLayout(solver.getDimension(), solver.getDimension(), 0, 0));
    	boxgrid.setPreferredSize(new Dimension(width, height));
    	
    	
    	JTextField[][] textFields = new JTextField[solver.getDimension()][solver.getDimension()]; 
		List<Integer> edges = new ArrayList<>(Arrays.asList(0, 1, 2, 6, 7, 8));
		List<Integer> middle = new ArrayList<>(Arrays.asList(3,4,5));

    	for (int r = 0; r < solver.getDimension(); r++) {
    		for(int c = 0; c < solver.getDimension(); c++) {
    			JTextField tf = new JTextField(12); 
				tf.setPreferredSize(new Dimension(40, 20));
				tf.setHorizontalAlignment(JTextField.CENTER);
				tf.setFont(new Font("ARIAL", Font.PLAIN, 30));
				
				if (edges.contains(r) && edges.contains(c)) tf.setBackground(new Color(51,123,255));
				if (middle.contains(r) && middle.contains(c)) tf.setBackground(Color.ORANGE);
				if (!edges.contains(r) && edges.contains(c)) tf.setBackground(Color.ORANGE);
				if (edges.contains(r) && !edges.contains(c)) tf.setBackground(Color.ORANGE);
				
				textFields[r][c] = tf;
				boxgrid.add(tf);
    		}
    	}
    	
    	//Buttons
		JButton solveButton = new JButton("Solve");
		JButton clearButton = new JButton("Clear");
		JPanel buttons = new JPanel();
		buttons.add(solveButton);
		buttons.add(clearButton);
    	
		//Add actionlistners to solveButton 
		solveButton.addActionListener(e -> { 
			for (int r = 0; r < solver.getDimension(); r++) {
	    		for(int c = 0; c < solver.getDimension(); c++) {
	    			String text = textFields[r][c].getText();
					if (text.isEmpty()) {
						solver.setNumber(r, c, 0);
					}
					else if(text.length() == 1 && text.equals("1") | text.equals("2") | text.equals("3") | text.equals("4")
							| text.equals("5")| text.equals("6") | text.equals("7") | text.equals("8") | text.equals("9")) {
						solver.setNumber(r, c, Integer.parseInt(text));
					}
					else {
						JOptionPane.showMessageDialog(frame, "YOU PUT THE WROONG NUMBA(s) \n Only numbers 1 - 9 allowed!");
						return; 
					}
	    		}
			}
			if(solver.isAllValid()) {
				if(solver.solve()) {
					for (int r = 0; r < solver.getDimension(); r++) {
			    		for(int c = 0; c < solver.getDimension(); c++) {
			    			textFields[r][c].setText(String.valueOf(solver.getNumber(r,c)));
			    		}
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "This sudoku is not solveable. Try again with another one.");
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Not a valid Sudoku. Try again.");

			}

		});
		//Add actionlistners to clearButton
		clearButton.addActionListener(e -> { 
			solver.clear();
			for (int r = 0; r < solver.getDimension(); r++) {
	    		for(int c = 0; c < solver.getDimension(); c++) {
	    			textFields[r][c].setText(null);
	    		}
			}
		});
		
		//Draw
		JPanel superPanel = new JPanel();
		superPanel.setLayout(new BoxLayout(superPanel, BoxLayout.PAGE_AXIS));
		superPanel.add(boxgrid);
		superPanel.add(buttons);
		pane.add(superPanel);
    	frame.pack();
        frame.setVisible(true);
    }
    
}
