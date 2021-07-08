package src;

import java.util.ArrayList;
import java.util.Random;

/*
 * Class that manages the enemy boss
 * The boss moves randomly along the x axis and slowly move forward
 * It has its own type of missile
 * The boss shoot one missile when moving up or left
 */
public class Boss extends Sprite{
	
	private int life = 20;
	private ArrayList<BossMissile> missiles; //array that contains all the bossmissile visible
	
	private boolean shooted = false; //boolean to state if the boss has already shot
	
	private final int B_HEIGHT = 340; //height of the board
	
	/*
	 * x and y are the coordinates used for the position of the boss
	 */
	public Boss(int x, int y){
		
		super(x,y);
		initBoss();	
		
	}
	
	public void initBoss(){
		
		loadImage("/" + Frame.getTheme() + "_boss.png");
		this.getImageDimensions();
		missiles = new ArrayList<>();
		
	}
	
	/*
	 * There are two random numbers, one for the y position (rand) and one to determine if it will move or not (rand2)
	 * The x increment is always equal to 5 pixels
	 * There is 5 levels on the y+axis where the boss can move
	 * In addition, the level is defined with the rand2 integer
	 */
	public void move(){

		Random rand = new Random();
		Random rand2 = new Random();
		
		int posY = rand.nextInt(B_HEIGHT);
		
		if(rand2.nextInt(1000) >= 990){
			x-=5;
			shooted = false;
		}
		
		if(rand2.nextInt(1000) >= 980){
			if(posY < 76 && y == 81) y = 20;
			else if(posY < 132 && (y == 20 || y == 142)) y = 81;
			else if(posY < 188 && (y == 81 || y == 203)) y = 142;
			else if(posY < 244 && (y == 142 || y == 264)) y = 203;
			else if(posY < 300 && y == 203) y = 264;
			shooted = false;
		
		}
	}
	
	/*
	 * If the boolean shooted is false, fire a bossmissile
	 * The boolean is set to true after the shooting, it goes to false after a move
	 */
	public void shoot(){
		
		if(!shooted)
			missiles.add(new BossMissile(x, y+20));
		shooted = true;
		
	}
	
	public ArrayList<BossMissile> getMissiles(){
		
		return missiles;
		
	}
	
	public int getLife(){		
		return life;
	}
	
	public void setLife(int life){
		this.life = life;
	}

}
