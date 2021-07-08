import java.awt.Color;

import javax.swing.JPanel;

/*
 * The CommandBoard class extends JPanel to make the panel that contains the buttons for the game.
 * The buttons come from the class Button.
 */
@SuppressWarnings("serial")
public class CommandBoard extends JPanel{
	
	
	public CommandBoard(){
		
		super();
		
		this.setBackground(Color.white);
		
		this.add(new Button(Mycolors.blue.getColor(), "blue"));
		this.add(new Button(Mycolors.green.getColor(), "green"));
		this.add(new Button(Mycolors.red.getColor(), "red"));
		this.add(new Button(Mycolors.purple.getColor(), "purple"));
		this.add(new Button(Mycolors.yellow.getColor(), "yellow"));
		this.add(new Button(Mycolors.orange.getColor(), "orange"));
		
		
	}

}
