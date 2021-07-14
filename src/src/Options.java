package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/*
 * The Options panel sets different options for the game
 * The player can choose his name, that will be save for the score
 * The skin of the game can be changed
 * And the player can delete the best scores
 * A GridBagLayout is used to manages the different components
 */
@SuppressWarnings("serial")
public class Options extends JPanel implements ActionListener{
	
	private JTextField nameField; //textfield used to set the name of the player
		
	public Options(){
		
		super(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		
		String playerName = Frame.getPlayerName();
		
		JLabel nameLabel = new JLabel("プレイヤーの名前を設定");
		Myfont.setFontSize(nameLabel, 15);
        c.gridy = 1;
        this.add(nameLabel, c);
		
		nameField = new JTextField(playerName, 15); 
		Myfont.setFontSize(nameField, 15); //namefield is the default value of the textfield to show the name of the player
        c.gridy = 2;
        this.add(nameField, c);
        
        JLabel skinLabel = new JLabel("背景を変更");
        Button skinButton = new Button("change");
        skinButton.setName("skinButton");
        c.gridy = 3;
        this.add(skinLabel, c);
        c.gridy = 4;
        this.add(skinButton, c);
        
        JLabel deleteLabel = new JLabel("スコア履歴を削除");
        Button deleteButton = new Button("delete");
        deleteButton.setName("deleteButton");
        c.gridy = 5;
        this.add(deleteLabel, c);
        c.gridy = 6;
        this.add(deleteButton, c);
        
        Button okButton = new Button("OK");
        okButton.setName("okButton");
        c.gridy = 7;
        this.add(okButton, c);
		
        Frame frame = Frame.getFrame();
        skinButton.addActionListener(frame);
        deleteButton.addActionListener(frame);
        okButton.addActionListener(frame);
	}
	
	/*
	 * A custom JOptionPane is used to confirm the deletion of the scores
	 * It's a simple window with a yes/no buttons 
	 */
	public void showMessage(){
		
		Button yes = new Button("Yes");
		yes.setName("Yes");
		yes.addActionListener(Frame.getFrame());
		yes.addActionListener(this);
		
		Button no = new Button("No");
		no.setName("No");
		no.addActionListener(Frame.getFrame());
		no.addActionListener(this);
		
		Button[] buttons = {yes, no}; //this array will be used in the constructor of the JOptionPane
		
		JLabel jl = new JLabel("削除しますか？"); //message displayed on the JOptionPane
		Myfont.setMyfont(jl);
		
		UIManager.put("OptionPane.background", new Color(253,253,253));
		UIManager.put("Panel.background", new Color(253,253,253));
		
		JOptionPane.showOptionDialog(null, jl, "スコア履歴を削除", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[1]);
		
	}
	
	/*
	 * The string playername frop the textfield is set in the Frame class
	 */
	public void setPlayerName(){
		
		if(Frame.getPlayerName() == "default name")
			Frame.setPlayerName("匿名");
		else
			Frame.setPlayerName(nameField.getText());
		
	}
	
	public String getPlayerName(){
		return nameField.getText();
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
	
	/*
	 * This method is used to notify that a button (yes or no) has been chosen in the JOptionPane
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		JOptionPane.getRootFrame().dispose();
	}
}
