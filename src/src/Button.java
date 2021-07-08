package src;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

/*
 * Custom button class to manages their design
 * The MouseListener interface is used to change the design for mouseover
 */
@SuppressWarnings("serial")
public class Button extends JButton implements MouseListener{
	
	public Button(String textbutton){
		
		super(textbutton);
		
		this.setBackground(Color.white);
		
		Myfont.setMyfont(this);// custom font for the text on the button
		
		this.addMouseListener(this);
		this.setFocusable(false);
	}
	
	
	public void mouseClicked(MouseEvent event){ }


	public void mouseEntered(MouseEvent event){ 
		  
		  this.setBackground(Color.gray);
		  this.setForeground(Color.white);
		  this.setBorderPainted(false);
	  }


	 public void mouseExited(MouseEvent event){
		 
		  this.setBackground(Color.white);
		  this.setForeground(Color.black);
		  this.setBorderPainted(true);
	  }

	 public void mousePressed(MouseEvent event){ }

	 public void mouseReleased(MouseEvent event){ }       

	

}
