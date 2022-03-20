//COMMENTS IN UPPERCASE WERE ADDED TO FURTHER DESCRIBE
//CODE FUNCTIONS AND EXPECTED OUTPUTS
//Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/*INITIATE SIMPLEAUDIOPLAYER CLASS
DO NOT FORGET YOUR ADDITIONAL FILE
WITH YOUR CLASS NAME ENDING WITH ".class" */
public class SimpleAudioPlayer
{

	// To store current position
	//Public class LONG extends number length
	Long currentFrame;
	//Public class CLIP allows to loop the audio file. 
	Clip clip;
	
	// current status of clip
	String status;
	
	AudioInputStream audioInputStream;
	static String filePath;

	// constructor to initialize streams and clip
	public SimpleAudioPlayer()
		throws UnsupportedAudioFileException,
		IOException, LineUnavailableException
	{
		// create AudioInputStream object
		audioInputStream =
				AudioSystem.getAudioInputStream(new File("3.Audio_Player/test.wav").getAbsoluteFile());
		
		// create clip reference
		clip = AudioSystem.getClip();
		
		// open audioInputStream to the clip
		clip.open(audioInputStream);
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void main(String[] args)
	{
		try
		{
			filePath = "3.Audio_Player/test.wav";
			SimpleAudioPlayer audioPlayer =
							new SimpleAudioPlayer();
			
			audioPlayer.play();
			Scanner sc = new Scanner(System.in);
			
			while (true)
			{
				System.out.println("1. pause");
				System.out.println("2. resume");
				System.out.println("3. restart");
				System.out.println("4. stop");
				System.out.println("5. Jump to specific time");
				int c = sc.nextInt();
				audioPlayer.gotoChoice(c);
				if (c == 4)
				break;
			}
			sc.close();
		}
		
		catch (Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		
		}
	}
	
	// Work as the user enters his choice
	/* SIMILAR TO C LANGUAGE AND C++, WE CAN OBSERVE THE USAGE OF 
	SWITCH CASES FOR THE MENU.
	
	IN THE MENU YOU CAN PICK:
	
	1. PAUSE
	2. RESUME 
	3. RESTART
	4. STOP (WHICH BREAKS THE PROGRAM)

	CASE 5 WILL PRINT OUT THE FOLLOWING DATA
	FROM PLAYED AUDIO FILE:
		- LENGTH OF WAV FILE IN MICROSECONDS

	*/
	private void gotoChoice(int c)
			throws IOException, LineUnavailableException, UnsupportedAudioFileException
	{
		switch (c)
		{
			case 1:
				pause();
				break;
			case 2:
				resumeAudio();
				break;
			case 3:
				restart();
				break;
			case 4:
				stop();
				break;
			case 5:
				System.out.println("Enter time (" + 0 +
				", " + clip.getMicrosecondLength() + ")");
				Scanner sc = new Scanner(System.in);
				long c1 = sc.nextLong();
				jump(c1);
				break;
	
		}
	
	}

	/* EACH SWITCH CASE COMES FROM A DEFINED METHOD,
	
	BELOW YOU CAN OBSERVE HOW THEY ARE PUBLIC VOID METHODS
	
	YOU CAN ALSO OBSERVE EACH STATEMENT ENDING WITH A SEMI-COLON
	
	AS MENTIONED IN THE CONCEPTS README FILE.
	
	*/
	
	// Method to play the audio
	public void play()
	{
		//start the clip
		clip.start();
		
		status = "play";
	}
	
	// Method to pause the audio
	public void pause()
	{
		if (status.equals("paused"))
		{
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame =
		this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}
	
	// Method to resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException,
								IOException, LineUnavailableException
	{
		if (status.equals("play"))
		{
			System.out.println("Audio is already "+
			"being played");
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}
	
	// Method to restart the audio
	public void restart() throws IOException, LineUnavailableException,
											UnsupportedAudioFileException
	{
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}
	
	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException,
	IOException, LineUnavailableException
	{
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}
	//THIS METHOD IS LIKE YOUR FASTFORWARD OR REVERSE 
	//OPTION IN AN AUDIO PLAYER:
	//OR SIMILAR TO THE SLIDER FOR YOUR AUDIO-
	//WHICH ALLOWS YOU TO JUMP TO DIFFERENT 
	//PARTS OF YOUR AUDIO FILE.
	public void jump(long c) throws UnsupportedAudioFileException, IOException,
														LineUnavailableException
	{
		if (c > 0 && c < clip.getMicrosecondLength())
		{
			clip.stop();
			clip.close();
			resetAudioStream();
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}
	
	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
											LineUnavailableException
	{
		audioInputStream = AudioSystem.getAudioInputStream(
		new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}

