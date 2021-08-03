/* “G‚ð’Ç‰Á:‘™•ª‘¿’n */

package src;

/*
 * Class for the second kind of alien
 * x and y coordinates are used to know the position of one alien on the board
 */
public class Alien3 extends Sprite{
	
	private double dy; //i is used in the move function to make a sinusoidal movement  
	private int life; //defines the life of the alien. need to be shot twice to die
	
	public Alien3(int x, int y){
		super(x,y);	
		initAlien3();
	}
	
	private void initAlien3(){
		
		dy = 3;
		life = 1;
		loadImage("/" + Frame.getTheme() + "_alien3.png");
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
		}
		
		x -= 3 ;
		
		if(y < 0)
			dy *= -1;
		if(y > height)
			dy *= -1;
			
		y+=dy;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
}
