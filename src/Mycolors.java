import java.awt.Color;

/*
 * This enum is used to define custom and use them only by their name 
 * and not the RGB code every time
 */
public enum Mycolors {

	blue (new Color(108,166,233)),
	green (new Color(147,247,204)), 
	red (new Color(247,147,147)),
	orange (new Color(255,168,110)),
	purple (new Color(225,171,255)),
	yellow (new Color(255,253,171)),
	greyblue (new Color(239,239,239));
	
	private final Color color;
	
	Mycolors(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
	
}
