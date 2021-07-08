package src;

import java.awt.EventQueue;

/*
 * This game is some kind of shooter game. The principle is simple : 
 * a craft shoot on aliens and must survive the longest time possible.
 * A tutorial was followed from this website :
 * http://zetcode.com/tutorials/javagamestutorial/
 * This tutorial gave come core functions : keyboard command, animations and collisions.
 * Many other features were then implemented. 
 */
public class Main{
	

	public static void main(String[] args){
		
		EventQueue.invokeLater(new Runnable(){
			
			@Override
			public void run(){
				
				Frame ex = new Frame();
				ex.setVisible(true);
				
			}
			
		});
		
		
	}
	
}