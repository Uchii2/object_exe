import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * This class is used to create the JPanel for the game itself.
 * It uses the paintComponent function to paint : the colored squares and some text.
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel{
	
	/*
	 * only function of the class.
	 * Uses the main methods given by java.awt.Graphics
	 * two different strings are drawn according to the number of players.
	 * To draw the squares, the function get from the Game class :
	 * the size of the squares, the number of rows and columns and the color of each square.
	 */
	public void paintComponent(Graphics g){
		
		g.setFont(new Font("Tahoma", Font.BOLD, 15));
		g.setColor(Color.GRAY);
		if(Game.getGame().getNbofplayers() == 1)
			g.drawString("Action left : " + Game.getGame().getPixels().getCount(), 15, 20);
		else
			g.drawString("Player " + Game.getGame().getPixels().getCount() + " turn", 15, 20);

				
		int i=0;
		int j=0;
		
		int size = Game.getGame().getPixels().getSize();
		
		for(i=0;i<Game.getGame().getPixels().getHeight();i++){
			for(j=0;j<Game.getGame().getPixels().getWidth();j++){
				g.setColor(Game.getGame().getPixels().getColors()[i][j]); // the color for a specific square is taken from the Array pixels[][] in the Pixels class
				g.fillRect(size*(i+1), size*(j+1), size, size);
			}
		}
		
		g.setColor(new Color(0,0,0,50));
		g.setFont(new Font("SansSerif", Font.BOLD, 20));

		/*
		 * used to draw the number of the player on the square he will play from
		 * changes according to the number of players and not used for the single player mode.
		 * the position of every number is calculated to be in the center of the square, without considering the size and number of squares
		 */
		if(Game.getGame().getNbofplayers() == 4){
			g.drawString("4", size + size/2 -5, Game.getGame().getPixels().getWidth()*size + size/2 +10);
			g.drawString("3", Game.getGame().getPixels().getHeight()*size + size/2 -5, size+size/2 +10);
			g.drawString("2", Game.getGame().getPixels().getHeight()*size + size/2 -5, Game.getGame().getPixels().getWidth()*size + size/2 +10);
			g.drawString("1", size + size/2 -5 , size + size/2 +10);
		}

		if(Game.getGame().getNbofplayers() == 3){
			g.drawString("3", Game.getGame().getPixels().getHeight()*size + size/2 -5, size+size/2 +10);
			g.drawString("2", Game.getGame().getPixels().getHeight()*size + size/2 -5, Game.getGame().getPixels().getWidth()*size + size/2 +10);
			g.drawString("1", size + size/2 -5 , size + size/2 +10);
		}
		
		if(Game.getGame().getNbofplayers() == 2){
			g.drawString("2", Game.getGame().getPixels().getHeight()*size + size/2 -5, Game.getGame().getPixels().getWidth()*size + size/2 +10);
			g.drawString("1", size + size/2 -5 , size + size/2 +10);
		}
		
	}

}
