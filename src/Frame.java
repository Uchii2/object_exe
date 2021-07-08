import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * The main frame contains all the JPanel used to display the game.
 * In order to show the panels, a CardLayout is used to switch from a view to another.
 */
@SuppressWarnings("serial")
public class Frame extends JFrame{
	
	private CardLayout cl;
	private JPanel content;
	
	private Board board;
	private Menu menu;
	private GameOver gameover;
	
	public Frame(){
				
		setTitle("ColorGame");
		setSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(true);
		
		this.setIconImage((new ImageIcon(getClass().getResource("/logo2.png"))).getImage());
		
		cl = new CardLayout();
		content = new JPanel();
		content.setLayout(cl);
		
		board = new Board();
		menu = new Menu();
		
		content.add(menu, "Menu");
		content.add(board, "Board");
		this.add(content);
		
	}
	
	public void newGame(){
		
		content.add(board, "Board");
		cl.show(content, "Board");
		
	}
	
	public void gameOver(){
		
		gameover = new GameOver();
		content.add(gameover, "GameOver");
		cl.show(content, "GameOver");
		
	}
	
	public void menu(){
		
		cl.show(content, "Menu");
		
	}

}
