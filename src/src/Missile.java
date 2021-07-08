package src;

/*
 * This class manages the missiles exclusive to the craft
 * The x and y coordinates are used to define the positions 
 */
public class Missile extends Sprite {

    private int B_WIDTH;//width of the board
    private int speed;

    public Missile(int x, int y) {
        super(x,y);
        initMissile();
    }
    
    /*
     * There is two different skins for the missile following the kind of missile the craft has
     * If the craft.getShoot is equals to 20, the skin is different to clearly show the differences between missiles
     */
    private void initMissile() {
    	
    	B_WIDTH = 480;
    	speed = 3;
        
    	if(Craft.getCraft().getShoot() == 20)
        	loadImage("/" + Frame.getTheme() + "_missile2.png");
        else
        	loadImage("/" + Frame.getTheme() + "_missile.png");
        
        getImageDimensions();
    }

/*
 * Increment of the coordinate x of the missile by 3 (speed)
 * When the missile reaches the right bound of the screen, it is no more visible 
 */
    public void move() {
        
        x += speed;
        
        if (x > B_WIDTH) {
            vis = false;
        }
    }
}