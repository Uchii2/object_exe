package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/*
 * The Game class is the core of the game.
 * It manages the position and movement of every sprite in the game.
 * The component are painted and updated every frame.
 * The timer object is used to define the number of frame per second for this game.
 * The class also manages the command key by ActionListener.
 * The source code for the commands and the management of the sprite on the screen was taken from this tutorial :
 * http://zetcode.com/tutorials/javagamestutorial/movingsprites/
 */
@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener{
	
	private final int ICRAFT_X = 30; // x coordinate of the craft. can't be changed by the player
	private final int ICRAFT_Y = 142; // initial y coordinate of the craft
	private final int DELAY = 15; //delay between each refreshing the frame, in millisecond (nearly 60FPS)
		
	private Timer timer;
	private Craft craft;
	private Boss boss;
	
	private Background back;
	
	private int score; //player's score
	private int life; // player's life
	private int spawned; //number of aliens that are dead (either killed or just out of the screen)
	
	private boolean ingame; //this boolean is set to false when the player loses
	private boolean inboss; // this boolean is set to true during the boss phase
	private final int B_WIDTH = 480; //width of the board
	private final int B_HEIGHT = 340; //height of the board
		
	private ArrayList<Alien> aliens; //list of visible aliens
	private ArrayList<Wall> walls; //list of visible walls
	private ArrayList<Life> lives; //list of visible bonus lives
	private ArrayList<Alien2> aliens2; //list of visible aliens2
	private ArrayList<Bonus> bonus; //list of visible bonuses
	
	private JPanel scorepan; //contains the score and the number of lives for the current game
	private JLabel scorelab; //current score of the player
	private JLabel lifelab; //current number of lives of the player
	
	private boolean paused; //this boolean is set to true when the game is paused
	
	private int[][] ennemies = new int[5][4]; //this array is used to know on which y level a wall is visible
	
	public Game(){
		
		initBoard();
		
	}
	
	private void initBoard(){
		
		/*
		 * This part of code was directly taken from this page :
		 * http://stackoverflow.com/questions/22700231/java-username-requestfocus-not-working-in-a-joptionpane
		 * It is used to give the focus on the Game panel.
		 * In fact, there is conflicts between mouse actions and keyboard actions so the Game need to request the focus from
		 * the Frame when it starts.
		 */
		this.addAncestorListener(new AncestorListener() {
		    @Override
		    public void ancestorRemoved(AncestorEvent pEvent) {
		    }

		    @Override
		    public void ancestorMoved(AncestorEvent pEvent) {
		    }

		    @Override
		    public void ancestorAdded(AncestorEvent pEvent) {
		        // TextField is added to its parent => request focus in Event Dispatch Thread
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                requestFocusInWindow();
		            }
		        });
		    }
		});
		
		addKeyListener(new TAdapter()); //see the internal class at the end of the class
		setFocusable(true);
		setDoubleBuffered(true);
		
		ingame = true;
		score = 0;
		life = 3; //the player starts the game with 3 lives
		spawned = 0;
		inboss = false;
		paused = false;
		
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
			
		craft = new Craft(ICRAFT_X, ICRAFT_Y);
		
		back = new Background();
		
		//initialization of the different speeds for the sprites
		Alien.setSpeed(3);
		Life.setSpeed(5);
		Bonus.setSpeed(5);
		
		walls = new ArrayList<>();
		lives = new ArrayList<>();
		aliens = new ArrayList<>();
		aliens2 = new ArrayList<>();
		bonus = new ArrayList<>();
		
		initScorepan();

		timer = new Timer(DELAY, this);
		timer.start();
		
	}
	
	/*
	 * Initiate the scorepan that displays the score and the number of lives
	 */
	public void initScorepan(){
		
		scorepan = new JPanel(new GridBagLayout());
		this.add(scorepan);
		
		scorepan.setPreferredSize(new Dimension(500,30));
		
		scorepan.setBackground(new Color(0,0,0,0));
		
		paintScorepan();
		
		craft.setShoot(2);
		
		
	}
	
	/*
	 * This function was created to avoid a slower of the game.
	 * The scorepan uses the custom font and was refreshed every frame;
	 * the constant call to Myfont caused the game to freeze sometimes.
	 * Now the scorepan is only repainted if it's necessary (whether the score or life changes)
	 */
	public void updateScorepan(){
		
		scorepan.removeAll();
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0,0,0,80);
		
		paintScorepan();
		
		scorepan.validate();
		scorepan.repaint();
	}
	
	/*
	 * This method is used to design the scorepan thanks to a GriBagLayout and some JLabel
	 * The space between them was thinking to let the score expand to a large number without being cut
	 */
	private void paintScorepan(){
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0,0,0,80);
		
		scorelab = new JLabel("スコア : " + score);
		Myfont.setMyfont(scorelab);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.anchor = GridBagConstraints.NORTH;
		scorepan.add(scorelab, c);
		lifelab = new JLabel("ライフ : " + life);
		Myfont.setMyfont(lifelab);
		if(craft.isImmune()) lifelab.setForeground(Color.white);
		c.insets = new Insets(3,3,3,80);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTH;
		scorepan.add(lifelab, c);
		
	}
	
	/*
	 * Aliens that are visible are contained in an ArrayList.
	 * They are all randomly generated by a simple system based on a random number
	 * To make the game more simple and avoid the aliens to spawn on top of each other, there is only five y position possible.
	 * Depending on the random number, the closer y position is selected.
	 */
	public void initAliens(){
		
		for(int i = 0; i<5; i++){
			Random rand = new Random();
			int posY = rand.nextInt(B_HEIGHT);
			int posX = rand.nextInt(B_WIDTH) + 500;
			
			if(posY < 76) posY = 20;
			else if(posY < 132) posY = 81;
			else if(posY < 188) posY = 142;
			else if(posY < 244) posY = 203;
			else posY = 264;
		
			aliens.add(new Alien(posX, posY));
		}
		
		Alien.setSpeed(3);
	}
	
	
	/*
	 * This function will paint every sprite that is visible at the moment and the background.
	 * A sprite is visible if the function sprite.isVisible() returns true.
	 * It takes the coordinates of every sprite and background to paint them at the right place on the board.
	 * It also displays the pause screen. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		/*
		 * This method for making the background move was inspired from this page :
		 * http://stackoverflow.com/questions/16138363/infinite-background-for-game
		 * The x position of the background image is updated every time the frame refresh.
		 * In order to repeat it, the image is also painted at the end of itself to make a perfect loop.
		 */
		g.drawImage(back.getImage(), -back.getPosX(), 0, this);
		
		if (back.getPosX() + 500 > back.getWidth()) {
            g.drawImage(back.getImage(), - back.getPosX() + back.getWidth(), 0, this);
        }
		
		Graphics2D g2d = (Graphics2D) g;
		
		//drawing of the craft
		if(craft.isVisible())
			g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
		
		//drawing of the player's missiles
		ArrayList<Missile> ms = craft.getMissiles();

        for (Missile m : ms) {
            if(m.isVisible())
            	g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }
        
		//drawing of the boss, only if the game is in boss phase (inboss is true)
		if(inboss && boss.isVisible())
			g2d.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);

        //drawing of the boss's missiles
        if(inboss && !boss.getMissiles().isEmpty()){
	        ArrayList<BossMissile> mb = boss.getMissiles();
	        
	        for (BossMissile m : mb){
	        	if(m.isVisible())
	        		g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
	        }
        }
        
        //drawing of aliens
        for (Alien a : aliens){
        	if(a.isVisible())
        		g.drawImage(a.getImage(), a.getX(), a.getY(), this);
        }
        
        //drawing of walls
        for (Wall w : walls){
        	if(w.isVisible())
        		g2d.drawImage(w.getImage(), w.getX(), w.getY(), this);
        }
        
        //drawing of life bonus
        for (Life l : lives){
        	if(l.isVisible())
        		g2d.drawImage(l.getImage(), l.getX(), l.getY(), this);
        }
        
        //drawing of bonuses
        for (Bonus b : bonus){
        	if(b.isVisible())
        		g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
        }
        
        //drawing of aliens2
        for (Alien2 a : aliens2){
        	if(a.isVisible())
        		g.drawImage(a.getImage(), a.getX(), a.getY(), this);
        }
        
        //drawing of the pause screen
        //it's made of simple rectangles drawn on the top of the game board
        if(paused){
        	g.setColor(new Color(0,0,0,150));
        	g.fillRect(0, 0, 500, 500);
        	g.setColor(Color.gray);
        	g.fillRect(B_WIDTH/2 - 100, B_HEIGHT/2 - 25, 220, 60);
        	g.setColor(Color.black);
        	g.drawRect(B_WIDTH/2 - 100, B_HEIGHT/2 - 25, 220, 60);
        	Myfont.setGraphicFont(g);
        	g.setColor(Color.white);
        	g.drawString("一時停止中", B_WIDTH/2 - 60, B_HEIGHT/2);
        	Myfont.setGraphicFontSize(g, 10);
        	g.drawString("Pキーを押して再開", B_WIDTH/2 - 60, B_HEIGHT/2 + 20);
        }
        
	}
	
	/*
	 * The actionPerformed method will be called every DELAY (15ms)
	 * In this method, every thing that moves in the game is updated 
	 * and the collisions are checked. 
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		
		inGame();
		
		updateBackground();
		updateMissiles();
        updateCraft();
        
        if(!inboss){ //these sprite only appears in the normal mode, when there is no boss
	        updateAliens();
	        updateWalls();
	        updateLives();
	        updateBonus();
	        updateAliens2();
        }
        
        if(inboss) //in boss phase, there only is the boss and its missiles 
        	updateBoss();
        
        updateSpeed(); //upgrade the speed of some sprite to increase the difficulty of the game
        
        checkCollisions(); //the collisions between sprites are checked to see if they are still visible
        
		repaint(); //the repaint method is called to erase all the component on the screen and redraw them at their new position
				
	}

	/*
	 * When the game ends, the timer is stopped and the game over screen must be displayed by the frame.
	 */
	private void inGame(){
		
		if(!ingame){
			timer.stop();
			Frame frame = Frame.getFrame();
			frame.gameOver(score, spawned, life);
			
		}
	}
	
	/*
	 * The pause (triggered by the 'P' key) causes the timer to stop and the pause screen to be drawn.
	 * When the key is pressed again, the timer starts and the game continues. 
	 */
	public void doPause(){

		if(timer.isRunning()){
			paused = true;
			repaint(); //used to make the paintComponent method draw the pause screen
			timer.stop();
		}
		
		else{
			timer.start();
			paused = false;
		}
	}

	/*
	 * Make the background picture move
	 */
	private void updateBackground(){
		
		if(ingame)
			back.move();		
	}
	
	/*
	 * Update of the position of the different missiles; those of the player or those of the boss.
	 * Every missile from the missile list is checked to see if it is visible, in this case, its position is updated.
	 * On the other case, it is removed from the list.
	 */
	@SuppressWarnings("unchecked")
	private void updateMissiles() {

	        ArrayList<Missile> ms = craft.getMissiles();

	        for (int i = 0; i < ms.size(); i++) {

	            Missile m = ms.get(i);

	            if (m.isVisible()) {
	                m.move();
	            } else {
	                ms.remove(i);
	            }
	        }
	        
	        if(inboss && !boss.getMissiles().isEmpty()){
	        	
		        ArrayList<BossMissile> mb = boss.getMissiles();
		        
		        for (int i = 0; i < mb.size(); i++){
		        	BossMissile n = mb.get(i);
		        	
		        	if (n.isVisible()) {
		                n.move();
		            } else {
		                mb.remove(i);
		            }
		        }
	        }
	    }
	
	/*
	 * Update of the position of the craft using the move method of the Craft class.
	 * If the life of the player is < 0, the game finishes (ingame is set to false).
	 */
	private void updateCraft() {
		
		if(life < 0){
			craft.vis = false;
			ingame = false;
		}
		
		
		if(craft.isVisible())
			craft.move();
	}
	
	/*
	 * Initialize the boss.
	 * It first removes every sprite from the screen (except the craft) then display a new boss with its music. 
	 */
	public void initBoss(){
		
		aliens.removeAll(aliens);
		aliens2.removeAll(aliens2);
		lives.removeAll(lives);
		walls.removeAll(walls);
		bonus.removeAll(bonus);
		
		boss = new Boss(450,142);
		
		Frame.getFrame().playBossTheme();
	}
	
	/*
	 * Until the boss is not dead, its position is updated.
	 * When the player kills the boss, new aliens are generated and the normal phase restarts.
	 */
	public void updateBoss(){
		
		if(boss.isVisible()){
			boss.move();
			boss.shoot(); //the boss fires a missile every time it moves
		}
		
		if(boss.getLife() == 0){
			boss.vis = false;
    		score += 20;
    		boss.setLife(-1); //this avoid the boss to restart itself
    		inboss = false;
    		initAliens();
    		
    		Frame.getFrame().playTheme();
		}
			
	}
	
	/*
	 * This method got three functions :
	 * it check if the boss must be launch or not
	 * it updates the position and the alien list
	 * it makes new aliens spawn
	 * Aliens are randomly generated by a random function.
	 * In order to not invade the screen by aliens, they have a little chance to spawn (spawn > 980) 
	 * and there can be only 10 aliens at the same time on the board.
	 */
	private void updateAliens(){
		//TODO
		//to move away
		//boucle boss
		if(craft.getShoot()>0 && spawned > 20 && score < 30){
			inboss = true;
			initBoss();
			return;
		}
		
		for(int i = 0; i < aliens.size(); i++){
			
			Alien a = aliens.get(i);
			if(a.isVisible())
				a.move();
			else{
				aliens.remove(i);
				spawned++;
				}
		}
		
		Random rand = new Random();
		int spawn = rand.nextInt(1000);
		
		if(spawn > 980 && aliens.size() < 10){
			int posY = rand.nextInt(B_HEIGHT);
			int posX = rand.nextInt(B_WIDTH) + 400;
						
			if(posY < 76 && ennemies[0][2] == 0) aliens.add(new Alien(posX, 20)); //the condition on the enemies array is used to avoid an alien spawning on a line if there is already a wall in it
			else if(posY > 76 && posY < 132 && ennemies[1][2] == 0) aliens.add(new Alien(posX, 81));
			else if(posY > 132 && posY < 188 && ennemies[2][2] == 0) aliens.add(new Alien(posX, 142));
			else if(posY > 188 && posY < 244 && ennemies[3][2] == 0) aliens.add(new Alien(posX, 203));
			else if(posY > 244 && posY < 305 && ennemies[4][2] == 0) aliens.add(new Alien(posX, 264));
		
		}
	}
	
	/*
	 * Update of the position of the walls.
	 * There can't be more than two walls at the same time.
	 * There is a second kind of wall : 4 walls on the same y position to force the player to go between them.
	 * This special wall as a few chance to spawn
	 */
	public void updateWalls(){
		
		Random rand = new Random();
		int spawn = rand.nextInt(1000);
		
		if(spawn > 995 && walls.isEmpty() && craft.getShoot() > 3){
			int rd = (int)(Math.random()*5); //this determines the line where there will be no wall
			
			if(rd != 0){
				walls.add(new Wall(400, 20));
				ennemies[0][2] = 1;} //when a wall is in a line, it is 	registered in the enemies array
			if(rd != 1) {
				walls.add(new Wall(400, 81));
				ennemies[1][2] = 1;}
			if(rd != 2) {
				walls.add(new Wall(400, 142));
				ennemies[2][2] = 1;}
			if(rd != 3) {
				walls.add(new Wall(400, 203));
				ennemies[3][2] = 1;}
			if(rd != 4) {
				walls.add(new Wall(400, 264));
				ennemies[4][2] = 1;}
		}
		
		if(spawn > 990 && walls.size()<=2){
			int posY = rand.nextInt(B_HEIGHT);
			int posX = rand.nextInt(B_WIDTH) + 400;
			
			if(posY < 76 && ennemies[0][2] == 0) walls.add(new Wall(posX, 20));
			else if(posY > 76 && posY < 132 && ennemies[1][2] == 0) walls.add(new Wall(posX, 81));
			else if(posY > 132 && posY < 188 && ennemies[2][2] == 0) walls.add(new Wall(posX, 142));
			else if(posY > 188 && posY < 244 && ennemies[3][2] == 0) walls.add(new Wall(posX, 203));
			else if(posY > 244 && posY < 305 && ennemies[4][2] == 0) walls.add(new Wall(posX, 264));
				
				if(posY < 76) ennemies[0][2] = 1;
				else if(posY < 132) ennemies[1][2] = 1;
				else if(posY < 188) ennemies[2][2] = 1;
				else if(posY < 244) ennemies[3][2] = 1;
				else if(posY < 305) ennemies[4][2] = 1;
			}
		
		for (int i = 0; i <walls.size(); i++){
			Wall w = walls.get(i);
			if (w.isVisible())
				w.move();
			else{
				walls.remove(i);
				
				int posY = w.getY();
				if(posY == 20) ennemies[0][2] = 0; //when a wall disapear from the board, the array is updated
				else if(posY == 81) ennemies[1][2] = 0;
				else if(posY == 142) ennemies[2][2] = 0;
				else if(posY == 203) ennemies[3][2] = 0;
				else if(posY == 264) ennemies[4][2] = 0;
			}
		}
		
	}
	
	/*
	 * Update of the position of the life bonus on the screen.
	 * The life bonus cannot spawn on a line if there is a wall on it.
	 */
	public void updateLives(){
		
		Random rand = new Random();
		int spawn = rand.nextInt(1000);
		
		if(spawn > 990 && lives.size() == 0 && aliens2.size() == 0){
			int posY = rand.nextInt(B_HEIGHT);
			int posX = rand.nextInt(B_WIDTH) + 400;
			
			if(posY < 76 && ennemies[0][2] == 0) lives.add(new Life(posX, 20));
			else if(posY > 76 && posY < 132 && ennemies[1][2] == 0) lives.add(new Life(posX, 81));
			else if(posY > 132 && posY < 188 && ennemies[2][2] == 0) lives.add(new Life(posX, 142));
			else if(posY > 188 && posY < 244 && ennemies[3][2] == 0) lives.add(new Life(posX, 203));
			else if(posY > 244 && posY < 305 && ennemies[4][2] == 0) lives.add(new Life(posX, 264));
			
			}
		
		for (int i = 0; i <lives.size(); i++){
			Life l = lives.get(i);
			if (l.isVisible())
				l.move();
			else 
				lives.remove(i);
		}
		
	}
	
	/*
	 * Update of the bonus position.
	 * The kind of bonus is randomly selected.
	 * There is three kind of bonuses that spawn in certain conditions.
	 */
	public void updateBonus(){
		
		Random rand = new Random();
		int spawn = rand.nextInt(1000);
		
		if(spawn > 995 && bonus.size() == 0){
			int posY = rand.nextInt(B_HEIGHT);
		
			if(posY < 76) posY = 20;
			else if(posY < 132) posY = 81;
			else if(posY < 188) posY = 142;
			else if(posY < 244) posY = 203;
			else posY = 264;
			
			
			Random rand2 = new Random();
			int bonustype = rand2.nextInt(3);
			if (bonustype == 1)
				bonus.add(new Bonus(B_WIDTH, posY, 1));
			else if(bonustype == 2 && craft.getShoot() < 20 && craft.getShoot() > 4) //this is the second kind of missile, obtainable after having the missile updated to the fourth rank
				bonus.add(new Bonus(B_WIDTH, posY, 2));
			else if(bonustype == 0 && !craft.isImmune()) //this bonus give immunity to the next damage. As long as the player preserve the immunity, this bonus will not spawn
				bonus.add(new Bonus(B_WIDTH, posY, 3));
				
			
		}
		
		for(int i = 0; i < bonus.size(); i++){
			Bonus b = bonus.get(i);
			if(b.isVisible())
				b.move();
			else
				bonus.remove(i);
		}
		
	}
	
	/*
	 * Update of the position of the second kind of alien
	 */
	public void updateAliens2(){
		
		Random rand = new Random();
		int spawn = rand.nextInt(1000);
		
		if(spawn > 990 && aliens2.size()==0 && lives.size()==0){
			int posY = rand.nextInt(B_HEIGHT);
			int posX = rand.nextInt(B_WIDTH) + 400;
			
			if(posY < 76) posY = 20;
			else if(posY < 132) posY = 81;
			else if(posY < 188) posY = 142;
			else if(posY < 244) posY = 203;
			else posY = 264;
		
			aliens2.add(new Alien2(posX, posY));}
		
		for (int i = 0; i <aliens2.size(); i++){
			Alien2 a = aliens2.get(i);
			if (a.isVisible())
				a.move();
			else
				aliens2.remove(i);
		}
		
	}
	
	/*
	 * When 20 aliens spawned, the speed of some elements is increased.
	 */
	public void updateSpeed(){
		
		if(spawned%20==0 && spawned != 0){
			back.setSpeed((back.getSpeed()+1)); ;
			Alien.setSpeed((Alien.getSpeed()+1));
			Life.setSpeed((Life.getSpeed()+1));
			spawned++;
			}
		
	}
	
	/*
	 * The collisions between sprites are verified by simple rectangles (bounds)
	 * The detection is then not really good because the picture of the player is not a rectangle
	 * The detection method was taken from this tutorial :
	 * http://zetcode.com/tutorials/javagamestutorial/collision/
	 */
	@SuppressWarnings("unchecked")
	public void checkCollisions(){
		
        Rectangle rC = craft.getBounds(); //this method returns the rectangle of the craft

        //collision between the craft and the aliens
        for (Alien alien : aliens){
            Rectangle rA = alien.getBounds();

            if (rC.intersects(rA)) { //the intersects method simply verify if the two bounds touch
            	alien.setVisible(false); //if its the case, the alien disappear 
            	if(craft.isImmune()){
            		craft.setImmune(false); //if the craft was immune, the player doesn't lose a life
            		updateScorepan();}
            	else{
            		life--; //the craft was not immune, the player loses a life
            		updateScorepan();
            		craft.downShoot(); //and the missile rank is downgraded
            		if(craft.getShoot() > 15)
                    	craft.setShoot(2);
            	}
                alien.playSound(); //sound the sprite plays on its death
            }
        }
        
        //collision between the craft and the second kind of aliens
        for(Alien2 alien : aliens2){
        	Rectangle rA2 = alien.getBounds();
        	if(rC.intersects(rA2)){
        		alien.setVisible(false);
        		if(craft.isImmune()){
        			craft.setImmune(false);
        			updateScorepan();}
        		else{
        			life -= 2; //the player lose two lives if he touches this kind of alien
        			craft.downShoot();
        			if(craft.getShoot() > 15)
                    	craft.setShoot(2);
        		}
        	}  
        	
        }
        
        //collision between the craft and the walls 
        for(Wall wall : walls){
        	Rectangle rW = wall.getBounds();
        	if(rC.intersects(rW)){
        		if(craft.isImmune()){
        			craft.setImmune(false);
        			wall.setVisible(false);
        			updateScorepan();
        		}
        		else{
        			craft.setVisible(false); //the player lose the game if he touches a wall
        			wall.playSound();
        			ingame = false; //the game end
        		}
        	}
        }
        
        //collision between the craft and the life bonus
        for(Life l : lives){
        	Rectangle rL = l.getBounds();
        	if(rC.intersects(rL)){
        		life++; //if the player collect this bonus, he gets 1 life
        		updateScorepan();
        		l.setVisible(false);
        		l.playSound();
        	}
        }
        
        //collision between the craft and the bonuses 
        for(Bonus b : bonus){
        	Rectangle rB = b.getBounds();
        	if(rC.intersects(rB)){
        		b.setVisible(false);
        		if(b.getBonusType() == 2)
        			craft.setShoot(20); //this is the second kind of missile
        		else if(b.getBonusType() == 1){
        			craft.upShoot(); //this updates the rank of the missiles 
        			if(craft.getShoot() == 21)
        				craft.setShoot(4); //the player can choose to downgrade the missile rank
        			if(craft.getShoot() == 6)
        				craft.setShoot(5);} //5 is the maximum rank for the missiles
        		else{
        			craft.setImmune(true); //set immunity to the next collision
        			updateScorepan();}
        	}
        }
       
	    if(inboss){
	        Rectangle rB = boss.getBounds();
	        
	        //collision between the craft and the boss
	        if(rB.intersects(rC))
	        	ingame = false; //the player lose the game if he touches the boss
	        
	        ArrayList<Missile> ms = craft.getMissiles();
	        ArrayList<BossMissile> mb = boss.getMissiles();
	        
	        //collision between the craft missile and the boss
	        for (Missile m : ms) {

	            Rectangle rM = m.getBounds();
	            
	            if(rM.intersects(rB)){
	            	m.setVisible(false);
	            	boss.setLife(boss.getLife()-1); //the boss lose one life per missile that touch it
	            }
	            
	            //collision between craft missile and boss missile
	            for (BossMissile n : mb) {

		            Rectangle rMB = n.getBounds();
		            
		            if(rM.intersects(rMB)){ //if the two missile collides, they each disappear
		            	n.setVisible(false);
		            	m.setVisible(false);
		            }
	            }
	        }
	        
	        //collision between the craft and the boss missile
	        for (BossMissile n : mb) {

	            Rectangle rMB = n.getBounds();
	            
	            if(rC.intersects(rMB)){
	            	n.setVisible(false);
	            	if(craft.isImmune())
	            		craft.setImmune(false);
	            	else
	            		life--;
	            	craft.downShoot(); 
            		if(craft.getShoot() > 15)
                    	craft.setShoot(2);
            		if(craft.getShoot() < 2)
            			craft.setShoot(2); //the player can't lose all is missile rank, in order to keepa chance to fight the boss
	            	updateScorepan();
	            	
	            }
            }
	    }
	        

        ArrayList<Missile> ms = craft.getMissiles();

        for (Missile m : ms) {

            Rectangle rM = m.getBounds();

            //collision between the craft missiles and the aliens
            for (Alien alien : aliens) {

                Rectangle rA = alien.getBounds();

                if (rM.intersects(rA)) {
                    alien.setVisible(false);
                    score++;
                    updateScorepan();
                    alien.playSound();
                    if(craft.getShoot() != 20 ){
                		m.setVisible(false);
                	}
                }
            }
            
            //collision between the craft missiles and the second kind of aliens
            for(Alien2 alien : aliens2){
            	Rectangle rA2 = alien.getBounds();
            	if(rM.intersects(rA2)){
            		if(craft.getShoot() != 20 ){
                		m.setVisible(false);
                	}
            		alien.setLife(alien.getLife()-1);
            		if(alien.getLife() == 0){ //this alien needs to be shot twice 
            			alien.setVisible(false);
            			score+=2;
            			updateScorepan();
            			alien.playSound();
            		}
            	}
            }
            
            //collision between walls and craft missile
            for(Wall wall : walls){
            	Rectangle rW = wall.getBounds();
            	if(rM.intersects(rW))
            		m.setVisible(false); //if a missile touches a wall, it disappear 
            }
        }
    }
	
	/*
	 * This class is used to affect the key to a certain action
	 * The class was taken from this tutorial :
	 * http://zetcode.com/tutorials/javagamestutorial/movingsprites/
	 * The 'P' key pauses the game.
	 */
	private class TAdapter extends KeyAdapter{
		
		@Override
		public void keyReleased(KeyEvent e){
			
			craft.keyReleased(e);
			
			if(e.getKeyCode() == KeyEvent.VK_P)
				doPause();
				
			
		}
		
		@Override
		public void keyPressed(KeyEvent e){
			
			craft.keyPressed(e);

		}
	}
}
