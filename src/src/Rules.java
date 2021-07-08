package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/*
 * Rules panel is composed of two pans in a JTabbedPane
 * The first pan shows the commands and the second one the different kind of bonuses
 * It also contains another JPanel for the EXIT button
 */
@SuppressWarnings("serial")
public class Rules extends JPanel{
	
	private JPanel tab1; //command tab
	private JPanel tab2; // bonus tab
	
	public Rules(){
		
		super(new BorderLayout());
		
		tab1 = new JPanel();
		tab2 = new JPanel();
				
		initTab1();
		initTab2();
		
		UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE); //make the background of the tabs transparent
		JTabbedPane tab = new JTabbedPane();
		tab.setOpaque(false);
		Myfont.setMyfont(tab);
		tab.setBackground(new Color(255,255,255,120));
		
		tab.addTab("Commands", tab1);
		tab.addTab("Bonus effects", tab2);
		
		this.add(tab);

		JPanel pan = new JPanel();
		
		Button exitButton = new Button("Exit");
		exitButton.setName("exitButton");
		
		pan.add(exitButton);
		pan.setBackground(new Color(0,0,0,0));
		
		this.add(pan, BorderLayout.SOUTH);
		
		Frame frame = Frame.getFrame();
		exitButton.addActionListener(frame);
		
	}
	
	public void initTab1(){
		
		tab1.setLayout(new GridBagLayout());
		
		tab1.setBackground(new Color(255,255,255,120));
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		
		JLabel jinst1 = new JLabel("Use UP and DOWN to move");
		Myfont.setMyfont(jinst1);
		c.gridy = 1;
		tab1.add(jinst1, c);
		
		JLabel jinst2 = new JLabel("Use SPACE to shoot");
		Myfont.setMyfont(jinst2);
		c.gridy = 2;
		tab1.add(jinst2, c);
		
		JLabel jinst3 = new JLabel("Use P to pause the game");
		Myfont.setMyfont(jinst3);
		c.gridy = 3;
		tab1.add(jinst3, c);
		
	}
		
	public void initTab2(){
		
		tab2.setLayout(new GridBagLayout());
		
		tab2.setBackground(new Color(255,255,255,120));
		
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(0,0,5,15);
		
		ImageIcon i1 = new ImageIcon(getClass().getResource("/" + Frame.getTheme() + "_bonus3rules.png"));
		JLabel jlab = new JLabel(i1);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.gridx = 1;
		tab2.add(jlab, c);
		
		JLabel jlab2 = new JLabel("Upgrade missile");
		Myfont.setMyfont(jlab2);
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 0;
		c.gridx = 2;
		tab2.add(jlab2, c);
		
		ArrayList<JLabel> rules = new ArrayList<JLabel>();
		
		rules.add(new JLabel("1: Fire up to 5 missiles"));
		rules.add(new JLabel("2: Fire infinite missiles"));
		rules.add(new JLabel("3: Fire missile on two rows"));
		rules.add(new JLabel("4: Fire on three rows"));
		rules.add(new JLabel("5: Fire on five rows"));
		
		int i = 0;
		
		for(JLabel j : rules){
			Myfont.setFontSize(j, 10);
			c.gridx = 2;
			c.gridy = i+1;
			c.anchor = GridBagConstraints.WEST;
			tab2.add(j, c);
			i++;
		}		
		
		ImageIcon i2 = new ImageIcon(getClass().getResource("/" + Frame.getTheme() + "_bonus2rules.png"));
		JLabel jlab3 = new JLabel(i2);
		c.gridy = 6;
		c.gridx = 1;
		tab2.add(jlab3, c);
		
		JLabel jlab4 = new JLabel("Best missile shot");
		Myfont.setMyfont(jlab4);
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 6;
		c.gridx = 2;
		tab2.add(jlab4, c);
		
		ImageIcon i3 = new ImageIcon(getClass().getResource("/" + Frame.getTheme() + "_bonus1rules.png"));
		JLabel jlab5 = new JLabel(i3);
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 7;
		c.gridx = 1;
		tab2.add(jlab5, c);
		
		JLabel jlab6 = new JLabel("Immune to next damage");
		Myfont.setMyfont(jlab6);
		c.gridy = 7;
		c.gridx = 2;
		tab2.add(jlab6, c);
		
		ImageIcon i4 = new ImageIcon(getClass().getResource("/" + Frame.getTheme() + "_liferules.png"));
		JLabel jlab7 = new JLabel(i4);
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 8;
		c.gridx = 1;
		tab2.add(jlab7, c);
		
		JLabel jlab8 = new JLabel("1UP");
		Myfont.setMyfont(jlab8);
		c.gridy = 8;
		c.gridx = 2;
		tab2.add(jlab8, c);
		
	}
	
	/*
	 * This method is used to paint the background image of the rules
	 * The image change according to the theme
	 */
	@Override
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		ImageIcon ii = new ImageIcon(getClass().getResource("/" + Frame.getTheme() + "_back.png"));
        Image image = ii.getImage();
		g.drawImage(image, 0, 0, null);
		
		//a transparent white square is drawn on the background image to make it transparent
		g.drawRect(0, 0, 500, 500);
		g.setColor(new Color(255,255,255,200));
		g.fillRect(0, 0, 500, 500);
		
	}

}
