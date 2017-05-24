import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class TitlePanel extends JPanel {
	
	private JPanel title;
	private JPanel buttonPanel; 
	private JButton start;
	private JButton quit;
	
	private JPanel left;
	private JPanel right;
	private JPanel top;
	private JPanel bottom;
	
	private static BufferedImage background = null;
	
	private ImageIcon startBtn = new ImageIcon("../res/start.png");
	private ImageIcon quitBtn = new ImageIcon("../res/quit.png");
	
	public TitlePanel(NChancy nchancy) {
		setLayout(new BorderLayout());
		
		try {
			background = ImageIO.read(new File("../res/mainBG.png"));
			
		} catch(Exception e) {
			System.out.println("Image failed to load");
		}
		
		
		title = new JPanel();
		title.setPreferredSize(new Dimension(500,300));
		
		top = new JPanel();
		top.setPreferredSize(new Dimension(500,100));
		left = new JPanel();
		left.setPreferredSize(new Dimension(150,600));
		right = new JPanel();
		right.setPreferredSize(new Dimension(150,600));
		bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(500,240));
		
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(500,250));
		buttonPanel.setBorder(new EmptyBorder(10,0,0,0));

		start = new JButton();
		start.setPreferredSize(new Dimension(150,50));
		start.setOpaque(true);
		start.setIcon(startBtn);
		
		title.setOpaque(false);
		top.setOpaque(false);
		left.setOpaque(false);
		right.setOpaque(false);
		bottom.setOpaque(false);
		buttonPanel.setOpaque(false);
		
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Container c = nchancy.getFrame().getContentPane();
				CardLayout cl = (CardLayout) c.getLayout();
				cl.show(c, "menu");
			}
			
		});
		
		quit = new JButton();
		quit.setPreferredSize(new Dimension(150,50));
		quit.setIcon(quitBtn);
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = nchancy.getFrame();
				frame.dispose();
			}
			
		});
		
		buttonPanel.add(start);
		buttonPanel.add(quit);
		
		add(top, BorderLayout.NORTH);
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
		add(bottom, BorderLayout.SOUTH);
		add(title, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}

}
