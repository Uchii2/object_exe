package src;

import java.io.Serializable;

/*
 * This class implements Serializable to make a score object that can be saved in a file to keep the best scores
 */
@SuppressWarnings("serial")
public class Score implements Serializable{
	
	private String playerName; //name of the player (Anonymous by default)
	private int score; //score at the end of the game
	
	public Score(String playerName, int score){
		
		this.playerName = playerName;
		this.score = score;
		
	}
	
	/*
	 * This String will be used and shown at the end of the game by the ScoreBoard class
	 */
	public String toString(){
		
		return playerName + " with " + score + " points \n";
	}
	
	public int getScore(){
		return score;
	}
	
	public String getPlayerName(){
		return playerName;
	}

}
