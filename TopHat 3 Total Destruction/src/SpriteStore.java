import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteStore {
	/** The single instance of this class */
	private static SpriteStore single = new SpriteStore();
	
	/**
	 * Get the single instance of this class 
	 * 
	 * @return The single instance of this class
	 */
	public static SpriteStore get() {
		return single;
	}
	
	/** The cached sprite map, from reference to sprite instance */
	private static HashMap<String, SpriteFrame> sprites = new HashMap<String, SpriteFrame>();
	
	/**
	 * Retrieve a sprite from the store
	 * 
	 * @param ref The reference to the image to use for the sprite
	 * @return A sprite instance containing an accelerate image of the request reference
	 */
	public static SpriteFrame getSprite(String ref) {
		// if we've already got the sprite in the cache
		// then just return the existing version
		if (sprites.get(ref) != null) {
			return (SpriteFrame) sprites.get(ref);
		}
		
		// otherwise, go away and grab the sprite from the resource
		// loader
		BufferedImage sourceImage = null;
		
		try {
			// The ClassLoader.getResource() ensures we get the sprite
			// from the appropriate place, this helps with deploying the game
			// with things like webstart. You could equally do a file look
			// up here.
			File file = new File(ref);
			
			// use ImageIO to read the image in
			sourceImage = ImageIO.read(file);
		} catch (IOException e) {
			fail("Failed to load: " + ref);
		}
		
		// create an accelerated image of the right size to store our sprite in
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
		
		// draw our source image into the accelerated image
		image.getGraphics().drawImage(sourceImage, 0, 0, null);
		
		// create a sprite, add it the cache then return it
		SpriteFrame sprite = new SpriteFrame(image);
		sprites.put(ref, sprite);
		
		return sprite;
	}
	
	public static SpriteFrame getFrame(String ref, int width, int height, int x, int y) {
		//retrieve a single frame of a sprite from a Sprite in the hashmap 
		//if the sprite is not already in the hashmap, call getSprite(ref) to load the
		// image, then use the given parameters to return a single frame
		BufferedImage sourceImage = (BufferedImage) getSprite(ref).getImage();
		
		// create an accelerated image of the right size to store our sprite in
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(width, height, Transparency.BITMASK);
		
		// draw our source image into the accelerated image using Image.getGraphics().drawImage() into the specified image
		image.getGraphics().drawImage(sourceImage, 0, 0, width, height, x, y, x+width, y+height, null);
		// create the sprite and return it
		SpriteFrame sprite = new SpriteFrame(image);
		
		return sprite;
	}
	
	/**
	 * Utility method to handle resource loading failure
	 * 
	 * @param message The message to display on failure
	 */
	private static void fail(String message) {
		// we'n't available
		// we dump the message and exit the game
		System.err.println(message);
		System.exit(0);
	}
}
