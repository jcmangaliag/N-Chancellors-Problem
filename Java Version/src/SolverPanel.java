import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	
	private ImageIcon chancellor = new ImageIcon("res/icon.png");
	private NChancellorSolver NChancySolver;
	
	public SolverPanel(MenuPanel menu, int boardSize, int[][] initial) {
		this.size = boardSize;
		this.menu = menu;
		board = new int[size][size];
		initialBoard = new int[size][size];
		this.NChancySolver = new NChancellorSolver();
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
						tiles[x][y].setIcon(chancellor);
						tiles[x][y].setEnabled(false);
					}
				}
				
				if (x % 2 == 0 && y % 2 != 0 || x % 2 != 0 && y % 2 == 0) {
					tiles[x][y].setBackground(Color.BLACK);
				} else {
					tiles[x][y].setBackground(Color.WHITE);
				}
				
				tiles[x][y].addActionListener(this);
				boardPanel.add(tiles[x][y]);
			}
		}
		
		top = new JPanel();
		top.setPreferredSize(new Dimension(400,50));
		
		bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(400,100));
		
		check = new JButton("CHECK");
		check.setPreferredSize(new Dimension(100,50));
		check.addActionListener(this);
		bottom.add(check);
		
		left = new JPanel();
		left.setBorder(new EmptyBorder(0,20,0,20));
		left.setPreferredSize(new Dimension(140,600));
		left.setLayout(new BorderLayout());
		
		back = new JButton("BACK");
		back.setPreferredSize(new Dimension(50,50));
		back.addActionListener(this);
		
		left.add(back, BorderLayout.SOUTH);
		
		right = new JPanel();
		right.setBorder(new EmptyBorder(0,20,0,20));
		right.setPreferredSize(new Dimension(140,600));
		right.setLayout(new BorderLayout());
		
		giveUp = new JButton("GIVE UP");
		giveUp.setPreferredSize(new Dimension(50,50));
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
					if (tiles[a][b].getIcon() == chancellor) {
						tiles[a][b].setIcon(null);
					} else {
						Image icon = chancellor.getImage();
						Image newIcon = icon.getScaledInstance(tiles[a][b].getWidth(), tiles[a][b].getHeight(), java.awt.Image.SCALE_SMOOTH);
						tiles[a][b].setIcon(chancellor);
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
			// if the board is solved
				Reader.showSolution();
		   // else
// 				TODO: show prompt "Not yet solved! Bubu"
		}
		
		if (e.getSource() == giveUp) {
			
			NChancySolver.solveNChancellors(initialBoard);
			Reader.showSolution();
			for (int a = 0; a < size; a += 1) {
				for (int b = 0; b < size; b += 1) {
					System.out.print(board[a][b] + " ");
				}
				System.out.println("\n");
			}
		}
		
		if (e.getSource() == back) {
			Container c = menu.getNChancy().getFrame().getContentPane();
			CardLayout cl = (CardLayout) c.getLayout();
			
			cl.show(c, "menu");
		}
		
	}
}
