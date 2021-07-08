package src;

/*
 * This is the class for the enemy aliens, which extends the Sprite class.
 * The x and y coordinates define the position of a specific alien on the board
 */
public class Alien extends Sprite{
	
	private static int speed; //speed of the alien, in pixels per frame
	
	public Alien(int x, int y){
		super(x,y);	
		initAlien();
	}
	
	private void initAlien(){
		
		loadImage("/" + Frame.getTheme() + "_alien.png");
		getImageDimensions();
		
		loadSoundName("magicarpe.wav");		
	}
	/*
	 * Increment of the x coordinate of aliens for going from right to left 
	 * when an alien touches the left bound of the screen, it is no more visible
	 */
	public void move(){
		
		if(x+width<0)
			vis = false;
		
		x -= speed ;
	}

	public static int getSpeed() {
		return speed;
	}

	public static void setSpeed(int speed) {
		Alien.speed = speed;
	}

}
