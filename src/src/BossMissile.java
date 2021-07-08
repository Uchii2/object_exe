package src;

/*
 * This class manages the missiles exclusive to the boss
 * The x and y coordinates are used to define the positions
 */
public class BossMissile extends Sprite{
	
	private int speed;

	public BossMissile(int x, int y) {
	    super(x,y);
	    initMissile();
	}
	    
	private void initMissile() {
	    
		speed = 3;
	    loadImage("/" + Frame.getTheme() + "_bossmissile.png");  
	    getImageDimensions();
	}

	/*
	 * Increment of the coordinate x of the missile by 3 (speed)
	 * When the missile reaches the left bound of the screen, it is no more visible 
	 */
	 public void move() {
	        
	    x -= speed;
	        
	    if (x < 0) {
	            vis = false;
	    }
	}

}
