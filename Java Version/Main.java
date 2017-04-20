import java.awt.*;
import javax.swing.*;

public class Main {
	private static JFrame mainFrame;
	private static CardLayout cl;
	private static JPanel titlePanel;
	private static JPanel configPanel;
	private static JPanel solverPanel;

	private static JButton startButton;
	private static JButton quitButton;


	public static void main(String[] args) {
		mainFrame = new JFrame("N-Chancellors Problem");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(640,480);
		mainFrame.setResizable(false);

		Container c = mainFrame.getContentPane();
		c.setPreferredSize(new Dimension(640,480));
		
		cl = new CardLayout();
		c.setLayout(cl);

		titlePanel = new JPanel();
		startButton = new JButton("START");
		quitButton = new JButton("QUIT");
		titlePanel.add(startButton);
		titlePanel.add(quitButton);

		titlePanel.setLayout(new FlowLayout());
		configPanel = new JPanel();
		solverPanel = new JPanel();

		c.add(titlePanel, "title");
		c.add(configPanel, "config");
		c.add(solverPanel, "solver");

		cl.show(c,"title");

		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}