import java.awt.Color;

/*
 * This class manages the colors of the board throughout the game.
 * The colors are stored in a 2D array that represents the board, each case is a square.
 */
public class Pixels {
	
	private Color[][] colors; //the 2D arrays of colors
	
	private int height;
	private int width;
	private int size; //size of the squares, in pixels
	private int count; //defines the number of turns left or the number of the player who will play
	
	/*
	 * The size, the number of squares and the count for the current game is given
	 * at the initialization of the game in the Game class.
	 */
	public Pixels(int x, int y, int z, int count){
		
		colors = new Color[x][y];
		
		this.height = x;
		this.width = y;
		this.size = z;
		this.count = count;
		 		
		initColors();
		
	}	
	
	/*
	 * This method is the core of the game.
	 * When the player click on a command button, the top left square
	 * and all the consecutive square of the same color too.
	 * To optimize the game, the method does not check all the cells of the array every time.
	 * From the first square, if a square on the left, top, right or right is the same color, 
	 * this square get the new color and the method check the neighbors of this square.
	 */
	public void checkAdj(Color newcolor, Color oldcolor, int i, int j){
		
		//check right square
		if(j+1<width && colors[i][j+1] == oldcolor){ //if this square is the same color of the checked square
			colors[i][j+1] = newcolor; //the color is changed to the new color
			checkAdj(newcolor, oldcolor, i, j+1); //then we check the consecutive square of this changed square
			
		}
		
		//check bottom square
		if(i+1<height && colors[i+1][j] == oldcolor){
			colors[i+1][j] = newcolor;
			checkAdj(newcolor, oldcolor, i+1, j);
			
		}
		
		//check left square
		if(j-1>=0 && colors[i][j-1] == oldcolor){
			colors[i][j-1] = newcolor;
			checkAdj(newcolor, oldcolor, i, j-1);
			
		}
		
		//check tom square
		if(i-1>=0 && colors[i-1][j] == oldcolor){
			colors[i-1][j] = newcolor;
			checkAdj(newcolor, oldcolor, i-1, j);
		
		}
	}
	
	/*
	 * This function check all the cells of the array and return at the 
	 * moment it meets a different color than the first one.
	 */
	public boolean isWinning(){
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if(colors[i][j] != colors[0][0])
					return false;
			}
		}
		
		return true;
	}
	
	/*
	 * This function is used to change the color of a specific cell.
	 * In the recursive method checkAlign, we don't change the color of the first cell checked.
	 */
	public void setColor(Color newcolor, int x, int y){
		colors[x][y] = newcolor;
	}
	
	/*
	 * This function initialize the array with random colors.
	 * As there is only 6 colors, there is little risk that the puzzle
	 * will be impossible to finish with the number of turn defined (count).
	 */
	public void initColors(){
		
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				int rand = (int)(Math.random()*6);
				switch(rand){
				case 0:
					this.colors[i][j] = Mycolors.blue.getColor();
					break;
				case 1:
					this.colors[i][j] = Mycolors.red.getColor();
					break;
				case 2:
					this.colors[i][j] = Mycolors.green.getColor();
					break;
				case 3:
					this.colors[i][j] = Mycolors.orange.getColor();
					break;
				case 4:
					this.colors[i][j] = Mycolors.yellow.getColor();
					break;
				case 5:
					this.colors[i][j] = Mycolors.purple.getColor();
					break;
				}
			}
		}
	}
	
	
	public Color[][] getColors(){
		return colors;
	}

	public int getHeight(){
		return height;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getWidth(){
		return width;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getSize(){
		return size;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	

}
