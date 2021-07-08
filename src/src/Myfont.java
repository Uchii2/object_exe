package src;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;

/*
 * This class is used to define a custom police either for a component (like buttons) or Graphics (for the paintComponent method)
 * In both case, there is two different function (the second one specifies the size of the font)
 * All the functions are static so they can be called directly
 * Some code was taken from this page :
 * http://www.java2s.com/Code/Java/2D-Graphics-GUI/Loadfontfromttffile.htm
 */
public class Myfont {

	
	public static void setMyfont(JComponent component){
		try{
			InputStream is = component.getClass().getResourceAsStream("/font.ttf");
			Font myfont = Font.createFont(Font.TRUETYPE_FONT, is);
			component.setFont(myfont.deriveFont(Font.PLAIN, 15));
		}
		catch(FontFormatException e){
			
		}
		catch(IOException e){
			
		}
	}
	
	public static void setFontSize(JComponent component, int size){
		
		try{
			InputStream is = component.getClass().getResourceAsStream("/font.ttf");
			Font myfont = Font.createFont(Font.TRUETYPE_FONT, is);
			component.setFont(myfont.deriveFont(Font.PLAIN, size));
		}
		catch(FontFormatException e){
			
		}
		catch(IOException e){
			
		};
		
	}
	
	public static void setGraphicFont(Graphics g){
		
		try{
			InputStream is = g.getClass().getResourceAsStream("/font.ttf");
			Font myfont = Font.createFont(Font.TRUETYPE_FONT, is);
			g.setFont(myfont.deriveFont(Font.PLAIN, 15));
		}
		catch(FontFormatException e){
			
		}
		catch(IOException e){
			
		};
		
	}
	
	public static void setGraphicFontSize(Graphics g, int size){
		
		try{
			InputStream is = g.getClass().getResourceAsStream("/font.ttf");
			Font myfont = Font.createFont(Font.TRUETYPE_FONT, is);
			g.setFont(myfont.deriveFont(Font.PLAIN, size));
		}
		catch(FontFormatException e){
			
		}
		catch(IOException e){
			
		};
		
	}
	
}
