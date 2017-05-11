import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
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
	public static void main(String[] args){
		
		Reader read = new Reader("output.txt");
		int [][] solution = get_solution();
		button_array = new JButton[dimension][dimension];
		
		mainFrame = new JFrame("N-Chancellors Problem");
		solverPanel = new JPanel();
		subPanel = new JPanel();
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(640,480);
		mainFrame.setResizable(false);
		
		Container c = mainFrame.getContentPane();
		c.setPreferredSize(new Dimension(640,480));
		grid = new GridLayout(4,4);
		border = new BorderLayout();
	
		subPanel.setLayout(border);
		solverPanel.setLayout(grid);
	
		leftButton = new JButton("Left");
		rightButton = new JButton("Right");
		
		//leftButton.addActionListener(go_left());
		//rightButton.addActionListener(go_right());
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				button_array[i][j] = new JButton(String.valueOf(solution[i][j]));
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
		subPanel.add(leftButton,BorderLayout.WEST);
		subPanel.add(rightButton,BorderLayout.EAST);
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
		int[][] solution = new int[4][4];
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
					for(int j=0;j<dimension;j++){
						solution[i][j] = Integer.valueOf(row[j]);
					}
				}
				
				return solution;
			}catch(IOException e){}
			return solution;
	}
	public static void go_left(){
		//if(!(line_number == 1)){
		line_number -= (dimension+1);
		if(line_number<3)
			line_number=2;
		
		update_board();
	}
	//(dimension * solutions)+(solutions+1) = total line numbers in one file
	public static void go_right(){
		int max_lines = (dimension*no_of_solutions)+(no_of_solutions+3);
		if(line_number==max_lines-(dimension+2)){
			line_number=max_lines-(dimension+2);
		}else
			line_number+= (dimension+1);
		update_board();
	}
	
	public static void update_board(){
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