import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Entity {

	int height, width;
	int x, y;
	int numFrames = 12;
	SpriteFrame[] frames = new SpriteFrame[numFrames];
	int frameNumber = 0;
	
	public void move(long delta) {
		
	}

	public void draw(Graphics2D g) {
		frames[frameNumber].draw(g, x, y, width, height);
	}
	
	public boolean collidesWith(Entity entity) {
		boolean collision = false;
		
		Ellipse2D.Double me = new Ellipse2D.Double(x, y, height, width);
		if (me.intersects(entity.x, entity.y, entity.height, entity.width)) {
			collision = true;
		}
		
		return collision;
	}
}
