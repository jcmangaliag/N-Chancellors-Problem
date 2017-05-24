import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Reader{
	public static String file;
	public static int dimension;
	public static int line_number;
	public static int no_of_solutions;
		
	private static JFrame mainFrame;
	private static GridLayout grid;
	private static BorderLayout border;
	private static JPanel titlePanel;
	private static JPanel configPanel;
	private static JPanel solverPanel;
	private static JPanel subPanel;

	private static JButton leftButton;
	private static JButton rightButton;
	public static JButton[][] button_array;
	
	private static JPanel left;
	private static JPanel right;
	
	public static void showSolution() {
		
		Reader read = new Reader("output.txt");
		int [][] solution = get_solution();
		button_array = new JButton[dimension][dimension];
		
		String title = "SOLVED - " + no_of_solutions + " possible solutions";
		mainFrame = new JFrame(title);
		solverPanel = new JPanel();
		subPanel = new JPanel();
		
		mainFrame.setSize(640,480);
		mainFrame.setResizable(false);
		
		Container c = mainFrame.getContentPane();
		c.setPreferredSize(new Dimension(640,480));
		grid = new GridLayout(dimension,dimension);
		border = new BorderLayout();
	
		subPanel.setLayout(border);
		solverPanel.setLayout(grid);
		
		leftButton = new JButton();
		leftButton.setPreferredSize(new Dimension(70,80));
		leftButton.setIcon(new ImageIcon("../res/left.png"));
		leftButton.setEnabled(false);
		
		rightButton = new JButton();
		rightButton.setPreferredSize(new Dimension(70,80));
		rightButton.setIcon(new ImageIcon("../res/right.png"));
		
		left = new JPanel();
		left.setBackground(Color.BLACK);
		left.setLayout(new BorderLayout());
		left.setBorder(new EmptyBorder(200,0,200,0));
		left.add(leftButton, BorderLayout.CENTER);
		
		right = new JPanel();
		right.setBackground(Color.BLACK);
		right.setLayout(new BorderLayout());
		right.setBorder(new EmptyBorder(200,0,200,0));
		right.add(rightButton, BorderLayout.CENTER);
		
		//leftButton.addActionListener(go_left());
		//rightButton.addActionListener(go_right());
		for(int i=0;i<dimension;i++){
			for(int j=0;j<dimension;j++){
				button_array[i][j] = new JButton();
				if (solution[i][j] == 1) {
					if (dimension < 4) {
						button_array[i][j].setIcon(new ImageIcon("../res/icon.png"));
					} else if (dimension < 6) {
						button_array[i][j].setIcon(new ImageIcon("../res/3x.png"));
					} else if (dimension < 9) {
						button_array[i][j].setIcon(new ImageIcon("../res/2x.png"));
					} else if (dimension >= 9) {
						button_array[i][j].setIcon(new ImageIcon("../res/1x.png"));
					}
				}
				if (i % 2 == 0 && j % 2 != 0 || i % 2 != 0 && j % 2 == 0) {
					button_array[i][j].setBackground(new Color(1, 87, 219));
				} else {
					button_array[i][j].setBackground(Color.WHITE);
				}
				button_array[i][j].setEnabled(false);
				solverPanel.add(button_array[i][j]);
			}
		}
		leftButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					go_left();
			}
		});
		
		rightButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					go_right();
			}
		});
		
		subPanel.add(solverPanel,BorderLayout.CENTER);
		subPanel.add(left,BorderLayout.WEST);
		subPanel.add(right,BorderLayout.EAST);
		//mainFrame.add(solverPanel);
		
		mainFrame.add(subPanel);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
	}
	
	public Reader(String file){
		this.file = file;
		this.dimension = getDimension();
		this.no_of_solutions=get_sol_count();
		this.line_number = 2;
	}
	//returns dimension of the board
	public int getDimension(){
		int dimension = 0;
		String line;
		String regex = "[4]";
		String[] catcher;
		Pattern pattern = Pattern.compile(regex);
		
		try{
			LineNumberReader br = new LineNumberReader(new FileReader(file));
			while((line=br.readLine()) !=null){
				//line=br.readLine();
				if(br.getLineNumber()==1){
					catcher = line.split("=");
					dimension = Integer.valueOf(catcher[1]);
				}
			}
			//no_of_solutions=Integer.valueOf(line);
		}catch(IOException e){
			System.out.println("error");
		}
		return dimension;
	}
	//returns number of solutions
	public static int get_sol_count(){
		String line;
		String[] catcher;
		int sol=0;
		try{
			LineNumberReader br = new LineNumberReader(new FileReader(file));
				
				while((line=br.readLine())!= null){
					if(line.startsWith("solutionsCount=")){
						catcher = line.split("=");
						sol = Integer.valueOf(catcher[1]);
					}	
				}
		}catch(IOException e){}
		
		return sol;
	}
	//returns the solutions
	public static int[][] get_solution(){
		String line;
		String[] row;
		int[][] solution = new int[dimension][dimension];
		//read the file now

			try{
				LineNumberReader br = new LineNumberReader(new FileReader(file));
				
				while(br.getLineNumber()!=line_number){
					br.readLine();
				}
				for(int i=0;i<dimension;i++){
					//read the next n lines
					line = br.readLine();
					row = line.split(" ");
					
					if (row[row.length-1].equals("solutionsCount=0")) {
						JOptionPane.showMessageDialog(null, "No solution");
					} else {
						for(int j=0;j<dimension;j++){
							solution[i][j] = Integer.valueOf(row[j]);
						}
					}
					
				}
				
				return solution;
			}catch(IOException e){}
			return solution;
	}
	public static void go_left(){
		//if(!(line_number == 1)){
		line_number -= (dimension+1);
		if(line_number<3) {
			leftButton.setEnabled(false);
			line_number=2;
		}
		rightButton.setEnabled(true);
		update_board();
		for (int i = 0; i < dimension; i += 1) {
			for (int j = 0; j < dimension; j += 1) {
				if (Integer.parseInt(button_array[i][j].getText()) == 1) {
					if (dimension < 4) {
						button_array[i][j].setIcon(new ImageIcon("../res/icon.png"));
					} else if (dimension < 6) {
						button_array[i][j].setIcon(new ImageIcon("../res/3x.png"));
					} else if (dimension < 9) {
						button_array[i][j].setIcon(new ImageIcon("../res/2x.png"));
					} else if (dimension >= 9) {
						button_array[i][j].setIcon(new ImageIcon("../res/1x.png"));
					}
					button_array[i][j].setText("");
				} else {
					button_array[i][j].setText("");
				}
			}
		}
	}
	//(dimension * solutions)+(solutions+1) = total line numbers in one file
	public static void go_right(){
		int max_lines = (dimension*no_of_solutions)+(no_of_solutions+3);
		if(line_number == max_lines-(dimension+2)){
			line_number=max_lines-(dimension+2);
			rightButton.setEnabled(false);
		} else {
			line_number+= (dimension+1);
		}
		leftButton.setEnabled(true);
		update_board();
		for (int i = 0; i < dimension; i += 1) {
			for (int j = 0; j < dimension; j += 1) {
				if (Integer.parseInt(button_array[i][j].getText()) == 1) {
					if (dimension < 4) {
						button_array[i][j].setIcon(new ImageIcon("../res/icon.png"));
					} else if (dimension < 6) {
						button_array[i][j].setIcon(new ImageIcon("../res/3x.png"));
					} else if (dimension < 9) {
						button_array[i][j].setIcon(new ImageIcon("../res/2x.png"));
					} else if (dimension >= 9) {
						button_array[i][j].setIcon(new ImageIcon("../res/1x.png"));
					}
					button_array[i][j].setText("");
				} else {
					button_array[i][j].setText("");
				}
			}
		}
	}
	
	public static void update_board(){
		for (int i = 0; i < dimension; i += 1) {
			for (int j = 0; j < dimension; j += 1) {
				button_array[i][j].setIcon(null);
			}
		}
		
		String line;
		String[] row;
		try{
			LineNumberReader br = new LineNumberReader(new FileReader(file));
			while(br.getLineNumber()!= line_number){
				br.readLine();
			}
			for(int i=0;i<dimension;i++){
				//read the next n lines
				line = br.readLine();
				row = line.split(" ");
				for(int j=0;j<dimension;j++){
					button_array[i][j].setText(row[j]);
				}
			}
		}catch(IOException e){}
	}
}