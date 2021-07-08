import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/*
 * JPanel to set the menu of the game
 * There is two different options : the difficulty of the game (easy, medium or hard)
 * and the number of players from one to four.
 * The difficulty changes the number and the size of the squares.
 * A GridBagLayout is used for the components.
 */
@SuppressWarnings("serial")
public class Menu extends JPanel{
	
	
	public Menu(){
		
		super(new GridBagLayout());
		
		this.setOpaque(true);
		this.setBackground(Mycolors.greyblue.getColor());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);		
		
		Dimension n = new Dimension(150,30);
		
		/*
		 * The players can only choose one level of difficulty
		 * so the JRadioButtons are grouper in a Button Group.
		 */
		ButtonGroup bg = new ButtonGroup();
		
		JRadioButton easy = new JRadioButton("Easy", true);
		easy.setName("easy");
		easy.setFocusable(false);
		easy.setBackground(Mycolors.greyblue.getColor());
		bg.add(easy);
		c.gridy = 1;
		this.add(easy, c);
		easy.addActionListener(Game.getGame());
		
		JRadioButton medium = new JRadioButton("Medium");
		medium.setName("medium");
		medium.setFocusable(false);
		medium.setBackground(Mycolors.greyblue.getColor());
		bg.add(medium);
		c.gridy = 1;
		c.gridx = 1;
		this.add(medium, c);
		medium.addActionListener(Game.getGame());
		
		JRadioButton hard = new JRadioButton("Hard");
		hard.setName("hard");
		hard.setFocusable(false);
		hard.setBackground(Mycolors.greyblue.getColor());
		bg.add(hard);
		c.gridy = 1;
		c.gridx = 2;
		this.add(hard, c);
		hard.addActionListener(Game.getGame());
				
		JLabel jlab = new JLabel("Difficulty :");
		c.gridy = 0;
		c.gridx = 1;
		this.add(jlab, c);
		
		JLabel jlab2 = new JLabel("Number of players :");
		c.gridy = 2;
		c.gridx = 1;
		this.add(jlab2, c);
		
		/*
		 * Four different buttons to choose the number of players
		 * When the player click on a button, the game starts.
		 */
		Button oneplayer = new Button(Mycolors.purple.getColor(), "oneplayer", "Single Player");
		oneplayer.setPreferredSize(n);
		c.gridy = 3;
		c.gridx = 1;
		this.add(oneplayer, c);
		
		Button twoplayers = new Button(Mycolors.purple.getColor(), "twoplayers", "Two Players");
		twoplayers.setPreferredSize(n);
		c.gridy = 4;
		c.gridx = 1;
		this.add(twoplayers, c);
		
		Button threeplayers = new Button(Mycolors.purple.getColor(), "threeplayers", "Three Players");
		threeplayers.setPreferredSize(n);
		c.gridy = 5;
		this.add(threeplayers, c);
		
		Button fourplayers = new Button(Mycolors.purple.getColor(), "fourplayers", "Four Players");
		fourplayers.setPreferredSize(n);
		c.gridy = 6;
		this.add(fourplayers, c);
		
	}
	
	/*
	 * This method is used to display the title of the game.
	 * Because the game is based on color change, the color of the title is randomly selected.
	 */
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		int rand = (int)(Math.random()*6);
		Color color;
		
		switch(rand){
		case 0:
			color = Mycolors.purple.getColor();
			break;
		case 1:
			color = Mycolors.red.getColor();
			break;
		case 2:
			color = Mycolors.green.getColor();
			break;
		case 3:
			color = Mycolors.blue.getColor();
			break;
		case 4:
			color = Mycolors.yellow.getColor();
			break;
		case 5:
			color = Mycolors.orange.getColor();
			break;
		default :
			color = Mycolors.orange.getColor();
			break;
		}
		
		g.setColor(color);
		g.setFont(new Font("Tahoma", Font.BOLD, 60));
		g.drawString("COLOR GAME", 200, 80);
		
	}
}
