import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	
	BufferedImage background = null;
	
	public MenuPanel(NChancy nchancy) {
		setLayout(new BorderLayout());
		fileChooser = new JFileChooser();
		this.nchancy = nchancy;
		
		try {
			background = ImageIO.read(new File("../res/menuBG.png"));
			
		} catch(Exception e) {
			System.out.println("Image failed to load");
		}
		
		boardSizePanel = new JPanel();
//		boardSizePanel.setBackground(new Color(129,212,250));
		boardSizePanel.setPreferredSize(new Dimension(500, 150));
		boardSizePanel.setBorder(new EmptyBorder(100,0,0,0));
		
		boardsizeLabel = new JLabel("Board size (n): ");
		boardsizeLabel.setSize(new Dimension(100,40));
		boardSize = new JTextField(3);
		
		boardSizePanel.add(boardsizeLabel);
		boardSizePanel.add(boardSize);
		
		top = new JPanel();
//		top.setBackground(new Color(129,212,250));
		top.setLayout(new BorderLayout());
		top.setPreferredSize(new Dimension(500,200));
		top.add(boardSizePanel, BorderLayout.SOUTH);
		
		initialBoard = new JPanel();
//		initialBoard.setBackground(new Color(129,212,250));
		initialBoard.setBorder(new EmptyBorder(20,200,200,200));
		initialBoard.setPreferredSize(new Dimension(400,500));
		
		initialBoardLabel = new JLabel("Initial Board: ");
		emptyBoard = new JButton();
		emptyBoard.setIcon(new ImageIcon("../res/empty.png"));
		emptyBoard.setPreferredSize(new Dimension(150,100));
		loadBoard = new JButton();
		loadBoard.setIcon(new ImageIcon("../res/load.png"));
		loadBoard.setPreferredSize(new Dimension(150,100));
		
		emptyBoard.addActionListener(this);
		loadBoard.addActionListener(this);
	
		initialBoard.add(initialBoardLabel);
		initialBoard.add(emptyBoard);
		initialBoard.add(loadBoard);
		
		back = new JPanel();
//		back.setBackground(new Color(129,212,250));
		back.setLayout(new BorderLayout());
		back.setPreferredSize(new Dimension(200,600));
		back.setBorder(new EmptyBorder(0,30,30,30));
		
		backButton = new JButton();
		backButton.setIcon(new ImageIcon("../res/back.png"));
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
//		right.setBackground(new Color(129,212,250));
		right.setPreferredSize(new Dimension(150,600));
		
		top.setOpaque(false);
		initialBoard.setOpaque(false);
		back.setOpaque(false);
		right.setOpaque(false);
		boardSizePanel.setOpaque(false);
		
		add(top, BorderLayout.NORTH);
		add(initialBoard, BorderLayout.CENTER);
		add(back, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
	}

	public NChancy getNChancy() {
		return this.nchancy;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == emptyBoard) {
			
			if (boardSize.getText().equals("") || Integer.parseInt(boardSize.getText()) <= 0) {
				System.out.println("Please enter board size");
				JOptionPane.showMessageDialog(null, "Please enter a valid board size");
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
