import java.awt.BorderLayout;

import javax.swing.JPanel;

/*
 * This is the main JPanel.
 * It contains two sup-panel : one for the board itself (colored squares)
 * and one for the commands (the colored buttons)
 */
@SuppressWarnings("serial")
public class Board extends JPanel{
	
	public Board(){
		
		super(new BorderLayout());
				
		GameBoard gameboard = new GameBoard();
		this.add(gameboard, BorderLayout.CENTER);
		
		CommandBoard commandboard = new CommandBoard();
		this.add(commandboard, BorderLayout.SOUTH);
		
	}

}
