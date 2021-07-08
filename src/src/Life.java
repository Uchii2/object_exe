package src;

/*
 * Class of the life bonus, that extends Sprite
 * x and y coordinates are used to define the position of the life
 */
public class Life extends Sprite{
	
	private static int speed;
	
	public Life(int x, int y){
		super(x,y);
		initLife();
	}
	
	public void initLife(){
		loadImage("/" + Frame.getTheme() + "_life.png");
		getImageDimensions();
		loadSoundName(Frame.getTheme() + "_heal.wav");
	}
	
	public void move(){
		
		if(x+width<0)
			vis = false;
		
		x-=speed;
	}

	public static int getSpeed() {
		return speed;
	}

	public static void setSpeed(int speed) {
		Life.speed = speed;
	}

}
