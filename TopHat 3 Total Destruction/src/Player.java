import java.awt.Color;
import java.awt.Graphics2D;


public class Player extends Entity{
	
	int moveRate = 5;
	String direction = "E";
	
	public Player(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Player(int w, int h){
		this.width = w;
		this.height = h;
	}
	
	public void move(){
		if(direction.equals("N")){
			y-= moveRate;
		}else if(direction.equals("NW")){
			y-= moveRate;
			x-= moveRate;
		}else if(direction.equals("W")){
			x-= moveRate;
		}else if(direction.equals("SW")){
			x-= moveRate;
			y+= moveRate;
		}else if(direction.equals("S")){
			y+= moveRate;
		}else if(direction.equals("SE")){
			y+= moveRate;
			x+= moveRate;
		}else if(direction.equals("E")){
			x+= moveRate;
		}else if(direction.equals("NE")){
			x+= moveRate;
			y-= moveRate;
		}
		//System.out.println("Moving " + direction);
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.GREEN);
		g.drawRect(x, y, width, height);
	}
	
}
