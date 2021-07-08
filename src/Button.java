import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

/*
 * This class is used to make personalized buttons more easily.
 * The class contains two constructors, one for the command button (the ones used to control the game)
 * and the menu buttons with text on it.
 */
@SuppressWarnings("serial")
public class Button extends JButton{
	
	public Button(Color color, String name){
		
		super();
		
		this.setBackground(color);
		this.setPreferredSize(new Dimension(90,40));
		this.setName(name);
		this.addActionListener(Game.getGame());
		this.setFocusable(false);
		
	}
	
	public Button(Color color, String name, String text){
		
		super();
		
		this.setBackground(color);
		this.setPreferredSize(new Dimension(90,40));
		this.setName(name);
		this.setText(text);
		this.addActionListener(Game.getGame());
		this.setFocusable(false);
		
	}

}
