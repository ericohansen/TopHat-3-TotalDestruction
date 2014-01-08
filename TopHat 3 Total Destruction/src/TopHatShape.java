//Simple Entity class wrapper for shape types, for CS291 Assignment 2

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TopHatShape {
	
	private int xPos, yPos;
	private int height;
	private static BufferedImage topHat;
	
	public TopHatShape(int x, int y, int width, int height) {
		this.height = height;
		xPos = x;
		yPos = y;
	}//TopHatShape() constructor
	
	public void move(long delta) {
		//move the shape diagonally down a small amount each frame
		yPos += delta/8;
		//wrap around the screen
		if (yPos >= height +64) {
			yPos += -(height + 128);
		}
	}//move()

	public void draw(Graphics2D g){
		try{
			topHat = ImageIO.read(new File("Images/TopHat.png"));
		    g.drawImage(topHat,  xPos, yPos, null);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }//end catch
	}//draw()
}
