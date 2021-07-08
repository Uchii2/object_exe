package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * This panel is shown at the end of the game to show the best scores and some buttons to navigate.
 * This screen is the more polyvalent because it needs to change according to the result of the game.
 * If the player set a new score, a different message is displayed and the score is added to the best score list in color.
 * Finally, the list must be updated if the player delete the score and goes back to the scoreboard.
 * Help for the score file was taken here :
 * http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
 */
@SuppressWarnings("serial")
public class ScoreBoard extends JPanel implements ActionListener{
	
	private ArrayList<Score> scorelist; //list of different scores
	
	private static final String fscore = "score.dat"; //the name of the file.dat which contains the list
	
	private boolean newRecord = false; //set to true if a new record is set at the end of a game
	private int index = 0; //to know where the new score is (to make the font red)
	
	ObjectOutputStream oos = null; //object to read the score file
	ObjectInputStream ois = null; //object to write in the score file
	
	public ScoreBoard(){
		
		super(new GridBagLayout());
		
		scorelist = new ArrayList<Score>();
		
		/*
		 * Initialization of the score file if it's the first time the game is launched.
		 * There is two initial scores to beat.
		 */
		File scoreFile = new File(fscore);
		if(!scoreFile.exists()){
			try{
				scoreFile.createNewFile();
				scorelist.add(new Score("Winner", 100));
				scorelist.add(new Score("Looser", 0));
				updateScoreFile();
			}catch(IOException e){
				System.out.print("impossible de créer le fichier");
			}
		}
		
		afficherScore();
	}
	
	public ArrayList<Score> getScores(){
		
		loadScoreFile();
		return scorelist;
		
	}
	
	/*
	 * This method is used to sort the scores in the scorelist
	 */
	public void sort(){
		ScoreComparator comparator = new ScoreComparator();
		Collections.sort(scorelist,comparator);
	}
	
	/*
	 * This function add a new score in the list.
	 * The board only print the first five better scores (newRecord set to false on the other hand)
	 */
	public void addScore(String playerName, int score){
		
		loadScoreFile();
		sort();
		Iterator<Score> i = scorelist.iterator();
			while(i.hasNext()){
				Score s = i.next();
				if(score >= s.getScore()){
					Score newScore = new Score(playerName, score);
					scorelist.add(newScore);
					sort();
					updateScoreFile();
					newRecord = true;
					index = getIndex(newScore);
					if(index>5)
						newRecord = false;
					break;
				}
			}	
		
		afficherScore();
	}
	
	/*
	 * The index returned is used to know where in the list the score was added in order
	 * to make its font red if its a new best score.
	 * The score objects are compared (a score object is a player name and a score).
	 */
	public int getIndex(Score score){
		
		int i =0;
		
		for(i=0; i<scorelist.size(); i++){
			if(scorelist.get(i).equals(score))
				return i;
		}
		
		return 0;
		
	}
	
	/*
	 * Code taken from this website :
	 * http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
	 * This method load the arraylist from the file to the scorelist object
	 */
	@SuppressWarnings("unchecked")
	public void loadScoreFile(){
		
		try{
			ois = new ObjectInputStream(new FileInputStream(fscore));
			scorelist = (ArrayList<Score>) ois.readObject();
		} catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        } finally {
        	try{
        		if(oos != null){
        			oos.flush();
        			oos.close();
        		}
        	}catch(IOException e){
        		System.out.println("[Laad] IO Error: " + e.getMessage());
        	}
        }
		
	}
	
	/*
	 * Code taken from this website :
	 * http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
	 * This method load the arraylist from scorelist object and write it in the score file.
	 */
	public void updateScoreFile(){
		
		try{
			oos = new ObjectOutputStream(new FileOutputStream(fscore));
			oos.writeObject(scorelist);
		}catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
        	try{
        		if(oos != null){
        			oos.flush();
        			oos.close();
        		}
        	}catch(IOException e){
        		System.out.println("[Laad] IO Error: " + e.getMessage());
        	}
        }
	}
	
	/*
	 * The first five scores are displayed at the end of the game.
	 * They are JLabel managed by a GridBagLayout.
	 */
	public void afficherScore(){
		
		this.removeAll();
		
		String score;
		ArrayList<Score> scores;
		scores = getScores();
		
		int i = 0;
		int x = scores.size();
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(10,10,10,10);
		
		while(i < x && i < 5){
			score = scores.get(i).toString();
			JLabel jl = new JLabel(score);
			Myfont.setMyfont(jl);
			if(i == index && newRecord) //the new record is in red to better see it
				jl.setForeground(Color.RED);
	        c.gridx = 1;
	        c.gridy = i+5;
			this.add(jl, c);
			i++;
		}
		
		JLabel js = new JLabel("Best Scores");
		Myfont.setMyfont(js);
		c.gridx = 1;
		c.gridy = 1;
		this.add(js, c);
		
		if(newRecord && index < 5){ //the new record message is displayed only if there actually is a new record
			JLabel jr = new JLabel("NEW RECORD");
			jr.setForeground(Color.BLUE);
			Myfont.setMyfont(jr);
			c.insets = new Insets(0,0,0,0);
			c.gridx = 1;
			c.gridy = 3;
			this.add(jr, c);
			
			newRecord = false;
		}
		
		
		c.insets = new Insets(10,10,10,10);
		
		Button tryagainB = new Button("Try Again ?");
		tryagainB.setName("tryagainB");
		tryagainB.addActionListener(this);
		c.gridy = 11;
		this.add(tryagainB, c);
		
		Button optionsButton = new Button("Options");
		optionsButton.setName("optionsButton");
		optionsButton.addActionListener(this);
		c.gridy = 12;
		this.add(optionsButton, c);
	}
	
	
	/*
	 * This method is called when the player wants to delete his best scores.
	 * All the content of the scorelist object is deleted then the sample scores are added and the file is updated.
	 */
	public void deleteScores() {
		
		scorelist.removeAll(scorelist);
		scorelist.add(new Score("winner", 100));
		scorelist.add(new Score("Looser", 0));
		updateScoreFile();
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e){
		
		if(((Button)e.getSource()).getName().equals("tryagainB"))
			Frame.getFrame().doTryAgain();
		else if(((Button)e.getSource()).getName().equals("optionsButton")){
			Frame.getFrame().doOptions();
			Frame.previousmenu = false;
		}
		
	}
	
	/*
	 * Code taken from this page :
	 * http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
	 * This comparator sorts the scores in descending order.
	 */
	class ScoreComparator implements Comparator<Score>{
		
		public int compare(Score s1, Score s2){
			int score1 = s1.getScore();
			int score2 = s2.getScore();
			
			if(score1>score2)
				return -1;
			else if(score2>score1)
				return 1;
			else
				return 0;
		}
		
	}
	
	/*
	 * This method is used to paint the background image of the menu
	 * The image change according to the theme
	 */
	@Override
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		ImageIcon ii = new ImageIcon(getClass().getResource("/" + Frame.getTheme() + "_back.png"));
        Image image = ii.getImage();
		g.drawImage(image, 0, 0, null);
		
		
		//a transparent white square is drawn on the background image to make it transparent
		g.setColor(new Color(255,255,255,200));
		g.fillRect(0, 0, 500, 500);
		
	}
	

}
