import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

/*
 * The Game class is the core of the game, it manages the actions for every button,
 * the upgrade of the board, the panel change, etc
 */
public class Game implements ActionListener{

	private static Game game;
	
	private Frame frame;
	private Pixels pixels;
	
	private int count; //defines the number of turns left or the number of the player who will play
	private String difficulty;
	private int nbofplayers; 
	private String name; //name is used to get the name of a button 
	
	public Game(){
		
		Game.game = this;
		
		frame = new Frame();
		frame.setVisible(true);

		difficulty = "easy"; //default value 
		nbofplayers = 1;
	}
	
	/*
	 * After every color change, this method is used to verify if the game is finished or not
	 * There is two cases for the game to finish :
	 * either the board is in one color (pixels.isWinning())
	 * or, in single player, the count is equal to 0 (pixels.getCount() <= 0 && nbofplayers == 1)
	 */
	public void checkWin(){
		
		if((pixels.getCount() <= 0 && nbofplayers == 1) || pixels.isWinning())
			frame.gameOver();
		
	}
	
	/*
	 * The management of the button is divided in two functions : one for the menu button
	 * and one for the command color buttons. 
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		
		String bname = (((AbstractButton)e.getSource()).getName());
		name = bname;
		
		if(bname == "blue" || bname == "green" || bname == "red" || bname == "orange" || bname == "purple" || bname == "yellow")
			commandbutton();
		
		else
			menubutton();
	}
	
	public void menubutton(){
		
		//set the difficulty of the game
		if(name == "easy" || name == "medium" || name == "hard")
			difficulty = name;
		
		//set the number of players then launch the game with the difficulty previously selected
		if(name == "oneplayer"){
			nbofplayers = 1;
			setLevel(difficulty);
		}
		
		if(name == "twoplayers"){
			nbofplayers = 2;
			setLevel(difficulty);
		}
		
		if(name == "threeplayers"){
			nbofplayers = 3;
			setLevel(difficulty);
		}
		
		if(name == "fourplayers"){
			nbofplayers = 4;
			setLevel(difficulty);
		}
		
		//launch a new game, reset the count in single player mode
		if(name == "tryagain"){
			if(nbofplayers == 1)
				pixels.setCount(count);
			pixels.initColors();
			frame.newGame();
		}
		
		//in multiplayer, the first player is randomly selected
		if(nbofplayers != 1){
			int rand = (int)(Math.random()*nbofplayers)+1;
			pixels.setCount(rand);
		}
		
		if(name == "menu"){
			frame.menu();
		}
		
		if(name == "exit")
			//TODO
			//voir la différence entre ces deux
			System.exit(0);//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		
	}
	
	public void commandbutton(){
		

		Color newcolor;
		
		switch(name){
		case "blue":
			newcolor = Mycolors.blue.getColor();
			break;
		case "green":
			newcolor = Mycolors.green.getColor();
			break;
		case "red":
			newcolor = Mycolors.red.getColor();
			break;
		case "orange":
			newcolor = Mycolors.orange.getColor();
			break;
		case "purple":
			newcolor = Mycolors.purple.getColor();
			break;
		case "yellow":
			newcolor = Mycolors.yellow.getColor();
			break;
		default:
			newcolor = Mycolors.blue.getColor();
			break;
		}
		
		Color oldcolor;
		
		//in single player mode
		if(nbofplayers == 1){
			//oldcolor is the color of the top-left square
			oldcolor = pixels.getColors()[0][0];
			if(oldcolor == newcolor)
				return;
			pixels.checkAdj(newcolor, oldcolor, 0, 0); //change the color of the consecutive squares
			pixels.setColor(newcolor, 0, 0); // change the color of the first square
			pixels.setCount(pixels.getCount()-1); //the count is decremented
		}
		
		//in multiplayer mode
		if(nbofplayers == 2){
			if(pixels.getCount() == 1){
				oldcolor = pixels.getColors()[0][0];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, 0, 0);
				pixels.setColor(newcolor, 0, 0);
				pixels.setCount(pixels.getCount()+1);
			}
			else{
				//for the second player, the oldcolor is the color of the bottom-right square
				oldcolor = pixels.getColors()[pixels.getHeight()-1][pixels.getWidth()-1];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, pixels.getHeight()-1, pixels.getWidth()-1);
				pixels.setColor(newcolor, pixels.getHeight()-1, pixels.getWidth()-1);
				pixels.setCount(pixels.getCount()-1); //the count is used to show which player turn it is
			}
		}
		
		if(nbofplayers == 3){
			if(pixels.getCount() == 1){
				oldcolor = pixels.getColors()[0][0];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, 0, 0);
				pixels.setColor(newcolor, 0, 0);
				pixels.setCount(pixels.getCount()+1);
			}
			else if(pixels.getCount() == 2){
				oldcolor = pixels.getColors()[pixels.getHeight()-1][pixels.getWidth()-1];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, pixels.getHeight()-1, pixels.getWidth()-1);
				pixels.setColor(newcolor, pixels.getHeight()-1, pixels.getWidth()-1);
				pixels.setCount(pixels.getCount()+1);
			}
			else{
				oldcolor = pixels.getColors()[pixels.getHeight()-1][0];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, pixels.getHeight()-1, 0);
				pixels.setColor(newcolor, pixels.getHeight()-1, 0);
				pixels.setCount(1);
			}
		}
		
		if(nbofplayers == 4){
			if(pixels.getCount() == 1){
				oldcolor = pixels.getColors()[0][0];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, 0, 0);
				pixels.setColor(newcolor, 0, 0);
				pixels.setCount(pixels.getCount()+1);
			}
			else if(pixels.getCount() == 2){
				oldcolor = pixels.getColors()[pixels.getHeight()-1][pixels.getWidth()-1];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, pixels.getHeight()-1, pixels.getWidth()-1);
				pixels.setColor(newcolor, pixels.getHeight()-1, pixels.getWidth()-1);
				pixels.setCount(pixels.getCount()+1);
			}
			else if(pixels.getCount() == 3){
				oldcolor = pixels.getColors()[pixels.getHeight()-1][0];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, pixels.getHeight()-1, 0);
				pixels.setColor(newcolor, pixels.getHeight()-1, 0);
				pixels.setCount(pixels.getCount()+1);
			}
			else{
				oldcolor = pixels.getColors()[0][pixels.getWidth()-1];
				if(oldcolor == newcolor)
					return;
				pixels.checkAdj(newcolor, oldcolor, 0, pixels.getWidth()-1);
				pixels.setColor(newcolor, 0, pixels.getWidth()-1);
				pixels.setCount(1);
			}
		}
		
		frame.validate();
		frame.repaint();
		
		
		
		checkWin();
		
	}
	
	/*
	 * The three level of difficulty are differentiate by :
	 * the number of squares, their size and the count
	 */
	public void setLevel(String difficulty){
		
		if(difficulty == "easy"){
			pixels = new Pixels(10,6,70,16);
			count = 16;
		}
		else if(difficulty == "medium"){
			pixels = new Pixels(14,9,50,21);
			count = 21;
		}
		else if(difficulty == "hard"){
			pixels = new Pixels(25,16,30,33);
			count = 33;
		}
		
		frame.newGame();
		
	}
	
	static public Game getGame(){
		return Game.game;
	}
	
	public Pixels getPixels(){
		return pixels;
	}
	
	public int getCount(){
		return count;
	}
	
	public int getNbofplayers(){
		return nbofplayers;
	}
	
}
