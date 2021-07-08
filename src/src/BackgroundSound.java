package src;

/*
 * This class manages the background sound of the game
 * There is 4 different musics : one for the menu, one for the normal game, one for the boss and the game over sound.
 */
public class BackgroundSound {
	
	private Sound sound; //sound object that will contain the different music clip depending of the state of the game
	
	/*
	 * The state parameter is used to define which music has to be played
	 */
	public void play(String state){
		
		if(sound != null)
			sound.stop(); //stop a music before launching another one
		
		if(state == "game"){
			sound = new Sound(Frame.getTheme() + "_road.wav");
			sound.loop();
		}
		else if(state == "gameover"){
			sound = new Sound(Frame.getTheme() + "_gameover.wav");
		}
		else if(state == "boss"){
			sound = new Sound(Frame.getTheme() + "_boss.wav");
		}
		else{
			sound = new Sound("on.wav");
		}
	}
	
		
}
