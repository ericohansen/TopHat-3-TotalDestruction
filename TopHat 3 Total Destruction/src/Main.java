import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Main extends Canvas{

	//menu options
	private final static int IN_GAME = 1;
	private final static int MAIN_MENU = 0;
	private final static int END_GAME = 2;
	private final static int UPGRADE_MENU = 3;	
	private int gameState = MAIN_MENU;
	
	//resolution
	static int resY, resX, width, height;

	//top hat animations
	private int hats = 30;
	private Vector<TopHatShape> topHats = new Vector<TopHatShape>(hats);
	
	//player object
	Player player = new Player(20, 20);
	
	//timer vars
	public long lastLoopTime;
	public long delta;
	
	//ingame display
	private int xDisp = 50, yDisp = 50, adjustment = 25;
	
	private BufferStrategy strategy; //for double buffering
	static Scanner in = new Scanner(System.in);
	
	public Main() { //Constructor to create the window
		JFrame frame = new JFrame("Top Hat 3: Total Destruction");
		
		// Get the current screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();
		resY = scrnsize.height;
		resX = scrnsize.width;
		width = resX-2*(resX/12);
		height = resY-2*(resY/12);
		
		//sets contentPanel, also sets opening size
		JPanel contentPanel = (JPanel) frame.getContentPane();
		contentPanel.setPreferredSize(new Dimension(width, height));
		contentPanel.setLayout(null);
		
		//sets the bounds of game screen
		setBounds(0, 0, width, height);
		contentPanel.add(this);
		setIgnoreRepaint(true);
		
		//sets default frame position
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocation(resX/12, resY/12);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addKeyListener(new MultiKeyPressListener());
		
		createBufferStrategy(2); //use 2 buffers for double buffering
		strategy = getBufferStrategy();

		gameState = MAIN_MENU;
	}

	
	public static void main(String[] args) {
		Main topHat = new Main();
		topHat.mainGameLoop();
	}//end main
	
	void mainGameLoop() {
		while(hats > 0){
			Random rand = new Random();
			topHats.add(new TopHatShape(rand.nextInt(resX), rand.nextInt(resY), width, height));
			hats--;
		}
		hats = 30;
		
		lastLoopTime = System.currentTimeMillis();
		while (true) {
			delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			switch(gameState) {
				case MAIN_MENU:
					mainMenu();
					break;
				case IN_GAME:
					//go();
					break;
				case END_GAME:
					//endGame();
					break;
				case UPGRADE_MENU:
					//upgrade();
					break;
				default:
					break;
			}//end switch
		}//end while
	}//end mainGameLoop

	private void mainMenu() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, resX, resY);
		g.setColor(Color.GREEN);
		changeFont("Dashley.ttf", g, 100);
		g.drawString("Top Hat 3 Total Destruction", resX/6, resY/4);
		changeFont("Dashley.ttf", g, 40);
		g.drawString("New Game  1", xDisp, yDisp + adjustment*3);
		g.drawString("Exit  2", xDisp, yDisp + adjustment*6);
		
		for(int j = 0; j < topHats.size(); j++){
			topHats.get(j).move(delta);
		}
		for(int a = 0; a < topHats.size(); a++){
			topHats.get(a).draw(g);
		}
		player.draw(g);

		g.dispose();
		strategy.show();
	}//end mainMenu

	
	 private void changeFont(String filename,Graphics2D g,int txtsize) 
	 {
		
		 float size = new Float(txtsize);
		 try{
			 
			 Font font = Font.createFont(Font.TRUETYPE_FONT,new File(filename)).deriveFont(size);
			 g.setFont(font);
			 
		 } catch (IOException e) {
			 
			 System.out.print(e);
		 }catch (FontFormatException e){
			 System.out.print(e);
		 }
		 
	 }
	 
	private class MouseListener extends MouseAdapter{
		//public void 
	}
	 
	private class MultiKeyPressListener extends KeyAdapter {
		
	    private final Set<Character> pressed = new HashSet<Character>();
		
	    @Override
		public synchronized void keyPressed(KeyEvent e) {		
			pressed.add(e.getKeyChar());
			if(pressed.size() > 1){
				
				System.out.println(pressed);
				//LEFT UP PRESSED
				if (e.getKeyCode() == KeyEvent.VK_LEFT && e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_A && e.getKeyCode() == KeyEvent.VK_W) {
					player.direction = "NW";
				}
				//UP RIGHT PRESSED
				if (e.getKeyCode() == KeyEvent.VK_UP && e.getKeyCode() == KeyEvent.VK_RIGHT
						|| e.getKeyCode() == KeyEvent.VK_W && e.getKeyCode() == KeyEvent.VK_D) {
					player.direction = "NE";

				}
				//RIGHT DOWN PRESSED
				if (e.getKeyCode() == KeyEvent.VK_RIGHT && e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_S && e.getKeyCode() == KeyEvent.VK_D) {
					player.direction = "SE";
				}
				//DOWN LEFT PRESSED
				if (e.getKeyCode() == KeyEvent.VK_LEFT && e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_S && e.getKeyCode() == KeyEvent.VK_A) {
					player.direction = "SW";
				}
			}else{
				//LEFT PRESSED
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					player.direction = "W";
				}
				//DOWN PRESSED
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
					player.direction = "S";
				}
				//RIGHT PRESSED
				if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					player.direction = "E";
				}
				//UP PRESSED
				if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
					player.direction = "N";
				}
			}
			player.move();
		}
		
	    @Override
	    public synchronized void keyReleased(KeyEvent e) {
	        pressed.remove(e.getKeyChar());
	        
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
				System.exit(5);
			}//end if
	    }
	}
}

