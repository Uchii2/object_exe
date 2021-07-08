package src;

/*
 * Class of the bonuses that extends Sprite 
 * There is three kind of bonuses with different effects.
 */
public class Bonus extends Sprite{
	
	private static int speed; //speed of the bonus 
	private int type; //defines the type of the bonus among 3 different kinds
	
	/*
	 *  x and y are the coordinates of the bonus and i is its type
	 */
	public Bonus(int x, int y, int i){
		
		super(x,y);
		initBonus(i);
		
	}
	
	public void initBonus(int bonustype){
		
		String imagename = null;
		
		switch(bonustype)
		{
		case 1:
			imagename = "/" + Frame.getTheme() + "_bonus3.png";
			break;
		case 2:
			imagename = "/" + Frame.getTheme() + "_bonus2.png";
			break;
		case 3:
			imagename = "/" + Frame.getTheme() + "_bonus1.png";
			break;
		default:
			System.out.print("Bonus not found");
		}
		
		loadImage(imagename);
		getImageDimensions();
		
		type = bonustype;
		
	}
	
	/*
	 * Increment of the x coordinate of a bonus
	 */
	public void move(){
		
		if(x+width<0)
			vis = false;
		
		x-=speed;
		
	}
	
	public static int getSpeed() {
		return speed;
	}

	public static void setSpeed(int speed) {
		Bonus.speed = speed;
	}
	
	public int getBonusType(){
		return this.type;
	}
}
