import java.awt.*;
import javax.swing.*;

public class Main {
	private static JFrame mainFrame;
	private static CardLayout cl;
	private static JPanel titlePanel;

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
		titlePanel.setLayout(new FlowLayout());

		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}