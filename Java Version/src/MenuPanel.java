import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {

	final private JFileChooser fileChooser;
	private JPanel boardSizePanel;
	private JLabel boardsizeLabel;
	private JTextField boardSize;
	
	private JPanel initialBoard;
	private JLabel initialBoardLabel;
	private JButton emptyBoard;
	private JButton loadBoard;
	
	private JPanel back;
	private JButton backButton;
	
	private JPanel start;
	private JButton startButton;
	
	private JPanel top;
	private JPanel bottom;
	private JPanel right;
	private JPanel left;
	
	private NChancy nchancy;
	
	public MenuPanel(NChancy nchancy) {
		setLayout(new BorderLayout());
		fileChooser = new JFileChooser();
		this.nchancy = nchancy;
		
		boardSizePanel = new JPanel();
		boardSizePanel.setPreferredSize(new Dimension(500, 150));
		boardSizePanel.setBorder(new EmptyBorder(100,0,0,0));
		
		boardsizeLabel = new JLabel("Board size (n): ");
		boardsizeLabel.setSize(new Dimension(100,40));
		boardSize = new JTextField(3);
		
		boardSizePanel.add(boardsizeLabel);
		boardSizePanel.add(boardSize);
		
		top = new JPanel();
		top.setLayout(new BorderLayout());
		top.setPreferredSize(new Dimension(500,200));
		top.add(boardSizePanel, BorderLayout.SOUTH);
		
		initialBoard = new JPanel();
		initialBoard.setBorder(new EmptyBorder(20,200,200,200));
		initialBoard.setPreferredSize(new Dimension(400,500));
		
		initialBoardLabel = new JLabel("Initial Board: ");
		emptyBoard = new JButton("CREATE");
		emptyBoard.setPreferredSize(new Dimension(150,100));
		loadBoard = new JButton("LOAD");
		loadBoard.setPreferredSize(new Dimension(150,100));
		
		emptyBoard.addActionListener(this);
		loadBoard.addActionListener(this);
	
		initialBoard.add(initialBoardLabel);
		initialBoard.add(emptyBoard);
		initialBoard.add(loadBoard);
		
		back = new JPanel();
		back.setLayout(new BorderLayout());
		back.setPreferredSize(new Dimension(200,600));
		back.setBorder(new EmptyBorder(0,30,30,30));
		
		backButton = new JButton("BACK");
		backButton.setPreferredSize(new Dimension(150,40));
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Container c = nchancy.getFrame().getContentPane();
				CardLayout cl = (CardLayout) c.getLayout();
				cl.show(c, "title");
				
			}
			
		});
		back.add(backButton, BorderLayout.SOUTH);
		right = new JPanel();
		right.setPreferredSize(new Dimension(150,600));
		
		add(top, BorderLayout.NORTH);
		add(initialBoard, BorderLayout.CENTER);
		add(back, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
	}

	public NChancy getNChancy() {
		return this.nchancy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == emptyBoard) {
			
			if (boardSize.getText().equals("")) {
				System.out.println("Please enter board size");
			} else {
				String size = this.boardSize.getText();
				int boardsize = Integer.parseInt(size);
				Container c = nchancy.getFrame().getContentPane();
				CardLayout cl = (CardLayout) c.getLayout();
				
				SolverPanel solver = new SolverPanel(this, boardsize, null);
				c.add(solver, "solver");
				cl.show(c, "solver");
			}
			
		}
		
		if (e.getSource() == loadBoard) {
			int open = fileChooser.showOpenDialog(MenuPanel.this);
			
			if (open == JFileChooser.APPROVE_OPTION) {
				System.out.println("File chooser opened");
				File file = fileChooser.getSelectedFile();
				FileReader fileReader = null;
				try {
					fileReader = new FileReader(file);
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				BufferedReader reader = new BufferedReader(fileReader);
				StringBuffer buffer = new StringBuffer();
				
				String line;
				String[] numbers;
				int[][] initial;
				int count = 0;
				int lineCount = 0;
				try {
					while ((line = reader.readLine()) != null) {
						if (lineCount == 0) {
							count = Integer.parseInt(line);
							break;
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				initial = new int[count][count];
				for (int a = 0; a < count; a += 1) {
					for (int b = 0; b < count; b += 1) {
						initial[a][b] = 0;
					}
				}
				lineCount = 1;
				try {
					while ((line = reader.readLine()) != null) {
						numbers = line.split(" ");
						for (int n = 0; n < numbers.length; n += 1) {
							initial[lineCount-1][n] = Integer.parseInt(numbers[n]);
						}	
						lineCount += 1;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				try {
					fileReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Container c = nchancy.getFrame().getContentPane();
				CardLayout cl = (CardLayout) c.getLayout();
				SolverPanel solver = new SolverPanel(this, count, initial);
				c.add(solver, "solver");
				cl.show(c, "solver");
			} else {
				System.out.println("File chooser closed");
			}
		}
	}
}
