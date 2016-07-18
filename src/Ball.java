import java.awt.Color;


public class Ball {
	
	
	private double x;
	private double y;
	private double xVelocity;
	private double yVelocity;
	//private double angle;
	private int size;
	private Color color;
	
	
	public Ball(double x, double y, double velocity, int size, Color color) {
		this(x,y, 0, 0, size, color);
		if(Math.random() > 0.5) {
			this.xVelocity = velocity;
		} else {
			this.xVelocity = -velocity;
		}
		if(Math.random() > 0.5) {
			this.yVelocity = velocity;
		} else {
			this.yVelocity = -velocity;
		}
	}

	public Ball(double x, double y, double xVelocity, double yVelocity, int size, Color color) {
		//super();
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.size = size;
		this.color = color;
	}
	
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getXVelocity() {
		return xVelocity;
	}
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	public double getYVelocity() {
		return yVelocity;
	}
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Color getMyColor() {
		return color;
	}
	public void setMyColor(Color color) {
		this.color = color;
	}
	public void updateX() {
		this.x += this.xVelocity;
	}
	public void updateY() {
		this.y += this.yVelocity;
	}
	/*public void findAngle() {
		angle = Math.atan((this.yVelocity/this.xVelocity));
	}
	public double getAngle() {
		return angle;
	}*/


	@Override
	public String toString() {
		return "Particle [x=" + x + ", x=" + y + ", xVelocity="
				+ xVelocity + ", yVelocity=" + yVelocity + ", size=" + size + ", color=" + color + "]";
	}
	
	
}

