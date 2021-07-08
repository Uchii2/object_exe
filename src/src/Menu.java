package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Menu panel displayed at the beginning of the game
 * It includes a welcome message and three buttons
 * A GridBagLayout is used to position the components
 */
@SuppressWarnings("serial")
public class Menu extends JPanel{
		
	public Menu(){
		
		super(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
        
       JLabel instructions = new JLabel("ようこそ");
       Myfont.setFontSize(instructions, 30);
       c.insets = new Insets(10,10,10,10);
       c.gridy = 0;
       this.add(instructions, c);
       
       Button startButton = new Button("New Game");
       startButton.setName("startButton");
       c.gridy = 2;
       this.add(startButton, c);
       
       Button optionButton = new Button("Options");
       optionButton.setName("optionsButton");
       c.gridy = 3;
       this.add(optionButton, c);
       
       Button rulesButton = new Button("Rules");
       rulesButton.setName("rulesButton");
       c.gridy = 4;
       this.add(rulesButton, c);
      
       Frame frame = Frame.getFrame();
       startButton.addActionListener(frame);
       optionButton.addActionListener(frame);
       rulesButton.addActionListener(frame);
       
       this.setBackground(new Color(0,0,0,20));
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
		g.setColor(new Color(255,255,255,200)); //the fourth parameter, on a scale from 0 to 255, defines the opacity
		g.fillRect(0, 0, 500, 500);
		
	}

}
