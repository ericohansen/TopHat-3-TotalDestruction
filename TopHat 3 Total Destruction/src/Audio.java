
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public final class Audio {

	public static final int SAMPLE_RATE = 44100;

    private static final int BYTES_PER_SAMPLE = 2;                // 16-bit audio
    private static final int BITS_PER_SAMPLE = 16;                // 16-bit audio
    private static final int SAMPLE_BUFFER_SIZE = 4096;
    
    private static SourceDataLine line;   // to play the sound
    
    private Audio() { }
    
    static { init(); }
    
    private static void init() {
    	try {
	        // 44,100 samples per second, 16-bit audio, mono, signed PCM, little Endian
	        AudioFormat format = new AudioFormat((float) SAMPLE_RATE, BITS_PER_SAMPLE, 1, true, false);
	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
	
	        line = (SourceDataLine) AudioSystem.getLine(info);
	        line.open(format, SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE);
	        
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        System.exit(1);
	    }
	
	    // no sound gets made before this call
	    line.start();
    }
    
    public static void close() {
        line.drain();
        line.stop();
    }
    
    public static void play(String filename) {
        URL url = null;
        try {
            File file = new File(filename);
            if (file.canRead()) url = file.toURI().toURL();
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
    	if (url == null) throw new RuntimeException("audio " + filename + " not found");
	    AudioClip clip = Applet.newAudioClip(url);
	    clip.play();
    }
    
    public static void loop(String filename) {
        URL url = null;
        try {
            File file = new File(filename);
            if (file.canRead()) url = file.toURI().toURL();
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
        if (url == null) throw new RuntimeException("audio " + filename + " not found");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
    }
    
}
