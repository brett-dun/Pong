import java.awt.*;
import java.applet.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.Timer;

public class FourPlayer extends Applet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final short FPS = 25;
	private static final short DELAY = 1000 / FPS;
	private static final short[] VELOCITY_CONST = {125,250,375,500,1000};
	private static final double MAX_VELOCITY = (double) VELOCITY_CONST[0] / FPS;
	private static final Color JPINK = new Color(255,0,128);
	
	private Image buffer;
	private Graphics2D g2d;
	private Ball ball;
	private Deflector leftDeflector;
	private Deflector rightDeflector;
	private Deflector topDeflector;
	private Deflector bottomDeflector;
	private short leftPlayerScore;
	private short rightPlayerScore;
	private short topPlayerScore;
	private short bottomPlayerScore;
	
	public void init() {

		//this.setLocation(100, 100);
		setSize(
				(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(),
				(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		setBackground(Color.black);
		setVisible(true);
		
		buffer = createImage(getHeight(), getHeight());
		g2d = (Graphics2D) buffer.getGraphics();
		ball = new Ball(getWidth()/2.0-10, getHeight()/2.0-10, -MAX_VELOCITY, -MAX_VELOCITY, 20, JPINK);
		leftDeflector = new Deflector(ball.getSize()*2, getHeight()/2-50, ball.getSize(), 100);
		rightDeflector = new Deflector(getWidth()-(ball.getSize()*3), getHeight()/2-50, ball.getSize(), 100);
		topDeflector = new Deflector(getWidth()/2-50, ball.getSize()*2, 100, ball.getSize());
		bottomDeflector = new Deflector(getWidth()/2-50, getHeight()-(ball.getSize()*3), 100, ball.getSize());
		leftPlayerScore = 0;
		rightPlayerScore = 0;
		topPlayerScore = 0;
		bottomPlayerScore = 0;
		
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		};
		
		this.addKeyListener(new KeyListener() {  
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
		    public void keyPressed(KeyEvent e) {  
				char temp = e.getKeyChar();
		    	if(temp == 'q' || temp == 'a' || temp == '\\' || temp == '\n' || temp == 'x' || temp == 'c' || temp == ',' || temp == '.') {
		    		move(temp);
		    	}
		    }
		});

		new Timer(DELAY, taskPerformer).start();
	}

	public Color randomColor() {
		return new Color(
				(int) (Math.random()*256),
				(int) (Math.random()*256),
				(int) (Math.random()*256)
				);
	}
	

	public void update(Graphics g) { 
		paint(g); 
	}

	public void paint(Graphics g) {
		
		if(leftPlayerScore >= 10 || rightPlayerScore >= 10 || topPlayerScore >= 10 || bottomPlayerScore >= 10) {
			//this.stop();
			//this.destroy();
			return;
		}
		
		if(ball == null) {
			reset();
		}
		
		
		buffer = createImage(getSize().width, getSize().height);
		g2d = (Graphics2D) buffer.getGraphics();
			
		ball.updateY();
		ball.updateX();
		
		
		if(ball.getX() <= 0) {
			rightPlayerScore++;
			topPlayerScore++;
			bottomPlayerScore++;
			//System.out.println("L: " + leftPlayerScore);
			reset();
		}
		
		if(ball.getX() >= getWidth()) {
			leftPlayerScore++;
			topPlayerScore++;
			bottomPlayerScore++;
			//System.out.println("R: " + rightPlayerScore);
			reset();
		}
		
		if(ball.getY() <= 0) {
			leftPlayerScore++;
			rightPlayerScore++;
			bottomPlayerScore++;
			reset();
		}
		
		if(ball.getY() >= getHeight()) {
			leftPlayerScore++;
			rightPlayerScore++;
			topPlayerScore++;
			reset();
		}

		if(ball.getX() <= (leftDeflector.getX()+leftDeflector.getWidth()) && (ball.getX() >= leftDeflector.getX())) {
			if(ball.getY() >= (leftDeflector.getY()-ball.getSize()) && ball.getY() <= (leftDeflector.getY()+leftDeflector.getHeight()+ball.getSize())) {
				ball.setXVelocity(Math.abs(ball.getXVelocity()));
			}
		}
		
		if(ball.getX() <= (rightDeflector.getX()+rightDeflector.getWidth()) && (ball.getX() >= rightDeflector.getX())) {
			if(ball.getY() >= (rightDeflector.getY()-ball.getSize()) && ball.getY() <= (rightDeflector.getY()+rightDeflector.getHeight()+ball.getSize())) {
				ball.setXVelocity(-Math.abs(ball.getXVelocity()));
			}
		} 
		
		if(ball.getY() >= topDeflector.getY() && ball.getY() <= (topDeflector.getY()+topDeflector.getHeight())) {
			if(ball.getX() <= (topDeflector.getX()+topDeflector.getWidth()) && ball.getX() >= (topDeflector.getX()-ball.getSize())) {
				ball.setYVelocity(Math.abs(ball.getYVelocity()));
			}
		}
		
		if(ball.getY() >= (bottomDeflector.getY()-ball.getSize()) && ball.getY() <= (bottomDeflector.getY()+bottomDeflector.getHeight())) {
			if(ball.getX() <= (bottomDeflector.getX()+bottomDeflector.getWidth()+ball.getSize()) && (ball.getX() >= bottomDeflector.getX())) {
				ball.setYVelocity(-Math.abs(ball.getYVelocity()));
			}
		}
		
		leftDeflector.checkBounds(getWidth(), getHeight());
		rightDeflector.checkBounds(getWidth(), getHeight());
		topDeflector.checkBounds(getWidth(), getHeight());
		bottomDeflector.checkBounds(getWidth(), getHeight());
			
		g2d.setPaint(ball.getMyColor());
		g2d.fill(
				new Ellipse2D.Double(
						ball.getX(),
						ball.getY(),
						ball.getSize(),
						ball.getSize()
						)
				);
		
		g2d.setPaint(leftDeflector.getColor());
		g2d.fill(
				new Rectangle2D.Double(
						leftDeflector.getX(),
						leftDeflector.getY(),
						leftDeflector.getWidth(),
						leftDeflector.getHeight()
						)
				);
		
		g2d.setPaint(rightDeflector.getColor());
		g2d.fill(
				new Rectangle2D.Double(
						rightDeflector.getX(),
						rightDeflector.getY(),
						rightDeflector.getWidth(),
						rightDeflector.getHeight()
						)
				);
		
		g2d.setPaint(topDeflector.getColor());
		g2d.fill(
				new Rectangle2D.Double(
						topDeflector.getX(),
						topDeflector.getY(),
						topDeflector.getWidth(),
						topDeflector.getHeight()
						)
				);
		
		g2d.setPaint(bottomDeflector.getColor());
		g2d.fill(
				new Rectangle2D.Double(
						bottomDeflector.getX(),
						bottomDeflector.getY(),
						bottomDeflector.getWidth(),
						bottomDeflector.getHeight()
						)
				);
		
		drawScore();
		
		if(leftPlayerScore >= 10 || rightPlayerScore >= 10 || topPlayerScore >= 10 || bottomPlayerScore >= 10) {
			drawWinner();
		}
		
		g.drawImage(buffer, 0, 0, null);

	}
	
	
    public void move(char c) {
    	switch(c) {
    		case 'q': leftDeflector.moveUp(leftDeflector.getHeight()/2); return;
    		case 'a': leftDeflector.moveDown(leftDeflector.getHeight()/2); return;
    		
    		case '\\': rightDeflector.moveUp(rightDeflector.getHeight()/2); return;
    		case '\n': rightDeflector.moveDown(rightDeflector.getHeight()/2); return;
    		
    		case 'x': topDeflector.moveLeft(topDeflector.getWidth()/2); return;
    		case 'c': topDeflector.moveRight(topDeflector.getWidth()/2); return;
    		
    		case ',': bottomDeflector.moveLeft(bottomDeflector.getWidth()/2); return;
    		case '.': bottomDeflector.moveRight(bottomDeflector.getWidth()/2); return;
    	}
    	leftDeflector.checkBounds(getWidth(), getHeight());
    	rightDeflector.checkBounds(getWidth(), getHeight());
    	topDeflector.checkBounds(getWidth(), getHeight());
    	bottomDeflector.checkBounds(getWidth(), getHeight());
    }
    
    
    public void reset() {
    	ball = new Ball(getWidth()/2.0, getHeight()/2.0, -MAX_VELOCITY, -MAX_VELOCITY, 20, JPINK);
    }
    
    
    public void drawScore() {
    	
    	String temp = "Player 1: " + leftPlayerScore + "     " + "     " + "     " + "Player 2: " + rightPlayerScore + "     " + "     " + "     " + "Player 3: " + topPlayerScore + "     " + "     " + "     " + "Player 4: " + bottomPlayerScore; 
		
	    FontMetrics metrics = g2d.getFontMetrics(getFont());
	    
	    int x = (int) ((getWidth() - metrics.stringWidth(temp)) / 2.0);
	    int y = 50;
	    
	    g2d.setPaint(JPINK);
	    g2d.drawString(temp, x, (int) (y-(metrics.getAscent())));
	    
	}
    
    public void drawWinner() {
    	
    	int[] array = {leftPlayerScore, rightPlayerScore, topPlayerScore, bottomPlayerScore};
    	
    	String temp = new String();
    	
    	int max = leftPlayerScore;
    	ArrayList<Integer> loc = new ArrayList<Integer>();
    	loc.add(0);
    	for(int i = 1; i < array.length; i++) {
    		if(array[i] == max) {
    			loc.add(i);
    		} else if(array[i] > max) {
    			loc.clear();
    			loc.add(i);
    			max = array[i];
    		}
    	}
    	
    	for(int i: loc) {
    		switch(i) {
    		case 0: temp += "     Player 1 Wins!!!     "; break;
    		case 1: temp += "     Player 2 Wins!!!     "; break;
    		case 2: temp += "     Player 3 Wins!!!     "; break;
    		case 3: temp += "     Player 4 Wins!!!     "; break;
    		}
    	}
    	
	    FontMetrics metrics = g2d.getFontMetrics(getFont());
	    
	    int x = (int) ((getWidth() - metrics.stringWidth(temp)) / 2.0);
	    int y = (int) ((getHeight() - metrics.getAscent()) / 2.0);
	    
	    g2d.setPaint(JPINK);
	    g2d.drawString(temp, x, (int) (y-(metrics.getAscent())));
    }
    
    
}