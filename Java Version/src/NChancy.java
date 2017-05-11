import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

public class NChancy {
	private JFrame mainFrame;
	private CardLayout cl;
	private TitlePanel title;
	private MenuPanel menu;
	
	public NChancy() {
		init();
	}
	
	public void init() {
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800,600);
		mainFrame.setResizable(false);
				
		Container c = mainFrame.getContentPane();
		c.setPreferredSize(new Dimension(800, 600));
		
		cl = new CardLayout();
		c.setLayout(cl);
		
		title = new TitlePanel(this);
		menu = new MenuPanel(this);
		
		c.add(title, "title");
		c.add(menu, "menu");
				
		cl.show(c, "title");
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public JFrame getFrame() {
		return this.mainFrame;
	}
	
}