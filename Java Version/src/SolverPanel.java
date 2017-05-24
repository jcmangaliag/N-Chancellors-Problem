import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SolverPanel extends JPanel implements ActionListener {
	private int size = 0;
	private JPanel boardPanel;
	private MenuPanel menu;
	
	private JPanel top;
	
	private JPanel bottom;
	private JButton check;
	
	private JPanel left;
	private JButton back;
	
	private JPanel right;
	private JButton giveUp;
	
	private JButton[][] tiles;
	private int[][] board;
	private int[][] initialBoard;
	
	private ImageIcon chancellor = new ImageIcon("../res/icon.png");
	private ImageIcon one = new ImageIcon("../res/1x.png");
	private ImageIcon two = new ImageIcon("../res/2x.png");
	private ImageIcon three = new ImageIcon("../res/3x.png");
	
	private NChancellorSolver nChancySolver;
	
	public SolverPanel(MenuPanel menu, int boardSize, int[][] initial) {
		this.size = boardSize;
		this.menu = menu;
		board = new int[size][size];
		initialBoard = new int[size][size];
		this.nChancySolver = new NChancellorSolver();
		setLayout(new BorderLayout());
		
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(size,size));
		boardPanel.setBackground(Color.black);
		boardPanel.setPreferredSize(new Dimension(400, 400));
		
		tiles = new JButton[size][size];

		if (initial != null){
			for (int x = 0; x < size; x += 1){	// copy the contents of initial to initialBoard
				System.arraycopy(initial[x], 0, initialBoard[x], 0, initial[x].length);
			}
		}
		
		for (int x = 0; x < size; x += 1) {
			for (int y = 0; y < size; y += 1) {
				tiles[x][y] = new JButton();
				
				Image icon = chancellor.getImage();
//				Image newIcon = icon.getScaledInstance(board[a][b].getWidth(), board[a][b].getHeight(), java.awt.Image.SCALE_SMOOTH);
				
				if (initial != null) {
					if (initial[x][y] == 1) {
						if (size < 4) {
							tiles[x][y].setIcon(new ImageIcon("../res/icon.png"));
						} else if (size < 6) {
							tiles[x][y].setIcon(new ImageIcon("../res/3x.png"));
						} else if (size < 9) {
							tiles[x][y].setIcon(new ImageIcon("../res/2x.png"));
						} else if (size >= 9) {
							tiles[x][y].setIcon(new ImageIcon("../res/1x.png"));
						}
						tiles[x][y].setEnabled(false);
					}
				}
				
				if (x % 2 == 0 && y % 2 != 0 || x % 2 != 0 && y % 2 == 0) {
					tiles[x][y].setBackground(new Color(1, 87, 219));
				} else {
					tiles[x][y].setBackground(Color.WHITE);
				}
				
				tiles[x][y].addActionListener(this);
				boardPanel.add(tiles[x][y]);
			}
		}
		
		top = new JPanel();
		top.setBackground(Color.BLACK);
		top.setPreferredSize(new Dimension(400,50));
		
		bottom = new JPanel();
		bottom.setBackground(Color.BLACK);
		bottom.setPreferredSize(new Dimension(400,100));
		
		check = new JButton();
		check.setIcon(new ImageIcon("../res/check.png"));
		check.setPreferredSize(new Dimension(100,50));
		check.addActionListener(this);
		bottom.add(check);
		
		left = new JPanel();
		left.setBackground(Color.BLACK);
		left.setBorder(new EmptyBorder(0,20,0,20));
		left.setPreferredSize(new Dimension(140,600));
		left.setLayout(new BorderLayout());
		
		back = new JButton();
		back.setIcon(new ImageIcon("../res/back2.png"));
		back.setPreferredSize(new Dimension(50,50));
		back.addActionListener(this);
		
		left.add(back, BorderLayout.SOUTH);
		
		right = new JPanel();
		right.setBackground(Color.BLACK);
		right.setBorder(new EmptyBorder(0,20,0,20));
		right.setPreferredSize(new Dimension(140,600));
		right.setLayout(new BorderLayout());
		
		giveUp = new JButton();
		giveUp.setIcon(new ImageIcon("../res/giveUp.png"));
		giveUp.setPreferredSize(new Dimension(100,50));
		giveUp.addActionListener(this);
		
		right.add(giveUp, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
		
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
	}
	
	public void createBoard(int size) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for (int a = 0; a < size; a += 1) {
			for (int b = 0; b < size; b += 1) {
				if (e.getSource() == tiles[a][b]) {
					if (tiles[a][b].getIcon() == chancellor || tiles[a][b].getIcon() == one || tiles[a][b].getIcon() == two || tiles[a][b].getIcon() == three ) {
						tiles[a][b].setIcon(null);
					} else {
						if (size <= 3) {
							Image icon = chancellor.getImage();
							Image newIcon = icon.getScaledInstance(tiles[a][b].getWidth(), tiles[a][b].getHeight(), java.awt.Image.SCALE_SMOOTH);
							tiles[a][b].setIcon(chancellor);
						} else if (size < 6) {
							Image icon = three.getImage();
							Image newIcon = icon.getScaledInstance(tiles[a][b].getWidth(), tiles[a][b].getHeight(), java.awt.Image.SCALE_SMOOTH);
							tiles[a][b].setIcon(three);
						} else if (size < 9) {
							Image icon = two.getImage();
							Image newIcon = icon.getScaledInstance(tiles[a][b].getWidth(), tiles[a][b].getHeight(), java.awt.Image.SCALE_SMOOTH);
							tiles[a][b].setIcon(two);
						} else if (size >= 9) {
							Image icon = one.getImage();
							Image newIcon = icon.getScaledInstance(tiles[a][b].getWidth(), tiles[a][b].getHeight(), java.awt.Image.SCALE_SMOOTH);
							tiles[a][b].setIcon(one);
						}
					}
				}
			}
		}
		
		if (e.getSource() == check) {
			for (int a = 0; a < size; a += 1) {
				for (int b = 0; b < size; b += 1) {
					if (tiles[a][b].getIcon() != null) {
						board[a][b] = 1;
					} else {
						board[a][b] = 0;
					}
				}
			}

			if (nChancySolver.filledRows(board, size) && nChancySolver.validateInitialBoard(board, size)){
				nChancySolver.solveNChancellors(initialBoard);
				Reader.showSolution();
				System.out.println("solved na! di Bubu");
			} else {
				JOptionPane.showMessageDialog(null, "Not yet solved!");
			}
		   
		}
		
		if (e.getSource() == giveUp) {
			
			nChancySolver.solveNChancellors(initialBoard);
			System.out.println("Di mo kaya? Bubu");
			Reader.showSolution();
		}
		
		if (e.getSource() == back) {
			System.out.println("Quitter ka Bubu");
			Container c = menu.getNChancy().getFrame().getContentPane();
			CardLayout cl = (CardLayout) c.getLayout();
			
			cl.show(c, "menu");
		}
		
	}
}
