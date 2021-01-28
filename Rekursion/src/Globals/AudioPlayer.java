package Globals;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {
	
	private Clip clip;
	
	public AudioPlayer(String s) {
		
		try {
			File file = new File(s);
			
					AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels()*2,
					baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = 
					AudioSystem.getAudioInputStream(
							decodeFormat,ais
					);
			clip = AudioSystem.getClip();
			clip.open(dais);
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f); 
			
			
		}
		
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public void play () {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
		
	}
	
	private void stop () {
		if (clip.isRunning()) clip.stop();
	}
	
	public void close () {
		stop();
		clip.close();
	}
}