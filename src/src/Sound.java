package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
 * The Sound class is used to play different kind of sounds during the game.
 * The first method uses a sound object and is used for the music in the background.
 * The second one, static, is called for ponctual sounds (for bonuses for example).
 * Tutorial was found on this page :
 * http://noobtuts.com/java/play-sounds
 * The name of the sound that must be played is given in parameter
 */
public class Sound {
	
	private Clip clip;
	
	public Sound(String name){
		
		try{
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource(name));
			clip.open(inputStream);
			clip.start();
		}catch(Exception e){
			System.out.println("play sound error" + e.getMessage());
		}
		
	}
	
	public void stop(){
		
		clip.stop();
		
	}
	
	public void loop(){
		
		clip.loop(10);
		
	}
	
	public static synchronized void play(final String name){
		
		new Thread(new Runnable(){
			
			public void run(){
				
				try{
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource(name));
					clip.open(inputStream);
					clip.start();
				}catch(Exception e){
					System.out.println("play sound error" + e.getMessage());
				}
				
			}
			
		}).start();
		
	}

}
