import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton implements ActionListener {
	private int x;
	private int y;
	
	private boolean clicked;
	
	public Button(int x, int y) {
		this.x = x;
		this.y = y;
		this.clicked = false;
		
		if (x % 2 == 0 && y % 2 != 0 || x % 2 != 0 && y % 2 == 0) {
			this.setBackground(Color.BLACK);
		} else {
			this.setBackground(Color.WHITE);
		}
	}
	
	public void setClicked() {
		this.clicked = true;
	}
	
	public boolean isClicked() {
		return this.clicked;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
