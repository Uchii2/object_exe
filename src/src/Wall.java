package src;

/*
 * This is the class for the walls, which extends the Sprite class.
 * The x and y coordinates define the position of a specific wall on the board
 */
public class Wall extends Sprite{
	
	public Wall(int x, int y){
		super(x,y);
		initWall();
	}
	
	public void initWall(){
		
		loadImage("/" + Frame.getTheme() + "_wall.png");
		getImageDimensions();
		loadSoundName("explosion.wav");
		
	}
	
	/*
	 * Increment of the x position of the wall
	 */
	public void move(){
		
		if(x+width<0)
			vis = false;
			
		x-=2;
		
	}

}
