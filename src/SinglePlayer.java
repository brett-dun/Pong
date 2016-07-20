import java.awt.*;
import java.applet.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;

public class SinglePlayer extends Applet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final short FPS = 25;
	private static final short DELAY = 1000 / FPS;
	private static final short[] VELOCITY_CONST = {125,250,375,500,1000};
	private static final double MAX_VELOCITY = (double) VELOCITY_CONST[3] / FPS;
	private static final Color JPINK = new Color(255,0,128);
	
	private Image buffer;
	private Graphics2D g2d;
	private Ball ball;
	private Deflector leftDeflector;
	private Deflector rightDeflector;
	private short leftPlayerScore;
	//private short rightPlayerScore;
	private long rallyLength;
	
	
	public void init() {

		setSize(
				(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		setBackground(Color.black);
		setVisible(true);
		
		buffer = createImage(getSize().width, getSize().height);
		g2d = (Graphics2D) buffer.getGraphics();
		ball = new Ball(getWidth()/2.0, getHeight()/2.0, MAX_VELOCITY, MAX_VELOCITY, 20, JPINK);
		leftDeflector = new Deflector(ball.getSize()*2, getHeight()/2-50, ball.getSize(), 100);
		rightDeflector = new Deflector(getWidth()-(ball.getSize()*3), 0, ball.getSize(), getHeight());
		leftPlayerScore = 0;
		//rightPlayerScore = 0;
		rallyLength = 0;
		
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
		    	if(temp == 'w' || temp == 's' || temp == 'o' || temp == 'l') {
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
		
		if(leftPlayerScore >= 1) {
			//this.stop();
			//this.destroy();
			return;
		}
		
		
		buffer = createImage(getSize().width, getSize().height);
		g2d = (Graphics2D) buffer.getGraphics();
			
		ball.updateY();
		ball.updateX();

		if(ball.getY()>=getHeight()-ball.getSize() || ball.getY()<=0) {
			ball.setYVelocity(-ball.getYVelocity());
		}
		
		if(ball.getX() <= ball.getSize()) {
			leftPlayerScore++;
			//System.out.println(leftPlayerScore);
			reset();
		}
		
		if(ball.getX() >= getSize().getWidth()-(2*ball.getSize())) {
			reset();
		}

		
		if(ball.getX() <= (leftDeflector.getX()+leftDeflector.getWidth()) && (ball.getX() >= leftDeflector.getX())) {
			if(ball.getY() >= (leftDeflector.getY()-ball.getSize()) && ball.getY() <= (leftDeflector.getY()+leftDeflector.getHeight())) {
				ball.setXVelocity(Math.abs(ball.getXVelocity()));
				rallyLength++;
			}
		}
		
		if(ball.getX() <= (rightDeflector.getX()+rightDeflector.getWidth()) && (ball.getX() >= rightDeflector.getX()-ball.getSize())) {
			if(ball.getY() >= (rightDeflector.getY()-ball.getSize()) && ball.getY() <= (rightDeflector.getY()+rightDeflector.getHeight())) {
				ball.setXVelocity(-Math.abs(ball.getXVelocity()));
				rallyLength++;
			}
		}
		
		leftDeflector.checkBounds(getWidth(), getHeight());
		rightDeflector.checkBounds(getWidth(), getHeight());
			
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
		
		drawRally();

		g.drawImage(buffer, 0, 0, null);

	}
	
	
    public void move(char c) {
    	switch(c) {
    		case 'w': leftDeflector.moveUp(leftDeflector.getHeight()/2); return;
    		case 's': leftDeflector.moveDown(leftDeflector.getHeight()/2); return;
    		//case 'o': rightDeflector.moveUp(rightDeflector.getHeight()/2); return;
    		//case 'l': rightDeflector.moveDown(rightDeflector.getHeight()/2, (int) getHeight()); return;
    	}
    	leftDeflector.checkBounds(getWidth(), getHeight());
    	rightDeflector.checkBounds(getWidth(), getHeight());
    }
    
    
    public void reset() {
    	ball = new Ball(getWidth()/2.0, getHeight()/2.0, MAX_VELOCITY, MAX_VELOCITY, 20, JPINK);
    }
    
    public void drawRally() {
    	String temp = "Rally Length: " + rallyLength; 
		
	    FontMetrics metrics = g2d.getFontMetrics(getFont());
	    
	    int x = (int) ((getWidth() - metrics.stringWidth(temp)) / 2.0);
	    int y = 50;
	    
	    g2d.setPaint(JPINK);
	    g2d.drawString(temp, x, (int) (y-(metrics.getAscent())));
    }
    
}



