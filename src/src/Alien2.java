package src;

/*
 * Class for the second kind of alien
 * x and y coordinates are used to know the position of one alien on the board
 */
public class Alien2 extends Sprite{
	
	private double i; //i is used in the move function to make a sinusoidal movement  
	private int life; //defines the life of the alien. need to be shot twice to die
	
	public Alien2(int x, int y){
		super(x,y);	
		initAlien2();
	}
	
	private void initAlien2(){
		
		i = 0;
		life = 3;
		loadImage("/" + Frame.getTheme() + "_alien2.png");
		getImageDimensions();
		loadSoundName("gyarados.wav");
		
	}
	/*
	 * Increment of the x coordinate of aliens for going from right to left 
	 * when an alien touches the left bound of the screen, it is no more visible
	 * The movement of the alien2 is a sinusoid. The code for this sinus was taken from this website :
	 * https://www.scm.tees.ac.uk/isg/website/lecture/java/javaWeb/Loops/SinWave-code.html
	 */
	public void move(){
		
		if(x+width<0){
			vis = false;
			i = 0;
		}
		
		x -= 3 ;
		
		int dy = (int)(Math.sin(Math.toRadians(i))*20) /3;
		y+=dy;
		i = i + 3;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
}
